package com.johncole.cn.irfinalhw;

import java.util.List;
/*
 * 该类的功能为建立postinglist表
 * isRepeat的作用为检查所有单词的以及文章的isInNode
 * isInNode的作用为判断文章是否已经存在于单词的串中
 * addNewNode的作用是有新词时建立新的结点
 * modifyNode的作用是有旧词出现则取修改旧词的结点
*/
public class PostingList {
	static int id =1;//此处应为不重复随机数
	public static void postingList(List<Nodepoint>nodes,String []term,String article){
		if(nodes!=null){
			for(int i =0;i<term.length;i++){
				if(isRepeat(nodes,term[i],article)){//将重复的放到之前的node中
					modifyNode(nodes, term[i], article);
				}else{//没有出现过的单词加入到nodes中
					addNewNode(nodes, term[i], article);
				}
			}
		}
	}
	
	
	static boolean isRepeat(List<Nodepoint>nodes,String word,String article){
		for(int i =0;i<nodes.size();i++){
			if(nodes.get(i).word.equals(word)){
				//检查该文章是否已存在串内，存在返回true，该文章不在串内返回false
				return true;//isInNode(nodes.get(i),article);
			}
		}
		return false;
	}
	
	static boolean isInNode(Nodepoint node,String article){
		if(node.next.next.next!=null){
			node = node.next.next.next;//将指针指到表示文章的第一个结点
		}
		while(node!=null){
			if(node.word.equals(article)){
				return true;
			}else{
				node = node.next;
			}
		}
		return false;
	}

	
	static void addNewNode(List<Nodepoint>nodes,String word,String article){
		Nodepoint node = new Nodepoint(word);
		node.appendNum(id);
		node.appendNum(1);
		node.appendWord(article.substring(15, article.length()));
		nodes.add(node);
		id++;
	}
	
	static void modifyNode(List<Nodepoint>nodes,String word,String article){
		for(int i =0;i<nodes.size();i++){
			if(nodes.get(i).word.equals(word)){
				int freq =nodes.get(i).getFreq();
				freq++;
				nodes.get(i).modifyFreq(freq);
				nodes.get(i).appendWord(article.substring(15, article.length()));
			}
		}
	}
}
