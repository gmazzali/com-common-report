package com.common.report.excel.business.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.annotation.ExcelClass;
import com.common.report.excel.domain.annotation.ExcelField;
import com.common.report.excel.domain.exception.UncheckedExcelException;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.ParseType;
import com.common.report.excel.domain.model.parser.ExcelFieldParser;

/**
 * La clase que nos permite
 * 
 * @author gmazzali
 * 
 */
public class ExcelProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelProcess.class);

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

		for (long index = excelClass.start(); index <= excelClass.end(); index++) {
			try {
				// Creamos una nueva instancia del DTO.
				C excelDto = clazz.newInstance();

				// Cargamos la instancia con los datos desde el excel.
				dataNotNull = ExcelProcess.readDto(sheet, index, excelClass.parseType(), excelDto);

				// Solo si la entidad no es nula, la agregamos a la lista.
				if (dataNotNull) {
					list.add(excelDto);
				} else {
					break;
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
}