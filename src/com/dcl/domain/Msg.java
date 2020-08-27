package com.dcl.domain;

public class Msg {

	private int id;
	private String context;
	private String fromAccount;
	private String toAccount;
	private String createDate;
	private String type;
	public Msg() {
		super();
	}
	public Msg(int id, String context, String fromAccount, String toAccount, String createDate, String type) {
		super();
		this.id = id;
		this.context = context;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.createDate = createDate;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Msg [id=" + id + ", context=" + context + ", fromAccount=" + fromAccount + ", toAccount=" + toAccount
				+ ", createDate=" + createDate + ", type=" + type + "]";
	}
}
