package uk.ac.bris.cs.databases.cwk3;

import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetPersonView {
	
	 @SuppressWarnings("unchecked")
	public static  Result<PersonView> GetPersonViews(Connection c,String sql, String uname){ 
	    	
	        ResultSet rst;
	    	/*try with statement closes closes the recources after it is done*/
	       	try (PreparedStatement pstmt = c.prepareStatement(sql)) {
	          pstmt.setString(1, uname);
	       	  rst=pstmt.executeQuery();
	       	  if (rst.next()) {
	       		  String Name=rst.getString("name").trim();
				  String Username=rst.getString("username").trim();
				  String stuId=rst.getString("stuId").trim();
				  return Result.success(new PersonView(Name,Username,stuId));
	       		  
	       	  } else {
	       		return Result.failure("There are no user for this table");
	       	  }  
	    } catch (SQLException e) {
	    	return Result.fatal("exception - " + e);
	    	
	    }
	    //return Result.fatal("No error ");
	}
}
