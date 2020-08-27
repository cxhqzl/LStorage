package com.dcl.domain;

public class Log {

	private int id;
	private String context;
	private String type;
	private String createDate;
	private String source;
	private String account;
	public Log() {
		super();
	}
	public Log(int id, String context, String type, String createDate, String source, String account) {
		super();
		this.id = id;
		this.context = context;
		this.type = type;
		this.createDate = createDate;
		this.source = source;
		this.account = account;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", context=" + context + ", type=" + type + ", createDate=" + createDate + ", source="
				+ source + ", account=" + account + "]";
	}
	
}
