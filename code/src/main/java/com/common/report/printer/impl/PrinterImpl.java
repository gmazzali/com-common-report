package com.common.report.printer.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.common.report.printer.Printer;
import com.common.util.domain.exception.UncheckedException;

/**
 * La clase que nos va a servir para abrir una ventana de impresión o para abrir un archivo de manera nativa dentro del sistema operativo.
 * 
 * @since 01/04/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class PrinterImpl implements Printer {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(PrinterImpl.class);

	@Override
	public void openFile(File file) {
		// Si el archivo no existe.
		if (!file.exists()) {
			PrinterImpl.log.error("file doesn't exist");
			throw new UncheckedException("The file doesn't exist", "printer.open.file.not.exist");
		}

		// Si el archivo es un directorio.
		if (file.isDirectory()) {
			PrinterImpl.log.error("file is a directory");
			throw new UncheckedException("The file is a directory", "printer.open.file.is.directory");
		}

		// Si el sistema operativo lo soporta.
		if (Desktop.isDesktopSupported()) {
			try {
				// Abrimos el archivo.
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				PrinterImpl.log.error("open file failed", e);
				throw new UncheckedException("Open file failed", "printer.open.file.failed");
			}
		} else {
			PrinterImpl.log.error("open file don't supported");
			throw new UncheckedException("Open file doesn't supported", "printer.open.file.os.not.support");
		}
	}

	@Override
	public void printFile(File file) {
		// TODO Auto-generated method stub
	}
}