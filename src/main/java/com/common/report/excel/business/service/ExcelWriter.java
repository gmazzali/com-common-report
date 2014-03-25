package com.common.report.excel.business.service;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;

import com.common.report.excel.domain.model.ExcelDto;

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
public interface ExcelWriter<E extends ExcelDto> extends Serializable {

	/**
	 * Permite escribir una lista de DTOs dentro de una salida para poder escribirlos en un archivo o desplegarlo.
	 * 
	 * @param excelDtos
	 *            Los datos que vamos a escribir dentro del archivo.
	 * @return La salida donde fueron escritos los datos recibidos.
	 */
	public OutputStream write(Collection<E> excelDtos);
}