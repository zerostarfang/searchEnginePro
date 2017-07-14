package com.app.backend.utils;

import java.util.*;

public class Word {
	String value;
	int frequency;
	
	public Word(String s) {
		value = s;
		frequency = 0;
	}
	
	public String getval() {
		return value;
	}
	
	public int getfreq() {
		return frequency;
	}
	
	public void setfreq(int n) {
		frequency = n;
	}
	
	public void incfreq() {
		frequency++;
	}
}
