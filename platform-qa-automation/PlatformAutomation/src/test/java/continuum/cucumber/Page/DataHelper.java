package continuum.cucumber.Page;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.itextpdf.text.log.SysoCounter;

public class DataHelper {

	static Connection con = getConnection(
			System.getProperty("user.dir") + "\\src\\test\\resources\\Data\\testData.xlsx");

	/**
	 * @author rakesh.karkare
	 * @param strFileName
	 * @return fillo connection object
	 */
	public static Connection getConnection(String strFileName) {
		Connection connection = null;
		Fillo fillo = new Fillo();
		try {
			connection = fillo.getConnection(strFileName);
		} catch (FilloException e) {

			e.printStackTrace();
		}

		return connection;
	}

	/**
	 * @author rakesh.karkare
	 * @param strQuery
	 * @return fillo recordset
	 */

	public static Recordset getRecordSet(String strQuery) {

		Recordset recordset = null;
		try {
			System.out.println("inside getRecordSet strQuery is " + strQuery);
			recordset = con.executeQuery(strQuery);
		} catch (FilloException e) {

			e.printStackTrace();
		}
		return recordset;
	}

	/**
	 * @author rakesh.karkare
	 * @param Scenario
	 * @param sheetName
	 * @return list of hashmap data This method is used to fetch data from excel
	 *         based on scenario and store it in list of hashmap
	 */
	public static List<HashMap<String, String>> data(String Scenario, String sheetName) {
		List<HashMap<String, String>> mydata = new ArrayList<>();
		String filepath = System.getProperty("user.dir") + "\\src\\test\\resources\\Data\\testData.xlsx";
		//System.out.println("filepath ==>" + filepath);
		try {
			FileInputStream fs = new FileInputStream(filepath);
			XSSFWorkbook workbook = new XSSFWorkbook(fs);
			//System.out.println(sheetName);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Row HeaderRow = sheet.getRow(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row currentRow = sheet.getRow(i);
				HashMap<String, String> currentHash = new HashMap<String, String>();
				if (currentRow.getCell(1).getStringCellValue().contains(Scenario)) {

					for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
						Cell currentCell = currentRow.getCell(j);

						// if(currentRow.getCell(0).getStringCellValue().contains(Scenario)){

						switch (currentCell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							// System.out.print(currentCell.getStringCellValue()
							// + "\t");

							currentHash.put(HeaderRow.getCell(j).getStringCellValue(),
									currentCell.getStringCellValue());

							break;
						}

						// }

					}

					mydata.add(currentHash);
				}

			}
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mydata;
	}

	/**
	 * @author rakesh.karkare
	 * @param sheetName
	 * @param colName
	 * @param testToBeSaved
	 * @param AutomationID
	 *            This Method is useful to update column value in excel
	 */
	public void exportDataToExcel(String sheetName, String colName, String testToBeSaved, String AutomationID) {
		if (testToBeSaved == null || testToBeSaved == "" | testToBeSaved.contains("NA")) {
			return;
		}
		try {
			String updateQuery = "Update " + sheetName + " Set " + colName + "='" + testToBeSaved + "' where AutoID='"
					+ AutomationID + "'";
			int i = con.executeUpdate(updateQuery);
			System.out.println(i);
		} catch (FilloException e) {

			e.printStackTrace();
		}

	}

}
