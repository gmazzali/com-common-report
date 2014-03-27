package com.common.report.excel.business.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.annotation.ExcelClass;
import com.common.report.excel.domain.annotation.ExcelField;
import com.common.report.excel.domain.exception.UncheckedExcelException;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;

public class ExcelUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelUtils.class);

	/**
	 * Permite recuperar el listado de campos que tenemos mapeados como campos de excel dentro de la clase recibida.
	 * 
	 * @param clazz
	 *            La clase a la que vamos a analizarle los campos.
	 * @return El listado de los campos que podemos usar con la libreria de anotaciones del excel.
	 */
	public static <T> List<Field> getMappedExcelField(Class<T> clazz) {
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			ExcelField excelField = field.getAnnotation(ExcelField.class);

			if (excelField != null) {
				field.setAccessible(true);
				fieldList.add(field);
				log.info("The field \"" + field.getName() + "\" is ExcelField");
			} else {
				log.warn("The field \"" + field.getName() + "\" isn't ExcelField");
			}
		}

		return fieldList;
	}

	/**
	 * Permite recuperar una celda dada una posición dentro de la hoja del excel.
	 * 
	 * @param sheet
	 *            La hoja sobre la que va a realizarse la búsqueda de la celda.
	 * @param rowIndex
	 *            El índice de la fila desde donde queremos recuperar la celda.
	 * @param columnIndex
	 *            El índice de la columna desde donde queremos recuperar la celda.
	 * @return La celda que corresponde con la posición recibida dentro de la hoja.
	 */
	public static Cell getCell(Sheet sheet, long rowIndex, long columnIndex) {
		Row row = sheet.getRow((int) rowIndex);
		if (row == null) {
			log.warn("The row is null");
			return null;
		} else {
			log.info("The row isn't null");
			return row.getCell((int) columnIndex);
		}
	}

	/**
	 * Permite crear una celda dada una posición dentro de la hoja del excel.
	 * 
	 * @param sheet
	 *            La hoja sobre la que va a crearse la celda.
	 * @param rowIndex
	 *            El índice de la fila desde donde queremos crear la celda.
	 * @param columnIndex
	 *            El índice de la columna desde donde queremos crear la celda.
	 * @return La celda que corresponde con la posición recibida dentro de la hoja.
	 */
	public static Cell createCell(Sheet sheet, long rowIndex, long columnIndex) {
		Row row = sheet.createRow((int) rowIndex);
		if (row == null) {
			log.warn("The row is null");
			return null;
		} else {
			log.info("The row isn't null");
			return row.createCell((int) columnIndex);
		}
	}

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

		// Obtenemos la clase del excel.
		ExcelClass excelClass = clazz.getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + clazz.getSimpleName());
			throw new UncheckedExcelException("Invalid class configuration - ExcelClass annotation missing in: " + clazz.getSimpleName());
		}

		Sheet sheet = workbook.getSheet(excelClass.sheet());
		Boolean dataNotNull = true;

		for (long index = excelClass.start(); index <= excelClass.end(); index++) {
			try {
				// Creamos una nueva instancia del DTO.
				C excelDto = clazz.newInstance();

				// Cargamos la instancia con los datos desde el excel.
				dataNotNull = ExcelUtils.readDto(sheet, index, excelDto);

				// Solo si la entidad no es nula, la agregamos a la lista.
				if (dataNotNull) {
					list.add(excelDto);
				} else {
					break;
				}
			} catch (UncheckedExcelException e) {
				throw e;
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
	 *            La hoja del excel desde donde vamos a leer los datos para cargar el {@link ExcelDto}.
	 * @param index
	 *            El índice donde vamos a leer los datos para el {@link ExcelDto}.
	 * @param excelDto
	 *            La instancia clase que extiende a {@link ExcelDto} y que vamos a cargar con los datos del excel.
	 * @return <i>true</i> en caso de que se haya cargado algún atributo desde el excel al {@link ExcelDto}, en caso de que se haya leido un recorrido
	 *         completamente <code>null</code> se retorna <i>false</i>.
	 * @throws UncheckedExcelException
	 *             En caso de que no se pueda crear una entidad del {@link ExcelDto} o del {@link ExcelFieldFormatter} o una falla al enlazar el campo
	 *             con el objeto leído.
	 */
	public static <C extends ExcelDto> Boolean readDto(Sheet sheet, long index, C excelDto) {
		// Obtenemos la clase del excel.
		ExcelClass excelClass = excelDto.getClass().getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + excelDto.getClass().getSimpleName());
			throw new UncheckedExcelException("Invalid class configuration - ExcelClass annotation missing in: "
					+ excelDto.getClass().getSimpleName());
		}

		Boolean dataNotNull = false;

		try {
			// Tomamos todos los campos y los cargamos con los datos desde las celdas.
			List<Field> fields = ExcelUtils.getMappedExcelField(excelDto.getClass());

			for (Field field : fields) {
				// Tomamos el campo de excel y el parseador.
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				ExcelFieldFormatter parser = excelField.parser().newInstance();

				// Obtenemos la celda de acuerdo al tipo de recorrido que tenemos.
				Cell cell = null;
				switch (excelClass.parseType()) {
				case COLUMN:
					cell = ExcelUtils.getCell(sheet, index, excelField.posicion());
					break;

				case ROW:
					cell = ExcelUtils.getCell(sheet, excelField.posicion(), index);
					break;

				default:
					break;
				}

				// Si obtuvimos alguna celda en esa posición, la parseamos.
				Object value = parser.get(cell, field.getType());
				field.set(excelDto, value);

				// El valor que indica si se leyó al menos un atributo desde el excel.
				dataNotNull |= value != null;
			}
		} catch (Exception e) {
			log.error("Fail to parser the excel cell to the DTO", e);
			throw new UncheckedExcelException("Fail to parser the excel cell to the DTO");
		}

		return dataNotNull;
	}

	/**
	 * El método encargada de escribir el archivo de excel a partir de un listado de las entidades que recibimos.
	 * 
	 * @param workbook
	 *            El archivo de excel que queremos escribir.
	 * @param dtos
	 *            Las entidades que queremos almacenar dentro del archivo de excel.
	 * @param clazz
	 *            La clase que tenemos anotada con los campos correspondiente al excel.
	 */
	public static <C extends ExcelDto> void write(Workbook workbook, List<C> dtos, Class<C> clazz) {
		ExcelClass excelClass = clazz.getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + clazz.getSimpleName());
			throw new UncheckedExcelException("Invalid class configuration - ExcelClass annotation missing in: " + clazz.getSimpleName());
		}

		Sheet sheet = workbook.getSheet(excelClass.sheet());

		for (long index = 0; index < dtos.size(); index++) {
			try {
				C c = dtos.get((int) index);
				ExcelUtils.writeDto(sheet, index, c);
			} catch (UncheckedExcelException e) {
				throw e;
			}
		}
	}

	public static <C extends ExcelDto> void writeDto(Sheet sheet, long index, C excelDto) {
		// Obtenemos la clase del excel.
		ExcelClass excelClass = excelDto.getClass().getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + excelDto.getClass().getSimpleName());
			throw new UncheckedExcelException("Invalid class configuration - ExcelClass annotation missing in: "
					+ excelDto.getClass().getSimpleName());
		}

		try {
			// Tomamos todos los campos y los cargamos con los datos desde las celdas.
			List<Field> fields = ExcelUtils.getMappedExcelField(excelDto.getClass());

			for (Field field : fields) {
				// Tomamos el campo de excel y el parseador de este campo.
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				ExcelFieldFormatter parser = (ExcelFieldFormatter) excelField.parser().newInstance();

				// Creamos la celda de acuerdo al tipo de recorrido que tenemos.
				Cell cell = null;
				switch (excelClass.parseType()) {
				case COLUMN:
					cell = ExcelUtils.createCell(sheet, index, excelField.posicion());
					break;

				case ROW:
					cell = ExcelUtils.createCell(sheet, excelField.posicion(), index);
					break;

				default:
					break;
				}

				// Si obtuvimos alguna celda en esa posición, la escribimos.
				Object value = field.get(excelDto);
				parser.set(cell, value);
			}
		} catch (Exception e) {
			log.error("Fail to parser the DTO to the excel cell", e);
			throw new UncheckedExcelException("Fail to parser the DTO to the excel cell");
		}
	}
}