package excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class T {

	public static void main(String[] args) throws BiffException, IOException {
		// TODO Auto-generated method stub
		Workbook wb=Workbook.getWorkbook(new File("t.xls"));
		Sheet sheet=wb.getSheet("Sheet1");
		Range[] ranges=sheet.getMergedCells();
		for(Range range:ranges){
			Cell tmp=range.getTopLeft();
			Cell br=range.getBottomRight();
			System.out.println("topleft"+tmp.getContents());
			System.out.println("bottomright"+br.getContents());
		}
	}

}
