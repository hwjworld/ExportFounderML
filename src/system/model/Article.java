package system.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @created 26-¾ÅÔÂ-2010 11:24:36
 */
public class Article {

	private String Abstract;
	private List<Attachment> attachements = new LinkedList<Attachment>();
	private int Attr = 63;
	private String Author;
	private String Content;
	private double DisplayOrder;
	private int DocID;
	private String Editor;
	private String ExpirationTime;
	private int hasTitlePic;
	private int Importance;
	private String Introtitle;
	private String JabbarMark;
	private String keyword;
	private String Liability;
	private String LinkTitle;
	private String Multiattach;
	private String Nsdate;
	private int piccount;
	private Attachment piclink = new Attachment();
	private String RelatedNode;
	private String RelatedTitle;
	private String Source;
	private String SourceUrl;
	private String Subtitle;
	private String Title;
	private String Url;
	private int WordCount;

	public Article(){

	}

	public void finalize() throws Throwable {

	}

	public String getAbstract(){
		return Abstract;
	}

	public List<Attachment> getAttachements(){
		return attachements;
	}

	public int getAttr(){
		return Attr;
	}

	public String getAuthor(){
		return Author;
	}

	public String getContent(){
		return Content;
	}

	public double getDisplayOrder(){
		return DisplayOrder;
	}

	public int getDocID(){
		return DocID;
	}

	public String getEditor(){
		return Editor;
	}

	public String getExpirationTime(){
		return ExpirationTime;
	}

	public int gethasTitlePic(){
		return hasTitlePic;
	}

	public int getImportance(){
		return Importance;
	}

	public String getIntrotitle(){
		return Introtitle;
	}

	public String getJabbarMark(){
		return JabbarMark;
	}

	public String getkeyword(){
		return keyword;
	}

	public String getLiability(){
		return Liability;
	}

	public String getLinkTitle(){
		return LinkTitle;
	}

	public String getMultiattach(){
		return Multiattach;
	}

	public String getNsdate(){
		return Nsdate;
	}

	public int getpiccount(){
		return piccount;
	}

	public Attachment getpiclink(){
		return piclink;
	}

	public String getRelatedNode(){
		return RelatedNode;
	}

	public String getRelatedTitle(){
		return RelatedTitle;
	}

	public String getSource(){
		return Source;
	}

	public String getSourceUrl(){
		return SourceUrl;
	}

	public String getSubtitle(){
		return Subtitle;
	}

	public String getTitle(){
		return Title;
	}

	public String getUrl(){
		return Url;
	}

	public int getWordCount(){
		return WordCount;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setAbstract(String newVal){
		Abstract = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void addAttachement(Attachment attachment){
		attachements.add(attachment);
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setAttr(int newVal){
		Attr = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setAuthor(String newVal){
		Author = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setContent(String newVal){
		Content = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDisplayOrder(double newVal){
		DisplayOrder = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDocID(int newVal){
		DocID = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setEditor(String newVal){
		Editor = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setExpirationTime(String newVal){
		ExpirationTime = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void sethasTitlePic(int newVal){
		hasTitlePic = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setImportance(int newVal){
		Importance = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setIntrotitle(String newVal){
		Introtitle = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setJabbarMark(String newVal){
		JabbarMark = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setkeyword(String newVal){
		keyword = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setLiability(String newVal){
		Liability = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setLinkTitle(String newVal){
		LinkTitle = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMultiattach(String newVal){
		Multiattach = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setNsdate(String newVal){
		Nsdate = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setpiccount(int newVal){
		piccount = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setpiclink(Attachment newVal){
		piclink = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setRelatedNode(String newVal){
		RelatedNode = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setRelatedTitle(String newVal){
		RelatedTitle = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setSource(String newVal){
		Source = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setSourceUrl(String newVal){
		SourceUrl = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setSubtitle(String newVal){
		Subtitle = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setTitle(String newVal){
		Title = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setUrl(String newVal){
		Url = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setWordCount(int newVal){
		WordCount = newVal;
	}

}