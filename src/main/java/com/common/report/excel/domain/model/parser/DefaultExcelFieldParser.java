package com.common.report.excel.domain.model.parser;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * El parseador por omisión que tenemos dentro del sistema.
 * 
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class DefaultExcelFieldParser implements ExcelFieldParser<Object> {

	@Override
	public Object get(Cell cell, Class<Object> clazz) {
		if (cell == null) {
			return null;
		}
		int type = cell.getCellType();
		Object value = null;
		switch (type) {
		case Cell.CELL_TYPE_BLANK:
			value = null;
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			value = clazz.cast(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_ERROR:
			value = cell.getErrorCellValue();
			break;

		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();

			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				value = cell.getDateCellValue();
			} else {
				value = cell.getNumericCellValue();
			}
			break;

		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;

		default:
			value = null;
			break;
		}

		if (value != null) {
			return clazz.cast(value);
		} else {
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