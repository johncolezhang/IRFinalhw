package com.johncole.cn.irfinalhw;

import java.util.Scanner;


public class InputWords {
	public static String[] inputWords(String str){
		String a[] = SplitString.spiltString(str);
		for(int j =0;j<a.length;j++){
			a[j] = Stemmer.stemmer(a[j]);
		}//减词
		return a;
	}
}
