package com.common.report.excel.domain.model.formatter;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.formatter.impl.DateExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.FormulaExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.NumberExcelFieldFormatter;

/**
 * Permite definir un parseador de una celda con un campo de un {@link ExcelDto} de un archivo de excel.
 *  
 * @see DateExcelFieldFormatter
 * @see NumberExcelFieldFormatter
 * @see FormulaExcelFieldFormatter
 * @see StringExcelFieldFormatter
 *   
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public interface ExcelFieldFormatter<O extends Serializable> extends Serializable {

	/**
	 * Carga el contenido de la celda dentro del un objeto y lo retorna formateado correctamente. Debe ser un método null-safe.
	 * 
	 * @param cell
	 *            La celda que va a leerse para cargar el objeto.
	 * @param El
	 *            patrón que vamos a usar para leer el dato desde la celda.
	 * @return El valor cargado con los datos de la celda.
	 */
	public O get(Cell cell, String pattern);

	/**
	 * Carga el contenido del objeto recibido dentro de la celda, convirtiendo el mismo en el tipo correcto antes de realizarlo. Debe ser un método
	 * null-safe.
	 * 
	 * @param workbook
	 *            El {@link Workbook} que vamos a utilizar para crear el formateador del campo.
	 * @param cell
	 *            La celda donde vamos a guardar el objeto.
	 * @param El
	 *            patrón que vamos a usar para leer el dato desde la celda.
	 * @param value
	 *            El valor que vamos a guardar dentro de la celda.
	 */
	public void set(Workbook workbook, Cell cell, String pattern, O value);
}