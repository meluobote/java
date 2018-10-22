import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;

import excelInfo.BodyList;
import excelInfo.Field;
import excelInfo.IFWService;
import excelInfo.Input;
import excelInfo.Output;
import excelInfo.Put;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import tool.CreateBindings;
import tool.CreateFile;
import tool.CreateHeaderStr;
import tool.CreateMessage;
import tool.CreatePortType;
import tool.CreateReqRespStr;
import tool.CreateServiceBlocks;

public class Main {
	public static final String PRE_IP="http://i.soa.abc.com/ifw/";
	public static final int SERVICE_COLUMN=2;
	public static final int PUT_COLUMN=5;
	public static final int ISNESSARY=6;
	public static final int LIST_COLUMN=7;
	public static final int FIELD_COLUMN=8;
	
	//list name format
	public static final String REQUEST_LIST_SUFFIX="RequestBodyList";
	public static final String RESPONSE_LIST_SUFFIX="RequestBodyList";
	public static void main(String[] args)throws BiffException, IOException, DocumentException {
		// TODO Auto-generated constructor stub
//		Workbook book=Workbook.getWorkbook(new File("servicesInterfaces.xls"));
//		File f=new File("servicesInterfaces.xls");
		File f = new File("CREATEWSDL\\servicesInterfaces.xls");

		Workbook book=Workbook.getWorkbook(f);
		Sheet sheet = book.getSheet("系统服务接口清单");
		int rownum=sheet.getRows();   //该函数计算结CZBANK@35果可能不是实际想要的，如果多，删除多余的行
		String Interfacename=sheet.getCell(1, 1).getContents().trim();
		String total="";
		total=total+CreateHeaderStr.createHeaderStr(PRE_IP+Interfacename);
		
		StringBuffer Service=new StringBuffer();
		StringBuffer Message=new StringBuffer();
		Message.append("<!-- MESSAGE BLOCKS -->");
		StringBuffer PortType=new StringBuffer();
		PortType.append("<!-- PORT TYPES -->");
		PortType.append("<portType name=\"" +Interfacename +"\">");
		
		StringBuffer Binding=new StringBuffer();
		Binding.append("<!-- BINDINGS -->");
		Binding.append("<binding name=\"" +Interfacename+"SOAPBinding\" type=\"IFWXML:" +Interfacename +"\">");
		Binding.append("<soap:binding transport=\"http://schemas.xmlsoap.org/soap/http\" style=\"document\"/>");
		
		StringBuffer ServiceBlocks=new StringBuffer();
		ServiceBlocks.append(CreateServiceBlocks.getServiceBlocksStr(Interfacename));
		
		for(int i=1;i<rownum;i++){
			String service_id=sheet.getCell(5, i).getContents().trim();
			String service_name=sheet.getCell(4, i).getContents().trim();
			IFWService ifws=getServiceInfo(service_id, service_name);
			
			printPut(ifws.getInput());
			printPut(ifws.getOutput());
			
			//配置一半自动推出
			if(ifws.getMinRow()==-1){
				System.out.println("服务详细说明未配置完");
				break;
			}
			
			Service.append(CreateReqRespStr.createRequestAndResponseStr(ifws));
			Message.append(CreateMessage.getMessageBolcks(service_name));
			PortType.append(CreatePortType.getPortTypes(service_name));
			Binding.append(CreateBindings.getBindings(service_name));
		}
		
		Service.append("</xsd:schema></types>");
		
		Message.append("<message name=\"ErrorInfo\"><part name=\"ErrorInfo\" element=\"IFWXML:ErrorInfo\"/></message>");
		Message.append("<!-- END MESSAGE BLOCKS -->");
		
		PortType.append("</portType>");
		PortType.append("<!-- END OF PORT TYPES -->");
		
		Binding.append("</binding>");
		Binding.append("<!-- END OF BINDING -->");
		
		total=total+Service.toString();
		total=total+Message.toString();
		total=total+PortType.toString();
		total=total+Binding.toString();
		total=total+ServiceBlocks.toString();
		
		total=total+"</definitions>";
		CreateFile.createFile(Interfacename, total);

		System.out.println("Success");
	}
	
