package xmlAndJson;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/*
    程序功能， 将某节点下的内容转成json， 然后base64加密重新放到body节点下。
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        InputStream in=new FileInputStream("resources/t.xml");
        SAXReader sr=new SAXReader();
        Document doc=sr.read(in);
        Element root=doc.getRootElement();
        Element body=root.element("body");

        System.out.println("body元数据: "+body.asXML());
        StringBuffer json=new StringBuffer();
        xml_json.xml2json1(body, json);
        body.clearContent();
        body.setText(json.toString());
        System.out.println("body转换为json后: "+body.asXML());
    }
}
