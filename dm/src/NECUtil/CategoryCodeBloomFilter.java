package NECUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.skjegstad.utils.BloomFilter;

import cue.lang.WordIterator;
import cue.lang.stop.StopWords;

public class CategoryCodeBloomFilter {       //分类码布隆过滤器
	
	static Random r = new Random();
    private static BloomFilter<String> instance = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
    static {
    	try {
			constructBloomFilter();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void constructBloomFilter() throws IOException {
		Set<String> categoryword = new HashSet<String>();
		
		//String filename = ConfigureFileUtil.config.getString("classification_file");
		//ClassLoader.getSystemResource(filename).getFile()
		//CategoryCodeBloomFilter.class.getResource(filename).getFile()
		//File file=new File("ClassificationInstructional.dic");

		//if(!file.exists()||file.isDirectory())
		//	System.out.println("no");
		//BufferedReader br = new BufferedReader(new FileReader(file));
		InputStream inputStream= CategoryCodeBloomFilter.class.getResourceAsStream("surname.txt");
		Charset encoding = Charset.forName("UTF-8");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream, encoding));
		
		String temp=null;
		String newword;
		temp=br1.readLine();
		while((temp = br1.readLine())!= null){
			//instance.add(UUID.randomUUID().toString());
			for (String word : new WordIterator(temp)) {
				word = word.toLowerCase();
				newword = word.substring(0, 1).toUpperCase()+word.substring(1);
				if (!StopWords.English.isStopWord(newword)) {
					//System.out.println("Adding category to Bloom Filter: " + newword);
					categoryword.add(newword);
				}
			}
		}
		instance.addAll(categoryword);
		
	}
    
    public static String extractField(String content) {
    	String retstr = "";
    	if (null == content || content.length() < 1)
    		return retstr;
    	
		Set<String> fields = new HashSet<String>();
		for (String word : new WordIterator(content)) {
			word = word.toLowerCase();
			if (!StopWords.English.isStopWord(word) && instance.contains(word)) 
				fields.add(word);
		}
		if (fields.size() > 0)
			retstr = fields.toString();
		fields = null;
		
		return retstr;
    }

	public static void main(String[] args) throws IOException {
		CategoryCodeBloomFilter.constructBloomFilter();
		
		if(instance.contains("james"));
		System.out.println("OK");
		String tempstr=new String();
		String str=new String();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("data//text2.txt"));
			while((tempstr=in.readLine())!=null){
				 //str=str+tempstr+"\n";
				 //System.out.println(tempstr);
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
        System.out.println(extractField(str));          //
	}
     
	
	
	public static BloomFilter<String> getCategoryword()
	{
		return instance;
	}
	
	
	
}
