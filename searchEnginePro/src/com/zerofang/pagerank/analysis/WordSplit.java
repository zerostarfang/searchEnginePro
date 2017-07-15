package com.zerofang.pagerank.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.zerofang.pagerank.dao.UrlDAOImpl;
import com.zerofang.pagerank.dao.WordDAOImpl;
import com.zerofang.pagerank.entity.Url;
import com.zerofang.pagerank.entity.Word;

public class WordSplit {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		WordSplit wordSplit = new WordSplit();
		// ����URL���
		// wordSplit.importUrl("result_URL.txt");
		// ����pagerankֵ
		// PageRankCalc pageRank = new PageRankCalc();
		// pageRank.calcPageRank("LINK.txt", "\\s");
		// ������������

		// �ִ�
		for (File file : wordSplit.getAllFiles("data")) {
			System.out.println(file.toString());
			wordSplit.analysisFile(file.toString());
		}
		// ��ҳ����
		/*
		 * UrlClassification urlCate = new UrlClassification();
		 * urlCate.calCate("result_URL.txt");
		 */

	}

	public void analysisFile(String inputFile) throws IOException {
		// �ļ�������url��ID��������������url����ݿ�
		int urlID = Integer.parseInt(inputFile.substring(5,
				inputFile.indexOf(".txt")));
		HashMap<String, Word> map = new HashMap<>();
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile)));
		String line = "";
		Url tempUrl = new Url(urlID);
		while ((line = reader.readLine()) != null) {
			if ("".equals(line.trim())) {
				continue;
			}
			String newline = "";
			if (line.startsWith("title:")) {
				newline = line.substring(6);
				tempUrl.setTitle(newline);
			} else if (line.startsWith("author")) {
				newline = line.substring(7);
				tempUrl.setAuthor(newline);
			} else if (line.startsWith("date")) {
				newline = line.substring(5);
				tempUrl.setDate(new Date());
				// ���ʱ�䣬�������ڲ�֪��ʱ���ʽ
			} else if (line.startsWith("content")) {
				newline = line.substring(8);
				tempUrl.setText(newline);
			}
			analysisString(line, urlID, map);
		}
		UrlDAOImpl urlDao = new UrlDAOImpl();
		urlDao.updateContent(tempUrl);
		for (Word value : map.values()) {
			// System.out.println("value = " + value.getValue()+"times:"+
			// value.getTimesInUrl());
			WordDAOImpl wordDao = new WordDAOImpl();
			wordDao.add(value);
		}
	}

	public void analysisString(String text, int urlID,
			HashMap<String, Word> result) throws IOException {
		// �����ִʶ���
		Analyzer anal = new IKAnalyzer(true);
		StringReader reader = new StringReader(text);
		// �ִ�
		TokenStream ts = anal.tokenStream("", reader);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		// ����ִ����
		while (ts.incrementToken()) {
			// System.out.print(term.toString()+"|");
			// �������ӷִʣ���ʱ����ĳһ���ļ��ķִʣ�Ȼ��ͳһд����ݿ�
			String temp = term.toString();
			if (result.get(temp) != null) {
				// Word t = result.get(temp);
				// t.setTimesInUrl(t.getTimesInUrl()+1);
				// result.put(temp, t);
				continue;
			} else {
				result.put(temp, new Word(urlID, temp));
			}
		}
		reader.close();
		// System.out.println();
	}

	public File[] getAllFiles(String directory) {
		File root = new File(directory);
		return root.listFiles();
	}

	public void importUrl(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if ("".equals(line.trim())) {
				continue;
			}
			String[] t = line.split("\\s");
			UrlDAOImpl urlDao = new UrlDAOImpl();
			Url temp = new Url(Integer.parseInt(t[0]), t[1], 0);
			urlDao.add(temp);
		}
	}

}
