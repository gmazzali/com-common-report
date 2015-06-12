package com.common.report.excel.business.util;

/**
 * La enumeraci�n que contiene la extensi�n del archivo dado.
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
	 * La extensi�n del archivo.
	 */
	private String extension;

	/**
	 * El constructor de una extensi�n de un archivo.
	 * 
	 * @param extension
	 *            La extensi�n del archivo.
	 */
	private ExcelTypeEnum(String extension) {
		this.extension = extension;
	}

	/**
	 * Retorna la extensi�n del tipo de archivo.
	 * 
	 * @return La extensi�n del archivo.
	 */
	public String getExtension() {
		return extension;
	}
}