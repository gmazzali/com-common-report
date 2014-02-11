package com.common.report.service.printer;

import java.io.File;

import com.common.util.exception.UncheckedException;

/**
 * La interfaz que define el comportamiento b�sico de un servicio de impresi�n dentro del sistema.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public interface Printer {

	/**
	 * Permite la apertura de un archivo cuya ubicaci�n se pasa como par�metro.
	 * 
	 * @param pathFile
	 *            La ubicaci�n del archivo que queremos abrir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void openFile(String pathFile);

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
	 * Permite la impresi�n directa por impresora de un archivo cuya ubicaci�n se pasa como par�metro.
	 * 
	 * @param pathFile
	 *            La ubicaci�n del archivo que queremos imprimir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void printFile(String pathFile);

	/**
	 * Permite la impresi�n directamente por impresora de un archivo que se pasa como par�metro.
	 * 
	 * @param file
	 *            El archivo que queremos imprimir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void printFile(File file);
}