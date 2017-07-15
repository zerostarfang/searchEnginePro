package com.zerofang.pagerank.entity;

import com.zerofang.pagerank.entity.Identifiable;

public class Word implements Identifiable {
	private int ID;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private int urlID;
	private int timesInUrl;
	private int position;

	public Word() {
		super();
	}

	public Word(int urlID, String value) {
		super();
		this.urlID = urlID;
		this.value = value;
	}

	public int getUrlID() {
		return urlID;
	}

	public void setUrlID(int urlID) {
		this.urlID = urlID;
	}

	public int getTimesInUrl() {
		return timesInUrl;
	}

	public void setTimesInUrl(int timesInUrl) {
		this.timesInUrl = timesInUrl;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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
