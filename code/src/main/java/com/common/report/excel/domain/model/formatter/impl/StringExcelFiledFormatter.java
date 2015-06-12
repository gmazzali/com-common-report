package com.common.report.excel.domain.model.formatter.impl;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;
import com.common.util.business.tool.StringUtil;

/**
 * El formateador de las cadenas de textos que vamos a usar dentro de un archivo de excel. En caso de que la cadena dentro de la celda sea vacía se
 * toma como un valor nulo.
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class StringExcelFiledFormatter implements ExcelFieldFormatter<String> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(StringExcelFiledFormatter.class);

	@Override
	public String get(Cell cell, String pattern) {
		String string = null;

		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
			string = cell.getStringCellValue();
		} else {
			log.error("The cell isn't string type");
		}

		return !StringUtil.isBlank(string) ? string : null;
	}

	@Override
	public void set(Workbook workbook, Cell cell, String pattern, String value) {
		if (!StringUtil.isEmpty(pattern)) {
			CellStyle stringCellStyle = workbook.createCellStyle();
			stringCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(pattern));
			cell.setCellStyle(stringCellStyle);
		}

		cell.setCellType(Cell.CELL_TYPE_STRING);

		if (value != null) {
			cell.setCellValue(value);
		} else {
			log.info("The value is null");
		}
	}
}