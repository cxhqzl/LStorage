package com.dcl.domain;

public class Friend {

	private int id;
	private String myAccount;
	private String friendAccount;
	private String createDate;
	public Friend() {
		super();
	}
	public Friend(int id, String myAccount, String friendAccount, String createDate) {
		super();
		this.id = id;
		this.myAccount = myAccount;
		this.friendAccount = friendAccount;
		this.createDate = createDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMyAccount() {
		return myAccount;
	}
	public void setMyAccount(String myAccount) {
		this.myAccount = myAccount;
	}
	public String getFriendAccount() {
		return friendAccount;
	}
	public void setFriendAccount(String friendAccount) {
		this.friendAccount = friendAccount;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Friend [id=" + id + ", myAccount=" + myAccount + ", friendAccount=" + friendAccount + ", createDate="
				+ createDate + "]";
	}
	
}
