package com.common.report.excel.business.util;

/**
 * La enumeración que contiene la extensión del archivo dado.
 * 
 * <ul>
 * <li>{@link ExcelTypeEnum#XLS}</li>
 * <li>{@link ExcelTypeEnum#XLSX}</li>
 * </ul>
 * 
 * @since 27/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public enum ExcelTypeEnum {

	XLS("xls"), XLSX("xlsx");

	/**
	 * La extensión del archivo.
	 */
	private String extension;

	/**
	 * El constructor de una extensión de un archivo.
	 * 
	 * @param extension
	 *            La extensión del archivo.
	 */
	private ExcelTypeEnum(String extension) {
		this.extension = extension;
	}

	/**
	 * Retorna la extensión del tipo de archivo.
	 * 
	 * @return La extensión del archivo.
	 */
	public String getExtension() {
		return extension;
	}
}