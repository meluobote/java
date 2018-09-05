package excelInfo;

public abstract class Put {
	int minRow;
	int maxRow;
	Field[] fields;
	BodyList bodylist=null;
	boolean list_exist=false;
	
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
	public Field[] getFields() {
		return fields;
	}
	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	public BodyList getBodylist() {
		return bodylist;
	}
	public void setBodylist(BodyList bodylist) {
		this.bodylist = bodylist;
	}
	public boolean isList_exist() {
		return list_exist;
	}
	public void setList_exist(boolean list_exist) {
		this.list_exist = list_exist;
	}
	
	
}
