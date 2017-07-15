package com.zerofang.pagerank.entity;

import java.util.Date;
import com.zerofang.pagerank.entity.Identifiable;

public class Url implements Identifiable {
	private int ID;
	private String urlName;
	private double pagerankValue;
	private String title;
	private String author;
	private Date date;
	private String text;
	private int category;

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Url() {
		super();
	}

	public Url(int urlID) {
		super();
		this.ID = urlID;
	}

	public Url(int urlID, String urlName, double pagerankValue) {
		super();
		this.ID = urlID;
		this.urlName = urlName;
		this.pagerankValue = pagerankValue;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public double getPagerankValue() {
		return pagerankValue;
	}

	public void setPagerankValue(double pagerankValue) {
		this.pagerankValue = pagerankValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.ID = id;
	}
}
