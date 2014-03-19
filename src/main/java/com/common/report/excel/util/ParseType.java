package com.common.report.excel.util;

/**
 * Define el modo de parseo que vamos a realizar dentro del archivo de excel, es decir, si los datos van a leerse de arriba a abajo o de derecha a
 * izquierda.
 * 
 * <ul>
 * <li>{@link ParseType#ROW} -> Indica lectura de datos de arriba a abajo.</li>
 * <li>{@link ParseType#COLUMN} -> Indica lectura de datos de derecha a izquierda.</li>
 * </ul>
 * 
 * @since 19/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public enum ParseType {
	ROW, COLUMN
}