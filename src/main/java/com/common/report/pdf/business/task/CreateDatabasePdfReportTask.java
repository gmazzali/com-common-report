package com.common.report.pdf.business.task;

import java.io.FileInputStream;
import java.io.Serializable;
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

/**
 * La clase que nos permite crear pdf con los valores que recuperemos desde una base de datos.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public abstract class CreateDatabasePdfReportTask implements Serializable {

	private static final long serialVersionUID = 1L;
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
	}

	public void beforeExecute() {
		CreateDatabasePdfReportTask.log.trace("before execute");
	}

	public void execute() {
		CreateDatabasePdfReportTask.log.trace("execute");

		// Conectamos la base de datos en caso de que este desconectada.
		Connection connection = null;
		try {
			connection = this.getDataSource().getConnection();

			if (connection.isValid(5000)) {
				CreateDatabasePdfReportTask.log.info("connection ready");
			} else {
				CreateDatabasePdfReportTask.log.warn("connection failed");
			}

		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("connection failed", e);
			return;
		}

		// Obtenemos el reporte, si no lo tenemos compilado, lo compilamos.
		try {
			this.masterReport = (JasperReport) JRLoader.loadObject(new FileInputStream(this.getReportFile() + ".jasper"));
			CreateDatabasePdfReportTask.log.info("report ready");

		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.warn("compile report");
			try {
				this.masterReport = JasperCompileManager.compileReport(new FileInputStream(this.getReportFile() + ".jrxml"));
				CreateDatabasePdfReportTask.log.info("compile report succefull");

			} catch (Exception e1) {
				CreateDatabasePdfReportTask.log.error("compile report failed", e1);
				return;
			}
		}

		// Llenamos el reporte con los datos cargados desde la base de datos.
		try {
			this.filledReport = JasperFillManager.fillReport(this.masterReport, this.getParameter(), connection);
			CreateDatabasePdfReportTask.log.info("fill report succefull");

		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("fill report failed", e);
			return;
		}

		// Exportamos el PDF a un archivo.
		try {
			JasperExportManager.exportReportToPdfFile(this.filledReport, this.getNameFile());
		} catch (Exception e) {
			CreateDatabasePdfReportTask.log.error("export file failed", e);
		}
	}

	/**
	 * La función encargada de retornar la ubicación donde se encuentra el reporte, ya sea el <tt>.jrxml</tt> o el archivo compilado <tt>.jrxml</tt>,
	 * por lo que no es necesario que contenga la extensión del mismo, ya que se agregará de manera dinámica de acuerdo a que se necesite compilar o
	 * ya lo tengamos compilado.
	 * 
	 * @return El archivo que vamos a utilizar para crear el reporte.
	 */
	protected abstract String getReportFile();

	/**
	 * La función encargada de retornar el {@link DataSource} para que se recuperen los datos de la base de datos.
	 * 
	 * @return El {@link DataSource} que corresponde a la conexión a la base de datos que vamos a utilizar.
	 */
	protected abstract DataSource getDataSource();

	/**
	 * La función encargada de retornar los parámetros que vamos a ocupar para llenar el reporte.
	 * 
	 * @return El conjunto de parametros que vamos a ocupar para llenar el reporte.
	 */
	protected abstract HashMap<String, Object> getParameter();

	/**
	 * La función encargada de retornar el nombre del archivo que vamos a almacenar.
	 * 
	 * @return El nombre del archivo que vamos a crear.
	 */
	protected abstract String getNameFile();
}