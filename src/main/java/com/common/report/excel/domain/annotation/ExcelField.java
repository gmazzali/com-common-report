package com.common.report.excel.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.common.report.excel.domain.model.formatter.ExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.DateExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.FormulaExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.NumberExcelFieldFormatter;

/**
 * Define un campo dentro de un archivo de excel a partir de un atributo.
 * 
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ExcelField {

	/**
	 * La posición que corresponde al campo dentro del archivo (se comienza a contar las posiciones desde el valor 0).
	 * 
	 * @return La posición del campo dentro del archivo (se comienza a contar las posiciones desde el valor 0).
	 */
	int posicion();

	/**
	 * El nombre del atributo.
	 * 
	 * @return El nombre del atributo.
	 */
	String name() default "";

	/**
	 * El patrón que vamos a uilizar dentro de este campo dentro del archivo de excel.
	 * 
	 * @return El patrón que corresponde con este campo.
	 */
	String pattern();

	/**
	 * El parseador que vamos a utilizar con este campo dentro del excel.
	 * 
	 * <ul>
	 * <li>{@link DateExcelFieldFormatter}</li>
	 * <li>{@link NumberExcelFieldFormatter}</li>
	 * <li>{@link FormulaExcelFieldFormatter}</li>
	 * <li>{@link StringExcelFieldFormatter}</li>
	 * </ul>
	 * 
	 * @return El parseador que vamos a utilizar dentro de este campo.
	 */
	Class<? extends ExcelFieldFormatter<?>> parser();

	/**
	 * Define si va a cargarse una celda vacía en la celda del campo en caso de que el valor de este sea nulo.
	 * 
	 * @return <i>true</i> en caso de que quiera cargarse una celda vacía en la celda del campo en caso de que el valor de este sea nulo, en caso
	 *         contrario retornamos <i>false</i>.
	 */
	boolean emptyIfNull() default true;
}