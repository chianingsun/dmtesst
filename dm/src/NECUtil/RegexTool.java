package NECUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.skjegstad.utils.BloomFilter;

import cue.lang.WordIterator;
import cue.lang.stop.StopWords;
public class RegexTool {
	
	private static Pattern emailPattern = Pattern.compile("(\\w[-.\\w]*\\@[^-][-a-z0-9]+(\\.[-a-z0-9]+)*\\.(com|edu|info))", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	
	private static Pattern personNamePattern =Pattern.compile("([A-Z]([a-z]+)\\s[A-Z]([a-z]+))", Pattern.MULTILINE);
	//以下是有middle name 的情况
	private static Pattern personNamePattern1 =Pattern.compile("([A-Z]([a-z]+)\\s[A-Z]\\.\\s[A-Z]([a-z]+))", Pattern.MULTILINE);
	
	
	 
	 private static BloomFilter<String> instancesur = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
	    static {
	    	try {
				constructBloomFilter1();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	
	   private static BloomFilter<String> instancefirst = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
	    static {
	    	try {
				constructBloomFilter2();
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
			
			InputStream inputStream= CategoryCodeBloomFilter.class.getResourceAsStream("surname.txt");
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
					if (!StopWords.English.isStopWord(newword)) {
						categoryword.add(newword);
					}
				}
			}
			instancesur.addAll(categoryword);
			
		}
	
	
	    
	    public static void constructBloomFilter2() throws IOException {
			Set<String> categoryword = new HashSet<String>();
			
			InputStream inputStream= CategoryCodeBloomFilter.class.getResourceAsStream("firstname.txt");
			Charset encoding = Charset.forName("UTF-8");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream, encoding));
			
			String temp=null;
			String newword;
			temp=br1.readLine();
			while((temp = br1.readLine())!= null){
				for (String word : new WordIterator(temp)) {
					word = word.toLowerCase();
					newword = word.substring(0, 1).toUpperCase()+word.substring(1);
					if (!StopWords.English.isStopWord(newword)) {
						categoryword.add(newword);
					}
				}
			}
			instancefirst.addAll(categoryword);
			
		}
	
	    
	
