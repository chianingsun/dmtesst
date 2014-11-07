package NECUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HashMapSort {
    public static boolean ASC = true;
    public static boolean DESC = false;

    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

    	if (unsortMap == null || unsortMap.isEmpty()) {  
            return null;  
        }  
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();  
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(unsortMap.entrySet());  
          if(order)
              Collections.sort(entryList, new MapValueComparator1());  
          else
        	  Collections.sort(entryList, new MapValueComparator2());  	  
        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();  
        Map.Entry<String, Integer> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
    }  
       
    
  
    public static void printMap(Map<String, Integer> map)
    {
    	String tempStr = "[";
        for (Entry<String, Integer> entry : map.entrySet())
        {
        	tempStr += entry.getKey() + ":" + entry.getValue() + ",";
            //System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
        }
        if (map.size() > 0)tempStr = tempStr.substring(0, tempStr.length() - 1);
        tempStr += "]";
        System.out.println(tempStr);
    }
}




 class MapValueComparator1 implements Comparator<Map.Entry<String, Integer>> {  
    @Override
	public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {  
        return me1.getValue().compareTo(me2.getValue());  
    }  
}  

 class MapValueComparator2 implements Comparator<Map.Entry<String, Integer>> {  
	    @Override
		public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {  
	        return me2.getValue().compareTo(me1.getValue());  
	    }  
	}  











