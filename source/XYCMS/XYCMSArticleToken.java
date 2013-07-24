package XYCMS;

import system.model.ArticleToken;

public class XYCMSArticleToken implements ArticleToken{
	private String articleid;

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	
	@Override
	public String toString() {
		return "Articleid="+articleid;
	}
}
