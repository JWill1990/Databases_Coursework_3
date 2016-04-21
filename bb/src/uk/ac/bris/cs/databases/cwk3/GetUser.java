package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import uk.ac.bris.cs.databases.api.Result;

public class GetUser {
    private final static String getUserStatement = "SELECT name,username FROM Person";
	  
    public static  Result<Map<String, String>> GetUserInfo(Connection c){ 
    	PreparedStatement pstmt;
        ResultSet rst;
        Map<String, String> maps=new HashMap<String,String>();  //Maps
    	try {
		pstmt = c.prepareStatement(getUserStatement);
		rst=pstmt.executeQuery();
		System.out.println("test");
        while(rst.next()){
          		 String name=rst.getString("name").trim();
  		         String username=rst.getString("username").trim();
  		         System.out.println(name+","+username);
  		         System.out.println(rst.getRow());
  		         maps.put(name,username);
  	 	  }
      
        /*  condition to check if the map/databse is empty*/
      if(!maps.isEmpty()){
    	return Result.success(maps);
      }
      else{
    	return Result.failure("There are no user for this table");
      }
    } catch (SQLException e) {}
    return Result.fatal("Unknown error");
}
}

