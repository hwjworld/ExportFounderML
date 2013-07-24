package DXCNML;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import system.exportStrategy.AbstractExportStrategy;
import system.model.Article;
import system.model.Attachment;
import system.model.Node;
import system.model.Operation;
import system.model.RelatedNode;
import cnml.apiengine.baseException.CNMLException;
import cnml.apifactory.CNMLAPI;
import cnml.apifactory.CNMLAPIFactory;
import cnml.node.CNML;
import cnml.node.Item;
import cnml.node.Items;

import com.founder.enp.dataportal.util.FileUtils;

public class DXCNML extends AbstractExportStrategy<DXCNMLArticleToken>  {
	
	private String currentCNML = null;
	private File currentCNMLFile = null;
	//新添加的字段
	private String currentMPML=null;
	private Element currentMPMLRoot=null;
	
	@Override
	public void closeOneArticle(DXCNMLArticleToken token) {
		if(token.isLast()){
			try {
				System.out.println("结束转换一个cnml : "+currentCNML);
				FileUtils.move(currentCNML, currentCNML+".ok");
				/**
				 * 新添加部分
				 */
				if(currentMPML!=null){
					File file=new File(currentMPML);
					if(file.exists()){
						FileUtils.move(currentMPML, currentMPML+".ok");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				currentCNML = null;
				currentCNMLFile = null;
			}
		}
	}

	@Override
	public List<Attachment> getArticleAttachments(DXCNMLArticleToken articleToken) {
		Item item = articleToken.getItem();
		return DXCNMLTool.getAttachments(currentCNMLFile,item);	
	}

	@Override
	public Article getArticleInfo(DXCNMLArticleToken articleToken) {
		System.out.println("正在处理 : " + articleToken);
		Article article = new Article();
		Item item = articleToken.getItem();
		DXCNMLTool.setArticle(article, item);
		return article;
	}

	@Override
	public Attachment getArticlePiclink(DXCNMLArticleToken arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelatedNode> getArticleRelatedNode(DXCNMLArticleToken arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DXCNMLArticleToken> getArticleTokens() {
		List<DXCNMLArticleToken> list = new LinkedList<DXCNMLArticleToken>();
		currentCNML = DXCNMLScanner.scanFirstCNMLFile();
		System.out.println(currentCNML==null);
		if(currentCNML == null){
			return list;
		}
		currentCNMLFile = new File(currentCNML);
		CNMLAPI api = CNMLAPIFactory.getInstance();
		try {
			CNML cnml = api.parse(new File(currentCNML));
			Items items = cnml.getItems();
			@SuppressWarnings("unchecked")
			List<Item> itemlist = items.getItemList();
			for(Item item:itemlist){
				DXCNMLArticleToken token = new DXCNMLArticleToken();
				token.setItem(item);
				list.add(token);
			}
			list.get(list.size()-1).setLast(true);
		} catch (CNMLException e) {
			e.printStackTrace();
		}
		
		/**
		 * 新添加功能 targetName
		 */
		try{
			currentMPML=currentCNML.replace(".cnml.xml", ".mpml.xml");
			File file=new File(currentMPML);
			if(file.exists()){
				SAXReader reader = new SAXReader();
				Document document = reader.read(file);// 读取XML文
				currentMPMLRoot = document.getRootElement();// 得到根节点
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Node getNodeInfo(DXCNMLArticleToken token) {
		Node node = new Node();
		Item item = token.getItem();
		DXCNMLTool.setNode(node, item,currentMPMLRoot); //添加了一个Element参数
		return node;
	}
	
	@Override
	public Operation getOperationInfo(DXCNMLArticleToken token) {
		Operation operation = new Operation();
		Item item = token.getItem();
		DXCNMLTool.setOperation(operation, item);
		return operation;
	}
	
	@Override
	public void startOneArticle(DXCNMLArticleToken arg0) {
	}
	
	public String currentCNML(){
		return currentCNML;
	}
}
