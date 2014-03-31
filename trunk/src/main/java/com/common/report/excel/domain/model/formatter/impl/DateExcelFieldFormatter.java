package com.common.report.excel.domain.model.formatter.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;
import com.common.util.business.tool.StringUtil;

/**
 * El formateador de las fechas que vamos a usar dentro de un archivo de excel.
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class DateExcelFieldFormatter implements ExcelFieldFormatter<Date> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DateExcelFieldFormatter.class);

	@Override
	public Date get(Cell cell, String pattern) {
		Date date = null;

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
			date = cell.getDateCellValue();
		} else {
			log.error("The cell isn't date type");
		}

		return date;
	}

	@Override
	public void set(Workbook workbook, Cell cell, String pattern, Date value) {
		if (!StringUtil.isEmpty(pattern)) {
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(pattern));
			cell.setCellStyle(dateCellStyle);
		}

		cell.setCellType(Cell.CELL_TYPE_NUMERIC);

		if (value != null) {
			cell.setCellValue(value);
		} else {
			log.info("The value is null");
		}
	}
}