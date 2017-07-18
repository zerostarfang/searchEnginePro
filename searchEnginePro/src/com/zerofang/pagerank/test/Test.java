package com.zerofang.pagerank.test;

import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.zerofang.pagerank.dao.UrlDAOImpl;
import com.zerofang.pagerank.dao.WordDAOImpl;
import com.zerofang.pagerank.entity.KeyPair;
import com.zerofang.pagerank.entity.Url;
import com.zerofang.pagerank.entity.Word;
import com.zerofang.pagerank.util.DBHelper;

public class Test {

	static String sql = null;
	static DBHelper db1 = null;
	static ResultSet ret = null;

	public static void main(String[] args) throws IOException {
		/**sql测试*/
		sql = "select * from url";//SQL语句 db1 = new
		db1 = new DBHelper(sql);//创建DBHelper对象
		 
		try { ret = db1.pst.executeQuery();//执行语句，得到结果集 
			while (ret.next()) {
		 		String uid = ret.getString(1); 
		 		String ufname = ret.getString(2);
		 		String ulname = ret.getString(3); 
		 		System.out.println(uid + "\t" + ufname + "\t" + ulname ); 
		 	}//显示数据 
		 	ret.close(); db1.close();//关闭连接 
		 }
		 catch (SQLException e) { 
			 e.printStackTrace(); 
			 }
		 
		/**写入数据库测试*/
		 File file = new File("test-text.txt");
		 System.out.println(file.toString());
		 HashMap<KeyPair,String> test = new HashMap();
		 test.put(new KeyPair(1,2),"test");
		 System.out.println(test.get(new KeyPair(1,2)));
		 WordDAOImpl wordDao = new WordDAOImpl();
		 Word value = new Word(2,"我是");
		 wordDao.add(value);
		 UrlDAOImpl urlDao = new UrlDAOImpl();
		 Url url = new Url(2,"tt");
		 url.setDate(new Date());
		
		 urlDao.add(url);
		 String test2 = "title:5.txt";
		 System.out.println(test2.substring(6));
		 //正则匹配测试
		 String[] dataArr = { "moon", "mon", "moon", "mono" };
		 for (String str : dataArr) {
			 String patternStr="m(o+)n";
			 boolean result = Pattern.matches(patternStr, str);
			 if (result) {
				 System.out.println("字符串"+str+"匹配模式"+patternStr+"成功");
			 }
			 else{
				 System.out.println("字符串"+str+"匹配模式"+patternStr+"失败");
			 }
		 }
		 
		/**url分析*/
		Pattern pattern = Pattern.compile("(http://|https://){1}([\\w\\.]+)");
		BufferedReader in = new BufferedReader(new FileReader("result_URL.txt"));
		PrintWriter pw = new PrintWriter(new FileWriter("url_analysis.txt"));
		Set<String> words = new     TreeSet<>();
		String s;
		// 获得所有的url中前面半截的词并保存
		Set<String> urls = new TreeSet<>();
		while ((s = in.readLine()) != null) {
			String[] t = s.split("\\s");
			Matcher matcher = pattern.matcher(t[1]);
			if (matcher.find()) {
				 System.out.println(matcher.group(2));
				String ut = matcher.group(2);
				urls.add(ut);
				String[] temp = ut.split("\\.");
				for (String te : temp) {
					//System.out.println(te);
					words.add(te);
				}
			}
		}
		System.out.println(in.readLine());
		System.out.println("size =" + words.size());
		pw.println("size= "+words.size());
		Iterator<String> iwords = words.iterator();
		while (iwords.hasNext()) {
			Iterator<String> iurl = urls.iterator();
			String wo = iwords.next();
			pw.println(wo);
			System.out.println(wo);
			while(iurl.hasNext()){
				String t = iurl.next();
				if(t.contains(wo)){
					System.out.println(t);
					pw.println(t);
				}
			}
		}
		pw.close();
		
		/**解析出Url的正则表达式*/		
		Pattern pattern1 = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+"); 
		Pattern pattern2 = Pattern.compile("(http://|https://){1}([\\w\\.]+)");
		Matcher matcher = pattern.matcher("http://games.qq.com/a/20160218/000658.htm");
		StringBuffer buffer = new StringBuffer(); while(matcher.find()){
		buffer.append(matcher.group(2)); buffer.append("\r\n");
		System.out.println(buffer.toString()); }
		 
		String[] tests;
		String[] newtest = {"23","34"};
		//test = newtest;
		//System.out.println(test[0]+test[1]);
		String test3 = "news:dtsdtsd t  ";
		String[] keywords = test3.substring(5).split("\\s");
		tests = keywords;
		System.out.println(tests[0]+tests[1]);
//		boolean result = Pattern.matches("astro","http://astro.fashion.qq.com/a/20160220/009084.htm");
//		String test = " test  tests  testes";
//		String[] s = test.trim().split("\\s+");
//		System.out.println(s.length);
		IKAnalysis("词典");
		
	}
	public static String IKAnalysis(String str) {
		StringBuffer sb = new StringBuffer();
		try {
			// InputStream in = new FileInputStream(str);//
			byte[] bt = str.getBytes();// str
			InputStream ip = new ByteArrayInputStream(bt);
			Reader read = new InputStreamReader(ip);
			IKSegmenter iks = new IKSegmenter(read, false);
			Lexeme t;
			while ((t = iks.next()) != null) {
				sb.append(t.getLexemeText() + " , ");

			}
			sb.delete(sb.length() - 1, sb.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		return sb.toString();

	}
}
