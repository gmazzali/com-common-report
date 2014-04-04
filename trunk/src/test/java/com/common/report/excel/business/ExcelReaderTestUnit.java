package com.common.report.excel.business;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.common.report.excel.business.service.impl.ExcelReaderImpl;
import com.common.report.excel.domain.ExcelUtilTest;
import com.common.report.excel.domain.TestColumnModel;
import com.common.report.excel.domain.TestRowModel;

/**
 * El conjunto de pruebas para la lectura de un archivo de excel.
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class ExcelReaderTestUnit {

	private static Workbook workbook;

	@BeforeClass
	public static void init() {
		try {
			workbook = new XSSFWorkbook(new FileInputStream(ExcelUtilTest.FILE));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testRowRead() {
		try {
			List<TestRowModel> models = ExcelUtilTest.getTestRowModelList();

			ExcelReaderImpl<TestRowModel> reader = new ExcelReaderImpl<TestRowModel>() {
				private static final long serialVersionUID = 1L;
			};
			List<TestRowModel> readModels = reader.read(workbook);

			Assert.assertEquals(models.size(), readModels.size());

			for (int i = 0; i < readModels.size(); i++) {
				TestRowModel model = models.get(i);
				TestRowModel readModel = readModels.get(i);

				Assert.assertEquals(model.getNombre(), readModel.getNombre());
				Assert.assertEquals(model.getApellido(), readModel.getApellido());
				Assert.assertEquals(model.getFechaNacimiento(), readModel.getFechaNacimiento());
				Assert.assertEquals(model.getSueldo(), readModel.getSueldo());
				Assert.assertEquals(model.getEdad(), readModel.getEdad());
			}
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testColumnRead() {
		try {
			List<TestColumnModel> models = ExcelUtilTest.getTestColumnModelList();

			ExcelReaderImpl<TestColumnModel> reader = new ExcelReaderImpl<TestColumnModel>() {
				private static final long serialVersionUID = 1L;
			};
			List<TestColumnModel> readModels = reader.read(workbook);

			Assert.assertEquals(models.size(), readModels.size());

			for (int i = 0; i < readModels.size(); i++) {
				TestColumnModel model = models.get(i);
				TestColumnModel readModel = readModels.get(i);

				Assert.assertEquals(model.getNombre(), readModel.getNombre());
				Assert.assertEquals(model.getApellido(), readModel.getApellido());
				Assert.assertEquals(model.getFechaNacimiento(), readModel.getFechaNacimiento());
				Assert.assertEquals(model.getSueldo(), readModel.getSueldo());
				Assert.assertEquals(model.getEdad(), readModel.getEdad());
			}
		} catch (Exception e) {
			Assert.fail();
		}
	}
}