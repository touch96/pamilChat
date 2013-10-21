package pc.wsapi.biz;

import java.util.List;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import pc.wsapi.dbs.Users;
import pc.wsapi.utils.JsonUtil;
import play.Logger;
import play.libs.Json;
import play.libs.F.Callback;
import play.mvc.WebSocket.In;
import play.mvc.WebSocket.Out;

public class MsgPushBiz extends AbstractBiz {

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
				String msg = ns.get("msg").asText();
//				String uuid = ns.get("uuid").asText();
				
				
				Users users = new Users();
				List<Users> userslist = users.find.findList();
				
				for (Users tmpUsers : userslist) {
					Logger.debug("tmpUsers : " + tmpUsers.id);
				}
				
				PushNotificationPayload payload = PushNotificationPayload.complex();
		        payload.addAlert(msg);
		        payload.addBadge(1);
		        payload.addSound("default");
		        payload.addCustomDictionary("id", "1");


		        Logger.debug (payload.toString());
		        List<PushedNotification> NOTIFICATIONS = 
		        		Push.payload(payload, "/usr/local/keys/pamilApns.p12", "s6422635", true, "");

		        for (PushedNotification NOTIFICATION : NOTIFICATIONS) {
		            if (NOTIFICATION.isSuccessful()) {
		                    /* APPLE ACCEPTED THE NOTIFICATION AND SHOULD DELIVER IT */  
		                    System.out.println("PUSH NOTIFICATION SENT SUCCESSFULLY TO: " +
		                                                    NOTIFICATION.getDevice().getToken());
		                    /* STILL NEED TO QUERY THE FEEDBACK SERVICE REGULARLY */  
		            } 
		            else {
		                    String INVALIDTOKEN = NOTIFICATION.getDevice().getToken();
		                    /* ADD CODE HERE TO REMOVE INVALIDTOKEN FROM YOUR DATABASE */  

		                    /* FIND OUT MORE ABOUT WHAT THE PROBLEM WAS */  
		                    Exception THEPROBLEM = NOTIFICATION.getException();
		                    THEPROBLEM.printStackTrace();

		                    /* IF THE PROBLEM WAS AN ERROR-RESPONSE PACKET RETURNED BY APPLE, GET IT */  
		                    ResponsePacket THEERRORRESPONSE = NOTIFICATION.getResponse();
		                    if (THEERRORRESPONSE != null) {
		                            System.out.println(THEERRORRESPONSE.getMessage());
		                    }
		            }
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
