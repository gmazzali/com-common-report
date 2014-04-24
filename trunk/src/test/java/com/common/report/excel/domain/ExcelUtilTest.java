package com.common.report.excel.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;

/**
 * Se usa para crear las listas a cargar y la ubicación del archivo.
 * 
 * @since 28/03/2014
 * @author Guillermo Mazzali
 * @version 1.0
 */
public class ExcelUtilTest {

	/**
	 * La ubicación del archivo.
	 */
	public static final String FILE = "C:\\Users\\gmazzali\\Desktop\\test.xlsx";

	@BeforeClass
	public static void initClass() {
		BasicConfigurator.configure();
	}

	/**
	 * Crea un listado de DTOs para prueba de carga por fila.
	 * 
	 * @return Un listado de DTOs para prueba.
	 * @throws Exception
	 *             En caso de que falle el parser de la fecha.
	 */
	public static List<TestRowModel> getTestRowModelList() throws Exception {
		List<TestRowModel> models = new ArrayList<TestRowModel>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		models.add(new TestRowModel("nombre 1", "Apellido 1", 10000000.1, format.parse("01/01/2014"), 1L));
		models.add(new TestRowModel("nombre 2", "Apellido 2", 20000000.2, format.parse("02/02/2014"), 2L));
		models.add(new TestRowModel("nombre 3", "Apellido 3", 30000000.3, format.parse("03/03/2014"), 3L));
		models.add(new TestRowModel("nombre 4", "Apellido 4", 40000000.4, format.parse("04/04/2014"), 4L));
		models.add(new TestRowModel("nombre 5", "Apellido 5", 50000000.5, format.parse("05/05/2014"), 5L));
		models.add(new TestRowModel("nombre 6", "Apellido 6", 60000000.6, format.parse("06/06/2014"), 6L));
		models.add(new TestRowModel("nombre 7", "Apellido 7", 70000000.7, format.parse("07/07/2014"), 7L));
		models.add(new TestRowModel(null, "Apellido 8", 80000000.8, format.parse("08/08/2014"), 8L));
		models.add(new TestRowModel("nombre 9", null, 90000000.9, format.parse("09/09/2014"), 9L));
		models.add(new TestRowModel("nombre 10", "Apellido 10", null, format.parse("10/10/2014"), 10L));
		models.add(new TestRowModel("nombre 11", "Apellido 11", 110000001.1, null, 11L));
		models.add(new TestRowModel("nombre 12", "Apellido 12", 120000001.2, format.parse("12/12/2014"), null));

		return models;
	}

	/**
	 * Crea un listado de DTOs para prueba de carga por columnas.
	 * 
	 * @return Un listado de DTOs para prueba.
	 * @throws Exception
	 *             En caso de que falle el parser de la fecha.
	 */
	public static List<TestColumnModel> getTestColumnModelList() throws Exception {
		List<TestColumnModel> models = new ArrayList<TestColumnModel>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		models.add(new TestColumnModel("nombre 1", "Apellido 1", 10000000.1, format.parse("01/01/2014"), 1L));
		models.add(new TestColumnModel("nombre 2", "Apellido 2", 20000000.2, format.parse("02/02/2014"), 2L));
		models.add(new TestColumnModel("nombre 3", "Apellido 3", 30000000.3, format.parse("03/03/2014"), 3L));
		models.add(new TestColumnModel("nombre 4", "Apellido 4", 40000000.4, format.parse("04/04/2014"), 4L));
		models.add(new TestColumnModel("nombre 5", "Apellido 5", 50000000.5, format.parse("05/05/2014"), 5L));
		models.add(new TestColumnModel("nombre 6", "Apellido 6", 60000000.6, format.parse("06/06/2014"), 6L));
		models.add(new TestColumnModel("nombre 7", "Apellido 7", 70000000.7, format.parse("07/07/2014"), 7L));
		models.add(new TestColumnModel(null, "Apellido 8", 80000000.8, format.parse("08/08/2014"), 8L));
		models.add(new TestColumnModel("nombre 9", null, 90000000.9, format.parse("09/09/2014"), 9L));
		models.add(new TestColumnModel("nombre 10", "Apellido 10", null, format.parse("10/10/2014"), 10L));
		models.add(new TestColumnModel("nombre 11", "Apellido 11", 110000001.1, null, 11L));
		models.add(new TestColumnModel("nombre 12", "Apellido 12", 120000001.2, format.parse("12/12/2014"), null));

		return models;
	}
}
