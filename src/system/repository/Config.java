package system.repository;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.dom4j.DocumentException;

import system.util.XMLProperties;


/**
 * 归档配置文件管理
 * 从WEB-INF\ArchiveConfig.xml读取
 * 
 * @author wanghc
 * @created 2006-8-18 下午04:47:16
 * @version 1.0
 * 
 */
public class Config
{
	private XMLProperties properties = null;

	private String configFileName = "config.xml";
	
	private static Config config = new Config("config.xml");

	static{
		try {
			config.load();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Config(String configFileName)
	{
		this.configFileName = configFileName;
	}
	

	/**
	 * 提取配置文件
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	public synchronized void load() throws DocumentException, IOException
	{
//        SAXReader builder = new SAXReader();
//        Document doc = builder.read(getClass().getClassLoader().getResourceAsStream(configFileName));
		properties = new XMLProperties(Resources.getResourceAsStream(configFileName));
	}

	/**
	 * 提取系统配置参数
	 * @param name 参数值的名称
	 * @return String 系统参数\uFFFD
	 */
	public String getConfigProperty(String name)
	{
		return properties.getProperty(name);
	}
	
	public String getConfigProperty(String name, String defaultValue)
	{
		String value;
		if((value = properties.getProperty(name))==null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * 设置系统配置参数
	 * @param name 参数名称
	 * @param value 参数
	 */
	public void setConfigProperty(String name, String value)
	{
		properties.setProperty(name, value);
	}

	/**
	 * 删除系统配置参数
	 * @param name 参数名称
	 */
	public void deleteConfigProperty(String name)
	{
		properties.deleteProperty(name);
	}

	public static Config getConfig(){
		return config;
	}
}
