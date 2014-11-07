package university;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class main {

	public static void main(String args[])
	{
		//urlsearch us = new urlsearch();
		//String[][] urllist = us.geturl();
		
		search s = new search();
		
		int i,j;                           //有一个index文件索引所有网页源代码
		int index, num = 1;                           //num表示学校总数,应该为200
		
		
		
		DB db = new DB();
		
		int count;
		String str=new String();
		BufferedReader in,inn;
		String [] uarr = db.getuniversity();    //储存大学名
		String [] strings;
		//for(index=1;index<=num;index++)
		
		
		for(int iq=0;iq<uarr.length;iq++)  //每个大学为一个循环
		{
		try {
			//in = new BufferedReader(new FileReader("data//web//"+Integer.toString(index)+"//index.txt"));
			
			search.getdic(uarr[iq]);
			
			File file = new File("data//university//"+uarr[iq]+".txt");
			if(file.exists())
			
			{
				System.out.println(uarr[iq]);
			strings = db.geturl(uarr[iq]);
			count = db.getcount(uarr[iq]);
			
			j=0;
			while(j<count){
				 search.dosearch(strings[j],++j);//
			             }           
		    } 
		   
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		}
		
		
		db.close();
	}
	
	
}
