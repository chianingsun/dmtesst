package university;

import org.apache.log4j.Logger;

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
public class RegexTool {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegexTool.class);
	
	private static Pattern emailPattern = Pattern.compile("(\\w[-.\\w]*\\@[^-][-a-z0-9]+(\\.[-a-z0-9]+)*\\.(com|edu|info))", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	
	
	private static Pattern personNamePattern =Pattern.compile("([A-Z]([a-z]+)\\s[A-Z]([a-z]+))", Pattern.MULTILINE);
	//以下是有middle name 的情况
	private static Pattern personNamePattern1 =Pattern.compile("([A-Z]([a-z]+)\\s[A-Z]\\.\\s[A-Z]([a-z]+))", Pattern.MULTILINE);
	
	
	
	//
	
	
	
	/**
	 * This function is extract email addr
	 * @param s
	 * s is the input string which contain email addr
	 * @return a list of email addr
	 */
	
	public static String extractEmail(String s) {
		if (logger.isDebugEnabled()) {
			logger.debug("extractEmail(String) - start");
		}

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
		
		
			emaillist.add("null email");
		//System.out.println(emaillist.get(0));

		if (logger.isDebugEnabled()) {
			logger.debug("extractEmail(String) - end");
		}
		return emaillist.get(0);
	}
	

	public static List<String> extractName(String s) {
		if (logger.isDebugEnabled()) {
			logger.debug("extractName(String) - start");
		}
          //初步正则匹配
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
		
		if (logger.isDebugEnabled()) {
			logger.debug("extractName(String) - end");
		}
		return namelist;
	}

	
	
	
	
	
	
	/**
	 * 
	 * @param sourceStr
	 * sentences - The string to be classified
	 * @param taggedTripleList
	 * A List of Triples, each of which gives an entity type and the beginning and ending character offsets.
	 * @return
	 */
	

	/**
	 * This function is to extract the research field
	 * @param s
	 * @return a list of research field
	 */
	public static List<String> extractField(String s) {
		if (logger.isDebugEnabled()) {
			logger.debug("extractField(String) - start");
		}
		
		List<String> returnList = new ArrayList<String>();
		if (logger.isDebugEnabled()) {
			logger.debug("extractField(String) - end");
		}
		return returnList;
	}
	
/*
	
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
		//System.out.println(str);
		
		//System.out.println(getName(str));
		
		
	}*/
}
