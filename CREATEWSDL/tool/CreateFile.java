package tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class CreateFile {
	public static void createFile(String interfacename, String input) throws DocumentException, IOException{
		File file=new File("C:\\temp\\I"+interfacename +".wsdl");
		formatToXML(input, file);
	}

	private static void formatToXML(String str, File file) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		SAXReader reader=new SAXReader();
		StringReader stringReader=new StringReader(str);
		Document document;
		document=reader.read(stringReader);
		stringReader.close();
		FileOutputStream fs=new FileOutputStream(file);
		OutputStreamWriter osw=new OutputStreamWriter(fs,  "utf-8");
		XMLWriter writer=new XMLWriter(osw, OutputFormat.createPrettyPrint());
		writer.write(document);
		writer.close();
		osw.close();
		fs.close();
	}
}
