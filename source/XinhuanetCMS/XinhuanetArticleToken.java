package XinhuanetCMS;

import system.model.ArticleToken;

public class XinhuanetArticleToken implements ArticleToken {
	private String articleid,nodeid;

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	} 
	
	@Override
	public String toString() {
		return "Articleid="+articleid+"   nodeid="+nodeid;
	}
}
