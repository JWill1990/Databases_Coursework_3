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
import uk.ac.bris.cs.databases.api.SimpleTopicSummaryView;
import uk.ac.bris.cs.databases.api.ForumSummaryView;

public class Forum {

    private final static String simpleForumSummaryViewStatement = "SELECT id, title FROM Forum";
    private final static String forumSummaryViewStatement = "SELECT * FROM Forum ORDER BY Forum.title";
    private final static String topicSummaryStatement = "SELECT id, title FROM Topic";

    public static  Result<List<SimpleForumSummaryView>> getSimpleSummary(Connection c){ 
        ResultSet rst;
        ArrayList list = new ArrayList();
        try (PreparedStatement pstmt= c.prepareStatement(simpleForumSummaryViewStatement)){
            rst=pstmt.executeQuery();
            while(rst.next()){
                long id = rst.getLong("id");
                String title=rst.getString("title").trim();
                list.add(new SimpleForumSummaryView(id,title));
            }

            /*  condition to check if the map/databse is empty*/
            if(!list.isEmpty()){
                return Result.success(list);
            }
            else{
                return Result.failure("There are no forums for this table");
            }
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }
    }

    /**
     * Get the "main page" containing a list of forums ordered alphabetically
     * by title.
     * @return the list of all forums, empty list if there are none.
     */

    public static Result<List<ForumSummaryView>> getSummary(Connection c)
    {
        ResultSet rst;
        ResultSet secondRst;
        ArrayList forumList = new ArrayList();
        int currentLatest = 0;
        try (PreparedStatement pstmt = c.prepareStatement(forumSummaryViewStatement)){
            rst = pstmt.executeQuery();
            while(rst.next()){
                long forumID = rst.getLong("id");
                String forumTitle = rst.getString("title");
                long latestTopic = 0;
                String latestTopicTitle = "";
                try (PreparedStatement SecondPstmt = c.prepareStatement(topicSummaryStatement)) {                    
                    secondRst = SecondPstmt.executeQuery();
                    int latestTime = 0;
                    while(secondRst.next()) {
                        String topicTitle = secondRst.getString("title"); 
                        long topicID = secondRst.getLong("id");                 
                        SimpleTopicSummaryView topic = new SimpleTopicSummaryView(topicID, forumID, topicTitle);
                        int currentTime = Topic.getLatestPost(c, topicID).getValue().getPostedAt();
                        if (currentTime > latestTime) {
                            latestTopic = topicID;
                            latestTime = currentTime;
                            latestTopicTitle = secondRst.getString("title");
                        }
                    }
                    forumList.add(new ForumSummaryView(forumID, forumTitle, new SimpleTopicSummaryView(forumID, latestTopic, latestTopicTitle))); 
                    if(!forumList.isEmpty()){
                        return Result.success(forumList);
                    }
                    else{
                        return Result.failure("There are no forums for this table");
                    }
                }                   
                catch (SQLException e) {
                    return Result.fatal("Unknown error");
                }
            }
            return Result.failure("There are no forums");            
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }

    }

}
