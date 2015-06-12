package com.common.report.excel.domain.model.formatter.impl;

import org.apache.log4j.Logger;

/**
 * El formateador de los números enteros largos que vamos a usar dentro de un archivo de excel.
 * 
 * @since 01/04/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class LongExcelFieldFormatter extends NumberExcelFieldFormatter<Long> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LongExcelFieldFormatter.class);

	@Override
	protected Long converter(double number) {
		try {
			return new Long((long) number);
		} catch (Exception e) {
			log.error("The data isn't long", e);
			return null;
		}
	}
}