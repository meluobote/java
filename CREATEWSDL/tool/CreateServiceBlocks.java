package tool;

public class CreateServiceBlocks {
	public static String getServiceBlocksStr(String interfacename){
		String address="http://esb.abc.com:7080/soa/services/I"+interfacename;
		
		StringBuffer sb=new StringBuffer("<!-- SERVICEBLOCKS -->");
		sb.append("<service name=\"" + interfacename +"Service\">");
		sb.append("<port name=\""+interfacename+"SOAPPORT\" binding=\"IFWXML:"+interfacename
					+"SOAPBinding\">");
		sb.append("<soap:address location=\"" + address +"\"/>");
		sb.append("</port></service><!-- end of SERVICE BLOCKS -->");
		return sb.toString();
	}
}
