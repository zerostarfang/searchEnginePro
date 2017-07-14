package com.app.backend.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class WriteToLog {
	
	public static void main(String[] args) throws IOException {
		if(args.length < 2){
			System.out.println("invalid input");
		}
		else {
			String[] words = args[1].split("\\|");
			List<String> wlist = new ArrayList();
			for (String x : words) {
				if (x.length() > 0) {
					wlist.add(x);	
				}
			}
			WriteToLog(args[0], wlist);
		}
		//List<String> test1 = new ArrayList<String>() {{ add("a"); add("c");	add("d"); }};
		//WriteToLog("acd", test1);
	}
	
	// return history items most associated with input words
	// result is delimited by "|"
	// instr represents the entire input string typed by the user
	// words is a list of strings which represents the segmentation result of instr
	public static void WriteToLog(String instr, List<String> words) throws IOException{
		// history log document : "history.txt"
		// word frequency document : "frequency.txt"
		
		// write to history
		BufferedWriter output = new BufferedWriter(new FileWriter("history.txt", true));
		// "ssssss#xx|xx|xx@yyyy/MM/dd HH:mm:ss\n"
		// get current time
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String now = dateFormat.format(d);
		String buff = "";
		buff += instr + "#";
		for (String s : words) {
			buff += s + "|";
		}
		buff += "@" + now;
		output.write(buff);
		output.newLine();
		output.close();
		
		// update word frequency list
		BufferedReader input = new BufferedReader(new FileReader("frequency.txt"));
		List<Word> wlist = new ArrayList();
		wlist.clear();
		buff = input.readLine();
		// "sssss#xxx\n"
		while (buff != null) {
			String str[] = buff.split("#");
			Word w = new Word(str[0]);
			w.setfreq(Integer.parseInt(str[1]));
			wlist.add(w);
			buff = input.readLine();
		}
		input.close();
		for (String s : words) {
			boolean find = false;
			for (Word w : wlist) {
				if (s.equals(w.getval())) {
					find = true;
					w.incfreq();
					break;
				}
			}
			if (!find) {
				Word w = new Word(s);
				w.incfreq();
				wlist.add(w);
			}
		}
		// write back
		output = new BufferedWriter(new FileWriter("frequency.txt"));
		for (Word w : wlist) {
			buff = w.getval() + "#" + w.getfreq();
			output.write(buff);
			output.newLine();
		}
		output.close();
		
		// construct transaction database
		input = new BufferedReader(new FileReader("history.txt"));
		output = new BufferedWriter(new FileWriter("transaction.txt"));
		buff = input.readLine();
		// "ssssss#xx|xx|xx@yyyy/MM/dd HH:mm:ss\n"
		while (buff != null) {
			String[] p1 = buff.split("#");
			String[] p2 = p1[1].split("@");
			String[] trans = p2[0].split("\\|");
			String result = "";
			for (String x : trans) {
				boolean find = false;
				for (int i = 0; i < wlist.size(); i++) {
					if (x.equals(wlist.get(i).getval())) {
						find = true;
						result += i + " ";
						break;
					}
				}
			}
			output.write(result);
			output.newLine();
			buff = input.readLine();
		}
		input.close();
		output.close();
		
		// prefix span recommendation
		int min_sup = 1;
		int max_pat = 0;
		PrefixSpan a = new PrefixSpan(min_sup, max_pat);
		a.run("transaction.txt");
	}

}
