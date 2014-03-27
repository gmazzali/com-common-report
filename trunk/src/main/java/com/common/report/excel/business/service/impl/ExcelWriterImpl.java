package com.common.report.excel.business.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.business.service.ExcelWriter;
import com.common.report.excel.business.util.ExcelUtils;
import com.common.report.excel.domain.annotation.ExcelClass;
import com.common.report.excel.domain.annotation.ExcelField;
import com.common.report.excel.domain.exception.UncheckedExcelException;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;

/**
 * La implementación base del escritor de entidades a partir de un archivo de excel.
 * 
 * @see ExcelDto
 * @see ExcelWriter
 * 
 * @since 27/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <E>
 *            La clase del {@link ExcelDto} del excel que vamos a ocupar dentro de este servicio.
 */
@SuppressWarnings("unchecked")
public class ExcelWriterImpl<E extends ExcelDto> implements ExcelWriter<E> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelWriterImpl.class);

	/**
	 * La clase que manejamos dentro del excel.
	 */
	private final Class<E> excelDtoClass;

	/**
	 * El constructor de un lector de archivos de excel.
	 */
	public ExcelWriterImpl() {
		if (this instanceof ParameterizedType) {
			this.excelDtoClass = (Class<E>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} else {
			log.error("The generic parameter of this class doesn't must be empty");
			throw new UncheckedExcelException("The generic parameter of this class doesn't must be empty", "report.excel.writer.error.parameter.empty");
		}
	}

	@Override
	public void write(Workbook workbook, List<E> excelDtos) {
		for (Integer index = 0; index < excelDtos.size(); index++) {
			try {
				E excelDto = excelDtos.get(index);
				this.writeUnique(workbook, index, excelDto);
			} catch (UncheckedExcelException e) {
				throw e;
			}
		}
	}

	@Override
	public void writeUnique(Workbook workbook, Integer index, E excelDto) {
		// Obtenemos la clase del excel.
		ExcelClass excelClass = this.excelDtoClass.getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + this.excelDtoClass.getSimpleName());
			throw new UncheckedExcelException("Invalid class configuration - ExcelClass annotation missing in: "
					+ excelDto.getClass().getSimpleName(), "report.excel.writer.error.excelclass.null", this.excelDtoClass.getSimpleName());
		}

		// Obtenemos la hoja, si no existe la creamos.
		Sheet sheet = workbook.getSheet(excelClass.sheet());
		if (sheet == null) {
			sheet = workbook.createSheet(excelClass.sheet());
		}

		try {
			// Tomamos todos los campos y los cargamos con los datos desde las celdas.
			List<Field> fields = ExcelUtils.getMappedExcelField(this.excelDtoClass);

			for (Field field : fields) {
				// Tomamos el campo de excel y el parseador de este campo.
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				ExcelFieldFormatter parser = (ExcelFieldFormatter) excelField.parser().newInstance();

				// Creamos la celda de acuerdo al tipo de recorrido que tenemos.
				Cell cell = null;
				switch (excelClass.parseType()) {
				case COLUMN:
					cell = ExcelUtils.getCell(sheet, index, excelField.posicion());
					if (cell == null) {
						cell = ExcelUtils.createCell(sheet, index, excelField.posicion());
					}
					break;

				case ROW:
					cell = ExcelUtils.getCell(sheet, excelField.posicion(), index);
					if (cell == null) {
						cell = ExcelUtils.createCell(sheet, excelField.posicion(), index);
					}
					break;

				default:
					break;
				}

				// Si obtuvimos alguna celda en esa posición, la escribimos.
				Object value = field.get(excelDto);
				parser.set(cell, value);
			}
		} catch (Exception e) {
			log.error("Fail to parser the DTO to the excel cell", e);
			throw new UncheckedExcelException("Fail to parser the DTO to the excel cell", "report.excel.writer.error.parser.fail");
		}
	}
}