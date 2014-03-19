package com.common.report.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.common.report.excel.util.ParseType;

/**
 * Define una clase que vamos a mapear con un archivo de excel.
 * 
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ExcelClass {

	/**
	 * Indica el nombre de la hoja que vamos a leer dentro del archivo.
	 * 
	 * @return El nombre de la hoja que vamos a leer dentro del archivo.
	 */
	String sheet() default "";

	/**
	 * Indica el valor a partir de donde vamos a comenzar a leer los datos.
	 * 
	 * @return El valor a partir de donde vamos a comenzar a leer los datos.
	 */
	int start();

	/**
	 * Indica el valor hasta donde vamos a leer los datos.
	 * 
	 * @return El valor hasta donde vamos a leer los datos.
	 */
	int end() default Integer.MAX_VALUE;

	/**
	 * Define el tipo de parseo que vamos a realizar sobre los datos dentro del archivo.
	 * 
	 * @return El tipo de parseo que vamos a realizar sobre los datos dentro del archivo.
	 */
	ParseType parseType();

	/**
	 * Indica que la lectura de los campos dentro de un archivo de excel finalizará en caso de que se encuentre un fila o columna con datos vacíos
	 * completamente.
	 * 
	 * @return Si es <i>true</i> se corta la lectura de los campos cuando se lea una fila o columna con datos completamente vacíos, en caso de
	 *         retornar <i>false</i> se completa la lectura hasta el valor definido en {@link #end()}.
	 */
	boolean breakWithNull() default true;
}