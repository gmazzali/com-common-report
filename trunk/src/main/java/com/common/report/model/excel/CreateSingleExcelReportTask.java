package com.common.report.model.excel;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;

import com.common.report.model.printer.Printer;
import com.common.util.exception.UncheckedException;
import com.common.util.model.thread.GenericTask;

/**
 * La clase que nos permite crear un reporte con un listado de registros dentro de un archivo de excel.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public abstract class CreateSingleExcelReportTask extends GenericTask<Double> {

	private static final long serialVersionUID = 1817771488275361035L;

	/**
	 * El Logger que vamos a ocupar dentro de la clase.
	 */
	private static final Logger log = Logger.getLogger(CreateSingleExcelReportTask.class);

	/**
	 * El archivo de excel que contiene el libro que vamos a utilizar dentro de este objeto.
	 */
	protected WritableWorkbook book;
	/**
	 * La hoja del libro que vamos a cargar dentro del archivo con los datos de los registros que queremos cargarle.
	 */
	protected WritableSheet sheet;

	/**
	 * El constructor de un proceso de creación de reportes en excel.
	 */
	public CreateSingleExcelReportTask() {
		super("CreateSingleExcelReportTask");
		CreateSingleExcelReportTask.log.trace("CreateSingleExcelReportTask create");
	}

	/**
	 * @see com.common.util.model.thread.GenericTask#beforeExecute()
	 */
	@Override
	protected void beforeExecute() {
		CreateSingleExcelReportTask.log.trace("CreateSingleExcelReportTask beforeExecute");

		try {
			// Creamos el libro del archivo que vamos a llenar.
			this.book = Workbook.createWorkbook(new File(this.getFileName()));
			// Creamos la primer hoja del libro.
			this.sheet = this.book.createSheet(this.getSheetName(), 0);
		} catch (IOException e) {
			CreateSingleExcelReportTask.log.error("CreateSingleExcelReportTask beforeExecute failed", e);
		}
	}

	/**
	 * @see com.common.util.model.thread.GenericTask#execute()
	 */
	@Override
	protected void execute() {
		CreateSingleExcelReportTask.log.trace("CreateSingleExcelReportTask execute");

		try {
			this.taskMonitor.setValue(0.0);

			// Formateamos las columnas.
			this.columnsFormat();
			this.taskMonitor.setValue(0.1);

			// Agregamos los titulos de las columnas.
			this.columnsTitles();
			this.taskMonitor.setValue(0.2);

			// Obtenemos los datos desde la base de datos.
			this.getData();
			this.taskMonitor.setValue(0.5);

			// Agregamos los datos y las fechas.
			this.addData();
			this.taskMonitor.setValue(1.0);

			// Escribimos los datos en el archivo y lo cerramos.
			this.book.write();
			this.book.close();

		} catch (Exception e) {
			CreateSingleExcelReportTask.log.error("CreateSingleExcelReportTask execute failed", e);
			throw new UncheckedException(e);
		}
	}

	/**
	 * @see com.common.util.model.thread.GenericTask#afterExecute()
	 */
	@Override
	protected void afterExecute() {
		CreateSingleExcelReportTask.log.trace("CreateSingleExcelReportTask afterExecute");

		try {
			Printer.openFile(this.getFileName());
		} catch (Exception e) {
			CreateSingleExcelReportTask.log.error("CreateSingleExcelReportTask afterExecute failed", e);
			throw new UncheckedException(e);
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
	protected abstract void columnsTitles() throws WriteException;

	/**
	 * Función encargada de recuperar desde la base de datos los valores de los registros almacendos dentro del rango de fechas dada.
	 * 
	 * @throws Exception
	 *             En caso de algún problema en al recuperación de los datos desde la base de datos.
	 */
	protected abstract void getData() throws Exception;

	/**
	 * Función encargada de cargar la fecha y la hora y los datos recuperados desde la base de datos a la hoja de trabajo.
	 * 
	 * @throws WriteException
	 *             La excepción que describe una falla en la escritura de la hoja del libro.
	 */
	protected abstract void addData() throws WriteException;

}
