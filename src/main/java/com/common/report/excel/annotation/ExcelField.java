package com.common.report.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
	 * La posición que corresponde al campo dentro del archivo.
	 * 
	 * @return La posición del campo dentro del archivo.
	 */
	int posicion();

	/**
	 * El nombre del atributo.
	 * 
	 * @return El nombre del atributo.
	 */
	String name() default "";

	/**
	 * Define si va a cargarse con el valor 0 para los campos númericos en caso de que no se encuentre dato para este campo.
	 * 
	 * @return <i>true</i> en caso de que quiera cargarse el valor 0 para los valores númericos vacios, en caso contrario retornamos <i>false</i>.
	 */
	boolean zeroIfNull() default false;
}