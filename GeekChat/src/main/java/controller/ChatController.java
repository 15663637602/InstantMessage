package controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import po.Message;
import po.User;
import service.MessageDao;
import service.UserDao;
import websocket.MyWebSocketHandler;
import websocket.PublicChatSocketHandler;

import com.google.gson.GsonBuilder;
/**
 * 
 * @author Yuqi Li
 * date: Dec 2, 2017 1:06:56 AM
 */
@Controller
public class ChatController {
	@Autowired
	PublicChatSocketHandler phandler;
	
	
	@Autowired
	UserDao userDao;
	@Autowired
	MessageDao messageDao;
	
	
	@RequestMapping(value="onlineusers",method = RequestMethod.GET)
	@ResponseBody
	public Set<User> onlineusers(HttpSession session) throws InterruptedException{
		Map<String, WebSocketSession> map=MyWebSocketHandler.userSocketSessionMap;
		Set<String> set=map.keySet();
		Iterator<String> it = set.iterator();
		Set<User> nameset=new HashSet<User>();
		while(it.hasNext()){
			String entry = it.next();
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("u_id", entry);
			String name=userDao.getnamebyid(params);
			String user=(String)session.getAttribute("username");
			if(!user.equals(name)){
				User usr = new User();
				usr.setU_id(entry);
				usr.setUsername(name);
				nameset.add(usr);
			}
				
		}//
		return nameset;
	}
	//public chatroom
	@RequestMapping(value="ponlineusers",method = RequestMethod.GET)
	@ResponseBody
	public Set<User> ponlineusers(HttpSession session) throws InterruptedException{
		Map<String, WebSocketSession> map=PublicChatSocketHandler.puserSocketSessionMap;
		Set<String> set=map.keySet();
		Iterator<String> it = set.iterator();
		Set<User> nameset=new HashSet<User>();
		while(it.hasNext()){
			String entry = it.next();
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("u_id", entry);
			String name=userDao.getnamebyid(params);
			String user=(String)session.getAttribute("username");
			if(!user.equals(name)){
				User usr = new User();
				usr.setU_id(entry);
				usr.setUsername(name);
				nameset.add(usr);
			}
				
		}//
		return nameset;
	}
	
	// broadcast
		@ResponseBody
		@RequestMapping(value = "broadcast", method = RequestMethod.POST)
		public void broadcast(@RequestParam("text") String text,@RequestParam("username") String username) throws IOException {
			System.out.println("broadcast");
			Message msg = new Message();
			msg.setDate(new Date());
			msg.setFrom(-1L);//-1 indicates public message
			msg.setFromName(username);
			msg.setTo("0");
			msg.setText(text);
			phandler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		}
		
	@RequestMapping(value = "getuid", method = RequestMethod.POST)
	@ResponseBody
	public User getuid(@RequestParam("username")String username){
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("username", username);
		String a = userDao.getidbyname(params);
		User u=new User();
		u.setU_id(a);
		return u;
	}
	
	@RequestMapping(value = "openChat" , method = RequestMethod.GET)
	public ModelAndView openChat(@RequestParam("uid") String uid,HttpServletRequest request){
		String uId = uid;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("u_id", uId);
		String name=userDao.getnamebyid(params);
		request.setAttribute("objectName", name);
		request.setAttribute("objectId", uId);
		
		return new ModelAndView("/WEB-INF/chatroom");
	}
	
	@RequestMapping(value = "openPublicC" , method = RequestMethod.GET)
	public ModelAndView openPublicC(HttpServletRequest request){
		
		return new ModelAndView("/WEB-INF/publicChat");
	}
	
	@RequestMapping(value = "getMessages", method = RequestMethod.GET)
	@ResponseBody
	public List<Message> getMessages(@RequestParam("myId")String myId,@RequestParam("objectId")String objectId){
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("fromId", myId);
		params.put("to", objectId);
		List<Message> message = messageDao.getMessageBy2Id(params);
		System.out.println("getmessages----------");
		for(Message m : message){
			System.out.println(m.toString());
		}
		return message;
	}
}
