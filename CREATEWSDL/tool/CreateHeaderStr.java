package tool;

public class CreateHeaderStr {
	public static String createHeaderStr(String namespace){
		StringBuffer sb=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<definitions name=\"IFWXML_WS\" targetNamespace=\""
				+namespace+"\"");
		sb.append(" xmlns:IFWXML=\"" + namespace +"\"");
		sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns=\"http://schemas.xmlsoap.org/wsdl/\">");
		sb.append("<types><xsd:schema targetNamespace=\"" + namespace +"\">");
		
		//RequestHeader
		sb.append("<xsd:complexType name=\"RequestHeader\">");
		sb.append("<xsd:sequence>");
		sb.append("<xsd:element name=\"businessAcceptId\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"serialNumber\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"version\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"cmdType\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"requester\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"channel\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"requestTimeStamp\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"organizationUnitId\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"teller1\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"teller2\" type=\"xsd:string\"/>");
		sb.append("</xsd:sequence>");
		sb.append("</xsd:complexType>");
		sb.append("<xsd:element name=\"RequestHeader\" type=\"IFWXML:RequestHeader\"/>");
		//ResponseHeader
		sb.append("<xsd:complexType name=\"ResponseHeader\">");
		sb.append("<xsd:sequence>");
		sb.append("<xsd:element name=\"hostSerialNo\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"responseTimeStamp\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"errorNo\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"errorInfo\" type=\"xsd:string\"/>");
		sb.append("</xsd:sequence>");
		sb.append("</xsd:complexType>");
		sb.append("<xsd:element name=\"ResponseHeader\" type=\"IFWXML:ResponseHeader\"/>");
		
		//ErrorInfo
		sb.append("<xsd:complexType name=\"ErrorInfo\">");
		sb.append("<xsd:sequence>");
		sb.append("<xsd:element name=\"errorMessageType\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"errorCode\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"errorMessageText\" type=\"xsd:string\"/>");
		sb.append("<xsd:element name=\"errorState\" type=\"xsd:string\"/>");
		sb.append("</xsd:sequence>");
		sb.append("</xsd:complexType>");
		sb.append("<xsd:element name=\"ErrorInfo\" type=\"IFWXML:ErrorInfo\"/>");
		
		return sb.toString();
	}
}
