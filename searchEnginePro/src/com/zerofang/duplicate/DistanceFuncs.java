package com.zerofang.duplicate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistanceFuncs {
	/*
	 * 计算两个二进制字符串的海明距离
	 * @param binStrA 长度为N的二进制字符串A
	 * @param binStrB 长度为N的二进制字符串B
	 */
	public static int hammingDist(String binStrA, String binStrB){
		int hammingDist=-1;
		if(binStrA.length()==binStrB.length()){
			hammingDist=0;
			for(int i=0;i<binStrA.length();i++){
				if(binStrA.charAt(i)!=binStrB.charAt(i)){
					hammingDist=hammingDist+1;
				}
			}
		}
		return hammingDist;
	}
	
	/*
	 * A、B:待计算jaccard系数的两个Integer集合
	 * K:用于计算jaccard系数的哈希值最小的K个元素
	 * 如果size(A)<K或size(B)<K，则用min(size(A), size(B))作为K的实际值
	 */
	public static double jaccardIndex(List<?> minHashA, List<?> minHashB){
		/*
		 * 计算Jaccard系数
		 */
		Set<Object> mergedSet=new HashSet<Object>();
		mergedSet.addAll(minHashA);
		mergedSet.addAll(minHashB);
		double jaccardIndex=(double)(minHashA.size()+minHashB.size()-mergedSet.size())/(double)mergedSet.size();
		return jaccardIndex;
	}
}
