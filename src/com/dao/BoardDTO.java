package com.dao;

import java.util.*;

public class BoardDTO {
	private int no;
	private String name;
	private String subject;
	private String content;
	private String pwd;
	private Date date;
	private int hit;

	private int group_id;
	private int group_step;
	private int group_tab;

	private int root;
	private int depth;

	private String msg;
	private String today;
	private String dbday;
	private int replycount;

	public int getReplycount() {
		return replycount;
	}

	public void setReplycount(int replycount) {
		this.replycount = replycount;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getGroup_step() {
		return group_step;
	}

	public void setGroup_step(int group_step) {
		this.group_step = group_step;
	}

	public int getGroup_tab() {
		return group_tab;
	}

	public void setGroup_tab(int group_tab) {
		this.group_tab = group_tab;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int gepth) {
		this.depth = depth;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getDbday() {
		return dbday;
	}

	public void setDbday(String dbday) {
		this.dbday = dbday;
	}

}
