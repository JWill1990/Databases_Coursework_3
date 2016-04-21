package uk.ac.bris.cs.databases.cwk3;

import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetPersonView {
    private final static String getPersonViewsStatement = "SELECT name, username, stuId FROM Person WHERE username = ?";
    private final static String getLikersStatement = "SELECT name, username, stuID FROM Person JOIN Likers ON Likers.personID = Person.id JOIN Topic ON Likers.topicID = ? ";
	
	 @SuppressWarnings("unchecked")
	public static  Result<PersonView> getPersonView(Connection c, String uname){ 
	    	
	        ResultSet rst;
	    	/*try with statement closes closes the recources after it is done*/
	       	try (PreparedStatement pstmt = c.prepareStatement(getPersonViewsStatement)) {
	          pstmt.setString(1, uname);
	       	  rst = pstmt.executeQuery();
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

    public static Result<List<PersonView>> getLikers(Connection c, long topicId)
    {
        ResultSet rst;
        ArrayList personList = new ArrayList();
        
        try (PreparedStatement pstmt = c.prepareStatement(getLikersStatement)) {
            rst = pstmt.executeQuery();
            pstmt.setLong(1, topicId);
            while(rst.next()) {
                String name = rst.getString("name").trim();
	            String username=rst.getString("username").trim();
		        String stuId=rst.getString("stuId").trim();                
                personList.add(new PersonView(name, username, stuId));
            }
            if(!personList.isEmpty()) {
	    	    return Result.success(personList);
	        }
	        else{
	    	    return Result.failure("There are no likes for this topic.");
            }
	    } catch (SQLException e) {
	            return Result.fatal("Unknown error");
	    }
    }

	
}
