package com.common.report.excel.domain.model.formatter.impl;

import org.apache.log4j.Logger;

/**
 * El formateador de los números enteros que vamos a usar dentro de un archivo de excel.
 * 
 * @since 01/04/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class IntegerExcelFieldFormatter extends NumberExcelFieldFormatter<Integer> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(IntegerExcelFieldFormatter.class);

	@Override
	protected Integer converter(double number) {
		try {
			return new Integer((int) number);
		} catch (Exception e) {
			log.error("The data isn't int", e);
			return null;
		}
	}
}