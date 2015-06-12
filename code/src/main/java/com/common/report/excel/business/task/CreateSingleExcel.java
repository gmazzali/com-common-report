package com.common.report.excel.business.task;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.util.domain.exception.error.Errors;

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
	 * El conjunto de errores que aparecieron a la hora de crear el archivo de excel.
	 */
	protected Errors errors;

	/**
	 * El constructor por omisi�n del proceso de creaci�n de un archivo de excel.
	 */
	public CreateSingleExcel() {
		super();
	}

	public void start() {
		this.thread = new Thread() {
			public void run() {
			};
		};

		this.thread.setDaemon(true);
		this.thread.start();
	}

	protected void execute() {
		// Formateamos las columnas.
		this.columnsFormat();
		// Agregamos los titulos de las columnas.
		this.columnsTitles();
		// Obtenemos los datos.
		this.getData();
		// Agregamos los datos.
		this.addData();
	}

	/**
	 * La funci�n encargada de retornar el nombre del archivo que vamos a crear.
	 * 
	 * @return El nombre del archivo que vamos a crear.
	 */
	protected abstract String getFileName();

	/**
	 * Funci�n encargada de formatear los anchos de las columnas que van a insertarse dentro del reporte.
	 */
	protected abstract void columnsFormat();

	/**
	 * Funci�n encargada de cargar los t�tulos del reporte y formatearlos.
	 * 
	 * @throws WriteException
	 *             En caso de que ocurra un fallo de escritura.
	 */
	protected abstract void columnsTitles();

	/**
	 * Funci�n encargada de recuperar desde la base de datos los valores de los registros almacendos dentro del rango de fechas dada.
	 * 
	 * @throws Exception
	 *             En caso de alg�n problema en al recuperaci�n de los datos desde la base de datos.
	 */
	protected abstract void getData();

	/**
	 * Funci�n encargada de cargar la fecha y la hora y los datos recuperados desde la base de datos a la hoja de trabajo.
	 * 
	 * @throws WriteException
	 *             La excepci�n que describe una falla en la escritura de la hoja del libro.
	 */
	protected abstract void addData();
}