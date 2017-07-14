package com.zerofang.pagerank.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	private static final int ROUNDS = 4;

	final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	// 加�?字符串产�?
	private static final char CODES[] = {
	/* 大写字母0~25 */
	'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			/* 小写字母26~51 */
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			/* 数字52~61 */
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			/* 特殊字符62~94 */
			'!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-',
			'.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
			'_', '`', '{', '|', '}', '~', '' };
	
	// 随机数生成器
	private static final Random RANDOM = new Random();
	
	/**
	 * Compare two object safely
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	public static boolean safeCompare(Object lhs, Object rhs) {
		if (lhs == null && rhs != null) {
			return false;
		}
		return lhs.equals(rhs);
	}
	
	/**
	 * Generate where statement in sql from given constrains. You can just
	 * append the return value to your sql statements.
	 * 
	 * @param key
	 *            key/value pairs splited by ';'. key/value pairs are strings in
	 *            format that "key=value"
	 * @return Generated statement.
	 */
	static public String generateWhereStatement(String key) {
		return generateWhereStatement(key, "");
	}

	static public String generateWhereStatement(String key, String tablename) {
		StringBuilder sb = new StringBuilder();
		sb.append(" where ");
		key = key.trim();
		if (!key.isEmpty()) {
			String[] constrains = key.split(";");
			for (int i = 0; i != constrains.length; i++) {
				String[] tmp = constrains[i].split("=");
				if (tmp.length != 2)
					continue;

				if (tablename != null && !tablename.isEmpty()
						&& tmp[0].equalsIgnoreCase("ID"))
					tmp[0] = tablename.concat(".").concat(tmp[0]);

				sb.append(tmp[0]).append("=").append("'").append(tmp[1])
						.append("'");

				if (i != constrains.length - 1)
					sb.append(" and ");
			}
		} else {
			sb.append("true");
		}

		sb.append(" ");
		return sb.toString();
	}
	
	/**
	 * Return the first element in the iterable container.
	 * 
	 * @param s
	 *            the container.
	 * @return null if the container is empty or null.
	 */
	static public <T> T first(Iterable<T> s) {
		T one = null;
		Iterator<T> it = s.iterator();
		if (s != null && it.hasNext()) {
			one = it.next();
		}
		return one;
	}
	
	/**
	 * Generate Codes: to generate random string include up or low case and
	 * number for given length. 本方法�?用于生成指定大小的随机字符串，含大小写字符以及数�?
	 * 
	 * @param length
	 *            the length you want
	 * @return the random string
	 */
	public static String generateCodes(int length) {
		StringBuffer jpsb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			jpsb.append(Utilities.CODES[Utilities.RANDOM.nextInt(62)]);
		}
		return jpsb.toString();
	}

	/**
	 * Generate salt: you know that 深成岩，用于加密
	 * 
	 * @return �?
	 */
	public static String generateSalt() {
		return Utilities.generateCodes(16);
	}

	
	/**
	 * Parse id from string. <br>
	 * Use this method for unsigned id parsing.
	 * 
	 * @param str
	 * @return -1 if there is any exception throwed during the parsing.
	 */
	static public int parseId(String str) {
		int id = -1;
		try {
			id = Integer.parseInt(str);
		} catch (Exception ex) {
		}
		return id;
	}

	/**
	 * Generate md5 sum of given str.
	 * 
	 * @param str
	 * @return md5sum in lower case
	 */
	static public String generateMd5sum(String str) {
		try {
			byte[] strTemp = str.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int mdlen = md.length;
			char res[] = new char[mdlen * 2];
			for (int i = 0, k = 0; i < mdlen; i++) {
				byte byte0 = md[i];
				res[k++] = hexDigits[byte0 >>> 4 & 0xf];
				res[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(res);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Generate secured password using salt. Utilities.ROUNDS turns is used.
	 * 
	 * @param passwd
	 *            plain password
	 * @param salt
	 *            salt used to secure
	 * @return the generated secured password
	 */
	static public String generateSecPass(String passwd, String salt) {
		return generateSecPass(passwd, salt, Utilities.ROUNDS);
	}

	/**
	 * Generate secured password using salt.
	 * 
	 * @param passwd
	 *            plain password
	 * @param salt
	 *            salt used to secure
	 * @param turn
	 *            rounds to generate
	 * @return the generated secured password
	 */
	static public String generateSecPass(String passwd, String salt, int turn) {
		String str = passwd + salt;
		for (int i = 0; i != turn; i++)
			str = generateMd5sum(str + salt);
		return str;
	}
	
	public static String dateToString(Date date)
	{
		String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return formatDate;
	}
	
	public static Date StringToDate(String string) throws ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(string);
	}
	public static String dateTimeToString(Date date){
		String formatDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return formatDate;
	}
	
	public static Date StringToDateTime(String string) throws ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(string);
	}
	public static boolean match(String inputString,String regex) throws IOException{
		boolean result = false;
		Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(inputString);
    	if (matcher.find()){
    		//System.out.println(matcher.group());
    		return true;
    	}
		return result;
	}
}
