package com.johncole.cn.irfinalhw;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String []word;//记录要搜索的单词
    public static List<Nodepoint> nodes = new ArrayList<Nodepoint>();//记录所有单词结点
    public static String []files;//文件
    public static int []sumcount;//总的单词出现权重
    public static int [][]count;//每个单词出现的权重
    public static double []rank;//文本排名
    EditText et;
    Button btn;
    Button fbtn;
    TextView tv;
    TextView pltv;
    public String str;
    static StringBuffer res;
    static StringBuffer postinglist;
    static int tmp = 0;//用来记录最佳匹配文件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.et);
        btn=(Button)findViewById(R.id.btn);
        tv=(TextView)findViewById(R.id.tv);
        pltv=(TextView)findViewById(R.id.pl);
        fbtn=(Button)findViewById(R.id.fbtn);
        postinglist = new StringBuffer();
        function();

        pltv.setText(postinglist.toString());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = et.getText().toString();
                word = InputWords.inputWords(str);
                res = new StringBuffer();
                res.append(str + "\n");
                if (!str.equals("")) {
                    sumcount = new int[files.length];//引入count来记录每个文件中该单词测数量，count和files有顺序对应关系
                    SearchWords.searchWords(word, nodes, files, res, sumcount);//搜索内容不为空的情况下才可查询
                    count = new int[word.length][files.length];//初始化记录单个单词的频率的count
                    for (int i = 0; i < word.length; i++) {
                        res.append("\n" + word[i] + "\n");
                        String[] w = new String[]{word[i]};
                        SearchWords.searchWords(w, nodes, files, res, count[i]);
                    }//获得单个单词的搜索结果
                    res.append("\n");
                    fbtn.setVisibility(View.VISIBLE);
                    System.out.println("sumcount:");
                    for(int i=0;i<files.length;i++){
                        System.out.print(sumcount[i]+" ");
                    }
                    System.out.println();
                    tmp = wtd(count, res);
                    System.out.print(res);
                    tv.setText(res.toString());
                    System.out.println("文件编号" + tmp);
                }

            }
        });

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent it = getTextFileIntent(files[tmp],false);
                startActivity(it);
            }
        });

    }

    public static void function(){//建立词结点的过程
        File root = new File("//data//homework");
        List<String> list = new ArrayList<String>();//记录文件名
        ShowAllFile.showAllFiles(root, list);//遍历所有文件并且将文件放入list中
        files = new String[list.size()];
        for(int i =0;i<list.size();i++){
            files[i] = list.get(i);//表示文本名
            String content = FileReader.readTextFile(files[i]);//表示文本中的内容
            String file = files[i].substring(15, files[i].length()-4);	//15为前缀的长度，4为.txt的长度
            content=content+" "+file;
            String []term = SplitString.spiltString(content);
            for(int j =0;j<term.length;j++){
                term[j] = Stemmer.stemmer(term[j]);
            }
            PostingList.postingList(nodes, term, files[i]);
        }
        display();
    }

    public static void display(){
        System.out.println("->WORD->ID->FREQ->ARTICLE->......");
        postinglist.append("->单词->单词ID->单词出现频率->文章名->......\n");
        System.out.println("---------------------------------");
        postinglist.append("----------------------------------------------\n");
        for(int i = 0;i<nodes.size();i++){
            nodes.get(i).showNode(postinglist);
        }
    }

    //android获取一个用于打开文本文件的intent

    public static Intent getTextFileIntent( String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param );
            intent.setDataAndType(uri1, "text/plain");
            }
        else {
            Uri uri2 = Uri.fromFile(new File(param ));
            intent.setDataAndType(uri2, "text/plain");
            }
        return intent;
        }

    public static int wtd(int [][]count,StringBuffer res){//计算每个文件wtd值，存在rank中
        for(int i =0 ;i<count.length;i++){//count的一行代表一个单词，在该行中的每列代表这个单词在该txt文件中的wtd值
            System.out.println("count["+i+"]:");
            for(int j = 0;j<count[i].length;j++){//每列值和代表该文件的wtd值
                System.out.print(count[i][j]+" ");
            }
            System.out.println();
        }

        rank = new double[count[0].length];
        for(int i = 0;i<rank.length;i++){
            rank[i] = 0;
        }
        int tmp = 0;
        for(int i=0;i<count.length;i++){
            for(int j=0;j<count[i].length;j++){
                if(count[i][j] == 0) {
                    rank[j] += 1;
                }else{
                    rank[j] += 1+Math.log10(count[i][j]+1);
                }
            }
        }
        double max = 0;
        for(int i=0;i<rank.length;i++){
            System.out.println(files[i] + ": " + rank[i]);
            res.append(files[i]+": "+rank[i]+"\n");
            if (rank[i]>max) {
                max = rank[i];
                tmp=i;
            }
        }
        res.append("\n" + "最佳匹配文件为:  " + files[tmp] + " wtd值为：" + rank[tmp]+"\n");
        return tmp;
    }


}
