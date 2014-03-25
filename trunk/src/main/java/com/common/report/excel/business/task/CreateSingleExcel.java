package com.common.report.excel.business.task;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * La clase que nos permite crear un reporte con un listado de registros dentro de un archivo de excel.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class CreateSingleExcel implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CreateSingleExcel.class);

	/**
	 * El thread que vamos a ejecutar para crear el excel.
	 */
	private Thread thread;

	/**
	 * El archivo de excel que contiene el libro que vamos a utilizar dentro de este objeto.
	 */
	protected Workbook book;

	/**
	 * El constructor por omisión del proceso de creación de un archivo de excel.
	 */
	public CreateSingleExcel() {
		super();
	}

	protected void beforeExecute() {
	}

	protected void execute() {
		try {
			// Formateamos las columnas.
			this.columnsFormat();

			// Agregamos los titulos de las columnas.
			this.columnsTitles();

			// Obtenemos los datos.
			this.getData();

			// Agregamos los datos.
			this.addData();

		} catch (Exception e) {
			CreateSingleExcel.log.error("execute failed", e);
		}
	}

	/**
	 * La función encargada de retornar el nombre del archivo que vamos a crear.
	 * 
	 * @return El nombre del archivo que vamos a crear.
	 */
	protected abstract String getFileName();

	/**
	 * La función encargada de retornar el nombre que le vamos a poner al libro que vamos a llenar con el listado de registros.
	 * 
	 * @return El nombre del libro que le vamos a poner al libro.
	 */
	protected abstract String getSheetName();

	/**
	 * Función encargada de formatear los anchos de las columnas que van a insertarse dentro del reporte.
	 */
	protected abstract void columnsFormat();

	/**
	 * Función encargada de cargar los títulos del reporte y formatearlos.
	 * 
	 * @throws WriteException
	 *             En caso de que ocurra un fallo de escritura.
	 */
	protected abstract void columnsTitles();

	/**
	 * Función encargada de recuperar desde la base de datos los valores de los registros almacendos dentro del rango de fechas dada.
	 * 
	 * @throws Exception
	 *             En caso de algún problema en al recuperación de los datos desde la base de datos.
	 */
	protected abstract void getData();

	/**
	 * Función encargada de cargar la fecha y la hora y los datos recuperados desde la base de datos a la hoja de trabajo.
	 * 
	 * @throws WriteException
	 *             La excepción que describe una falla en la escritura de la hoja del libro.
	 */
	protected abstract void addData();
}