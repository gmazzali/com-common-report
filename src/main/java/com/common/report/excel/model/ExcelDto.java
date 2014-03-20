package com.common.report.excel.model;

import java.io.Serializable;

/**
 * Define un DTO para las entradas de un archivo de excel.
 * 
 * @since 18/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public abstract class ExcelDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * La interfaz que define los atributos de esta clase.
	 * 
	 * @since 20/03/2014
	 * @author Guillermo Mazzali
	 * @version 1.0
	 */
	public interface Attributes {
		public static final String VALID = "valid";

		public static final String PROCESSED = "processed";
	}

	/**
	 * El valor booleano que nos indica si el {@link ExcelDto} es válido.
	 */
	private boolean valid = true;
	/**
	 * El valor booleano que nos indica si el {@link ExcelDto} ya fue procesado.
	 */
	private boolean processed = false;

	/**
	 * El constructor por omisión.
	 */
	public ExcelDto() {
		super();
	}

	/**
	 * Permite saber si el {@link ExcelDto} es válido dentro del sistema o no.
	 * 
	 * @return <i>true</i> si el {@link ExcelDto} es válido dentro del sistema, en caso contrario, retorna <i>false</i>.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Permite cargar el estado de validez del {@link ExcelDto}.
	 * 
	 * @param valid
	 *            El estado de validez del {@link ExcelDto}.
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * Permite saber si el {@link ExcelDto} ya fue procesado dentro del sistema o no.
	 * 
	 * @return <i>true</i> si el {@link ExcelDto} ya fue procesado dentro del sistema, en caso contrario, retorna <i>false</i>.
	 */
	public boolean isProcessed() {
		return processed;
	}

	/**
	 * Permite cargar si ya fue procesado el {@link ExcelDto} dentro del sistema.
	 * 
	 * @param processed
	 *            El valor que indica si ya fue procesado el {@link ExcelDto} dentro del sistema.
	 */
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
}