package com.zerofang.pagerank.entity;

public class KeyPair {
	private int wordID;  
    private int urlID;  
    public KeyPair(int wordID, int urlID){  
        this.wordID = wordID;  
        this.urlID = urlID;  
    }  
    @Override      
    public boolean equals(Object obj){  
        if(this == obj)//判断是否是本类的一个引用  
            return true;  
        if(obj == null)//  
            return false;             
        KeyPair pair = (KeyPair)obj;  
        if(this.wordID != pair.wordID)  
            return false;  
        if(this.urlID != pair.urlID)  
            return false;  
        return true;  
    }  
    @Override
    public int hashCode(){  
        int result = 17;  
        result = result * 31 + wordID;  
        result = result * 31 + urlID;  
        return result;  
    } 
}

