package com.common.report.excel.business.service;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.exception.UncheckedExcelException;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;

/**
 * Define un servicio para realizar las lecturas de un archivo de excel.
 * 
 * @since 18/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <E>
 *            La clase del {@link ExcelDto} del excel que vamos a ocupar dentro de este servicio.
 */
public interface ExcelReader<E extends ExcelDto> extends Serializable {

	/**
	 * Se encarga de leer el archivo y cargar los {@link ExcelDto} dentro de una lista.
	 * 
	 * @param workbook
	 *            El {@link Workbook} desde el que va a leerse los datos del archivo.
	 * @return El listado de los {@link ExcelDto} que obtenemos desde el archivo.
	 */
	public List<E> read(Workbook workbook);

	/**
	 * Permite leer una única entidad desde el archivo de excel dado un indice del mismo.
	 * 
	 * @param workbook
	 *            El {@link Workbook} desde el que va a leerse los datos del archivo.
	 * @param index
	 *            El índice donde vamos a leer los datos para el {@link ExcelDto}.
	 * @param excelDto
	 *            El {@link ExcelDto} que vamos a cargar con los datos del excel.
	 * @return <i>true</i> en caso de que se haya cargado algún atributo desde el excel al {@link ExcelDto}, en caso de que se haya leido un recorrido
	 *         completamente <code>null</code> se retorna <i>false</i>.
	 * @throws UncheckedExcelException
	 *             En caso de que no se pueda crear una entidad del {@link ExcelDto} o del {@link ExcelFieldFormatter} o una falla al enlazar el campo
	 *             con el objeto leído.
	 */
	Boolean readUnique(Workbook workbook, Integer index, E excelDto);
}