package com.common.report.excel.domain.model.formatter;

import org.apache.poi.ss.usermodel.Cell;

import com.common.report.excel.domain.model.ExcelDto;

/**
 * Permite definir un parseador de una celda con un campo de un {@link ExcelDto} de un archivo de excel.
 * 
 * @see ExcelDto
 * 
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public interface ExcelFieldFormatter {

	/**
	 * Carga el contenido de la celda dentro del un objeto y lo retorna formateado correctamente. Debe ser un método null-safe.
	 * 
	 * @param cell
	 *            La celda que va a leerse para cargar el objeto.
	 * @param clazz
	 *            La clase que corresponde con el tipo de objeto que es el campo de {@link ExcelDto} que vamos a cargar con el dato de la celda.
	 * @return El valor cargado con los datos de la celda.
	 */
	public Object get(Cell cell, Class<?> clazz);

	/**
	 * Carga el contenido del objeto recibido dentro de la celda, convirtiendo el mismo en el tipo correcto antes de realizarlo. Debe ser un método
	 * null-safe.
	 * 
	 * @param cell
	 *            La celda donde vamos a guardar el objeto.
	 * @param object
	 *            El objeto que vamos a guardar dentro de la celda.
	 */
	public void set(Cell cell, Object object);
}