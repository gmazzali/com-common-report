package com.common.report.excel.domain.model.formatter.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * El formateador de los números decimales grandes que vamos a usar dentro de un archivo de excel.
 * 
 * @since 01/04/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class BigDecimalExcelFieldFormatter extends NumberExcelFieldFormatter<BigDecimal> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BigDecimalExcelFieldFormatter.class);

	@Override
	protected BigDecimal converter(double number) {
		try {
			return new BigDecimal(number);
		} catch (Exception e) {
			log.error("The data isn't big decimal", e);
			return null;
		}
	}
}