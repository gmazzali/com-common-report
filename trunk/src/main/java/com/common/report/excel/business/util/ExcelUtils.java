package com.common.report.excel.business.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.common.report.excel.domain.annotation.ExcelField;

public class ExcelUtils {

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
		return row == null ? null : row.getCell((int) columnIndex);
	}
}