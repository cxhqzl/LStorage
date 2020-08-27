package com.dcl.domain;

public class FileSize {

	private int id;
	private double uploadSize;
	private double downloadSize;
	private String createDate;
	private String account;
	public FileSize() {
		super();
	}
	public FileSize(int id, double uploadSize, double downloadSize, String createDate, String account) {
		super();
		this.id = id;
		this.uploadSize = uploadSize;
		this.downloadSize = downloadSize;
		this.createDate = createDate;
		this.account = account;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getUploadSize() {
		return uploadSize;
	}
	public void setUploadSize(double uploadSize) {
		this.uploadSize = uploadSize;
	}
	public double getDownloadSize() {
		return downloadSize;
	}
	public void setDownloadSize(double downloadSize) {
		this.downloadSize = downloadSize;
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
		return "FileSize [id=" + id + ", uploadSize=" + uploadSize + ", downloadSize=" + downloadSize + ", createDate="
				+ createDate + ", account=" + account + "]";
	};
}
