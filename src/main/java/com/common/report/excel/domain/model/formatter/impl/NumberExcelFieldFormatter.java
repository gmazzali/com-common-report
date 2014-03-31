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
 * 
 * @see http://stackoverflow.com/questions/5335285/write-number-in-excel-cell-with-poi
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class NumberExcelFieldFormatter implements ExcelFieldFormatter<Number> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(NumberExcelFieldFormatter.class);

	@Override
	public Number get(Cell cell, String pattern) {
		Double number = null;

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
			number = cell.getNumericCellValue();
		} else {
			log.error("The cell isn't number type");
		}

		return number;
	}

	@Override
	public void set(Workbook workbook, Cell cell, String pattern, Number value) {
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
}