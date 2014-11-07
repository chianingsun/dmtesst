package university;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.skjegstad.utils.BloomFilter;

import cue.lang.WordIterator;

public class NameDic {

	
	 private static BloomFilter<String> instancesur = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
	    static {
	    	try {
				constructBloomFilter2();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	
	   private static BloomFilter<String> instancefirst = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
	    static {
	    	try {
				constructBloomFilter1();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    private static BloomFilter<String> instanceno = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
	    static {
	    	try {
				constructBloomFilter3();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	  
	    public static void constructBloomFilter1() throws IOException {
			Set<String> categoryword = new HashSet<String>();
			
			InputStream inputStream= main.class.getResourceAsStream("name//firstname.txt");
			
			
			//System.out.println(inputStream.toString());
			
			Charset encoding = Charset.forName("UTF-8");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream, encoding));
			
			String temp=null;
			String newword;
			temp=br1.readLine();
			while((temp = br1.readLine())!= null){
				for (String word : new WordIterator(temp)) {
					word = word.toLowerCase();
					newword = word.substring(0, 1).toUpperCase()+word.substring(1);
					
					//if (!StopWords.English.isStopWord(newword)) {
						categoryword.add(newword);
						//System.out.println(newword);
					//}
				}
			}
			instancefirst.addAll(categoryword);
			
		}
	
	
	    
	    public static void constructBloomFilter2() throws IOException {
			Set<String> categoryword = new HashSet<String>();
			
			InputStream inputStream= main.class.getResourceAsStream("name//surname.txt");
			Charset encoding = Charset.forName("UTF-8");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream, encoding));
			
			String temp=null;
			String newword;
			temp=br1.readLine();
			while((temp = br1.readLine())!= null){
				for (String word : new WordIterator(temp)) {
					word = word.toLowerCase();
					newword = word.substring(0, 1).toUpperCase()+word.substring(1);
					//if (!StopWords.English.isStopWord(newword)) {
						categoryword.add(newword);
					//}
				}
			}
			instancesur.addAll(categoryword);
			
		}
	
	    
	
	    public static void constructBloomFilter3() throws IOException {
			Set<String> categoryword = new HashSet<String>();
			
			InputStream inputStream= main.class.getResourceAsStream("name//noname.txt");
			Charset encoding = Charset.forName("UTF-8");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream, encoding));
			
			String temp=null;
			String newword;
			temp=br1.readLine();
			while((temp = br1.readLine())!= null){
				for (String word : new WordIterator(temp)) {
					word = word.toLowerCase();
					newword = word.substring(0, 1).toUpperCase()+word.substring(1);
					//System.out.println(newword);
					//if (!StopWords.English.isStopWord(newword)) {
						categoryword.add(newword);
					//}
				}
			}
			instanceno.addAll(categoryword);
			
		}
	    
	    
	
	
        public static  String getName(String s) {
		
		List<String> namelist = RegexTool.extractName(s);   //已提取的正则姓名词组
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		
		String bl = " ";
		String[] sa = new String[]{"",""};
		for(String item : namelist)                  //将姓和名分开存储到map中
		{
			
			if(item.contains(bl))
			{
				sa = item.split(bl);
              
				
				if((instancefirst.contains(sa[0])||instancesur.contains(sa[1]))&&!instanceno.contains(sa[0])&&!instanceno.contains(sa[1]))
				{
					
					if(map.containsKey(item))
					   map.put(item, map.get(item)+1);
					else
					   map.put(item, 1);
				}
			
			}
		}
		
		if(map.isEmpty())
        	map.put("null name",1);
        map = HashMapSort.sortByComparator(map, HashMapSort.DESC);
        
        
        Iterator it = map.entrySet().iterator();  
        Map.Entry entry = (Map.Entry) it.next(); 
        
        //这里的输出最终要改成只输出map的第一个元素
		//System.out.println(entry.getKey());
         
		return entry.getKey().toString();
	}
	
	
	
	
	
	
	
	
	
	
	
}
