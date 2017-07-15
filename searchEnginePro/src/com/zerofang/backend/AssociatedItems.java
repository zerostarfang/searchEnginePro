package com.zerofang.backend;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import com.app.backend.utils.Word;

public class AssociatedItems {

	public static void main(String[] args) throws IOException {
		if (args.length <= 0) {
			System.out.println("invalid input");
		} else {
			String[] str = args[0].split("\\|");
			getAssociatedItems(str);
			// String[] test = new String[]{"a", "b"};
			// getAssociatedItems(test);
		}

	}

	// return history items most associated with input words
	// result is delimited by "|"
	// instr represents the entire input string typed by the user
	// words is a list of strings which represents the segmentation result of
	// instr
	public static void getAssociatedItems(String[] words) throws IOException {
		List<Word> wlist = new ArrayList();
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
		input = new BufferedReader(new FileReader("patterns.txt"));
		int[] score = new int[wlist.size()];
		for (int i = 0; i < score.length; i++) {
			score[i] = 0;
		}
		buff = input.readLine();
		while (buff != null) {
			String str[] = buff.split(":");
			String tok[] = str[0].split(" ");
			int freq = Integer.parseInt(str[1]);
			int[] trans = new int[tok.length];
			int cnt = 0;
			for (String x : tok) {
				if (x.length() > 0) {
					trans[cnt] = Integer.parseInt(x);
					cnt++;
				}
			}
			cnt = 0;
			for (String x : words) {
				for (int y = 0; y < trans.length; y++) {
					if (trans[y] >= 0 && x.equals(wlist.get(trans[y]).getval())) {
						cnt++;
						trans[y] = -1;
						break;
					}
				}
			}
			int s = cnt * freq;
			for (int i = 0; i < trans.length; i++) {
				if (trans[i] >= 0) {
					if (trans[i] == 4) {
						for (int x : trans) {
							System.out.print(x + " ");
						}
						System.out.println();
					}
					score[trans[i]] += s;
				}
			}
			buff = input.readLine();
		}
		input.close();
		for (int i = 0; i < 5; i++) {
			int max = 0;
			int k = 0;
			for (int j = 0; j < score.length; j++) {
				if (score[j] > max) {
					max = score[j];
					k = j;
				}
			}
			if (score[k] > 0) {
				System.out.print(wlist.get(k).getval() /* + ":" + score[k] */
						+ "|");
			}
			score[k] = 0;
		}
		System.out.println();
	}

}
