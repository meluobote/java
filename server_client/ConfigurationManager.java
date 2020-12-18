import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ConfigurationManager {
    private Logger log= LogManager.getLogManager().getLogger("configurationManager");
    private Properties config=new Properties();
    private String path=null;

    private static String configFileName="config.propertis";

    public ConfigurationManager(String configFile) {
        if(configFile==null || "".equals(configFile)){
            try {
                this.config.load(ConfigurationManager.class.getResourceAsStream(configFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            configFile=configFile.replaceAll("\\.", "/");
            path=configFile+"/";
            configFile="/"+path+configFile;
        }
    }

    public static ConfigurationManager getInstance(Class clz){
        ConfigurationManager configurationManager=null;
        String configFile=null;

        configFile=clz.getPackage().getName();
        configurationManager=new ConfigurationManager(configFile);


        return configurationManager;
    }

}
