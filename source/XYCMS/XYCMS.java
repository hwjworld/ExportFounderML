package XYCMS;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import system.controller.ExportHelper;
import system.exportStrategy.AbstractExportStrategy;
import system.model.Article;
import system.model.Attachment;
import system.model.Node;
import system.model.Operation;
import system.model.RelatedNode;

public class XYCMS extends AbstractExportStrategy<XYCMSArticleToken>{

	public XYCMS() {
		setWork_page(20000);
		setWork_thread(2);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XYCMSArticleToken> getArticleTokens() {
		List<XYCMSArticleToken> list = null;
		SqlSession session = sessionFactory.openSession();
		try {
			list = session.selectList("selectArticleIDS", null, new RowBounds(
					0, work_page));
			log.info("一次查询，记录数 : "+list.size());
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public void startOneArticle(XYCMSArticleToken articleToken) {
		
	}

	@Override
	public Article getArticleInfo(XYCMSArticleToken articleToken) {
		Article article = null;
		log.info("正在处理 : "+articleToken);
		SqlSession session = sessionFactory.openSession();
		try {
			article = (Article) session.selectOne("selectArticleInfo", articleToken.getArticleid());
		} finally {
			session.close();
		}
		return article;
	}

	@Override
	public Node getNodeInfo(XYCMSArticleToken articleToken) {
		Node node = null;
		SqlSession session = sessionFactory.openSession();
		try {
			node = (Node) session.selectOne("selectNode", articleToken);
			node.setTargetNode(ExportHelper.reverseHierarchy(node
					.getTargetNode()));
		} finally {
			session.close();
		}
		return node;
	}

	@Override
	public Operation getOperationInfo(XYCMSArticleToken articleToken) {

		Operation operation = null;
		SqlSession session = sessionFactory.openSession();
		try {
			XYCMS_PAGELAYOUT pagelayout = new XYCMS_PAGELAYOUT();
			pagelayout.setArticleid(articleToken.getArticleid());
			operation = (Operation) session.selectOne("selectOperation",
					pagelayout);
		} finally {
			session.close();
		}
		return operation;
	}

	@Override
	public List<Attachment> getArticleAttachments(XYCMSArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		try {
			@SuppressWarnings("unchecked")
			List<String> attachmentidList = session.selectList("selectAttachmentIDS", articleToken);
			for (String attid:attachmentidList) {
				Attachment att = (Attachment) session.selectOne("selectAttachment", attid);
				if (att != null)
					attachmentList.add(att);
			}
		} finally {
			session.close();
		}
		return attachmentList;
	}

	@Override
	public Attachment getArticlePiclink(XYCMSArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		try {
			return (Attachment) session.selectOne("selectPiclink",articleToken);
		}finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatedNode> getArticleRelatedNode(
			XYCMSArticleToken articleToken) {
		List<RelatedNode> relatednodelist = new ArrayList<RelatedNode>();
		SqlSession session = sessionFactory.openSession();
		try {
			relatednodelist= session.selectList("selectRelatedNode", articleToken);
			for (RelatedNode rNode:relatednodelist) {
				rNode.setNodePath(ExportHelper.reverseHierarchy(rNode.getNodePath()));
			}
		}finally {
			session.close();
		}
		return relatednodelist;
	}

	@Override
	public void closeOneArticle(XYCMSArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		try {
			session.update("setDOXML", articleToken);
			session.commit();
		} finally {
			session.close();
		}
	}

}


class XYCMS_PAGELAYOUT {
	String articleid, nodeid;

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
}