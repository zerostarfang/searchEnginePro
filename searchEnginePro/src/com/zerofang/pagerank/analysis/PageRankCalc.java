package com.zerofang.pagerank.analysis;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.zerofang.pagerank.dao.UrlDAOImpl;
import edu.uci.ics.jung.algorithms.scoring.PageRank;

import java.util.Set;
import java.util.TreeSet;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import com.zerofang.pagerank.entity.Url;

public class PageRankCalc {

	DirectedGraph<Integer, String> g = new DirectedSparseGraph<Integer, String>();

	private void readFile(String filename, String delim) throws IOException {

		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);

		String line;

		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			String[] result = line.split(delim);
			g.addEdge(result[0] + " " + result[1], Integer.parseInt(result[0]),
					Integer.parseInt(result[1]));
		}

		br.close();
	}

	public void calcPageRank(String inputFile, String delimiter)
			throws IOException {
		readFile(inputFile, delimiter);
		for (Integer v : g.getVertices()) {
			System.out.println(v);
		}
		PageRank<Integer, String> pr = new PageRank<Integer, String>(g, 0.15);
		pr.evaluate();
		double sum = 0;
		Set<Integer> sortedVerticesSet = new TreeSet<Integer>(g.getVertices());
		UrlDAOImpl urlDao = new UrlDAOImpl();
		for (Integer v : sortedVerticesSet) {
			double score = pr.getVertexScore(v);
			Url temp = new Url();
			temp.setID(v);
			temp.setPagerankValue(score * 10000);
			urlDao.updatePagerankValue(temp);
			System.out.println(v + " = " + score * 10000);
		}
		System.out.println("s = " + sum);
	}

	public static void main(String args[]) throws IOException {

		PageRankCalc prc = new PageRankCalc();
		if (args.length != 2) {
			System.err
					.println("Usage: java PageRankCalc <delimiter> <input_file>");
			System.exit(1);
		}
		prc.readFile(args[1], args[0]);
		for (Integer v : prc.g.getVertices()) {
			System.out.println(v);
		}
		PageRank<Integer, String> pr = new PageRank<Integer, String>(prc.g,
				0.15);
		pr.evaluate();
		double sum = 0;
		Set<Integer> sortedVerticesSet = new TreeSet<Integer>(
				prc.g.getVertices());
		PrintWriter pw = new PrintWriter(new FileWriter("test.txt"));
		for (Integer v : sortedVerticesSet) {
			double score = pr.getVertexScore(v);
			sum += 1;
			pw.println(v + " = " + score);
			System.out.println(v + " = " + score);
		}
		pw.flush();
		pw.close();
		System.out.println("s = " + sum);
	}
}
