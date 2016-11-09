package com.johncole.cn.irfinalhw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/*
 * 该函数的作用为读取文件中的的内容
 * 传入一个文件的路径
 * 最后将文件中的内容返回出来
*/


public class FileReader {
	public static String readTextFile(String filePath){
		//System.out.println("文件 "+filePath+":");
		String encoding = "UTF-8";
		File file = new File(filePath);//打开文件
		if(file.isFile() && file.exists()){
			try {
				InputStreamReader read = new 
						InputStreamReader(new 
								FileInputStream(file),encoding);//获取输入流
				BufferedReader bRead = new BufferedReader(read);//生成字节流
				String lineText = null;
				StringBuffer sb = new StringBuffer();
				while((lineText = bRead.readLine()) != null){
					sb.append(lineText);
				}
				read.close();
				return sb.toString();
			}
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "读取失败";	
	}
}
