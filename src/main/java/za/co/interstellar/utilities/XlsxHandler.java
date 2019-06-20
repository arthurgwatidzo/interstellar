package za.co.interstellar.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import za.co.interstellar.persistence.Edge;
import za.co.interstellar.persistence.Traffic;
import za.co.interstellar.persistence.Vertex;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public class XlsxHandler {

	private File file;

	public XlsxHandler() {
	}

	public XlsxHandler(File file) {
		this.file = file;
	}

	public List<Vertex> readVertexes() {
		List<Vertex> vertices = new ArrayList<>();
		try {
			FileInputStream inputStream = new FileInputStream(this.file);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				if (nextRow.getRowNum() == 0) {
					continue;
				}
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				Vertex vertex = new Vertex();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex + 1) {
					case 1:
						vertex.setVertexId((String) getCellValue(cell));
						break;
					case 2:
						vertex.setName((String) getCellValue(cell));
						break;
					}
				}
				vertices.add(vertex);
			}

			workbook.close();
			inputStream.close();
		} catch (IOException ex) {
			 //TODO: Add proper logging of this exception using log4j
		}
		return vertices;
	}

	public List<Edge> readEdges() {
		List<Edge> edges = new ArrayList<>();
		try {
			FileInputStream inputStream = new FileInputStream(this.file);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet firstSheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = firstSheet.iterator();

			int recordId = 1;
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();

				if (nextRow.getRowNum() == 0) {
					continue;
				}
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				Edge edge = new Edge();
				edge.setRecordId(recordId);
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex + 1) {
					case 1:
						edge.setEdgeId(String.valueOf((int) cell.getNumericCellValue()));
						break;
					case 2:
						edge.setSource(cell.getStringCellValue());
						break;
					case 3:
						edge.setDestination(cell.getStringCellValue());
						break;
					case 4:
						edge.setDistance((float) cell.getNumericCellValue());
						break;
					}
				}

				edges.add(edge);
				recordId = recordId + 1;
			}

			workbook.close();
			inputStream.close();
		} catch (IOException ex) {
			
			 //TODO: Add proper logging of this exception using log4j

		}
		return edges;
	}

	public List<Traffic> readTraffics() {
		List<Traffic> traffics = new ArrayList<>();
		try {
			FileInputStream inputStream = new FileInputStream(this.file);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet thirdSheet = workbook.getSheetAt(2);
			Iterator<Row> iterator = thirdSheet.iterator();

			while (iterator.hasNext()) {
				Row nextRow = iterator.next();

				if (nextRow.getRowNum() == 0) {
					continue;
				}
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				Traffic traffic = new Traffic();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex + 1) {
					case 1:
						traffic.setRouteId(String.valueOf((int) cell.getNumericCellValue()));
						break;
					case 2:
						traffic.setSource((String) getCellValue(cell));
						break;
					case 3:
						traffic.setDestination((String) getCellValue(cell));
						break;
					case 4:
						traffic.setDelay((float) cell.getNumericCellValue());
						break;
					}
				}
				traffics.add(traffic);
			}

			workbook.close();
			inputStream.close();
		} catch (IOException ex) {
			
			 //TODO: Add proper logging of this exception using log4j

		}

		return traffics;
	}

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}

}
