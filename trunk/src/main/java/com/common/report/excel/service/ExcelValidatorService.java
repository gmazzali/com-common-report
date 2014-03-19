package com.common.report.excel.service;

import java.io.Serializable;
import java.util.Collection;

import com.common.report.excel.model.ExcelDto;
import com.common.util.exception.error.Errors;

/**
 * Define un servicio para las validaciones de entradas de un archivo de excel.
 * 
 * @since 18/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <T>
 *            La clase del DTO del excel que vamos a ocupar dentro de este servicio.
 */
public interface ExcelValidatorService<E extends ExcelDto> extends Serializable {

	/**
	 * Permite validar el conjunto de datos que obtuvimos desde un archivo de excel.
	 * 
	 * @param excelDtos
	 *            Los datos obtenidos dentro de un archivo de escel para ser validados.
	 * @param errors
	 *            El conjunto de errores que vamos a cargar con todos los que encontremos mientras validamos.
	 * @return <i>true</i> en caso de que la validación no haya encontrado ningún problema con los datos, en caso de haber encontrado al menos un
	 *         error retornamos <i>false</i>.
	 */
	public boolean validate(Collection<E> excelDtos, Errors errors);
}