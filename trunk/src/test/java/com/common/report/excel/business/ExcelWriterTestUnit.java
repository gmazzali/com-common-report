package com.common.report.excel.business;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.common.report.excel.business.service.ExcelWriter;
import com.common.report.excel.business.service.impl.ExcelWriterImpl;
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

	private static final String FILE = "C:\\Users\\gmazzali\\Desktop\\test.xlsx";

	private static Workbook workbook;

	@BeforeClass
	public static void init() {
		workbook = new XSSFWorkbook();
	}

	@AfterClass
	public static void end() {
		try {
			// Creo el archivo en el disco C:
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			workbook.write(output);
			output.close();

			FileOutputStream fileOutput = new FileOutputStream(FILE);
			fileOutput.write(output.toByteArray());
			fileOutput.close();
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testRowWrite() {
		try {
			List<TestRowModel> models = new ArrayList<TestRowModel>();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			models.add(new TestRowModel("nombre 1", "Apellido 1", 10000000.1, format.parse("01/01/2014"), 1L));
			models.add(new TestRowModel("nombre 2", "Apellido 2", 20000000.2, format.parse("02/02/2014"), 2L));
			models.add(new TestRowModel("nombre 3", "Apellido 3", 30000000.3, format.parse("03/03/2014"), 3L));
			models.add(new TestRowModel("nombre 4", "Apellido 4", 40000000.4, format.parse("04/04/2014"), 4L));
			models.add(new TestRowModel("nombre 5", "Apellido 5", 50000000.5, format.parse("05/05/2014"), 5L));
			models.add(new TestRowModel("nombre 6", "Apellido 6", 60000000.6, format.parse("06/06/2014"), 6L));
			models.add(new TestRowModel("nombre 7", "Apellido 7", 70000000.7, format.parse("07/07/2014"), 7L));

			ExcelWriter<TestRowModel> writer = new ExcelWriterImpl<TestRowModel>() {
				private static final long serialVersionUID = 1L;
			};
			writer.write(workbook, models);

		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testColumnWrite() {
		try {
			List<TestColumnModel> models = new ArrayList<TestColumnModel>();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			models.add(new TestColumnModel("nombre 1", "Apellido 1", 10000000.1, format.parse("01/01/2014"), 1L));
			models.add(new TestColumnModel("nombre 2", "Apellido 2", 20000000.2, format.parse("02/02/2014"), 2L));
			models.add(new TestColumnModel("nombre 3", "Apellido 3", 30000000.3, format.parse("03/03/2014"), 3L));
			models.add(new TestColumnModel("nombre 4", "Apellido 4", 40000000.4, format.parse("04/04/2014"), 4L));
			models.add(new TestColumnModel("nombre 5", "Apellido 5", 50000000.5, format.parse("05/05/2014"), 5L));
			models.add(new TestColumnModel("nombre 6", "Apellido 6", 60000000.6, format.parse("06/06/2014"), 6L));
			models.add(new TestColumnModel("nombre 7", "Apellido 7", 70000000.7, format.parse("07/07/2014"), 7L));

			ExcelWriter<TestColumnModel> writer = new ExcelWriterImpl<TestColumnModel>() {
				private static final long serialVersionUID = 1L;
			};
			writer.write(workbook, models);

		} catch (Exception e) {
			Assert.fail();
		}
	}
}