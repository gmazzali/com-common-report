package com.common.report.excel.business.service;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

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
public interface ExcelReader<T extends ExcelDto> extends Serializable {

	/**
	 * Se encarga de leer el archivo ubicado dentro del archivo ubicado por el nombre y cargar los DTO dentro de una lista.
	 * 
	 * @param workbook
	 *            El archivo de excel desde el que va a leerse los datos del archivo.
	 * @return El listado de los DTO que obtenemos desde el archivo.
	 */
	public List<T> read(Workbook workbook);
}