package com.zerofang.backend;

import java.util.*;
import java.io.*;

import com.app.backend.utils.Word;

public class HotItems {

	private static List<Word> wlist;

	public static void main(String[] args) throws IOException {
		getHotItems(4);
	}

	// return top k hot key words as a list of strings when web page starts
	// result is delimited by "|"
	// k = 4
	public static void getHotItems(int k) throws IOException {
		wlist = new ArrayList();
		wlist.clear();
		BufferedReader input = new BufferedReader(new FileReader(
				"frequency.txt"));
		String buff = input.readLine();
		// "sssss#xxx\n"
		while (buff != null) {
			String str[] = buff.split("#");
			Word w = new Word(str[0]);
			w.setfreq(Integer.parseInt(str[1]));
			wlist.add(w);
			buff = input.readLine();
		}
		input.close();
		sort(k);
		for (int i = 0; i < k; i++) {
			System.out.print(wlist.get(i).getval() + "|");
		}
	}

	public static void sort(int k) {
		for (int i = 0; i < k; i++) {
			for (int j = wlist.size() - 1; j > i; j--) {
				if (wlist.get(j).getfreq() > wlist.get(j - 1).getfreq()) {
					Collections.swap(wlist, j, j - 1);
				}
			}
		}
	}

}
