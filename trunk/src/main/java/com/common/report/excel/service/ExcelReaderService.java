package com.common.report.excel.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import com.common.report.excel.model.ExcelDto;

/**
 * Define un DTO para las entradas de un archivo de excel.
 * 
 * @since 18/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <T>
 *            La clase del DTO del excel que vamos a ocupar dentro de este servicio.
 */
public interface ExcelReaderService<T extends ExcelDto> extends Serializable {

	/**
	 * Se encarga de leer el archivo ubicado dentro del archivo ubicado por el nombre y cargar los DTO dentro de una lista.
	 * 
	 * @param inputStream
	 *            La entrada de datos del archivo que vamos a leer dentro de este servicio.
	 * @return El listado de los DTO que obtenemos desde el archivo.
	 */
	public List<T> read(InputStream inputStream);
}