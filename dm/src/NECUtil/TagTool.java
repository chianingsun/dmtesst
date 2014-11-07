package NECUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;
import entity.Scholar;
import cue.lang.WordIterator;
import cue.lang.stop.StopWords;

public class TagTool {
	
	//private static String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
	private static String serializedClassifier = "classifiers/ner-model.ser.gz";
	private static AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	
	
	
	/**
	 * 
	 * @param s is the input string, such as a string extrated from a web page
	 * @return a string tagged with [person��location��organization]
	 */
	public static List<Triple<java.lang.String,java.lang.Integer,java.lang.Integer>> tagStr(String s) {
		//return classifier.classifyWithInlineXML(s);
		return classifier.classifyToCharacterOffsets(s);
	}
		
	public Scholar computResult(String s) {
		Scholar sc = new Scholar();

		List<Triple<java.lang.String,java.lang.Integer,java.lang.Integer>> taggedStr = tagStr(s);
		//System.out.println(taggedStr);
		//String scholarname  = (RegexTool.countPerson(s, taggedStr)).toString();        //原来的方法,先自然处理再正则
		String scholarname = (RegexTool.getName(s)).toString();
		String scholaremail = (RegexTool.extractEmail(s)).toString();
		String scholarfield  = (RegexTool.extractField(s)).toString();
		System.out.println(scholarname);
		System.out.println(scholaremail);
		System.out.println(scholarfield);
		sc.setName(scholarname);
		sc.setEmail(scholaremail);
		sc.setField(scholarfield);
		
		taggedStr = null;
		
		return sc;
	}

	public static void main(String[] args) {
		
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
		
		//TagTool tt = new TagTool();
		//tt.computResult(str);
		//System.out.println(tt.computResult(str).toString());
		
		
		//classifier.train("");
		
		str="Aminata Garba received the Bachelor degree in Electrical Engineering from Laval University and respectively the Master of Engineering and Ph.D. degrees in telecommunication from McGill University. She worked at the National Institute for Scientific Research (Canada), the National Center for Scientific Research (France) and the Regulatory Authority of Niger in the development and implementation of telecommunications and utilities regulatory frameworks. Her research interests include coding, information theory, signal processing, transceiver design, and optical and wireless communication systems, ICT for development, public policy and regulations.";
		//List<String> list = classifier.classify(str);
		
		//classifier = CRFClassifier.
		
		
		//System.out.println(classifier.classifyToString(str));
		
	}

}
