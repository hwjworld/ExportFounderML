package system.model;

/**
 * @author Administrator
 * @version 1.0
 * @created 26-¾ÅÔÂ-2010 11:24:37
 */
public class Attachment {

	private String attname;
	private String attdesc;
	private int type;
	private FileShape fileshape = new FileShape();

	public Attachment(){

	}

	public void finalize() throws Throwable {

	}

	public String getAttname() {
		return attname;
	}

	public void setAttname(String attname) {
		this.attname = attname;
	}

	public String getAttdesc() {
		return attdesc;
	}

	public void setAttdesc(String attdesc) {
		this.attdesc = attdesc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public FileShape getFileshape() {
		return fileshape;
	}

	public void setFileshape(FileShape fileshape) {
		this.fileshape = fileshape;
	}

}