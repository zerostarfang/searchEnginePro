package com.zerofang.duplicate;

/**
 * Created by zerofang on 2016/2/25.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SeachEngine {
	private static String BASE = "D:/seachEngine/";

	public static void main(String[] args) throws Exception {
		// ���ڰ��ж�ȡtxt�ı��ļ��е�url
		File urlfile = new File("D:/result_URL.txt");
		BufferedReader urlReader = new BufferedReader(new FileReader(urlfile));

		try {
			String urls;
			while ((urls = urlReader.readLine()) != null) {
				System.out.println(urls);
				// urlInfo[0]�洢url��ţ�urlInfo[2]�洢url
				String[] urlInfo;
				urlInfo = urls.split("	");

				System.out.println(urlInfo[1]);
				Document document = Jsoup.connect(urlInfo[1]).get();

				String title = document.title();
				String content = "";
				Elements pContent = document.select("p");
				content += pContent.text();
				Elements liContent = document.select("li");
				content += liContent.text();

				String real = BASE + urlInfo[0] + ".txt";
				System.out.println(real);
				File file = new File(real);
				FileWriter writer = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(writer);
				bw.write("title:" + title + "\r\n" + "author:" + "\r\n"
						+ "content:" + content);
				bw.flush();
				bw.close();
				writer.close();

				/*
				 * URL url = new URL(urls); HttpURLConnection urlCon =
				 * (HttpURLConnection)url.openConnection(); BufferedReader
				 * contentReader = new BufferedReader(new
				 * InputStreamReader(urlCon.getInputStream()));
				 * 
				 * String content; String con = ""; while((content =
				 * contentReader.readLine())!=null){
				 * System.out.println(content+"123"); bw.write(content); con +=
				 * content; }
				 * 
				 * Document document = Jsoup.parse(con, "utf-8");
				 * System.out.println(content); Element div =
				 * document.getElementsByTag("div").first(); Elements p =
				 * div.getElementsByTag("p"); for(Element divs : div){ content
				 * += divs.text(); Elements li = div.getElementsByTag("li");
				 * content += li.text(); Elements p = div.getElementsByTag("p");
				 * System.out.println(p+"-----------------------");
				 * System.out.println(p.text()); content += p.text(); }
				 * 
				 * System.out.println("-----content-----"+content);
				 * System.out.println(div);
				 * 
				 * Element divFir = content.children().first();
				 * System.out.println(divFir); Elements brandBoxes =
				 * divFir.getElementsByTag("div.uibox");
				 * 
				 * for (Element brandBox : brandBoxes) { Elements dls =
				 * brandBox.getElementsByTag("dl"); if (dls == null ||
				 * dls.isEmpty()) { continue; } for (Element dl : dls) {
				 * Elements dts = dl.getElementsByTag("dt"); Element dt =
				 * dts.first(); String brandName = dt.text(); Elements lis =
				 * dl.getElementsByTag("li"); for (Element li : lis) { Elements
				 * h4s = li.getElementsByTag("h4"); if (h4s == null ||
				 * h4s.isEmpty()) { continue; } Element h4 = h4s.first(); String
				 * seriesName = h4.text(); Elements divs =
				 * li.getElementsByTag("div"); System.out.println(brandName +
				 * "," + seriesName); bw.write(brandName + "," + seriesName);
				 * bw.write("\r\n"); } }
				 * 
				 * }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
