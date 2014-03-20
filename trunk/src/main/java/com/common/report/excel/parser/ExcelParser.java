package com.common.report.excel.parser;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.annotation.ExcelClass;
import com.common.report.excel.annotation.ExcelField;
import com.common.report.excel.exception.UncheckedExcelException;
import com.common.report.excel.model.ExcelDto;
import com.common.report.excel.model.parser.ExcelFieldParser;
import com.common.report.excel.util.ExcelUtils;
import com.common.report.excel.util.ParseType;

/**
 * La clase que nos permite
 * 
 * @author gmazzali
 * 
 */
public class ExcelParser implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelParser.class);

	/**
	 * El método encargada de leer el archivo de excel y cargar un listado de las entidades que queremos recuperar de este.
	 * 
	 * @param workbook
	 *            El archivo de excel que queremos leer.
	 * @param clazz
	 *            La clase que tenemos anotada con los campos correspondiente al excel.
	 * @return El listado de los DTO que tenemos almacenados en el excel.
	 */
	public static <C extends ExcelDto> List<C> read(Workbook workbook, Class<C> clazz) {
		List<C> list = new ArrayList<C>();

		ExcelClass excelClass = clazz.getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + clazz.getSimpleName());
			throw new UncheckedExcelException("Invalid class configuration - ExcelClass annotation missing in: " + clazz.getSimpleName());
		}

		Sheet sheet = workbook.getSheet(excelClass.sheet());
		Boolean dataNotNull = true;

		for (long index = excelClass.start(); index <= excelClass.end() && dataNotNull; index++) {
			try {
				// Creamos una nueva instancia del DTO.
				C excelDto = clazz.newInstance();

				// Cargamos la instancia con los datos desde el excel.
				dataNotNull = ExcelParser.readDto(sheet, index, excelClass.parseType(), excelDto);

				// Solo si la entidad no es nula, la agregamos a la lista.
				if (dataNotNull) {
					list.add(excelDto);
				}
			} catch (Exception e) {
				log.error("Can't create a new instance of the DTO (missing default constructor?)", e);
				throw new UncheckedExcelException("Can't create a new instance of the DTO (missing default constructor?)");
			}
		}

		return list;
	}

	/**
	 * Permite leer una única entidad desde el archivo de excel dado un indice del mismo.
	 * 
	 * @param sheet
	 *            La hoja dl excel desde donde vamos a leer los datos para cargar el {@link ExcelDto}.
	 * @param index
	 *            El índice donde vamos a leer los datos para el {@link ExcelDto}.
	 * @param parseType
	 *            El tipo de lectura que vamos a realizar, ya sea, recorriendo filas o columnas para recuperar los atributos.
	 * @param excelDto
	 *            La instancia clase que extiende a {@link ExcelDto} y que vamos a cargar con los datos del excel.
	 * @return <i>true</i> en caso de que se haya cargado algún atributo desde el excel al {@link ExcelDto}, en caso de que se haya leido un recorrido
	 *         completamente <code>null</code> se retorna <i>false</i>.
	 * @throws UncheckedExcelException
	 *             En caso de que no se pueda crear una entidad del {@link ExcelDto} o del {@link ExcelFieldParser} o una falla al enlazar el campo
	 *             con el objeto leído.
	 */
	public static <C extends ExcelDto> Boolean readDto(Sheet sheet, long index, ParseType parseType, C excelDto) {
		Boolean dataNotNull = false;
		try {
			// Tomamos todos los campos y los cargamos con los datos desde las celdas.
			List<Field> fields = ExcelUtils.getMappedExcelField(excelDto.getClass());
			for (Field field : fields) {

				// Tomamos el campo de excel y el parseador.
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				ExcelFieldParser<?> parser = excelField.parser().newInstance();

				// Obtenemos la celda de acuerdo al tipo de recorrido que tenemos.
				Cell cell = null;
				if (parseType == ParseType.COLUMN) {
					cell = ExcelUtils.getCell(sheet, index, excelField.posicion());
				} else if (parseType == ParseType.ROW) {
					cell = ExcelUtils.getCell(sheet, excelField.posicion(), index);
				}

				// Si obtuvimos alguna celda en esa posición, la parseamos.
				Object value = parser.get(cell);
				field.set(excelDto, value);

				// El valor que indica si se leyó al menos un atributo desde el excel.
				dataNotNull |= value != null;
			}
		} catch (Exception e) {
			log.error("Fail to parser the excel data to the DTO", e);
			throw new UncheckedExcelException("Fail to parser the excel data to the DTO");
		}

		return dataNotNull;
	}

	public <T> List<T> createEntity(Sheet sheet, String sheetName, Class<T> clazz) throws ExcelParsingException {
		List<T> list = new ArrayList<T>();
		for (int currentLocation = excelObject.start(); currentLocation <= excelObject.end(); currentLocation++) {

			T object = getNewInstance(sheet, sheetName, clazz, excelObject.parseType(), currentLocation, excelObject.zeroIfNull());
			List<Field> mappedExcelFields = getMappedExcelObjects(clazz);

			for (Field mappedField : mappedExcelFields) {

				Class<?> fieldType = mappedField.getType();
				List<?> fieldValue = createEntity(sheet, sheetName, fieldType.equals(List.class) ? getFieldType(mappedField) : fieldType);
				if (fieldType.equals(List.class)) {
					setFieldValue(mappedField, object, fieldValue);
				} else if (!fieldValue.isEmpty()) {
					setFieldValue(mappedField, object, fieldValue.get(0));
				}
			}
			list.add(object);
		}
		return list;
	}

	private Class<?> getFieldType(Field field) {
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			return (Class<?>) pt.getActualTypeArguments()[0];
		}

		return null;
	}

	private <T> List<Field> getMappedExcelObjects(Class<T> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			MappedExcelObject mappedExcelObject = field.getAnnotation(MappedExcelObject.class);
			if (mappedExcelObject != null) {
				field.setAccessible(true);
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	private <T> ExcelObject getExcelObject(Class<T> clazz) throws ExcelParsingException {
		ExcelObject excelObject = clazz.getAnnotation(ExcelObject.class);
		if (excelObject == null) {
			throw new ExcelParsingException("Invalid class configuration - ExcelObject annotation missing - " + clazz.getSimpleName());
		}
		return excelObject;
	}

	private <T> T getNewInstance(Sheet sheet, String sheetName, Class<T> clazz, ParseType parseType, Integer currentLocation, boolean zeroIfNull)
			throws ExcelParsingException {
		T object = getInstance(clazz);
		Map<Integer, Field> excelPositionMap = getExcelFieldPositionMap(clazz);
		for (Integer position : excelPositionMap.keySet()) {
			Field field = excelPositionMap.get(position);
			Object cellValue;
			if (ParseType.ROW == parseType) {
				cellValue = hssfHelper.getCellValue(sheet, sheetName, field.getType(), currentLocation, position, zeroIfNull);
			} else {
				cellValue = hssfHelper.getCellValue(sheet, sheetName, field.getType(), position, currentLocation, zeroIfNull);
			}
			setFieldValue(field, object, cellValue);
		}

		return object;
	}

	private <T> T getInstance(Class<T> clazz) throws ExcelParsingException {
		T object = null;
		try {
			object = clazz.newInstance();
		} catch (Exception e) {
			LOGGER.error(e);
			throw new ExcelParsingException("Exception occured while instantiating the class " + clazz.getName(), e);
		}
		return object;
	}

	private <T> void setFieldValue(Field field, T object, Object cellValue) throws ExcelParsingException {
		try {
			field.set(object, cellValue);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ExcelParsingException("Exception occured while setting field value ", e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ExcelParsingException("Exception occured while setting field value ", e);
		}
	}

	private <T> Map<Integer, Field> getExcelFieldPositionMap(Class<T> clazz) {
		Map<Integer, Field> existingMap = excelMapCache.get(clazz.getName());
		return existingMap == null ? loadCache(clazz) : existingMap;
	}

	/**
	 * Load cached for the given class.
	 * 
	 * @param clazz
	 *            Class object to investigate.
	 * @return Map.
	 */
	private <T> Map<Integer, Field> loadCache(Class<T> clazz) {
		Map<Integer, Field> fieldMap = new HashMap<Integer, Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ExcelField excelField = field.getAnnotation(ExcelField.class);
			if (excelField != null) {
				field.setAccessible(true);
				fieldMap.put(excelField.position(), field);
			}
		}
		excelMapCache.put(clazz.getName(), fieldMap);
		return fieldMap;
	}
}