	private static void printPut(Put in){
		Field[] infs=in.getFields();
		for(Field f:infs){
			System.out.println(f.getFieldName()+"  "+f.isNessarySign()+" "+f.getType());
		}
		if(in.isList_exist()){
			BodyList bl=in.getBodylist();
			Field[] list_fields=bl.getFields();
			System.out.println("\t"+bl.getListName());
			for(Field f:list_fields){
				System.out.println(f.getFieldName()+"  "+f.isNessarySign()+" "+f.getType());
			}
		}
	}
	
	private static IFWService getServiceInfo(String serviceID, String serviceName) throws BiffException, IOException{
		Workbook book=Workbook.getWorkbook(new File("CREATEWSDL\\servicesInterfaces.xls"));
		Sheet sheet=book.getSheet("服务详细说明");
		jxl.Range[] ranges=sheet.getMergedCells();
		
		IFWService service=new IFWService(serviceID, serviceName);
		getServiceRange(serviceID, ranges, service);
		
		//get Input of the serviceID
		try{
			Input in=new Input();
			getInputRange(ranges, service, in);
			
			BodyList bl=new BodyList();
			getListRange(ranges, in, bl);
			
			Field[] bodylist_fields=null;
			if(in.isList_exist()){
				setInputList(service, sheet, in, bl);
				setInputFieldsWithList(sheet, in);
			}else{
				setInputFieldsWithoutList(sheet, in);
			}
			service.setInput(in);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//get output of serviceID
		try{
			Output out=new Output();
			getOutputRange(ranges, service, out);
			
			BodyList bl=new BodyList();
			getListRange(ranges, out, bl);
			
			Field[] bodylist_fields=null;
			if(out.isList_exist()){
				setOutputList(service, sheet, out, bl);
				setOutputFieldsWithList(sheet,out);
			}else{
				setOutputFieldsWithoutList(sheet,out);
			}
			service.setOutput(out);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return service;
	}
	
	private static void getOutputRange(Range[] ranges, IFWService service, Output out) {
		// TODO Auto-generated method stub
		for(Range range:ranges){
			Cell tl=range.getTopLeft();
			Cell br=range.getBottomRight();
			if(tl.getColumn()==PUT_COLUMN && tl.getContents().trim().equals("Output")&&br.getRow()==service.getMaxRow()){
				out.setMinRow(tl.getRow());
				out.setMaxRow(br.getRow());
				break;
			}
		}
	}

	private static void setOutputList(IFWService ifw, Sheet sheet, Output out, BodyList bl){
		Field[] bodylist_fields=new Field[bl.getMaxRow()-bl.getMinRow()+1];
		bl.setLen(bodylist_fields.length);
		bl.setListName(ifw.getServiceName()+RESPONSE_LIST_SUFFIX);
		String field_type="xsd:string";
		for(int i=0,row=bl.getMinRow(); row<=bl.getMaxRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			boolean is_need=false;
			if("Y".equals(nessary.getContents().trim())){
				is_need=true;
			}
			
			bodylist_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents(), field_type);
			i++;
		}
		bl.setFields(bodylist_fields);
		out.setBodylist(bl);
	}
	
	private static void setOutputFieldsWithoutList(Sheet sheet, Output out){
		Field[] out_fields=new Field[out.getMaxRow()-out.getMinRow()+1];
		int i=0;
		boolean is_need;
		String field_type;
		for(int row=out.getMinRow();row<=out.getMaxRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			is_need = "Y".equals(nessary.getContents().trim());
			
			if("responseHeader".equals(content.getContents().trim())){
				field_type="IFWXML:ResponseHeader";
			}else{
				field_type="xsd:string";
			}
			out_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents().trim(), field_type);
			i++;
		}
		out.setFields(out_fields);
	}
	private static void setOutputFieldsWithList(Sheet sheet, Output out){
		Field[] out_fields=new Field[out.getMaxRow()-out.getMinRow()+1 - out.getBodylist().getLen()];
		int i=0;
		boolean is_need;
		String field_type;
		for(int row=out.getMinRow();row<out.getBodylist().getMinRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			is_need = "Y".equals(nessary.getContents().trim());
			
			if("responseHeader".equals(content.getContents().trim())){
				field_type="IFWXML:ResponseHeader";
			}else{
				field_type="xsd:string";
			}
			out_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents().trim(), field_type);
			i++;
		}
		
