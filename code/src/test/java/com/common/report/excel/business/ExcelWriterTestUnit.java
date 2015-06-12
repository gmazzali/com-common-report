package com.common.report.excel.business;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.common.report.excel.business.service.ExcelWriter;
import com.common.report.excel.business.service.impl.ExcelWriterImpl;
import com.common.report.excel.domain.ExcelUtilTest;
import com.common.report.excel.domain.TestColumnModel;
import com.common.report.excel.domain.TestRowModel;

/**
 * Las pruebas para la escritura de un excel.
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class ExcelWriterTestUnit {

	private static Workbook workbook;

	@BeforeClass
	public static void initClass() {
		BasicConfigurator.configure();
		workbook = new XSSFWorkbook();
	}

	@AfterClass
	public static void end() {
		try {
			// Creo el archivo en el disco C:
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			workbook.write(output);
			output.close();

			FileOutputStream fileOutput = new FileOutputStream(ExcelUtilTest.FILE);
			fileOutput.write(output.toByteArray());
			fileOutput.close();
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testRowWrite() {
		try {
			ExcelWriter<TestRowModel> writer = new ExcelWriterImpl<TestRowModel>() {
				private static final long serialVersionUID = 1L;
			};
			writer.write(workbook, ExcelUtilTest.getTestRowModelList());

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testColumnWrite() {
		try {
			ExcelWriter<TestColumnModel> writer = new ExcelWriterImpl<TestColumnModel>() {
				private static final long serialVersionUID = 1L;
			};
			writer.write(workbook, ExcelUtilTest.getTestColumnModelList());

		} catch (Exception e) {
			Assert.fail();
		}
	}
}