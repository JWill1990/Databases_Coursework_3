package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.SimpleForumSummaryView;

public class CountPostsinTopic {
	public static  Result<Integer> getCount(Connection c,String sql, Long topicId){ 
    	ResultSet rst;
    	int count=0;
    	try (PreparedStatement pstmt= c.prepareStatement(sql)){
			pstmt.setLong(1, topicId);
    		rst=pstmt.executeQuery();
			if(rst.next()){
	          	count=rst.getInt(1);
	          	return Result.success(count);
			}
      else{
    	  	return Result.failure("Ther are no posts for this topic");
      }
    } catch (SQLException e) {
	    	System.out.println("Exception: "+e);
	    	return Result.fatal("Unknown error");
    		}
	}
}
