package system.model;

/**
 * @author Administrator
 * @version 1.0
 * @created 26-¾ÅÔÂ-2010 11:24:38
 */
public class Operation {

	private int Action;
	private int ArticleType;
	private int ObjectType;
	private int PublishState;
	private String SourceSystem;
	private int TargetLib;

	public Operation(){

	}

	public void finalize() throws Throwable {

	}

	public int getAction() {
		return Action;
	}

	public void setAction(int action) {
		Action = action;
	}

	public int getArticleType() {
		return ArticleType;
	}

	public void setArticleType(int articleType) {
		ArticleType = articleType;
	}

	public int getObjectType() {
		return ObjectType;
	}

	public void setObjectType(int objectType) {
		ObjectType = objectType;
	}

	public int getPublishState() {
		return PublishState;
	}

	public void setPublishState(int publishState) {
		PublishState = publishState;
	}

	public String getSourceSystem() {
		return SourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		SourceSystem = sourceSystem;
	}

	public int getTargetLib() {
		return TargetLib;
	}

	public void setTargetLib(int targetLib) {
		TargetLib = targetLib;
	}

}