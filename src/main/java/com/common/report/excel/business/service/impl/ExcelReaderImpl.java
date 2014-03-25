package com.common.report.excel.business.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.report.excel.business.service.ExcelReader;
import com.common.report.excel.business.util.ExcelProcess;
import com.common.report.excel.business.util.ExcelUtils;
import com.common.report.excel.domain.model.ExcelDto;

@SuppressWarnings("unchecked")
public abstract class ExcelReaderImpl<E extends ExcelDto> implements ExcelReader<E> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ExcelProcess.class);

	/**
	 * La clase que manejamos dentro del excel.
	 */
	private final Class<E> excelDtoClass;

	public ExcelReaderImpl() {
		this.excelDtoClass = (Class<E>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public List<E> read(Workbook workbook) {
		return ExcelUtils.read(workbook, this.excelDtoClass);
	}
}