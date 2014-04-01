package com.common.report.excel.domain.model.formatter.impl;

import org.apache.log4j.Logger;

/**
 * El formateador de los números dobles que vamos a usar dentro de un archivo de excel.
 * 
 * @since 01/04/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class DoubleExcelFieldFormatter extends NumberExcelFieldFormatter<Double> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DoubleExcelFieldFormatter.class);

	@Override
	protected Double converter(double number) {
		try {
			return new Double(number);
		} catch (Exception e) {
			log.error("The data isn't double", e);
			return null;
		}
	}
}