package tool;

import excelInfo.BodyList;
import excelInfo.Field;
import excelInfo.IFWService;
import excelInfo.Input;
import excelInfo.Output;

public class CreateReqRespStr {
	public static String createRequestAndResponseStr(IFWService ifws){
		StringBuffer sb=new StringBuffer("<!--"+ifws.getServiceName()+"-->");
		
		Input in=ifws.getInput();
		
		if(in.isList_exist()){
			BodyList bl=in.getBodylist();
			Field[] fields=bl.getFields();
			//request普通字段里的list的type属性，对应的是下面的name
//			sb.append("<xsd:complexType name=\"" +ifws.getServiceName() +"RequestBodyListRow\">");
			sb.append("<xsd:complexType name=\"" +ifws.getServiceName() +"RequestBodyList\">");
			sb.append("<xsd:sequence minOccurs=\"0\" maxOccurs=\"1\">");
			for(int i=0;i<fields.length; i++){
				if(fields[i].isNessarySign()){
					sb.append("<xsd:element name=\"" + fields[i].getFieldName()
							+ "\" type=\"" + fields[i].getType()+"\"/>");
				}else{
					sb.append("<xsd:element minOccurs=\"0\" name=\"" + fields[i].getFieldName()
							+ "\" type=\"" + fields[i].getType()+"\"/>");
				}
			}
			sb.append("</xsd:sequence></xsd:complexType>");
			
//			sb.append("<xsd:complexType name=\"" + ifws.getServiceName() +
//					"RequestBodyList\">");
//			sb.append("<xsd:sequence minOccrus=\"0\" maxOccurs=\"unbounded\">");
////			sb.append("<xsd:element name=\"row\" type=\"IFWML:" +
////						ifws.getServiceName()+"RequestBodyListRow\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>");
//			sb.append("<xsd:element name=\"row\" type=\"IFWML:" +
//					ifws.getServiceName()+"RequestBodyList\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>");
//			sb.append("</xsd:sequence></xsd:complexType>");
			
		}
		//生成Request
		sb.append("<xsd:complexType name=\"" +ifws.getServiceName() +"Request\">");
		sb.append("<xsd:annotation><xsd:documentation>"+ifws.getServiceID() + "</xsd:documentation></xsd:annotation>");
		sb.append("<xsd:sequence minOccurs=\"0\" maxOccurs=\"1\">");
		
		Field[] in_fields=in.getFields();
		for(int i=0;i<in_fields.length;i++){
			if(in_fields[i].isNessarySign()){
				sb.append("<xsd:element name=\"" + in_fields[i].getFieldName()
						+ "\" type=\"" + in_fields[i].getType()+"\"/>");
			}else{
				sb.append("<xsd:element minOccurs=\"0\" name=\"" + in_fields[i].getFieldName()
						+ "\" type=\"" + in_fields[i].getType()+"\"/>");
			}
		}
		
		if(in.isList_exist()){
			sb.append("<xsd:element name=\""+ifws.getServiceName()+"RequestList\" type=\"IFWXML:" + ifws.getServiceName() 
						+"RequestBodyList\" minOccurs=\"0\" maxOccurs=\"1\"/>");
		}
		sb.append("</xsd:sequence></xsd:complexType>");
		sb.append("<xsd:element name=\""+ ifws.getServiceName()
					+"Request\" type=\"IFWXML:" + ifws.getServiceName() +"Request\"/>");
		
		
		//Output
		Output out=ifws.getOutput();
		
		if(out.isList_exist()){
			BodyList bl=out.getBodylist();
			Field[] fields=bl.getFields();
			//request普通字段里的list的type属性，对应的是下面的name
			sb.append("<xsd:complexType name=\"" +ifws.getServiceName() +
					"ResponseBodyList\">");
			sb.append("<xsd:sequence minOccurs=\"0\" maxOccurs=\"1\">");
			for(int i=0;i<fields.length; i++){
				if(fields[i].isNessarySign()){
					sb.append("<xsd:element name=\"" + fields[i].getFieldName()
							+ "\" type=\"" + fields[i].getType()+"\"/>");
				}else{
					sb.append("<xsd:element minOccurs=\"0\" name=\"" + fields[i].getFieldName()
							+ "\" type=\"" + fields[i].getType()+"\"/>");
				}
			}
			sb.append("</xsd:sequence></xsd:complexType>");
			
//			sb.append("<xsd:complexType name=\"" + ifws.getServiceName() +
//					"RequestBodyList\">");
//			sb.append("<xsd:sequence minOccrus=\"0\" maxOccurs=\"unbounded\">");
//			sb.append("<xsd:element name=\"row\" type=\"IFWML:" +
//						ifws.getServiceName()+"ResponseBodyListRow\" maxOccurs=\"unbounded\" minOccurs=\"0\"/>");
//			sb.append("</xsd:sequence></xsd:complexType>");
			
		}
		//生成Response
		sb.append("<xsd:complexType name=\"" +ifws.getServiceName() +"Response\">");
		sb.append("<xsd:annotation><xsd:documentation>"+ifws.getServiceID() + "</xsd:documentation></xsd:annotation>");
		sb.append("<xsd:sequence minOccurs=\"0\" maxOccurs=\"1\">");
		
		Field[] out_fields=out.getFields();
		for(int i=0;i<out_fields.length;i++){
			if(out_fields[i].isNessarySign()){
				sb.append("<xsd:element name=\"" + out_fields[i].getFieldName()
						+ "\" type=\"" + out_fields[i].getType()+"\"/>");
			}else{
				sb.append("<xsd:element minOccurs=\"0\" name=\"" + out_fields[i].getFieldName()
						+ "\" type=\"" + out_fields[i].getType()+"\"/>");
			}
		}
		
		if(out.isList_exist()){
			sb.append("<xsd:element name=\""+ifws.getServiceName()+"ResponseList\" type=\"IFWXML:" + ifws.getServiceName() 
						+"ResponseBodyList\" minOccurs=\"0\" maxOccurs=\"1\"/>");
		}
		sb.append("</xsd:sequence></xsd:complexType>");
		sb.append("<xsd:element name=\""+ ifws.getServiceName()
					+"Response\" type=\"IFWXML:" + ifws.getServiceName() +"Response\"/>");
		
		return sb.toString();
	}
}
