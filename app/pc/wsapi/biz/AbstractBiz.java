package pc.wsapi.biz;

import org.codehaus.jackson.JsonNode;

public abstract class AbstractBiz {
	public abstract void execute (play.mvc.WebSocket.In<JsonNode> in, play.mvc.WebSocket.Out<JsonNode> out);
	
	protected static final String rtn = "rtn";
	
	protected static final String ok = "ok";
	protected static final String ng = "ng";
	protected static final String error = "error";
}
