package com.dcl.domain;

public class SecureFile {

	private int id;
	private String folderName;
	private int pid;
	private String fatherName;
	private double size;
	private String createDate;
	private String account;
	public SecureFile() {
		super();
	}
	public SecureFile(int id, String folderName, int pid, String fatherName, double size, String createDate,
			String account) {
		super();
		this.id = id;
		this.folderName = folderName;
		this.pid = pid;
		this.fatherName = fatherName;
		this.size = size;
		this.createDate = createDate;
		this.account = account;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "Document [id=" + id + ", folderName=" + folderName + ", pid=" + pid + ", fatherName=" + fatherName
				+ ", size=" + size + ", createDate=" + createDate + ", account=" + account + "]";
	}
}
