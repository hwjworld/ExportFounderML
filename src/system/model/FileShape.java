package system.model;

/**
 * 不使用子类则自己操作附件，path会填充<code><filepath><filewebpath>三项的值 
 * 
 * @author Administrator
 * @version 1.0
 * @created 26-九月-2010 11:24:37
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