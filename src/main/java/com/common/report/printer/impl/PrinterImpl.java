package com.common.report.printer.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.common.report.printer.Printer;
import com.common.util.exception.UncheckedException;

/**
 * La clase que nos va a servir para abrir una ventana de impresión o para abrir un archivo de manera nativa dentro del sistema operativo.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public abstract class PrinterImpl implements Printer {

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

		// Si el archivo es un directorio.
		if(file.isDirectory()) {
			PrinterImpl.log.error("file is a directory");
			throw new UncheckedException("printer.open.file.is.directory");
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
}