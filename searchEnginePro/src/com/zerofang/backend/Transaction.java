package com.zerofang.backend;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
	// / Possition in database
	public int first;
	// /List of items in transaction
	public List<Integer> second;

	/**
	 * Class constructor
	 */
	public Transaction() {
		this.first = 0;
		this.second = new ArrayList();
	}

	/**
	 * Clear transaction
	 */
	void clear() {
		this.first = 0;
		this.second = new ArrayList();
	}

}
