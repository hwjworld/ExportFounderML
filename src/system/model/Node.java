package system.model;

/**
 * @author Administrator
 * @version 1.0
 * @created 26-¾ÅÔÂ-2010 11:24:38
 */
public class Node {

	private String TargetNode;
	private int TargetNodeId;

	public Node(){

	}

	public void finalize() throws Throwable {

	}

	public String getTargetNode() {
		return TargetNode;
	}

	public void setTargetNode(String targetNode) {
		TargetNode = targetNode;
	}

	public int getTargetNodeId() {
		return TargetNodeId;
	}

	public void setTargetNodeId(int targetNodeId) {
		TargetNodeId = targetNodeId;
	}

}