package university;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class search {

	static DB db= new DB();
	static ner nlp = new ner();
	static String urllist[] = new String [200];
	
	public search()
	{
		
		
	}
	
	
	
	public static void getdic(String u)
	{
		
		for(int i=0;i<200;i++)
			urllist[i]="";
		
		String tempstr=new String();
        int count = 0;
		BufferedReader in;
		try {
			
			File file = new File("data//university//"+u+".txt");
			if(file.exists())
			{
				in = new BufferedReader(new FileReader("data//university//"+u+".txt"));
			while((tempstr=in.readLine())!=null){
				 urllist[count]=tempstr;
				 count++;
			 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static void dosearch(String url,int index2)     //index2表示第几个网页
	{
		        //注意用index2来做检索
		int i,j;
		String ms;
		Pattern pt;
		Matcher mt;
	    //String[] urllist=getdic(url);
		//System.out.println(url);
		
		//search3(url,index2);
		//
		
			for(j=1;!urllist[j].equals("");j++)
			{
				ms = urllist[j];
			    pt = Pattern.compile(ms);
				mt = pt.matcher(url);
				if(mt.find())
				{
					//System.out.println(ms);
					search3(url,index2);
					break;
				}	
			}		
		
	}
	
	
	
	
	public static void search3(String url,int index)  //i是大学编号j是院系编号         //匹配的情况
	{
		
		
		/*
		String dir;
		dir = "data//web//"+Integer.toString(i)+"//"+Integer.toString(index)+".txt";
		//System.out.println(dir);
		String tempstr=new String();
		String str=new String();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(dir));
			while((tempstr=in.readLine())!=null){
				 str=str+tempstr+"\n";
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		System.out.println(index);
		System.out.println(url);
		String str = db.getcontent(index);
		str = HTMLSpirit.delHTMLTag(str);
		
		String s1 = NameDic.getName(str);
		String s2 = RegexTool.extractEmail(str);
		String s3 = nlp.nersearch(act(str));
		if(s3.length()>200)
			s3 = s3.substring(0 , 199);
		
		db.write(s1, s2, s3);
		
		
		
		
		
		
		
		
		
		//System.out.println("yes");
		/*
		if(i==1&&j==1)        
		{
			//根据i和j确定网页的名称找出相应的解决算法
		}
		*/
		
	}	
	
	
	
	
	public static String[] act(String string)
	{
		
		String [] args = null;
		args = string.split("\n");
		for(int i=0;i<args.length;i++)
		{
			if (!(args[i].contains("esearch")|args[i].contains("nterst")))              //关键词预判
				args[i]="";
		}
		return args;
		
	}
}
