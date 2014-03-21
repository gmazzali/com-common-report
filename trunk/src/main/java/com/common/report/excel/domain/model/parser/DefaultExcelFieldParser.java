package com.common.report.excel.domain.model.parser;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

/**
 * El parseador por omisión que tenemos dentro del sistema.
 * 
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class DefaultExcelFieldParser implements ExcelFieldParser<Object> {

	@Override
	public Object get(Cell cell) {
		if (cell == null) {
			return null;
		}
		int type = cell.getCellType();
		switch (type) {
		case Cell.CELL_TYPE_BLANK:
			return null;

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();

		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();

		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		default:
			return null;
		}
	}

	@Override
	public void set(Cell cell, Object object) {
		if (cell == null) {
			return;
		}
		if (object != null) {
			if (object instanceof String) {
				cell.setCellValue(object.toString());
			} else if (object instanceof Boolean) {
				cell.setCellValue((Boolean) object);
			} else if (object instanceof Date) {
				cell.setCellValue((Date) object);
			} else if (object instanceof Number) {
				cell.setCellValue(((Number) object).doubleValue());
			}
		} else {
			cell.setCellValue("");
		}
	}
}