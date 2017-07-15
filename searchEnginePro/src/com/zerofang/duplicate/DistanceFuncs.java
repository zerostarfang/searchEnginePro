package com.zerofang.duplicate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistanceFuncs {
	/*
	 * ���������������ַ�ĺ�������
	 * 
	 * @param binStrA ����ΪN�Ķ������ַ�A
	 * 
	 * @param binStrB ����ΪN�Ķ������ַ�B
	 */
	public static int hammingDist(String binStrA, String binStrB) {
		int hammingDist = -1;
		if (binStrA.length() == binStrB.length()) {
			hammingDist = 0;
			for (int i = 0; i < binStrA.length(); i++) {
				if (binStrA.charAt(i) != binStrB.charAt(i)) {
					hammingDist = hammingDist + 1;
				}
			}
		}
		return hammingDist;
	}

	/*
	 * A��B:�����jaccardϵ�������Integer���� K:���ڼ���jaccardϵ��Ĺ�ϣֵ��С��K��Ԫ��
	 * ���size(A)<K��size(B)<K������min(size(A), size(B))��ΪK��ʵ��ֵ
	 */
	public static double jaccardIndex(List<?> minHashA, List<?> minHashB) {
		/*
		 * ����Jaccardϵ��
		 */
		Set<Object> mergedSet = new HashSet<Object>();
		mergedSet.addAll(minHashA);
		mergedSet.addAll(minHashB);
		double jaccardIndex = (double) (minHashA.size() + minHashB.size() - mergedSet
				.size()) / (double) mergedSet.size();
		return jaccardIndex;
	}
}
