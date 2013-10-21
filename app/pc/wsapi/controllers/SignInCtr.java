package pc.wsapi.controllers;

import org.codehaus.jackson.JsonNode;

import play.mvc.Controller;
import pc.wsapi.biz.SignInBiz;
import play.mvc.Result;
import play.mvc.WebSocket;

public class SignInCtr extends Controller{

	public static Result _index() {
		
		return ok(pc.wsapi.views.html.signIn.render());
	}
	
	public static WebSocket<JsonNode> signIn () {
		return new WebSocket<JsonNode> () {

			@Override
			public void onReady(play.mvc.WebSocket.In<JsonNode> in,
					play.mvc.WebSocket.Out<JsonNode> out) {
				
				SignInBiz sBiz = new SignInBiz();
				sBiz.execute(in, out);
			}
		};
	}
}
