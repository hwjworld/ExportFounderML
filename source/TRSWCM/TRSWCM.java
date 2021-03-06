package TRSWCM;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import system.controller.ExportHelper;
import system.exportStrategy.AbstractExportStrategy;
import system.model.Article;
import system.model.ArticleToken;
import system.model.Attachment;
import system.model.FilePathShape;
import system.model.Node;
import system.model.NodeToken;
import system.model.Operation;
import system.model.RelatedNode;
import system.repository.Config;
import system.util.Utils;

public class TRSWCM extends AbstractExportStrategy<TrsArticleToken, TrsNodeToken>  {
	private TRSWCM_condition cond = new TRSWCM_condition("0", "0");
	private static Properties props = null;
	private final String trswcm_config = "TRSWCM.ecfg";

	public TRSWCM() {
	}

	private Properties openConfig(){
		try {
			props = Resources.getResourceAsProperties(trswcm_config);
		} catch (IOException e) {
			System.err.println("来源相关配置不正确，请检查 TRSWCM.ecfg");
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
			output = new FileOutputStream(Resources.getResourceAsFile(trswcm_config));
			props.store(output, "TRSWCM Export Config File");
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(output);
		}
	}
	
	public List<ArticleToken> getArticleTokens() {
		List<ArticleToken> list = null;
		cond.setStart_docid(com.founder.enp.dataportal.util.StringUtils.getString(getConfigPropertie("start_docid"), "0"));
		cond.setEnd_docid(com.founder.enp.dataportal.util.StringUtils.getString(getConfigPropertie("end_docid"),"0"));
		cond.setDocid(com.founder.enp.dataportal.util.StringUtils.getString(getConfigPropertie("docid"),"0"));
		System.out.printf(" docid:%s  start_docid:%s   end_docid:%s  \n",cond.getDocid(),cond.getStart_docid(),cond.getEnd_docid());
		SqlSession session = sessionFactory.openSession();
		try {
			list = session.selectList("selectArticleIDS", cond, new RowBounds(
					0, work_page));
			log.info("一次查询，记录数 : "+list.size());
		} finally {
			session.close();
		}
		return list;
	
	}

//	public List<TrsArticleToken> getArticleTokens() {}

	@Override
	public void startOneArticle(TrsArticleToken articleToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Article getArticleInfo(TrsArticleToken articleToken) {
		Article article = null;
		log.info("正在处理 : "+articleToken);
		SqlSession session = sessionFactory.openSession();
		try {
			article = (Article) session.selectOne("selectArticleInfo", articleToken.getDocid());
			if(article.getNsdate()==null){
				article.setNsdate("1970-01-01");
			}
		} finally {
			session.close();
		}
		return article;
	}

	@Override
	public Node getNodeInfo(TrsArticleToken articleToken) {
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
	public Operation getOperationInfo(TrsArticleToken articleToken) {
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
	public List<Attachment> getArticleAttachments(Article article, TrsArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		try {
			@SuppressWarnings("unchecked")
			List<String> attachmentidList = session.selectList("selectAttachmentIDS", articleToken);
			for (String attid:attachmentidList) {
				Attachment att = (Attachment) session.selectOne("selectAttachment", attid);
				if (att != null){
					String path = att.getFileshape().getpath();
					att.getFileshape().setpath(StringUtils.substring(path, 0, 8)+"/"+StringUtils.substring(path, 0, 10)+"/"+path);
					attachmentList.add(att);
				}
			}
		} finally {
			session.close();
		}
		

		String content = article.getContent();

		List<String> imglist = Utils.getListFromMatchedPattern(" src=\"([^\"]+)\"", content);
		for (Iterator<String> it = imglist.iterator(); it.hasNext();) {
			String img = it.next();			
			if(Utils.endWith(img,Config.getConfig().getConfigProperty("attachment-type").split("\\|"))){
				if(img.startsWith("W0")) {
					Attachment att = new Attachment();
					att.setAttdesc(img);
					att.setAttname(img);
					FilePathShape shape = new FilePathShape();
					shape.setpath(StringUtils.substring(img, 0, 8)+"/"+StringUtils.substring(img, 0, 10)+"/"+img);
					att.setFileshape(shape);
					att.setType(1);
					attachmentList.add(att);
				}
			}
			
		}		
		return attachmentList;
	}

	@Override
	public Attachment getArticlePiclink(TrsArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		try {
			Attachment piclink = (Attachment) session.selectOne("selectPiclink",articleToken);
			if(piclink != null && piclink.getFileshape()!=null){
				String path = piclink.getFileshape().getpath();
				piclink.getFileshape().setpath(StringUtils.substring(path, 0, 8)+"/"+StringUtils.substring(path, 0, 10)+"/"+path);
			}
			
			return piclink;
		}finally {
			session.close();
		}
	}

	@Override
	public List<RelatedNode> getArticleRelatedNode(TrsArticleToken articleToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeOneArticle(TrsArticleToken articleToken) {
		SqlSession session = sessionFactory.openSession();
		try {
			session.update("setDOXML", articleToken);
			session.commit();
		} finally {
			session.close();
		}
	}

	public List<NodeToken> getNodeTokens() {
		List<NodeToken> list = null;
		SqlSession session = sessionFactory.openSession();
		try {
			list = session.selectList("selectNodeIDS", null, new RowBounds(
					0, work_page));
			log.info("栏目一次查询，记录数 : "+list.size());
		} finally {
			session.close();
		}
		return list;
	
	}

	@Override
	public void startOneNode(TrsNodeToken nodeToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeOneNode(TrsNodeToken nodeToken) {
		SqlSession session = sessionFactory.openSession();
		try {
			session.update("setNodeDOXML", nodeToken);
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public Node getNodeInfo(TrsNodeToken nodeToken) {
		Node node = null;
		SqlSession session = sessionFactory.openSession();
		try {
			node = (Node) session.selectOne("selectNodePath", nodeToken);
			node.setTargetNode(ExportHelper.reverseHierarchy(node
					.getTargetNode()));
		} finally {
			session.close();
		}
		return node;
	}
	

	}


class TRSWCM_condition {
	public String start_docid = "0";
	public String end_docid = "0";
	public String docid = "0";

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public TRSWCM_condition(String start_docid,String end_docid) {
		this.start_docid = start_docid;
		this.end_docid = end_docid;
	}

	public String getStart_docid() {
		return start_docid;
	}

	public void setStart_docid(String start_docid) {
		this.start_docid = start_docid;
	}

	public String getEnd_docid() {
		return end_docid;
	}

	public void setEnd_docid(String end_docid) {
		this.end_docid = end_docid;
	}


}