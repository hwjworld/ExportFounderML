package DXCNML;

import cnml.node.Item;
import system.model.ArticleToken;

public class DXCNMLArticleToken implements ArticleToken {
	private Item item;
	private boolean isLast = false;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
}
