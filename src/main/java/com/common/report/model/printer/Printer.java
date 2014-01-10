package com.common.report.model.printer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.common.util.exception.CheckedException;

/**
 * La clase que nos va a servir para abrir una ventana de impresión o para abrir un archivo de manera nativa dentro del sistema operativo.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class Printer {

	/**
	 * Función que permite la apertura del documento PDF que se pasa como parámetro para que el usuario pueda hacer con el lo que desee, no lo imprime
	 * directamente.
	 * 
	 * @param file
	 *            El nombre del archivo que se quiere abrir.
	 * @throws CheckedException
	 *             En caso de que ocurra un error a la hora de abrir el archivo.
	 */
	public static void openFile(String file) throws CheckedException {
		if (Desktop.isDesktopSupported()) {
			try {
				// abre el archivo
				Desktop.getDesktop().open(new File(file));
			} catch (IOException ex) {
				throw new CheckedException("file.open.failed");
			}
		} else {
			throw new CheckedException("file.dont.support.os");
		}
	}
}
