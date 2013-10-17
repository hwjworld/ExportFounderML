package TRSWCM;

import system.model.ArticleToken;

public class TrsArticleToken implements ArticleToken {
	private String docid;

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	@Override
	public String toString() {
		return "Docid="+docid;
	}

	public String getID() {
		return docid;
	}
	
}
