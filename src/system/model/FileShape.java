package system.model;

/**
 * ��ʹ���������Լ�����������path�����<code><filepath><filewebpath>�����ֵ 
 * 
 * @author Administrator
 * @version 1.0
 * @created 26-����-2010 11:24:37
 */
public class FileShape {

	private String path;

	public FileShape(){

	}

	public void finalize() throws Throwable {

	}

	public String getpath(){
		return path;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setpath(String newVal){
		path = newVal;
	}

}