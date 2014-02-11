package com.common.report.service.printer;

import java.io.File;

import com.common.util.exception.UncheckedException;

/**
 * La interfaz que define el comportamiento básico de un servicio de impresión dentro del sistema.
 * 
 * @author Guillermo Mazzali
 * @version 1.0
 */
public interface Printer {

	/**
	 * Permite la apertura de un archivo cuya ubicación se pasa como parámetro.
	 * 
	 * @param pathFile
	 *            La ubicación del archivo que queremos abrir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void openFile(String pathFile);

	/**
	 * Permite la apertura de un archivo que se pasa como parámetro.
	 * 
	 * @param file
	 *            El archivo que queremos abrir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void openFile(File file);

	/**
	 * Permite la impresión directa por impresora de un archivo cuya ubicación se pasa como parámetro.
	 * 
	 * @param pathFile
	 *            La ubicación del archivo que queremos imprimir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void printFile(String pathFile);

	/**
	 * Permite la impresión directamente por impresora de un archivo que se pasa como parámetro.
	 * 
	 * @param file
	 *            El archivo que queremos imprimir.
	 * @throws UncheckedException
	 *             En caso de que ocurra un error al manipular el archivo.
	 */
	public void printFile(File file);
}