		for(int row=out.getBodylist().getMaxRow()+1; row<=out.getMaxRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			is_need = "Y".equals(nessary.getContents().trim());
			
			if("responseHeader".equals(content.getContents().trim())){
				field_type="IFWXML:ResponseHeader";
			}else{
				field_type="xsd:string";
			}
			out_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents().trim(), field_type);
			i++;
		}
		out.setFields(out_fields);
	}
	
	private static void setInputFieldsWithoutList(Sheet sheet, Input in) {
		// TODO Auto-generated method stub
		Field[] in_fields=new Field[in.getMaxRow()-in.getMinRow()+1];
		int i=0;
		boolean is_need;
		String field_type;
		for(int row=in.getMinRow();row<=in.getMaxRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			is_need = "Y".equals(nessary.getContents().trim());
			
			if("requestHeader".equals(content.getContents().trim())){
				field_type="IFWXML:RequestHeader";
			}else{
				field_type="xsd:string";
			}
			in_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents().trim(), field_type);
			i++;
		}
		
		in.setFields(in_fields);
	}

	private static void setInputFieldsWithList(Sheet sheet, Input in){
		Field[] in_fields=new Field[in.getMaxRow()-in.getMinRow()+1 - in.getBodylist().getLen()];
		int i=0;
		boolean is_need;
		String field_type;
		for(int row=in.getMinRow();row<in.getBodylist().getMinRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			is_need = "Y".equals(nessary.getContents().trim());
			
			if("requestHeader".equals(content.getContents().trim())){
				field_type="IFWXML:RequestHeader";
			}else{
				field_type="xsd:string";
			}
			in_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents().trim(), field_type);
			i++;
		}
		
		for(int row=in.getBodylist().getMaxRow()+1; row<=in.getMaxRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			is_need = "Y".equals(nessary.getContents().trim());
			
			if("requestHeader".equals(content.getContents().trim())){
				field_type="IFWXML:RequestHeader";
			}else{
				field_type="xsd:string";
			}
			in_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents().trim(), field_type);
			i++;
		}
		in.setFields(in_fields);
	}
	
	private static void setInputList(IFWService ifw, Sheet sheet, Input in, BodyList bl){
		Field[] bodylist_fields=new Field[bl.getMaxRow()-bl.getMinRow()+1];
		bl.setLen(bodylist_fields.length);
		bl.setListName(ifw.getServiceName()+REQUEST_LIST_SUFFIX);
		String field_type="xsd:string";
		for(int i=0,row=bl.getMinRow(); row<=bl.getMaxRow();row++){
			Cell content=sheet.getCell(FIELD_COLUMN, row);
			Cell nessary=sheet.getCell(ISNESSARY, row);
			boolean is_need=false;
			if("Y".equals(nessary.getContents().trim())){
				is_need=true;
			}
			
			bodylist_fields[i]=new Field(row, FIELD_COLUMN, is_need, content.getContents(), field_type);
			i++;
		}
		bl.setFields(bodylist_fields);
		in.setBodylist(bl);
	}
	
	private static void getServiceRange(String serviceID, Range[] ranges, IFWService service) {
		for(Range range:ranges){
			Cell tl=range.getTopLeft();
			Cell br=range.getBottomRight();
			if(tl.getColumn()==SERVICE_COLUMN && tl.getContents().trim().equals(serviceID)){
				service.setMinRow(tl.getRow());
				service.setMaxRow(br.getRow());
				return;
			}
		}
		service.setMinRow(-1);
	}
	
	private static void getListRange(Range[] ranges, Put in, BodyList bl) {
		// TODO Auto-generated method stub
		for(Range range:ranges){
			Cell tl=range.getTopLeft();
			Cell br=range.getBottomRight();
			if(tl.getColumn()==LIST_COLUMN && tl.getRow()>=in.getMinRow() && tl.getRow()<=in.getMaxRow()){
				bl.setMinRow(tl.getRow());
				bl.setMaxRow(br.getRow());
				in.setList_exist(true);
				break;
			}
		}
	}

	private static void getInputRange(Range[] ranges, IFWService service, Input in) {
		// TODO Auto-generated method stub
		for(Range range:ranges){
			Cell tl=range.getTopLeft();
			Cell br=range.getBottomRight();
			if(tl.getColumn()==PUT_COLUMN && tl.getContents().trim().equals("Input")&&tl.getRow()==service.getMinRow()){
				in.setMinRow(tl.getRow());
				in.setMaxRow(br.getRow());
				break;
			}
		}
	}
}
