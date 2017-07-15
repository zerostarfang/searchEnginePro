package com.zerofang.duplicate;

import java.util.ArrayList;
import java.util.List;

public class MinHash {
	/*
	 * �ж�hashCodeA��hashCodeB�����ƶ��Ƿ񳬹����ֵthreshold
	 * MinHash�㷨ѡ�񼯺���K��hashֵ��С��Ԫ��
	 */
	public static boolean isSimilar(List<String> strListA,
			List<String> strListB, int K, int threshold) {
		/*
		 * ���size(A)<K��size(B)<K������min(size(A), size(B))��ΪK��ʵ��ֵ
		 */
		if (strListA.size() < K || strListB.size() < K) {
			K = Math.min(strListA.size(), strListB.size());
		}
		/*
		 * ����A��B������Ԫ�صĹ�ϣֵ
		 */
		List<Integer> hashA = getHashCodeList(strListA);
		List<Integer> hashB = getHashCodeList(strListB);

		/*
		 * �ֱ��ҳ�A��B����С��K��hashֵ
		 */
		// ��hashA��hashB��ѡ����С��K��Ԫ��
		List<Integer> minHashA = getMinKElement(hashA, K);
		List<Integer> minHashB = getMinKElement(hashB, K);
		double jaccardIndex = DistanceFuncs.jaccardIndex(minHashA, minHashB);
		if (jaccardIndex > threshold) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ��ȡstrList������string��hashֵ
	 */
	private static List<Integer> getHashCodeList(List<String> strList) {
		List<Integer> hashInt = new ArrayList<Integer>();
		HashFunctionLib hfl = new HashFunctionLib();
		for (int i = 0; i < strList.size(); i++) {
			// ѡ����Java�Դ��hash����
			hashInt.add(hfl.JavaHash(strList.get(i)));
		}
		return hashInt;
	}

	/*
	 * ��intList���ҵ���С��K��ֵ
	 */
	private static List<Integer> getMinKElement(List<Integer> intList, int K) {
		if (intList.size() == K) {
			return intList;
		}
		/*
		 * ��ʼ��
		 */
		List<Integer> minKElement = new ArrayList<Integer>();
		// ��ȡǰK��Ԫ����Ϊ��ʼֵ
		Integer intTmp, maxVal = -1;
		int i = 0, maxIndex = 0;
		for (i = 0; i < K; i++) {
			intTmp = intList.get(i);
			minKElement.add(intTmp);
			if (intList.get(i) > intTmp) {
				maxVal = intTmp;
				maxIndex = i;
			}
		}
		/*
		 * ����ʣ�µ�intList.size()-K��Ԫ��
		 */
		int intTmp2, j = 0;
		for (i = K; i < intList.size(); i++) {
			intTmp = intList.get(i);
			if (intTmp < maxVal) {
				minKElement.set(maxIndex, intTmp);
			}
			// �ҵ�K����С��ϣֵ������һ����������
			for (j = 0; j < minKElement.size(); j++) {
				intTmp2 = minKElement.get(j);
				if (intTmp2 > intTmp) {
					maxVal = intTmp2;
					maxIndex = j;
				}
			}
		}
		return minKElement;
	}
}
