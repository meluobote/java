package tool;

public class CreatePortType {
	public static String getPortTypes(String operationname){
		StringBuffer sb=new StringBuffer();
		
		sb.append("<operation name=\"" + operationname +"\">");
		sb.append("<input message=\"IFWXML:" + operationname +"Request\"/>");
		sb.append("<output message=\"IFWXML:" +operationname+"Response\"/>");
		sb.append("<fault name=\"ErrorResponseType\" message=\"IFWXML:ErrorInfo\"/>");
		sb.append("</operation>");
		
		return sb.toString();
	}
}
