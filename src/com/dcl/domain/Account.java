package com.dcl.domain;

public class Account {
	
	private String account;
	private String password;
	private String securePassword;
	private int role;
	private String userName;
	private String image;
	private String remark;
	private int type;
	
	public Account() {
		super();
	}

	public Account(String account, String password, String securePassword, int role, String userName, String image,
			String remark, int type) {
		super();
		this.account = account;
		this.password = password;
		this.securePassword = securePassword;
		this.role = role;
		this.userName = userName;
		this.image = image;
		this.remark = remark;
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecurePassword() {
		return securePassword;
	}

	public void setSecurePassword(String securePassword) {
		this.securePassword = securePassword;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Account [account=" + account + ", password=" + password + ", securePassword=" + securePassword
				+ ", role=" + role + ", userName=" + userName + ", image=" + image + ", remark=" + remark + ", type="
				+ type + "]";
	}

}
