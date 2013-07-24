package DXCNML;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import system.model.Article;
import system.model.Attachment;
import system.model.FilePathShape;
import system.model.Node;
import system.model.Operation;
import cnml.node.ContentItem;
import cnml.node.Creator;
import cnml.node.DataContent;
import cnml.node.DateTime;
import cnml.node.ExtendedTitle;
import cnml.node.Item;
import cnml.node.WordCount;

import com.founder.enp.dataportal.util.StringUtils;
import com.founder.enp.dataportal.util.StringValueUtils;

public class DXCNMLTool {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setArticle(Article article,Item item) {
		article.setDocID(item.getIdAttr().hashCode());
		
		String title = item.getMetaInfo().getDescriptionMetaGroup().getTitles().getHeadLineText(0);
		article.setTitle(StringUtils.getString(title, ""));
		String subtitle = item.getMetaInfo().getDescriptionMetaGroup().getTitles().getSubHeadLineText(0);
		article.setSubtitle(StringUtils.getString(subtitle));
		List<ExtendedTitle> extendedTitles = item.getMetaInfo().getDescriptionMetaGroup().getTitles().getExtendedTitles().getExtendedTitleList();
		String introtitle = getExtendedTitlesValue(extendedTitles,"IntroTitle");
		article.setIntrotitle(StringUtils.getString(introtitle));
		
		List creators = item.getMetaInfo().getDescriptionMetaGroup().getCreators().getCreatorList();
		String author = getCreatorsValue(creators, "Author");
		article.setAuthor(StringUtils.getString(author));
		String editor = getCreatorsValue(creators, "Editor");
		article.setEditor(StringUtils.getString(editor));
		
		List datetimes = item.getMetaInfo().getDescriptionMetaGroup().getDateTimes().getDateTimeList();
		String nsdate = getDatetimeValue(datetimes, "PublishedDate");
		article.setNsdate(StringUtils.getString(nsdate,"2000-01-01"));

		String abstrac = item.getMetaInfo().getDescriptionMetaGroup().getAbstractText(0);
		article.setAbstract(StringUtils.getString(abstrac));

		String keyword = item.getMetaInfo().getDescriptionMetaGroup().getKeywords().getKeywordText(0);
		article.setkeyword(StringUtils.getString(keyword));
		
		String source = item.getMetaInfo().getDescriptionMetaGroup().getSourceInfo(0).getItemSource().getNameText(0);
		article.setSource(StringUtils.getString(source));
		

		String wordCount = null;
		try{
			WordCount wc = item.getContents().getContentItem(0).getMetaInfo().getCharacteristicMetaGroup().getWordCount();
			if(wc != null){
				wordCount = wc.getText();
			}
			if(wordCount==null)
			{
				wordCount = "0";
			}
		}catch (Exception e) {
			wordCount = "0";
		}
		article.setWordCount(StringValueUtils.getInt(wordCount));
		


		StringBuffer contentbuffer = new StringBuffer("");
		StringBuffer notTextBuffer=new StringBuffer("");
		List cilist = null;
		try{
			cilist = item.getContents().getContentItemList();
		}catch (Exception e) {
		}
		if(cilist != null){
			for(int i=0;i<cilist.size();i++){
				ContentItem ci = (ContentItem)cilist.get(i);
				int contenttype = ci.getType();
				if(contenttype != ContentItem.TEXTCITYPE){
					String imgname = ci.getHrefAttr();
					DataContent o = ci.getDataContent();
					if(o!= null){
						notTextBuffer.append("<p><img src=\""+imgname+"\" /><br/>"+StringUtils.getString(o.getText())+"</p>");
					}
				}else{
					DataContent o = ci.getDataContent();
					if(o!= null){
						contentbuffer.append(StringUtils.getString(o.getText()));
					}
				}
			}
			StringBuffer content=new StringBuffer("<p>");
			if(!notTextBuffer.toString().equals("")){
				content.append("<center>"+StringUtils.getString(notTextBuffer.toString())+"</center>");
			}
			content.append("<p>"+StringUtils.getString(contentbuffer.toString()).replaceAll("\n", "</p><p>")+"</p>");
			article.setContent(content.toString());
		}
	}

