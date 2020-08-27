package com.dcl.domain;

public class Rsa {

	private int id;
	private String public_key;
	private String private_key;
	public Rsa() {
		super();
	}
	public Rsa(int id, String public_key, String private_key) {
		super();
		this.id = id;
		this.public_key = public_key;
		this.private_key = private_key;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPublic_key() {
		return public_key;
	}
	public void setPublic_key(String public_key) {
		this.public_key = public_key;
	}
	public String getPrivate_key() {
		return private_key;
	}
	public void setPrivate_key(String private_key) {
		this.private_key = private_key;
	}
	@Override
	public String toString() {
		return "Rsa [id=" + id + ", public_key=" + public_key + ", private_key=" + private_key + "]";
	}
}
