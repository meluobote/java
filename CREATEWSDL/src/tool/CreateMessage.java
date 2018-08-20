package tool;

public class CreateMessage {
	public static String getMessageBolcks(String operationname){
		StringBuffer sb =new StringBuffer();
		sb.append("<message name=\"" + operationname + "Request\">");
		sb.append("<part name=\"" + operationname +"\" element=\"IFWXML:"+ 
		operationname +"Request\" /></message>");
		
		sb.append("<message name=\"" + operationname + "Response\">");
		sb.append("<part name=\"" + operationname +"\" element=\"IFWXML:"+ 
		operationname +"Response\" /></message>");
		return sb.toString();
	}
}
