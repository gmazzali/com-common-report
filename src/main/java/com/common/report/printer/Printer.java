package com.common.report.printer;

import java.io.File;
import java.io.Serializable;

import com.common.util.domain.exception.UncheckedException;

/**
 * La interfaz que define el comportamiento b�sico de un servicio de impresi�n dentro del sistema.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public interface Printer extends Serializable {

	/**
	 * Permite la apertura de un archivo que se pasa como par�metro.
	 * 
	 * @param file
	 *            El archivo que queremos abrir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void openFile(File file);

	/**
	 * Permite la impresi�n directamente por impresora de un archivo que se pasa como par�metro.
	 * 
	 * @param stream
	 *            La salida de datos que vamos a imprimir en el sistema.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void printFile(File file);
}