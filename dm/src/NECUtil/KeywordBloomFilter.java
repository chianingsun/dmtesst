package NECUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.skjegstad.utils.BloomFilter;

import cue.lang.WordIterator;
import cue.lang.stop.StopWords;

public class KeywordBloomFilter {  
	
	static Random r = new Random();
    private static BloomFilter<String> instance = new BloomFilter<String>(0.0001, 5000);//bloomfilter用于检索一个元素是否在一个集合中
    
    public static void constructBloomFilter(Set<String> categoryword) {
		instance.addAll(categoryword);
	}
    
    public static String extractField(String content) {
    	String retstr = "";
    	if (null == content || content.length() < 1)
    		return retstr;
    	
		Set<String> fields = new HashSet<String>();
		for (String word : new WordIterator(content)) {
			word = word.toLowerCase();
			if (!word.matches("\\d+") && !StopWords.English.isStopWord(word) && instance.contains(word)) 
				fields.add(word);
		}
		if (fields.size() > 0)
			retstr = fields.toString();
		fields = null;
		
		return retstr;
    }

	public static void main(String[] args) throws IOException {
		
		
		CategoryCodeBloomFilter.constructBloomFilter();
		
		//KeywordBloomFilter.constructBloomFilter(CategoryCodeBloomFilter.getCategoryword());

	}

}
