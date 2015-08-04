/**
 * 文件名：User_other.java
 * 时间：2015年5月9日上午10:23:19
 * 作者：修维康
 */
package com.xuhao.activity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 类名：User_other 说明：用户对象
 */
public class User_other implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String account;
	private String User_otherName;
	private String password;
	private Date birthday;
	private int gender; // 0代表女生 1代表男生
	private boolean isOnline;
	private String location;
	private byte[] photo;
	private Bitmap  head_photo;
	private int age;
	private String User_otherBriefIntro;

	public String getUser_otherBriefIntro() {
		return User_otherBriefIntro;
	}

	public void setUser_otherBriefIntro(String User_otherBriefIntro) {
		this.User_otherBriefIntro = User_otherBriefIntro;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private ArrayList<User_other> friendList;

	public ArrayList<User_other> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<User_other> friendList) {
		this.friendList = friendList;
	}

	public User_other(String account, String User_othername, String password,
			Date birthday, int gender, byte[] photo) {
		this.account = account;
		this.User_otherName = User_othername;
		this.password = password;
		this.birthday = birthday;
		this.gender = gender;
		this.photo = photo;
	}
	public User_other( String User_othername, String User_otherBriefIntro) {

		this.User_otherName = User_othername;
		this.User_otherBriefIntro = User_otherBriefIntro;
	}

	public User_other() {

	}

	public byte[] getPhoto() {
		return photo;
	}

	public Bitmap getHead_photo() {
		return head_photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUser_otherName() {
		return User_otherName;
	}

	public void setUser_otherName(String User_otherName) {
		this.User_otherName = User_otherName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		User_other User_other = (User_other) o;
		if (this.id == User_other.getId())
			return true;
		return false;
	}
}
