package com.common.report.excel.domain;

import java.util.Date;

import com.common.report.excel.domain.annotation.ExcelClass;
import com.common.report.excel.domain.annotation.ExcelField;
import com.common.report.excel.domain.model.ExcelDto;
import com.common.report.excel.domain.model.ParseType;
import com.common.report.excel.domain.model.formatter.impl.DateExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.DoubleExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.LongExcelFieldFormatter;
import com.common.report.excel.domain.model.formatter.impl.StringExcelFiledFormatter;

/**
 * El modelo de prueba para los excel.
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
@ExcelClass(sheet = "TEST_ROW", start = 0, end = 100, parseType = ParseType.COLUMN, breakWithNull = true)
public class TestRowModel extends ExcelDto {
	private static final long serialVersionUID = 1L;

	@ExcelField(name = "Nombre", posicion = 0, parser = StringExcelFiledFormatter.class, emptyIfNull = true)
	private String nombre;

	@ExcelField(name = "Apellido", posicion = 1, parser = StringExcelFiledFormatter.class, emptyIfNull = true)
	private String apellido;

	@ExcelField(name = "Sueldo", posicion = 2, parser = DoubleExcelFieldFormatter.class, emptyIfNull = true, pattern="#,##0.00")
	private Double sueldo;

	@ExcelField(name = "Fecha de nacimiento", posicion = 3, parser = DateExcelFieldFormatter.class, emptyIfNull = true, pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;

	@ExcelField(name = "Edad", posicion = 4, parser = LongExcelFieldFormatter.class, emptyIfNull = true, pattern="###")
	private Long edad;
	
	public TestRowModel() {
		super();
	}

	public TestRowModel(String nombre, String apellido, Double sueldo, Date fechaNacimiento, Long edad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.sueldo = sueldo;
		this.fechaNacimiento = fechaNacimiento;
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getEdad() {
		return edad;
	}

	public void setEdad(Long edad) {
		this.edad = edad;
	}

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}