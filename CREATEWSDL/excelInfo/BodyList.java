package excelInfo;

public class BodyList {
	private String listName;
	private int minRow;
	private int maxRow;
	private int len;
	private Field[] fields;
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public int getMinRow() {
		return minRow;
	}
	public void setMinRow(int minRow) {
		this.minRow = minRow;
	}
	public int getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public Field[] getFields() {
		return fields;
	}
	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	
}
