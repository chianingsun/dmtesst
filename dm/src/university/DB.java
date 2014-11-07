package university;

import java.sql.*;
public class DB {

	
	String driver = "com.mysql.jdbc.Driver";
	String url="jdbc:mysql://127.0.0.1:3306";
    String user="root";
    String pwd="delajiqi";
    Connection conn;
    Statement stmt;
    ResultSet rs;
    static String uni = ""; 
    
    
    
    public DB()
    {
    	try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user, pwd);
		    stmt = conn.createStatement();//创建语句对象，用以执行sql语言
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
    }
    
    public void close()
    {
    	
    	  try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//关闭数据库
	      
    }
	
    public Integer getcount(String u)
    {
    	u=u.replace("(", "");
		u=u.replace(")", "");
		u=u.replace(",", "");
		u=u.replace(" ", "_");
		
    	
    	int nn = 0;
    	try {
			rs = stmt.executeQuery("SELECT max(id) FROM zjus."+u);
			
			if(rs.next())
			nn = rs.getInt(1);
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  new Integer(nn);
    	
    	
    	
    }
    
    
    
	public String[] geturl(String u)
	{
		String[] array = new String [99999];
		int allnum=0;
		
		uni = u;
		
		u=u.replace("(", "");
		u=u.replace(")", "");
		u=u.replace(",", "");
		u=u.replace(" ", "_");
		
		for(int i=0;i<99999;i++)
			array[i]="";
			
			    //Class.forName("com.mysql.jdbc.Driver");
	    try {
			
	    	//先读入一个大学名的列表
	    	//System.out.println("SELECT max(id) FROM zjus."+u);
	    	rs = stmt.executeQuery("SELECT max(id) FROM zjus."+u);
	    	
	    	if(rs.next())
	    	allnum = rs.getInt(1);
	    	
	    	
	    	int num = allnum/5000;
	    	num++;
	    	int i=0,j=0;
	    	String query1 ="SELECT url FROM zjus."+u+" where id>=";
	    	String query2 =" and id<";
	    	
	    	
	    	for(;i<num;i++)
	    	{
	    		
	    		//System.out.println(query1+Integer.toString(i*5000)+query2+Integer.toString(i*5000+5000));
		    rs = stmt.executeQuery(query1+Integer.toString(i*5000)+query2+Integer.toString(i*5000+5000)); //一次读取的行数有限制,建议取5001
		   
		    while (rs.next())
		      {
		        array[j++] = rs.getString("url");
		      }
		    
	    	}
		    	   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return array;
	}
	
	
	public String getcontent(int index) //待修改
	{
		
		String u=uni;
		
		u=u.replace("(", "");
		u=u.replace(")", "");
		u=u.replace(",", "");
		u=u.replace(" ", "_");
		
		
		
		
		String ct="";	
		String s = "SELECT content FROM zjus."+u+" where id="+Integer.toString(index);
		//System.out.println(s);
	    //Class.forName("com.mysql.jdbc.Driver");
	    try {
			
		    rs = stmt.executeQuery(s); //一次读取的行数有限制,建议取5000
				
		   
		    while (rs.next())
		      {
		        ct = rs.getString("content");
		      }
		    	   
		   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return ct;
	}
	
	
	public String[] getuniversity()
	{
		int count =200;
		String ct[]=new String [count];	
		String s = "SELECT name FROM zjus.university";
		
		
		//System.out.println(s);
	    //Class.forName("com.mysql.jdbc.Driver");
	    try {
			
		    rs = stmt.executeQuery(s); //一次读取的行数有限制,建议取5000
				
		    int i=0;
		    while (rs.next()&&i<count)
		      {
		        ct[i] = rs.getString("name");
		        //System.out.println(ct[i]);
		        i++;
		      }
		    	   
		   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return ct;
	}
	
	
	public void write(String s1,String s2,String s3)
	{
		int max = 7000000, maxx = 0;
		//暂时将以下代码注释
		/*
		try {
			rs = stmt.executeQuery("Select max(reviewerID) from zjus.reviewer2");
			
			 if (rs.next())
		      {
		        maxx = rs.getInt("max(reviewerID)");
		      }
			
			 if(max < maxx )	 
				 max = ++maxx;
			 
			 
			String sq="Insert into zjus.reviewer2 Values("+Integer.toString(max)+",'"+s1+"','','','','','"+s2+"','',0,0,'"+s3+"','','',0)"; 
			//System.out.println(sq);
			stmt.execute(sq);
			
			
			
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
	}
	
	
	
	
}
