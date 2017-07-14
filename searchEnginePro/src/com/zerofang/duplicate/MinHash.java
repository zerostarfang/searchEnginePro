package com.zerofang.duplicate;

import java.util.ArrayList;
import java.util.List;

public class MinHash {
	/*
	 * 判断hashCodeA与hashCodeB的相似度是否超过给定阈值threshold
	 * MinHash算法选择集合中K个hash值最小的元素
	 */
	public static boolean isSimilar(List<String> strListA,List<String> strListB, int K, int threshold){
		/*
		 * 如果size(A)<K或size(B)<K，则用min(size(A), size(B))作为K的实际值
		 */
		if(strListA.size()<K || strListB.size()<K){
			K=Math.min(strListA.size(), strListB.size());
		}
		/*
		 * 计算A和B中所有元素的哈希值
		 */
		List<Integer> hashA=getHashCodeList(strListA);
		List<Integer> hashB=getHashCodeList(strListB);
		
		/*
		 * 分别找出A和B中最小的K个hash值
		 */
		//从hashA和hashB中选择最小的K个元素
		List<Integer> minHashA=getMinKElement(hashA, K);
		List<Integer> minHashB=getMinKElement(hashB, K);
		double jaccardIndex=DistanceFuncs.jaccardIndex(minHashA, minHashB);
		if(jaccardIndex>threshold){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * 获取strList中所有string的hash值
	 */
	private static List<Integer> getHashCodeList(List<String> strList){
		List<Integer> hashInt=new ArrayList<Integer>();
		HashFunctionLib hfl=new HashFunctionLib();
		for(int i=0;i<strList.size();i++){
			//选用了Java自带的hash函数
			hashInt.add(hfl.JavaHash(strList.get(i)));
		}
		return hashInt;
	}
	
	/*
	 * 从intList中找到最小的K个值
	 */
	private static List<Integer> getMinKElement(List<Integer> intList, int K){
		if(intList.size()==K){
			return intList;
		}
		/*
		 * 初始化
		 */
		List<Integer> minKElement=new ArrayList<Integer>();
		//获取前K个元素作为初始值
		Integer intTmp, maxVal=-1;
		int i=0, maxIndex=0;
		for(i=0;i<K;i++){
			intTmp=intList.get(i);
			minKElement.add(intTmp);
			if(intList.get(i)>intTmp){
				maxVal=intTmp;
				maxIndex=i;
			}
		}
		/*
		 * 遍历剩下的intList.size()-K个元素
		 */
		int intTmp2, j=0;
		for(i=K;i<intList.size();i++){
			intTmp=intList.get(i);
			if(intTmp<maxVal){
				minKElement.set(maxIndex, intTmp);
			}
			//找到K个最小哈希值中最大的一个及其索引
			for(j=0;j<minKElement.size();j++){
				intTmp2=minKElement.get(j);
				if(intTmp2>intTmp){
					maxVal=intTmp2;
					maxIndex=j;
				}
			}
		}
		return minKElement;
	}
}
