package com.common.report.model.pdf;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.common.report.model.printer.Printer;
import com.common.util.model.thread.GenericTask;

/**
 * La clase que nos permite crear pdf con los valores que recuperemos desde una base de datos.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public abstract class CreateDatabasePdfReportTask extends GenericTask<Double> {

	private static final long serialVersionUID = 3503392399881858440L;

	/**
	 * El Logger que vamos a ocupar dentro de la clase.
	 */
	private static final Logger log = Logger.getLogger(CreateDatabasePdfReportTask.class);

	/**
	 * El reporte maestro para el sistema.
	 */
	private JasperReport masterReport;
	/**
	 * El reporte compilado que va a llenarse con los datos.
	 */
	private JasperPrint filledReport;

	/**
	 * El constructor del proceso que genera un reporte pdf desde datos de una base de datos.
	 */
	public CreateDatabasePdfReportTask() {
		super("CreateDatabasePdfReportTask");
		CreateDatabasePdfReportTask.log.trace("CreateDatabasePdfReportTask create");
	}

	/**
	 * @see com.commons.util.model.thread.GenericTask#beforeExecute()
	 */
	@Override
	public void beforeExecute() {
		CreateDatabasePdfReportTask.log.trace("CreateDatabasePdfReportTask beforeExecute");
	}

	/**
	 * @see com.commons.util.model.thread.GenericTask#execute()
	 */
	@Override
	public void execute() {
		CreateDatabasePdfReportTask.log.trace("CreateDatabasePdfReportTask execute");

		// Conectamos la base de datos en caso de que este desconectada.
		Connection connection = null;
		try {
			connection = this.getDataSource().getConnection();

			if (connection.isValid(5000)) {
				CreateDatabasePdfReportTask.log.info("CreateDatabasePdfReportTask connection ready");
			} else {
				CreateDatabasePdfReportTask.log.warn("CreateDatabasePdfReportTask connection failed");
			}

		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("CreateDatabasePdfReportTask connection failed", e);
			return;
		}

		// Obtenemos el reporte, si no lo tenemos compilado, lo compilamos.
		try {
			this.masterReport = (JasperReport) JRLoader.loadObject(new FileInputStream(this.getReportFile() + ".jasper"));
			CreateDatabasePdfReportTask.log.info("CreateDatabasePdfReportTask report ready");

		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.warn("CreateDatabasePdfReportTask compile report");
			try {
				this.masterReport = JasperCompileManager.compileReport(new FileInputStream(this.getReportFile() + ".jrxml"));
				CreateDatabasePdfReportTask.log.info("CreateDatabasePdfReportTask compile report succefull");

			} catch (Exception e1) {
				CreateDatabasePdfReportTask.log.error("CreateDatabasePdfReportTask compile report failed", e1);
				e1.printStackTrace();
				return;
			}
		}

		// Llenamos el reporte con los datos cargados desde la base de datos.
		try {
			this.filledReport = JasperFillManager.fillReport(this.masterReport, this.getParameter(), connection);
			CreateDatabasePdfReportTask.log.info("EventsReportTask fill report succefull");

		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("EventsReportTask fill report failed", e);
			return;
		}
	}

	/**
	 * @see com.commons.util.model.thread.GenericTask#afterExecute()
	 */
	@Override
	public void afterExecute() {
		CreateDatabasePdfReportTask.log.trace("EventsReportTask afterExecute");

		try {
			JasperExportManager.exportReportToPdfFile(this.filledReport, this.getNameFile());
		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("EventsReportTask export file failed", e);
		}
		try {
			Printer.openFile(this.getNameFile());
		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("EventsReportTask open file failed", e);
		}
	}

	/**
	 * La funci�n encargada de retornar la ubicaci�n donde se encuentra el reporte, ya sea el <tt>.jrxml</tt> o el archivo compilado <tt>.jrxml</tt>,
	 * por lo que no es necesario que contenga la extensi�n del mismo, ya que se agregar� de manera din�mica de acuerdo a que se necesite compilar o
	 * ya lo tengamos compilado.
	 * 
	 * @return El archivo que vamos a utilizar para crear el reporte.
	 */
	protected abstract String getReportFile();

	/**
	 * La funci�n encargada de retornar el {@link DataSource} para que se recuperen los datos de la base de datos.
	 * 
	 * @return El {@link DataSource} que corresponde a la conexi�n a la base de datos que vamos a utilizar.
	 */
	protected abstract DataSource getDataSource();

	/**
	 * La funci�n encargada de retornar los par�metros que vamos a ocupar para llenar el reporte.
	 * 
	 * @return El conjunto de parametros que vamos a ocupar para llenar el reporte.
	 */
	protected abstract HashMap<String, Object> getParameter();

	/**
	 * La funci�n encargada de retornar el nombre del archivo que vamos a almacenar.
	 * 
	 * @return El nombre del archivo que vamos a crear.
	 */
	protected abstract String getNameFile();
}