	public static void setNode(Node node,Item item,Element currentMPMLRoot) {
		//以前版本
		//String column = item.getMetaInfo().getMetaGroup(0).getValueByXPath("Columns/Column");
		try{
			//获取pageId
			String pageId=item.getContents().getContentItem(0).getMetaInfo().getMetaGroup(0).getValueByXPath("Regions/PageId");
			
			StringBuffer targetNode=new StringBuffer("");
			if(currentMPMLRoot!=null){
				Element pagesEle=currentMPMLRoot.element("Periods").element("Period").element("Pages");
				Element descriptionMetaGroupEle=currentMPMLRoot.element("Periods").element("Period").element("MetaInfo").element("DescriptionMetaGroup");
				Element paperNameEle=descriptionMetaGroupEle.element("Paper").element("PaperName");
				targetNode.append(paperNameEle.getText()); //添加报纸信息
				Element DateTimesEle=descriptionMetaGroupEle.element("DateTimes");
				
				for(Iterator i=DateTimesEle.elementIterator();i.hasNext();){
					Element dateTime=(Element)i.next();
					if("PublishedDate".equalsIgnoreCase(dateTime.attributeValue("kind"))){
						String dateTimeStr=dateTime.getText();
						if(dateTimeStr!=null){
							String dateTimeArr[]=dateTimeStr.split("-");
							if(dateTimeArr!=null&&dateTimeArr.length>2)
								targetNode.append("~").append(dateTimeArr[0]).append("~").append(dateTimeArr[1]); 
						}
						break;
					}
				}
				for(Iterator i=pagesEle.elementIterator();i.hasNext();){
					Element page=(Element)i.next();
					if(pageId.equals(page.attributeValue("id"))){
						//添加版次
						targetNode.append("~").append(page.element("MetaInfo").element("DescriptionMetaGroup").element("PageNumber").getText());
						break;
					}
				}
				//格式 ：钱江晚报~2010~11~n0002
				if(targetNode.toString().length()>0){
					node.setTargetNode(targetNode.toString());
				}else{
					node.setTargetNode("ZBCNML~NOCOLUMN");		
				}
			}else{
				node.setTargetNode("ZBCNML~NOCOLUMN");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void setOperation(Operation operation,Item item) {
		operation.setAction(1);
		operation.setArticleType(1);
		operation.setObjectType(1);
		operation.setPublishState(1);
		operation.setSourceSystem("鼎翔反解CNML");
		operation.setTargetLib(2);
	}
	
	private static String getExtendedTitlesValue(List<ExtendedTitle> extendedTitles,String kind){
		if(StringUtils.isNull(StringUtils.getString(kind),true)){
			return "";
		}
		String rv = "";
		for(ExtendedTitle extended:extendedTitles){
			if(kind.equals(extended.getKindAttr())){
				rv = extended.getText();
				break;
			}
		}
		return rv;
	}

	private static String getCreatorsValue(List<Creator> creators,String kind){
		if(StringUtils.isNull(StringUtils.getString(kind),true)){
			return "";
		}
		String rv = "";
		for(Creator creator:creators){
			if(kind.equals(creator.getKindAttr())){
				rv = creator.getName(0).getFullName(0).getText();
				break;
			}
		}
		return rv;
	}

	private static String getDatetimeValue(List<DateTime> datetimes,String kind){
		if(StringUtils.isNull(StringUtils.getString(kind),true)){
			return "";
		}
		String rv = "";
		for(DateTime datetime:datetimes){
			if(kind.equals(datetime.getKindAttr())){
				rv = datetime.getText();
				break;
			}
		}
		return rv;
	}

	@SuppressWarnings("unchecked")
	public static List<Attachment> getAttachments(File currentCNMLFile, Item item) {
		
		List<Attachment> list = new ArrayList<Attachment>();
		List<ContentItem> cilist = null;
		try{
			cilist = item.getContents().getContentItemList();
		}catch (Exception e) {
		}
		if(cilist != null){
			File cnmldirfile = new File(DXCNMLScanner.getDXCNML_DIR());
			for(int i=0;i<cilist.size();i++){
				Attachment attachment = new Attachment();
				ContentItem ci = (ContentItem)cilist.get(i);
				int contenttype = ci.getType();
				if(contenttype != ContentItem.TEXTCITYPE){
					String imgname = ci.getHrefAttr();
					DataContent o = ci.getDataContent();
					attachment.setAttdesc(StringUtils.getString(o.getText()));
					
					File imgfile = new File(currentCNMLFile.getParent()+"/"+imgname);
					
					attachment.setAttname(imgname);
					FilePathShape shape = new FilePathShape();
					
					String path = StringUtils.replace(imgfile.getAbsolutePath(), cnmldirfile.getAbsolutePath(), "");
					
					shape.setpath(path);
					attachment.setFileshape(shape);
					attachment.setType(1);
					list.add(attachment);
				}
			}
		}
		return list;
	}
}
