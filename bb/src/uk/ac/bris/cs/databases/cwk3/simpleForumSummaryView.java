package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.SimpleForumSummaryView;

public class simpleForumSummaryView {
	 public static  Result<List<SimpleForumSummaryView>> getSummary(Connection c,String sql){ 
	    	ResultSet rst;
	    	ArrayList list=new ArrayList();
	    	try (PreparedStatement pstmt= c.prepareStatement(sql)){
				rst=pstmt.executeQuery();
				while(rst.next()){
		          		 Long id=rst.getLong("id");
		  		         String title=rst.getString("title").trim();
		  		         list.add(new SimpleForumSummaryView(id,title));
		  	 	  }
			 
	        /*  condition to check if the map/databse is empty*/
	      if(!list.isEmpty()){
	    	return Result.success(list);
	      }
	      else{
	    	return Result.failure("There are no user for this table");
	      }
	    } catch (SQLException e) {}
	    return Result.fatal("Unknown error");
	}

}
