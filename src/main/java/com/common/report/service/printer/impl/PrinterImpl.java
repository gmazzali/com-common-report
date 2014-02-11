package com.common.report.service.printer.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.common.report.service.printer.Printer;
import com.common.util.exception.UncheckedException;

/**
 * La clase que nos va a servir para abrir una ventana de impresión o para abrir un archivo de manera nativa dentro del sistema operativo.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class PrinterImpl implements Printer {

	/**
	 * El Logger que vamos a ocupar dentro de la clase.
	 */
	private static final Logger log = Logger.getLogger(PrinterImpl.class);

	@Override
	public void openFile(String pathFile) {
		this.openFile(new File(pathFile));
	}

	@Override
	public void openFile(File file) {
		// Si el archivo no existe.
		if(!file.exists()) {
			PrinterImpl.log.error("file doesn't exist");
			throw new UncheckedException("printer.open.file.not.exist");
		}
		// Si el sistema operativo lo soporta.
		if (Desktop.isDesktopSupported()) {
			try {
				// Abrimos el archivo.
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				PrinterImpl.log.error("open file failed", e);
				throw new UncheckedException("printer.open.file.failed");
			}
		} else {
			PrinterImpl.log.error("open file don't supported");
			throw new UncheckedException("printer.open.file.os.not.support");
		}
	}

	@Override
	public void printFile(String pathFile) {
		this.printFile(new File(pathFile));
	}

	@Override
	public void printFile(File file) {
		// TODO Por ahora no hace nada.
	}
}