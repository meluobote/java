package xmlAndJson;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class xml_json {
    public static void xml2json1(Element data, StringBuffer json) throws DocumentException {
        json.append('{');
        List<Element> le=data.elements();
        List<Element> record=new LinkedList<Element>();

        for(int i=0;i<le.size();i++){
            Element ele=le.get(i);
            String name=ele.getName();
            int childnum=ele.elements().size();

            if(record.contains(ele)){
                continue;
            }
            if(childnum==0){
                json.append(name);
                json.append(':');
                json.append(ele.getTextTrim());
            }else{
                List<Element> namelist=data.elements(name);
                Iterator<Element> it=namelist.listIterator();
                if(namelist.size()==1){
                    json.append(name);
                    json.append(':');
                    xml2json1(ele, json);
                }else{
                    json.append(name);
                    json.append(":[");
                    while(it.hasNext()){
                        Element arrElement=it.next();
                        record.add(arrElement);
                        xml2json1(arrElement, json);
                        json.append(',');
                    }
                    json.delete(json.length()-1, json.length());
                    json.append(']');
                }
            }
            json.append(',');
        }
        json.delete(json.length()-1, json.length());
        json.append('}');
    }
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
