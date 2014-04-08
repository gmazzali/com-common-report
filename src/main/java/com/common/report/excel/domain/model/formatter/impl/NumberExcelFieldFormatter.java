package com.common.report.excel.domain.model.formatter.impl;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;
import com.common.util.business.tool.StringUtil;

/**
 * El formateador de los números que vamos a usar dentro de un archivo de excel.
 * <p>
 * <i> http://stackoverflow.com/questions/5335285/write-number-in-excel-cell-with-poi </i>
 * </p>
 * 
 * @see BigDecimalExcelFieldFormatter
 * @see DoubleExcelFieldFormatter
 * @see IntegerExcelFieldFormatter
 * @see LongExcelFieldFormatter
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 * 
 * @param <N>
 *            El tipo de numero que vamos a manejar dentro de la celda.
 */
public abstract class NumberExcelFieldFormatter<N extends Number> implements ExcelFieldFormatter<N> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(NumberExcelFieldFormatter.class);

	@Override
	public N get(Cell cell, String pattern) {
		N number = null;

		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC && !DateUtil.isCellDateFormatted(cell)) {
			number = this.converter(cell.getNumericCellValue());
		} else {
			log.error("The cell isn't number type");
		}

		return number;
	}

	@Override
	public void set(Workbook workbook, Cell cell, String pattern, N value) {
		if (!StringUtil.isEmpty(pattern)) {
			CellStyle numberCellStyle = workbook.createCellStyle();
			numberCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(pattern));
			cell.setCellStyle(numberCellStyle);
		}

		cell.setCellType(Cell.CELL_TYPE_NUMERIC);

		if (value != null) {
			cell.setCellValue(value.doubleValue());
		} else {
			log.info("The value is null");
		}
	}

	/**
	 * Permite convertir de un valor doble al valor que queremos recuperar desde la celda.
	 * 
	 * @param number
	 *            El valor doble que queremos convertir al tipo objetivo.
	 * @return El valor doble convertido al tipo objetivo.
	 */
	protected abstract N converter(double number);
}