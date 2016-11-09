package com.johncole.cn.irfinalhw;

import java.io.File;
import java.util.List;
/*
 * 该类中使用递归的方法
 * 传入根文件以及一个记录所有文件名的list
 * 将根文件下的所有文件名读出，存到list中
*/
public class ShowAllFile {
	static void showAllFiles(File file, List list){
		File[]fs=file.listFiles();
		for(int i=0;i<fs.length;i++){
			if(!fs[i].isDirectory()){
				list.add(fs[i].getAbsolutePath());
			}else{
				showAllFiles(fs[i],list);
			}
		}
	}
}
