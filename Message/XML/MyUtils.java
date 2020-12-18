package XML;

public class MyUtils {
    public static String removeWhiteSpaceBetweenXMLTags(String xmlStr){
        return xmlStr.replaceAll("(?<=>)(\\s+)(?=<)", "");
    }

    //escapeXML
    public static String xmlEscape(String xmlStr){
        StringBuilder sb=new StringBuilder();
        for(char c:xmlStr.toCharArray()){
            switch (c){
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    //recover the  escaped xml
    public static String recoverEscapedXml(String xmlStr){
        String s1= xmlStr.replaceAll("&lt\\;", "<");
        String s2=s1.replaceAll("&gt\\;", ">");
        String s3=s2.replaceAll("amp\\;", "&");
        String s4=s3.replaceAll("&quot\\;", "\"");
        return s4;
    }


}
