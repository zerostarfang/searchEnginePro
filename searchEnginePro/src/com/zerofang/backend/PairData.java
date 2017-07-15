package com.zerofang.backend;

import java.util.ArrayList;
import java.util.List;

import com.app.backend.utils.Transaction;

public class PairData {
	// /Database
	public List<Transaction> dataBase;
	// /Indeces
	public List<Integer> indeces;

	/**
	 * Class constructor
	 */
	public PairData() {
		this.dataBase = new ArrayList();
		this.indeces = new ArrayList();
	}

	/**
	 * Clean pairdata items
	 */
	public void clear() {
		this.dataBase.clear();
		this.indeces.clear();
	}
}
