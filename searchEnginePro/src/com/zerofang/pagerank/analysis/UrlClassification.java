package com.zerofang.pagerank.analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zerofang.pagerank.dao.UrlDAOImpl;
import com.zerofang.pagerank.entity.Url;

public class UrlClassification {
	public static String keyFile = "Cat_Input.txt";
	public static String[] newsRegex;
	public static String[] sportsRegex;
	public static String[] cultureRegex;
	public static String[] videoRegex;
	public static String[] funRegex;
	public static String[] autoRegex;
	public static String[] gamesRegex;
	public static String[] grossRegex;
	public static String[] houseRegex;
	public static String[] blogRegex;
	public static String[] techRegex;
	public static String[] financeRegex;
	public static String[] eduRegex;
	public static String[] adRegex;
	public static String[] appRegex;
	public UrlClassification() throws IOException{
		readKeyWords();
	}
	public void readKeyWords() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(keyFile));
		String s;
		while((s = in.readLine()) !=null){
			if(s.startsWith("news:")){
				String[] keywords = s.substring(5).split("\\s");
				newsRegex = keywords;
			}else if(s.startsWith("sports:")){
				String[] keywords = s.substring(7).split("\\s");
				sportsRegex = keywords;
			}else if(s.startsWith("culture:")){
				String[] keywords = s.substring(8).split("\\s");
				cultureRegex = keywords;
			}else if(s.startsWith("video:")){
				String[] keywords = s.substring(6).split("\\s");
				videoRegex = keywords;
			}else if(s.startsWith("fun:")){
				String[] keywords = s.substring(4).split("\\s");
				funRegex = keywords;
			}else if(s.startsWith("auto:")){
				String[] keywords = s.substring(5).split("\\s");
				autoRegex = keywords;
			}else if(s.startsWith("games:")){
				String[] keywords = s.substring(6).split("\\s");
				gamesRegex = keywords;
			}else if(s.startsWith("gross:")){
				String[] keywords = s.substring(6).split("\\s");
				grossRegex = keywords;
			}else if(s.startsWith("house:")){
				String[] keywords = s.substring(7).split("\\s");
				houseRegex = keywords;
			}else if(s.startsWith("blog:")){
				String[] keywords = s.substring(5).split("\\s");
				blogRegex = keywords;
			}else if(s.startsWith("tech:")){
				String[] keywords = s.substring(5).split("\\s");
				techRegex = keywords;
			}else if(s.startsWith("finance:")){
				String[] keywords = s.substring(8).split("\\s");
				financeRegex = keywords;
			}else if(s.startsWith("edu:")){
				String[] keywords = s.substring(4).split("\\s");
				eduRegex = keywords;
			}else if(s.startsWith("ad:")){
				String[] keywords = s.substring(3).split("\\s");
				adRegex = keywords;
			}else if(s.startsWith("app:")){
				String[] keywords = s.substring(4).split("\\s");
				appRegex = keywords;
			}
		}
		in.close();
	}
	public void calCate(String urlFile) throws IOException{
		FileReader fr = new FileReader(urlFile);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
        	System.out.println(line);
            String[] result = line.trim().split("\\s+");
            Url url = new Url(Integer.parseInt(result[0]));
            url.setCategory(this.classifyUrl(result[1]));
            UrlDAOImpl urlDao = new UrlDAOImpl();
            urlDao.updateCategory(url);
        }
	}
	public boolean match(String url,String[] regex){
		if(!url.contains("thetimes.co")){
			Pattern pattern = Pattern.compile("(http://|https://){1}([\\w\\.]+)");
			Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
				url = matcher.group(2);
			}
		}
		boolean result = false;
		if(regex.length ==0) return false;
		for(String t : regex){
			Pattern pattern = Pattern.compile(t);
			Matcher matcher = pattern.matcher(url);
			if(matcher.find()){
				return true;
			}
		}
		return result;
	}
	
	public int classifyUrl(String url){
		int cat = 9;
		if(match(url,sportsRegex)) {
			cat = 2;
		}else if(match(url,cultureRegex)){
			cat = 3;
		}else if(match(url,videoRegex)){
			cat = 4;
		}else if(match(url,funRegex)){
			cat = 6;
		}else if(match(url,autoRegex)){
			cat = 7;
		}else if(match(url,gamesRegex)){
			cat = 8;
		}else if(match(url,grossRegex)){
			cat = 9;
		}else if(match(url,houseRegex)){
			cat = 10;
		}else if(match(url,blogRegex)){
			cat = 11;
		}else if(match(url,techRegex)){
			cat = 12;
		}else if(match(url,financeRegex)){
			cat = 13;
		}else if(match(url,eduRegex)){
			cat = 14;
		}else if(match(url,adRegex)){
			cat = 15;
		}else if(match(url,appRegex)){
			cat = 16;
		}else if(match(url,newsRegex)){
			cat = 1;
		}
		return cat;
	}
}
