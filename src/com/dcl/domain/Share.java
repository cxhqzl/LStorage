package com.dcl.domain;

public class Share {

	private int id;
	private String name;
	private String path;
	private int fileLock;
	private String lockPassword;
	private String account;
	private String userName;
	private String createDate;
	private double size;
	public Share() {
		super();
	}
	public Share(int id, String name, String path, int fileLock, String lockPassword, String account, String userName,
			String createDate, double size) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.fileLock = fileLock;
		this.lockPassword = lockPassword;
		this.account = account;
		this.userName = userName;
		this.createDate = createDate;
		this.size = size;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getFileLock() {
		return fileLock;
	}
	public void setFileLock(int fileLock) {
		this.fileLock = fileLock;
	}
	public String getLockPassword() {
		return lockPassword;
	}
	public void setLockPassword(String lockPassword) {
		this.lockPassword = lockPassword;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "Share [id=" + id + ", name=" + name + ", path=" + path + ", fileLock=" + fileLock + ", lockPassword="
				+ lockPassword + ", account=" + account + ", userName=" + userName + ", createDate=" + createDate
				+ ", size=" + size + "]";
	}
	
}
