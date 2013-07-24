package system.exportStrategy;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSessionFactory;
import org.dom4j.DocumentException;

import system.controller.ExportHelper;
import system.model.Article;
import system.model.ArticleToken;
import system.model.Attachment;
import system.model.Node;
import system.model.Operation;
import system.model.RelatedNode;
import system.repository.DaoConfig;

public abstract class AbstractExportStrategy<T extends ArticleToken> implements ExportStrategy {

	protected static SqlSessionFactory sessionFactory = DaoConfig.getSessionFactory();
	protected static Logger log = Logger.getLogger("ExportStrategy");
	private int work_thread = 1;
	protected int work_page = 1000000;
	
	public void exportXML(){
		while(true){
			List<T> articlelist = getArticleTokens();
			if(articlelist==null || articlelist.size()==0){
				pause();
				continue;
			}
			for(T articleToken:articlelist){
					startOneArticle(articleToken);
					Article article = getArticleInfo(articleToken);
					Node node = getNodeInfo(articleToken);
					Operation operation = null;
					if(article == null)
						operation = getOperationInfo(null);
					else
						operation = getOperationInfo(articleToken);
					if(operation == null || (article==null && node==null)){
						System.out.printf("an error article : %s  status: article=%s ,nodeid=%s ,operation=%s\n",articleToken,article,node,operation);
						continue;
					}
					try {
						if(article != null){
							setAttachments(article, articleToken);
							setPiclink(article, articleToken);
							setRelatedNode(article, articleToken);
						}
						ExportHelper.article2Xml(article, node, operation);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					closeOneArticle(articleToken);
			}
		}
	}

	private void setAttachments(Article article,T articleToken){
		List<Attachment> atts = getArticleAttachments(articleToken);
		if(atts == null)
			return;
		for(Attachment att:atts){
			article.addAttachement(att);
		}
	}

	private void setPiclink(Article article,T articleToken){
		Attachment piclink = getArticlePiclink(articleToken);
		if(piclink == null)
			return;
		article.setpiclink(piclink);
	}
	
	private void setRelatedNode(Article article,T articleToken){
		List<RelatedNode> relatedNodes = getArticleRelatedNode(articleToken);
		if(relatedNodes == null)
			return;
		for(RelatedNode rNode:relatedNodes){
			ExportHelper.addRelatedNode(article, rNode);
		}
	}
	public abstract List<T> getArticleTokens();
	
	/**
	 * 导出一篇稿件时要做的事
	 * @param articleToken
	 */
	public abstract void startOneArticle(T articleToken);
	/**
	 * 注意检查以下几点：稿件分页，发布状态，关联栏目，相关稿件。
	 * @param artid
	 * @return
	 */
	public abstract Article getArticleInfo(T articleToken);
	public abstract Node getNodeInfo(T articleToken);
	public abstract Operation getOperationInfo(T articleToken);
	public abstract List<Attachment> getArticleAttachments(T articleToken);
	public abstract Attachment getArticlePiclink(T articleToken);
	public abstract List<RelatedNode> getArticleRelatedNode(T articleToken);

	/**
	 * 导出后设置标识,继承之可修改标识方式
	 * @param artid
	 */
	public abstract void closeOneArticle(T articleToken);
	
	private void pause(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getWork_thread() {
		return work_thread;
	}

	public void setWork_thread(int work_thread) {
		this.work_thread = work_thread;
	}

	public int getWork_page() {
		return work_page;
	}

	public void setWork_page(int work_page) {
		this.work_page = work_page;
	}

}
