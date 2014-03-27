package com.common.report.excel.business.service;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.exception.UncheckedExcelException;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;

/**
 * Define un servicio para realizar las escrituras de un archivo de excel.
 * 
 * @since 18/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <T>
 *            La clase del {@link ExcelDto} del excel que vamos a ocupar dentro de este servicio.
 */
public interface ExcelWriter<E extends ExcelDto> extends Serializable {

	/**
	 * Permite escribir una lista de {@link ExcelDto} dentro de una salida para poder escribirlos en un archivo o desplegarlo.
	 * 
	 * @param workbook
	 *            El {@link Workbook} donde vamos a escribir las entidades.
	 * @param excelDtos
	 *            Los {@link ExcelDto} que vamos a escribir dentro del archivo.
	 */
	public void write(Workbook workbook, List<E> excelDtos);

	/**
	 * Permite escribir una única entidad de {@link ExcelDto} dentro de una posición del archivo recibido.
	 * 
	 * @param workbook
	 *            El {@link Workbook} donde vamos a escribir la entidad.
	 * @param index
	 *            La posición donde vamos a escribir la entidad.
	 * @param excelDto
	 *            La entidad {@link ExcelDto} que vamos a escribir.
	 * @throws UncheckedExcelException
	 *             En caso de que no se pueda crear una entidad del {@link ExcelDto} o del {@link ExcelFieldFormatter} o una falla al enlazar el campo
	 *             con el objeto leído.
	 */
	public void writeUnique(Workbook workbook, Integer index, E excelDto);
}