package com.zerofang.duplicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shingling {
	/*
	 * �ж�hashCodeA��hashCodeB�����ƶ��Ƿ񳬹����ֵthreshold
	 */
	public static boolean isSimilar(String strA, String strB, int gramNum,
			int N, int threshold) {
		//
		List<String> nGramListA = getNGramList(strA, N);
		List<String> nGramListB = getNGramList(strB, N);
		double jaccardIndex = DistanceFuncs
				.jaccardIndex(nGramListA, nGramListB);
		if (jaccardIndex < threshold) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ��ȡ�����ַ�inputString��N-Gram�ַ��б�
	 */
	public static List<String> getNGramList(String inputString, int N) {
		List<String> nGramList = new ArrayList<String>();
		for (int i = 0; i < inputString.length() - N + 1; i++) {
			nGramList.add(inputString.substring(i, i + N));
		}
		return nGramList;
	}

	/*
	 * ����sentence�ַ���N-gram�Ĵ�����������Ȩ��
	 */
	public static Map<String, Integer> getTokenAndWeight(String sentence, int N) {
		List<String> nGramList = getNGramList(sentence, N);
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		for (int i = 0; i < nGramList.size(); i++) {
			String key = nGramList.get(i);
			if (!resultMap.containsKey(key)) {
				resultMap.put(key, 1);
			} else {
				resultMap.put(key, resultMap.get(key) + 1);
			}
		}
		return resultMap;
	}
}
