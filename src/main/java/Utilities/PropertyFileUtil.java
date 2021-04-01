package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
public static String getvalueForKey(String key) throws  Throwable
{
	Properties configProperties=new Properties();
	configProperties.load(new FileInputStream("E:\\OjtProject\\ERP_StockMaven\\PropertiesFile\\Environment.properties"));
	return configProperties.getProperty(key);
}
}
