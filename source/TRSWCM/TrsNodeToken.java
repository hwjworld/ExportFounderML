package TRSWCM;

import system.model.NodeToken;

public class TrsNodeToken implements NodeToken{
	private String channelid;

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	@Override
	public String toString() {
		return "channelid: "+channelid;
	}

	public String getID() {
		return channelid;
	}
}
