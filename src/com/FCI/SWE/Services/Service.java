package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.UserEntity;

import java.io.IOException;  
import java.io.PrintWriter;

import com.FCI.SWE.Models.AddFriendRequest;
  



import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class Service {
	
	
	/*@GET
	@Path("/index")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}*/


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
/*
	@POST
	@Path("/signout")
	public 
	*/
	
	 @POST
	@Path("/AddFriendService")
	public String addFriendService(@FormParam("SenderName") String SenderName,
			@FormParam("ReceiverName") String ReceiverName) {
		JSONObject object = new JSONObject();
		UserEntity user1 = UserEntity.getUsers(SenderName);
		UserEntity user2 = UserEntity.getUsers(ReceiverName);
		if (user1 == null ||user2 == null) {
			object.put("Status", "Failed");

		} else {
		object.put("Status", "OK");
		AddFriendRequest obj=new AddFriendRequest
		(user1.getName(),user2.getName(),user1.getEmail(),user2.getEmail(),"pinding");
		obj.saveRequest();
		}

		return object.toString();

	}
	 
	 @POST
		@Path("/AcceptFriendService")
		public String AcceptFriendService(@FormParam("SenderName") String SenderName,
				@FormParam("ReceiverName") String ReceiverName) {
			JSONObject object = new JSONObject();
			AddFriendRequest obj=new AddFriendRequest(SenderName,ReceiverName);
			if (!obj.Accept_request(SenderName, ReceiverName)) {
				object.put("Status", "Failed");

			} else {
			object.put("Status", "you are now friends");
			
		
			}

			return object.toString();

		}
		 
	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
	@POST
	@Path("/SearchService")
	public String SearchService(@FormParam("uname") String uname) {
		Vector<UserEntity> users = UserEntity.SearchUser(uname);
		JSONArray returnedJson = new JSONArray();
		System.out.println(users);
		for(UserEntity user: users)
		{
			JSONObject object=new JSONObject();
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			
			returnedJson.add(object) ;
		}

		return returnedJson.toString();

	}


}