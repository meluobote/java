package tool;

public class CreateBindings {
	public static String getBindings(String operationname){
		StringBuffer sb=new StringBuffer();
		sb.append("<operation name=\"" + operationname + "\">");
		sb.append("<soap:operation soapAction=\"http://www.ibm.com/ima/ifw/"
				+ operationname + "\"/>");
		sb.append("<input><soap:body use=\"literal\"/></input><output><soap:body use=\"literal\"/></output><fault name=\"ErrowResponseType\"><soap:fault use=\"literal\" name=\"ErrorResponseType\" namespace=\"\"/></fault>");
		sb.append("</operation>");
		return sb.toString();
	}
}
