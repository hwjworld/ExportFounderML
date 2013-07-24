package XinhuanetCMS;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import system.controller.ExportHelper;
import system.exportStrategy.AbstractExportStrategy;
import system.model.Article;
import system.model.Attachment;
import system.model.Node;
import system.model.Operation;
import system.model.RelatedNode;

import com.founder.enp.dataportal.util.StringUtils;
import com.founder.enp.dataportal.util.StringValueUtils;

/**
 * @author Administrator
 * @version 1.0
 * @created 26-锟斤拷锟斤拷-2010 11:24:38
 */
public class XinhuanetCMS extends AbstractExportStrategy<XinhuanetArticleToken> {

	private XinhuanetCMS_condition cond = new XinhuanetCMS_condition(null, "0");
	private final String xinhuanetCMS_config = "XinhuanetCMS.ecfg";
	private static Properties props = null;
	
	public XinhuanetCMS() {
		setWork_page(20000);
		setWork_thread(2);
	}

	public void finalize() throws Throwable {

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<XinhuanetArticleToken> getArticleTokens() {
		List<XinhuanetArticleToken> list = null;
		cond.setFromarticleid(StringUtils.getString(getConfigPropertie("from_articleid"), "0"));
		cond.setNodeids(getConfigPropertie("node_ids").split(","));
		System.out.printf("from_articleid:%s   node_ids:%s  \n",cond.getFromarticleid(),Arrays.toString(cond.getNodeids()));
		SqlSession session = sessionFactory.openSession();
		try {
			list = session.selectList("getArticleTokens", cond, new RowBounds(0, work_page));
			System.out.println("一次查询，记录数 : " + list.size());
		} finally {
			try{
				session.close();	
			}catch (Exception e) {
			}
		}
		return list;
	}
	

	private Properties openConfig(){
		try {
			props = Resources.getResourceAsProperties(xinhuanetCMS_config);
		} catch (IOException e) {
			System.err.println("来源相关配置不正确，请检查 XinhuanetCMS.ecfg");
			System.exit(1);
		}
		return props;
	}
	private void setConfigPropertie(String key,String value){
		if(props == null)
			openConfig();
		props.setProperty(key, value);
	}
	private String getConfigPropertie(String key){
		if(props == null)
			openConfig();
		return props.getProperty(key);
	}
	private void saveConfig(){
		if(props == null)
			openConfig();
		FileOutputStream output = null;
		try{
			output = new FileOutputStream(Resources.getResourceAsFile(xinhuanetCMS_config));
			props.store(output, "XinhuanetCMS Export Config File");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(output);
		}
	}

	@Override
	public void closeOneArticle(XinhuanetArticleToken articleToken) {
		setConfigPropertie("from_articleid", articleToken.getArticleid());
		saveConfig();
	}

	@Override
	public List<Attachment> getArticleAttachments(XinhuanetArticleToken articleToken) {
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
	public Article getArticleInfo(XinhuanetArticleToken articleToken) {
		Article article = null;
		System.out.println("正在处理 : " + articleToken);
		SqlSession session = sessionFactory.openSession();
		try {
			article = (Article) session.selectOne("selectArticleInfo", articleToken);
			if (article == null)
				return article;
			if(StringValueUtils.getInt(article.getContent())>0){
				article.setContent(StringUtils.getString( (String)session.selectOne("getArticleClobContent", article.getContent()),""));
			}else{
				;
				article.setContent(StringUtils.getString( (String)session.selectOne("getArticleContentstr", articleToken),""));
			}
		} finally {
			session.close();
		}
		return article;
	}

	@Override
	public Attachment getArticlePiclink(XinhuanetArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		try {
			return (Attachment) session.selectOne("selectPiclink",articleToken);
		}finally {
			session.close();
		}
	}

	@Override
	public List<RelatedNode> getArticleRelatedNode(XinhuanetArticleToken articleToken) {
//		List<RelatedNode> relatednodelist = new ArrayList<RelatedNode>();
//		SqlSession session = sessionFactory.openSession();
//		try {
//			relatednodelist= session.selectList("selectRelatedNode", articleToken);
//			for (RelatedNode rNode:relatednodelist) {
//				rNode.setNodePath(ExportHelper.reverseHierarchy(rNode.getNodePath()));
//			}
//		}finally {
//			session.close();
//		}
//		return relatednodelist;
		return null;
	}

	@Override
	public Node getNodeInfo(XinhuanetArticleToken articleToken) {
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
	public Operation getOperationInfo(XinhuanetArticleToken articleToken) {
		Operation operation = null;
		SqlSession session = sessionFactory.openSession();
		try {
			operation = (Operation) session.selectOne("selectOperation", articleToken);
		} finally {
			session.close();
		}
		return operation;
	}

	@Override
	public void startOneArticle(XinhuanetArticleToken arg0) {
		
	}
}

class XinhuanetCMS_condition {
	public String fromarticleid = "0";
	public String[] nodeids = new String[] {};

	public XinhuanetCMS_condition(String[] nodes, String fromarticleid) {
		this.nodeids = nodes;
		this.fromarticleid = fromarticleid;
	}

	public String[] getNodeids() {
		return nodeids;
	}

	public void setNodeids(String[] nodeids) {
		this.nodeids = nodeids;
	}

	public String getFromarticleid() {
		return fromarticleid;
	}

	public void setFromarticleid(String fromarticleid) {
		this.fromarticleid = fromarticleid;
	}

}