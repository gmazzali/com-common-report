package com.common.report.excel.domain.model.formatter.impl;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;
import com.common.util.business.tool.StringUtil;

/**
 * El formateador de las formulas que vamos a usar dentro de un archivo de excel.
 * 
 * @see http://stackoverflow.com/questions/5578535/get-cell-value-from-excel-sheet-with-apache-poi
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class FormulaExcelFieldFormatter implements ExcelFieldFormatter<String> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(FormulaExcelFieldFormatter.class);

	@Override
	public String get(Cell cell, String pattern) {
		String formula = null;

		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			formula = cell.getCellFormula();
		} else {
			log.error("The cell isn't formula type");
		}

		return formula;
	}

	@Override
	public void set(Workbook workbook, Cell cell, String pattern, String value) {
		if (!StringUtil.isEmpty(pattern)) {
			CellStyle formulaCellStyle = workbook.createCellStyle();
			formulaCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(pattern));
			cell.setCellStyle(formulaCellStyle);
		}

		cell.setCellType(Cell.CELL_TYPE_FORMULA);

		if (value != null) {
			cell.setCellFormula(value);
		} else {
			log.info("The value is null");
		}
	}
}