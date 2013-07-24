package system.repository;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.dom4j.DocumentException;

import system.util.XMLProperties;


/**
 * �鵵�����ļ�����
 * ��WEB-INF\ArchiveConfig.xml��ȡ
 * 
 * @author wanghc
 * @created 2006-8-18 ����04:47:16
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
	 * ��ȡ�����ļ�
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
	 * ��ȡϵͳ���ò���
	 * @param name ����ֵ������
	 * @return String ϵͳ����\uFFFD
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
	 * ����ϵͳ���ò���
	 * @param name ��������
	 * @param value ����
	 */
	public void setConfigProperty(String name, String value)
	{
		properties.setProperty(name, value);
	}

	/**
	 * ɾ��ϵͳ���ò���
	 * @param name ��������
	 */
	public void deleteConfigProperty(String name)
	{
		properties.deleteProperty(name);
	}

	public static Config getConfig(){
		return config;
	}
}
