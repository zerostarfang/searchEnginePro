package com.zerofang.duplicate;

import java.util.Map;
import java.util.Set;

public class SimHash {
	/*
	 * �ж�hashCodeA��hashCodeB�����ƶ��Ƿ񳬹����ֵthreshold
	 */
	public static boolean isSimilar(String hashCodeA, String hashCodeB,
			int threshold) {
		//
		int hammingDist = DistanceFuncs.hammingDist(hashCodeA, hashCodeB);
		if (hammingDist < threshold) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ��������������������Ȩ�ء�ǩ��λ�����simhash signNum<32�������޷�֧��
	 */
	public static String calcSimHash(Map<String, Integer> vec_wei, int signNum) {
		/*
		 * ��ʼ��
		 */
		int[] v = new int[signNum];
		int[] s = new int[signNum];

		/*
		 * ������������������vֵ
		 */
		Set<String> keys = vec_wei.keySet();
		int i = 0;
		int b_tmp;
		for (String key : keys) {
			int value = vec_wei.get(key);
			int b = calcHashCode(key);
			for (i = 0; i < signNum; i++) {
				// �ж�b�Ķ�����λ�����һλ��0����1
				b_tmp = b & Integer.MAX_VALUE;
				if (b != b_tmp) { // ��ʱb�Ķ�����λ�����һλ��1
					v[i] = v[i] + value;
				} else {
					v[i] = v[i] - value;
				}
				b = b >> 1;
			}
		}

		/*
		 * ���v����sֵ
		 */
		StringBuilder builder = new StringBuilder();
		for (i = 0; i < signNum; i++) {
			if (v[i] > 0) {
				s[i] = 1;
			} else {
				s[i] = 0;
			}
			builder.append(new Integer(s[i]).toString());
		}
		/*
		 * ���s��Ϊǩ��
		 */
		return builder.toString();
	}

	/*
	 * ����str��hash code
	 */
	private static int calcHashCode(String str) {
		return str.hashCode();
	}
}