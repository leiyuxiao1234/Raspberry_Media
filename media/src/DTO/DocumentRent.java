package DTO;

import java.sql.Timestamp;

public class DocumentRent {

	private String name;
	private Timestamp date;
	private String userId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "[Nom:"+name+", date:"+date+", userId:"+userId+"]";
	}
}
