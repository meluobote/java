package excelInfo;

public class Field {
	private int Row;
	private int Column;
	private boolean nessarySign;
	private String fieldName;
	private String type;  //type="IFWXML:RequestHeader" or"xsd:string" or "IFWXML:ResponseHeader"
	public Field(int row, int column, boolean nessarySign, String fieldName, String type) {
		super();
		Row = row;
		Column = column;
		this.nessarySign = nessarySign;
		this.fieldName = fieldName;
		this.type = type;
	}
	public int getRow() {
		return Row;
	}
	public void setRow(int row) {
		Row = row;
	}
	public int getColumn() {
		return Column;
	}
	public void setColumn(int column) {
		Column = column;
	}
	public boolean isNessarySign() {
		return nessarySign;
	}
	public void setNessarySign(boolean nessarySign) {
		this.nessarySign = nessarySign;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
