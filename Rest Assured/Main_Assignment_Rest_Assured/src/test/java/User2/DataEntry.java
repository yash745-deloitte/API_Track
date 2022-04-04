package User2;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class DataEntry {
    String filePath = "src/test/resources/Details.xlsx";

    public JSONObject DataEntry() {
        JSONObject sheetsJSONObject = new JSONObject();
        Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook(filePath);
        } catch (IOException e) {
            System.out.println("Input Exception");
        }

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

            JSONArray sheetArray = new JSONArray();
            ArrayList<String> columnNames = new ArrayList<String>();
            Sheet sheet = workbook.getSheetAt(i);
            Iterator<Row> sheetIterator = sheet.iterator();

            while (sheetIterator.hasNext()) {

                Row currentRow = sheetIterator.next();
                JSONObject jsonObject = new JSONObject();

                if (currentRow.getRowNum() != 0) {

                    for (int j = 0; j < columnNames.size(); j++) {

                        if (currentRow.getCell(j) != null) {
                            if (currentRow.getCell(j).getCellType() == STRING) {
                                jsonObject.put(columnNames.get(j), currentRow.getCell(j).getStringCellValue());
                            } else if (currentRow.getCell(j).getCellType() == NUMERIC) {
                                jsonObject.put(columnNames.get(j), currentRow.getCell(j).getNumericCellValue());
                            }
                        }
                        else {
                            jsonObject.put(columnNames.get(j), "");
                        }
                    }

                    sheetArray.put(jsonObject);

                } else {
                    // store column names
                    for (int k = 0; k < currentRow.getPhysicalNumberOfCells(); k++) {
                        columnNames.add(currentRow.getCell(k).getStringCellValue());
                    }
                }

            }

            sheetsJSONObject.put(workbook.getSheetName(i), sheetArray);

        }

        return sheetsJSONObject;

    }
}