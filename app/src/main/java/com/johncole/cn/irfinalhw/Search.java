package com.johncole.cn.irfinalhw;

import java.util.List;
import java.util.Scanner;


public class Search {
	public static void input(List<Nodepoint>nodes,String []files){
		if(nodes!=null){
			System.out.print("输入要查找的数据：");
			Scanner input = new Scanner(System.in);
			String data = input.nextLine();
			String []d = SplitString.spiltString(data);
			search(nodes,d,files);
		}
	}
	
	public static void search(List<Nodepoint>nodes,String []d,String []files){
		int freq;
		int []num = new int[files.length];
		for(int i=0;i<d.length;i++){
			for(int j=0;j<nodes.size();j++){
				if(d[i].equals(nodes.get(j).word)){
					Nodepoint node =nodes.get(j);
				}
			}
		}
	}
}
