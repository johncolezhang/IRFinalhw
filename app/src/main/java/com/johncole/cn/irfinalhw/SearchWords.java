package com.johncole.cn.irfinalhw;

import java.util.ArrayList;
import java.util.List;


public class SearchWords {

	public static void searchWords(String []words,List<Nodepoint> nodes,String []files,StringBuffer res,int []count){
		List<Nodepoint> result = new ArrayList<Nodepoint>();
		//引入count来记录每个文件中该单词测数量，count和files有顺序对应关系
		for(int i = 0;i<count.length;i++){
			count[i] = 0;
		}
		for(int i = 0;i < words.length;i++){
			for(int j = 0;j< nodes.size();j++){
				if(words[i].equals(nodes.get(j).word)){
					result.add(nodes.get(j));//将搜索到的词加到result中
					j = nodes.size();
				}
			}
		}
		if(result.size()==0){
			res.append("没有搜索到结果！");
			return ;
		}
		for(int i = 0;i< result.size();i++){
			Nodepoint n = result.get(i);
			n = n.next.next.next;//指向第一个文件
			while(n!=null){
				for(int j = 0;j<files.length;j++){
					if(n.word.equals(files[j].substring(15, files[j].length()))){//文件匹配则count加1
						count[j]++;
					}
				}
				n = n.next;
			}
		}
		for(int i =0 ; i< count.length; i++){
			System.out.println();
			res.append(files[i]+" :"+count[i]+"次"+'\n');
		}
	} 
}
