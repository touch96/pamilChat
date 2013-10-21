package pc.wsapi.biz;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import pc.wsapi.dbs.Users;
import pc.wsapi.utils.JsonUtil;
import play.Logger;
import play.libs.Json;
import play.libs.F.Callback;
import play.mvc.WebSocket.In;
import play.mvc.WebSocket.Out;

public class LoginBiz extends AbstractBiz {

	@Override
	public void execute(In<JsonNode> in, Out<JsonNode> out) {
		in.onMessage(new BizCallback(out));

	}
	
	public static class BizCallback implements Callback<JsonNode> {
		private Out<JsonNode> out;
		
		public BizCallback (Out<JsonNode> out) {
			this.out = out;
		}
		
		@Override
		public void invoke(JsonNode ns) throws Throwable {
			Logger.debug("invoke");
			ObjectNode echo = Json.newObject();
			
			try {
				/*
				 * 取得項目
				 * 　・　端末uuid
				 * 　・　id,pw
				 * 
				 */
				String id = ns.get("id").asText();
				String pw = ns.get("pw").asText();
				
				//DBに該当uuidが入っているか確認
				Users users = new Users();
				users.id  = id;
				users.pw = pw;
				
				boolean isExistsUser = Users.find.equals(users);
				
				if (!isExistsUser) {
					echo.put(rtn, JsonUtil.setRtn(ok, null));
				} else {
					echo.put(rtn, JsonUtil.setRtn(ok, null));
				}
			} catch (Exception e) {
				e.printStackTrace();
				echo.put(rtn, JsonUtil.setRtn(error, null));
			} finally {
	            out.write(echo);
			}
		}
		
	}
}