	    public static void constructBloomFilter3() throws IOException {
			Set<String> categoryword = new HashSet<String>();
			
			InputStream inputStream= CategoryCodeBloomFilter.class.getResourceAsStream("noname.txt");
			Charset encoding = Charset.forName("UTF-8");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream, encoding));
			
			String temp=null;
			String newword;
			temp=br1.readLine();
			while((temp = br1.readLine())!= null){
				for (String word : new WordIterator(temp)) {
					word = word.toLowerCase();
					newword = word.substring(0, 1).toUpperCase()+word.substring(1);
					if (!StopWords.English.isStopWord(newword)) {
						categoryword.add(newword);
					}
				}
			}
			instanceno.addAll(categoryword);
			
		}
	    
	    
	
	
	/**
	 * This function is extract email addr
	 * @param s
	 * s is the input string which contain email addr
	 * @return a list of email addr
	 */
	
	public static List<String> extractEmail(String s) {
		List<String> emaillist = new ArrayList<String>();
		String find=null;
		Matcher mat=emailPattern.matcher(s);
		while(mat.find()){
			find=mat.group(1);
			emaillist.add(find);
			//System.out.println("Find:"+find);
		}
		//if(find==null)
			  //System.out.print("Can't Find");
		find = null;
		mat = null;
		return emaillist;
	}
	

	public static List<String> extractName(String s) {
		List<String> namelist = new ArrayList<String>();
		String find=null;
		String point = "\\.";
		String arr[] = new String[2];
		Matcher mat=personNamePattern.matcher(s);
		while(mat.find()){
			find=mat.group(1);
			namelist.add(find);
			//System.out.println("Find:"+find);
		}             
		//if(find==null)
			           //System.out.print("Can't Find");
		
		mat=personNamePattern1.matcher(s);
		while(mat.find()){
			find=mat.group(1);
			//System.out.println("F "+find);
			arr = find.split(point);
			//System.out.println(arr[0]);
			//System.out.println(arr[1]);
			find=arr[0].substring(0, arr[0].length()-2);
			find=find+arr[1];
			//System.out.println(find);
			namelist.add(find);
			//System.out.println("Find:"+find);
		}
		
		

		
		
		
		find = null;
		mat = null;
		
		return namelist;
	}

	
	public static Map<String, Integer> getName(String s) {
		
		List<String> namelist = extractName(s);   //已提取的正则姓名词组
		
		
		//List<String> namelist1 = ;
		//List<String> namelist2 = ;
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		//for(Triple<java.lang.String,java.lang.Integer,java.lang.Integer> item : taggedTripleList)
		
		String bl = " ";
		String[] sa = new String[]{"",""};
		for(String item : namelist)                  //将姓和名分开存储到map中
		{
			//System.out.println(item);
			if(item.contains(bl))
			{
				sa = item.split(bl);
                //mt.put(item, 1);
				//if(instancefirst.contains(sa[0]))
					//System.out.println("F");
				//if(instancefirst.contains(sa[1]))
					//System.out.println("S");
				
				if(instancefirst.contains(sa[0])&&instancesur.contains(sa[1])&&!instanceno.contains(sa[0])&&!instanceno.contains(sa[1]))
				{
					
					if(map.containsKey(item))
					   map.put(item, map.get(item)+1);
					else
					   map.put(item, 1);
				}
			
			}
		}
		
		
		
	    //System.out.println(mt1.toString());
	    //System.out.println(mt2.toString());
	    
	    
	    /*
		Map<String, Integer> personmap = new HashMap<String, Integer>();
		for(Triple<java.lang.String,java.lang.Integer,java.lang.Integer> item : taggedTripleList)
		{
			if (item.first.equals("PERSON")) {
				String name = s.substring(item.second, item.third);		
					System.out.println(name);
				name = qualifyPersonName(name);
				
				if(mt1.containsKey(name))
					mt3.put(name, mt1.get(name));
					
				if(mt2.containsKey(name))
					mt4.put(name, mt2.get(name));
			
			}
		}
		*/
		
        //System.out.println(mt3.toString());
        //System.out.println(mt4.toString());
		
		
        map = HashMapSort.sortByComparator(map, HashMapSort.DESC);
		
        
         
		return map;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param sourceStr
	 * sentences - The string to be classified
	 * @param taggedTripleList
	 * A List of Triples, each of which gives an entity type and the beginning and ending character offsets.
	 * @return
	 */
	
	
	/*
	public static Map<String, Integer> countPerson(String sourceStr, List<Triple<java.lang.String,java.lang.Integer,java.lang.Integer>> taggedTripleList) {
		Map<String, Integer> personmap = new HashMap<String, Integer>();
		for(Triple<java.lang.String,java.lang.Integer,java.lang.Integer> item : taggedTripleList) {
			
			//System.out.println(item.first+" "+sourceStr.substring(item.second, item.third));
			
			
			if (item.first.equals("PERSON")) {
				String name = sourceStr.substring(item.second, item.third);
				//System.out.println("F:"+name);
				name = qualifyPersonName(name);
				//System.out.println("S:"+name);
				if (personmap.containsKey(name)) {
					personmap.put(name, personmap.get(name)+1);
				}else
					personmap.put(name, 1);
			}
			
		}
		
		
		
		
		System.out.println(personmap.size());
		return HashMapSort.sortByComparator(personmap, HashMapSort.DESC);
	}
	*/
	/**
	 * This function is to extract the research field
	 * @param s
	 * @return a list of research field
	 */
	public static List<String> extractField(String s) {
		
		return new ArrayList<String>();
	}
	
	
	/*
	public static String qualifyPersonName(String name) {
		String find = "";
		
		// constrain the length of name less than 100 letters
		if (name.length() > 100) return find;
		
		// prune the name: replace \n \t with "" and replace "  +" with " "
		name = name.replaceAll("\n", " ").replaceAll("\t", " ").replaceAll(" +", " ").trim();
		
		// use a regex to check whether name is satisfying the criteria
		Matcher mat = personNamePattern.matcher(name);
		while(mat.find()){
			find=mat.group();
			System.out.println("Find:"+find);
		}
		return find;
	}
	*/
	
	
	public static void main(String args[]) {
		
		
		
		String tempstr=new String();
		String str=new String();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("data//text1.txt"));
			while((tempstr=in.readLine())!=null){
				 str=str+tempstr+"\n";
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		str = HTMLSpirit.delHTMLTag(str);
		System.out.println(getName(str));
		
		
	}
}
