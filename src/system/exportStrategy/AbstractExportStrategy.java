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
import system.model.NodeToken;
import system.model.Operation;
import system.model.RelatedNode;
import system.model.StrategyToken;
import system.repository.DaoConfig;

public abstract class AbstractExportStrategy<T extends ArticleToken,N extends NodeToken> implements ExportNodeStrategy<NodeToken>,ExportArticleStrategy<ArticleToken> {

	protected static SqlSessionFactory sessionFactory = DaoConfig.getSessionFactory();
	protected static Logger log = Logger.getLogger("ExportStrategy");
	private int work_thread = 1;
	protected int work_page = 1000000;
	
	@SuppressWarnings("unchecked")
	public void exportXML(StrategyToken token) {
		if(token instanceof ArticleToken){
			exportArticleXML((T)token);
		}else if(token instanceof NodeToken){
			exportNodeXML((N)token);
		}
	}
	private void exportArticleXML(T articleToken){
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
			return;
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
//	public abstract List<T> getArticleTokens();
	
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

	
	
	private void exportNodeXML(N nodeToken){
		startOneNode(nodeToken);
		Node node = getNodeInfo(nodeToken);
		Operation operation = new Operation();
		operation.setAction(1);
		operation.setObjectType(0);
		if(operation == null ||  node==null){
			System.out.printf("an error node : %s  status: nodeid=%s ,operation=%s\n",nodeToken,node,operation);
			return;
		}
		try {
			ExportHelper.node2Xml(node, operation);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}		
		closeOneNode(nodeToken);
	}
	public abstract void startOneNode(N nodeToken);
	public abstract void closeOneNode(N nodeToken);
	public abstract Node getNodeInfo(N nodeToken);
	

}
