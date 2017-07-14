package com.zerofang.duplicate;

import java.util.Map;
import java.util.Set;

public class SimHash {
	/*
	 * 判断hashCodeA与hashCodeB的相似度是否超过给定阈值threshold
	 */
	public static boolean isSimilar(String hashCodeA,String hashCodeB, int threshold){
		//
		int hammingDist=DistanceFuncs.hammingDist(hashCodeA, hashCodeB);
		if(hammingDist<threshold){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * 根据输入的特征向量及其权重、签名位数，计算simhash
	 * signNum<32，否则无法支持
	 */
	public static String calcSimHash(Map<String, Integer> vec_wei,int signNum){
		/*
		 * 初始化
		 */
		int[] v=new int[signNum];
		int[] s=new int[signNum];
		
		/*
		 * 遍历所有特征，计算v值
		 */
		Set<String> keys=vec_wei.keySet();
		int i=0;
		int b_tmp;
		for(String key:keys){
			int value=vec_wei.get(key);
			int b=calcHashCode(key);
			for(i=0;i<signNum;i++){
				//判断b的二进制位的最后一位是0还是1
				b_tmp=b & Integer.MAX_VALUE;
				if(b!=b_tmp){	//此时b的二进制位的最后一位是1
					v[i]=v[i]+value;
				}
				else{
					v[i]=v[i]-value;
				}
				b=b >> 1;
			}
		}
		
		/*
		 * 根据v计算s值
		 * 
		 */
		StringBuilder builder=new StringBuilder();
		for(i=0;i<signNum;i++){
			if(v[i]>0){
				s[i]=1;
			}else{
				s[i]=0;
			}
			builder.append(new Integer(s[i]).toString());
		}
		/*
		 * 输出s作为签名
		 */
		return builder.toString();
	}
	
	/*
	 * 计算str的hash code
	 */
	private static int calcHashCode(String str){
		return str.hashCode();
	}
}