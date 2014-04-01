package com.common.report.excel.business.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.business.service.ExcelReader;
import com.common.report.excel.business.service.ExcelWriter;
import com.common.report.excel.business.util.ExcelUtils;
import com.common.report.excel.domain.annotation.ExcelClass;
import com.common.report.excel.domain.annotation.ExcelField;
import com.common.report.excel.domain.exception.UncheckedExcelException;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;
import com.common.util.business.tool.VerifierUtil;
import com.common.util.domain.exception.UncheckedException;

/**
 * La implementación base del lector de entidades a partir de un archivo de excel.
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
public abstract class ExcelReaderImpl<E extends ExcelDto> implements ExcelReader<E> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelReaderImpl.class);

	/**
	 * La clase que manejamos dentro del excel.
	 */
	private final Class<E> excelDtoClass;

	/**
	 * El constructor de un lector de archivos de excel.
	 */
	public ExcelReaderImpl() {
		try {
			this.excelDtoClass = (Class<E>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception ex) {
			log.error("The generic parameter of this class doesn't must be empty", ex);
			throw new UncheckedExcelException("The generic parameter of this class doesn't must be empty",
					"report.excel.reader.error.parameter.empty");
		}
	}

	@Override
	public List<E> read(Workbook workbook) {
		// Obtenemos la clase del excel.
		ExcelClass excelClass = this.excelDtoClass.getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + this.excelDtoClass.getSimpleName());
			throw new UncheckedExcelException(
					"Invalid class configuration - ExcelClass annotation missing in: " + this.excelDtoClass.getSimpleName(),
					"report.excel.reader.error.excelclass.null", this.excelDtoClass.getSimpleName());
		}

		Boolean dataNotNull = true;
		List<E> list = new ArrayList<E>();

		for (Integer index = excelClass.start(); index <= excelClass.end(); index++) {
			try {
				// Creamos una nueva instancia del DTO.
				E excelDto = this.excelDtoClass.newInstance();

				// Cargamos la instancia con los datos desde el excel.
				dataNotNull = this.readUnique(workbook, index, excelDto);

				// Solo si la entidad no es nula, la agregamos a la lista.
				if (dataNotNull) {
					list.add(excelDto);
				} else {
					break;
				}
			} catch (UncheckedException e) {
				throw e;
			} catch (Exception e) {
				log.error("Can't create a new instance of the DTO (missing default constructor?)", e);
				throw new UncheckedExcelException("Can't create a new instance of the DTO (missing default constructor?)",
						"report.excel.reader.error.newinstance.fail");
			}
		}

		return list;
	}

	@Override
	public Boolean readUnique(Workbook workbook, Integer index, E excelDto) {
		// Obtenemos la clase del excel.
		ExcelClass excelClass = this.excelDtoClass.getAnnotation(ExcelClass.class);
		if (excelClass == null) {
			log.error("Invalid class configuration - ExcelClass annotation missing in: " + this.excelDtoClass.getSimpleName());
			throw new UncheckedExcelException(
					"Invalid class configuration - ExcelClass annotation missing in: " + this.excelDtoClass.getSimpleName(),
					"report.excel.reader.error.excelclass.null", this.excelDtoClass.getSimpleName());
		}

		Sheet sheet = workbook.getSheet(excelClass.sheet());
		VerifierUtil.checkNotNull(sheet, "The sheet doesn't exist", "report.excel.reader.error.sheet.null");

		Boolean dataNotNull = false;

		try {
			// Tomamos todos los campos y los cargamos con los datos desde las celdas.
			List<Field> fields = ExcelUtils.getMappedExcelField(this.excelDtoClass);

			for (Field field : fields) {
				// Tomamos el campo de excel y el parseador.
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				ExcelFieldFormatter<Serializable> parser = (ExcelFieldFormatter<Serializable>) excelField.parser().newInstance();

				// Obtenemos la celda de acuerdo al tipo de recorrido que tenemos.
				Cell cell = null;
				switch (excelClass.parseType()) {
				case COLUMN:
					cell = ExcelUtils.getCell(sheet, index, excelField.posicion());
					break;

				case ROW:
					cell = ExcelUtils.getCell(sheet, excelField.posicion(), index);
					break;

				default:
					break;
				}

				// Si obtuvimos alguna celda en esa posición, la parseamos.
				String pattern = excelField.pattern();
				Serializable value = parser.get(cell, pattern);
				field.set(excelDto, value);

				// El valor que indica si se leyó al menos un atributo desde el excel.
				dataNotNull |= value != null;
			}
		} catch (Exception e) {
			log.error("Fail to parser the excel cell to the DTO", e);
			throw new UncheckedExcelException("Fail to parser the excel cell to the DTO", "report.excel.reader.error.parser.fail");
		}

		return dataNotNull;
	}
}