package university;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;

import java.io.IOException;
import java.util.List;

public class ner {

	String serializedClassifier;
	AbstractSequenceClassifier<CoreLabel> classifier;
	public ner()
	{

		    serializedClassifier = "classifiers/ner-model.ser.gz";


		    try {
				classifier = CRFClassifier.getClassifier(serializedClassifier);
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public String nersearch(String [] strings)
	{
	
		     

		      String ri = "";

		      int i=0;
		      for (String str : strings) {
		        for (List<CoreLabel> lcl : classifier.classify(str)) {
		          for (CoreLabel cl : lcl) {
		              if(cl.toShortString("Answer").equals("RI"))
		            	  ri+=(cl.word()+" ");
		          }
		                            
		        }
		      }
		      
		      if(ri.equals(""))
		    	  ri="null research interst";
		      ri+="\n";
		      
		      return ri;
		    
		  }
	

}
