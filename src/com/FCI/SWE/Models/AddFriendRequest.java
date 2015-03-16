package com.FCI.SWE.Models;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;



public class AddFriendRequest
{
	private String senderName;
	private String receiverName;
	private String senderID;
	private String receiverID;
	private String Status;
	public AddFriendRequest(String senderName, String receiverName, String senderID, String receiverID,String Status ) {
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.Status=Status;
	}
	public AddFriendRequest(String receiverName,  String receiverID) {
		this.receiverName = receiverName;
		this.receiverID = receiverID;
	}
	public String getsenderName() {
		return senderName;
	}

	public String getreceiverName() {
		return receiverName;
	}

	public String getsenderID() {
		return senderID;
	}
	
	public String getreceiverID() {
		return receiverID;
	}
	public String getStatus() {
		return Status;
	}
	public void setSenderName(String sN){
		senderName=sN;
	}
	public void setReceiverName(String rN){
		receiverName=rN;
	}
	public void setSenderID(String sID){
		senderID=sID;
	}
	public void setReceiverID(String rID){
		receiverID=rID;
	}
	public void setStatus(String s){
		Status=s;
	}

	public static AddFriendRequest getRequest(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new AddFriendRequest(object.get("receiverName").toString(), object.get(
					"receiverID").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static AddFriendRequest getUser(String receiverName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("receiverName").toString());
			if (entity.getProperty("receiverName").toString().equals(receiverName)) {
				AddFriendRequest returnedUser = new AddFriendRequest(entity.getProperty(
						"receiverName").toString(), entity.getProperty("receicerID").toString());
				return returnedUser;
			}
		}

		return null;
	}
	public Boolean saveRequest() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("requests", list.size() + 1);

		employee.setProperty("receiverName", this.receiverName);
		employee.setProperty("receiverID", this.receiverID);
		employee.setProperty("senderName", this.senderName);
		employee.setProperty("senderID", this.senderID);
		employee.setProperty("Status", this.Status);

		datastore.put(employee);

		return true;

	}
	
	public boolean Accept_request(String Sname,String rname)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			
			if (entity.getProperty("receiverName").toString().equals(rname)
					&&entity.getProperty("senderName").toString().equals(Sname)) {
				entity.setProperty("Status", "active");
				datastore.put(entity);
				return true;
			}
		}

		return false;	
		
	}
}

