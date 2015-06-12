package com.common.report.excel.business.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.common.report.excel.domain.annotation.ExcelField;
import com.common.util.business.tool.VerifierUtil;

/**
 * Contiene las herramientas basicas para la
 * 
 * @author gmazzali
 * 
 */
public class ExcelUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelUtils.class);

	/**
	 * Permite formatear el formato del nombre del archivo agregando la extensión al mismo en caso de ser necesario.
	 * 
	 * @param fileName
	 *            El nombre del archivo que vamos a verificar y formatear.
	 * @return El nombre del archivo correctamente formateado y con la extensión correspondiente.
	 */
	public static String formatFileName(String fileName, ExcelTypeEnum excelTypeEnum) {
		// El nombre del archivo no puede ser nulo.
		VerifierUtil.checkNotNull(fileName, "The file name cannot be null", "report.excel.error.filename.null");

		StringBuffer buffer = new StringBuffer(fileName);

		if (excelTypeEnum != null) {
			if (!fileName.endsWith(excelTypeEnum.getExtension())) {
				buffer.append(".");
				buffer.append(excelTypeEnum.getExtension());
			}
		}
		return buffer.toString();
	}

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
	public static Cell getCell(Sheet sheet, Integer rowIndex, Integer columnIndex) {
		Row row = sheet.getRow(rowIndex);
		Cell cell = null;
		if (row == null) {
			log.warn("The row is null");
			cell = null;
		} else {
			log.info("The row isn't null");
			cell = row.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK);
		}
		return cell;
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
	public static Cell createCell(Sheet sheet, Integer rowIndex, Integer columnIndex) {
		Row row = sheet.createRow(rowIndex);
		Cell cell = null;
		if (row == null) {
			log.warn("The row is null");
			cell = null;
		} else {
			log.info("The row isn't null");
			cell = row.createCell(columnIndex);
		}
		return cell;
	}
}