package pc.wsapi.controllers;

import org.codehaus.jackson.JsonNode;

import pc.common.controllers.AbstractController;
import pc.wsapi.biz.LoginBiz;
import play.mvc.Result;
import play.mvc.WebSocket;

public class LoginCtr extends AbstractController {
	
	public static WebSocket<JsonNode> autholization () {
		return new WebSocket<JsonNode> () {

			@Override
			public void onReady(play.mvc.WebSocket.In<JsonNode> in,
					play.mvc.WebSocket.Out<JsonNode> out) {
				
				LoginBiz biz = new LoginBiz();
				biz.execute(in, out);
			}
		};
	}


	public static Result _index() {
		return ok(pc.wsapi.views.html.login.render());
	}
}
