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

    public static final String countSQL = "SELECT count(*) FROM Topic JOIN Post WHERE Topic.id=? and Topic.id=Post.topicID";
    public static final String getSimpleSQL = "SELECT Topic.id, Topic.title, Post.id FROM Post JOIN Topic ON Post.topic=Topic.id WHERE Topic.id=?"

    public static  Result<Integer> getCount(Connection c, Long topicId){ 
        ResultSet rst;
        int count=0;
        try (PreparedStatement pstmt= c.prepareStatement(countSQL)){
            pstmt.setLong(1, topicId);
            rst=pstmt.executeQuery();
            if(rst.next()){
                count=rst.getInt(1);
                return Result.success(count);
            }
            else{
                return Result.failure("There are no posts for this topic");
            }
        }
        catch (SQLException e) {
            System.out.println("Exception: "+e);
            return Result.fatal("Unknown error");
        }
    }

    public static Result<SimpleTopicView> getSimpleTopic(long topicId) {
        ResultSet rst;
        try (PreparedStatement pstmt= c.prepareStatement(getSimpleSQL)){
            pstmt.setLong(1, topicId);
            rst = pstmt.executeQuery();

            

        }
        catch (SQLException e) {
            System.out.println("Exception: "+e);
            return Result.fatal("Unknown error");
        }
    }
}
