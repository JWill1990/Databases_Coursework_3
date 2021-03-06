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
import uk.ac.bris.cs.databases.api.AdvancedForumSummaryView;
import uk.ac.bris.cs.databases.api.TopicSummaryView;
import uk.ac.bris.cs.databases.api.ForumSummaryView;
import uk.ac.bris.cs.databases.api.ForumView;

public class Forum {

    private final static String simpleForumSummaryViewStatement =
        "SELECT * FROM Forum ORDER BY Forum.title";

    private final static String topicSummaryStatement =
        "SELECT Topic.id, Topic.title FROM Topic " +
        "JOIN Forum ON Forum.id = Topic.forumID " +
        "WHERE Forum.id=?";

    private static final String getDetailedForumSQL =
        "SELECT Forum.title AS forumTitle, Forum.id AS forumID, " +
        "Topic.id AS topicID, Topic.title AS topicTitle " +
        "FROM Topic " +
        "JOIN Forum ON Topic.forumID=Forum.id " +
        "WHERE Forum.id=?";

    private static final String addForum=
        "INSERT into Forum values (null, ?)";

    /**
     * Get the "main page" containing a list of forums ordered alphabetically
     * by title. Simple version that does not return any topic information.
     * @return the list of all forums; an empty list if there are no forums.
     */
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
        ArrayList<ForumSummaryView> forumList = new ArrayList<ForumSummaryView>();
        int currentLatest = 0;
        try (PreparedStatement pstmt = c.prepareStatement(simpleForumSummaryViewStatement)){
            rst = pstmt.executeQuery();
            while(rst.next()){
                long forumID = rst.getLong("id");
                String forumTitle = rst.getString("title");
                long latestTopic = 0;
                String latestTopicTitle = "no topics yet";
                try (PreparedStatement secondPstmt = c.prepareStatement(topicSummaryStatement)) {
                    secondPstmt.setLong(1, forumID);
                    secondRst = secondPstmt.executeQuery();
                    int latestTime = 0;
                    while(secondRst.next()) {
                        String topicTitle = secondRst.getString("title");
                        long topicID = secondRst.getLong("id");
                        SimpleTopicSummaryView topic = new SimpleTopicSummaryView(topicID, forumID, topicTitle);
                        int currentTime = 0;
                        /*int currentTime = Topic.getLatestPost(c, topicID).getValue().getPostedAt();
                        System.out.println("here?");  */
                        if (currentTime >= latestTime) {
                            latestTopic = topicID;
                            latestTime = currentTime;
                            latestTopicTitle = topicTitle;
                        }
                    }
                    forumList.add(new ForumSummaryView(forumID, forumTitle, new SimpleTopicSummaryView(forumID, latestTopic, latestTopicTitle)));
                }
                catch (SQLException e) {
                    return Result.fatal("Unknown error");
                }
            }
			if(!forumList.isEmpty()) {
                return Result.success(forumList);
            }
            else {
                return Result.failure("There are no forums for this table");
            }
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }

    }

    /**
     * Get the detailed view of a single forum.
     * @param id - the id of the forum to get.
     * @return A view of this forum if it exists, otherwise failure.
     */
    public static Result<ForumView> getDetailedForum(Connection c, long forumId)
    {
        ResultSet rst;
        ArrayList<SimpleTopicSummaryView> forumTopics = new ArrayList<SimpleTopicSummaryView>();
        try (PreparedStatement pstmt = c.prepareStatement(getDetailedForumSQL)){

            pstmt.setLong(1, forumId);

            rst = pstmt.executeQuery();

            String forumTitle = rst.getString("ForumTitle");
            while (rst.next()) {
                long topicId = rst.getLong("TopicID");
                String topicTitle = rst.getString("TopicTitle");
                forumTopics.add(new SimpleTopicSummaryView(topicId, forumId, topicTitle));
            }
            ForumView fv = new ForumView(forumId, forumTitle, forumTopics);
            return Result.success(fv);
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }
    }

    public static Result addForum(Connection c, String title){
        int row=0;
        System.out.println(CheckExists.forum(c, title));
        if(!CheckExists.forum(c, title)){

            try(PreparedStatement pstmt=c.prepareStatement(addForum)){
                pstmt.setString(1, title);
                pstmt.executeUpdate(); //Use update method to write to db
                c.commit();
                return Result.success();
            }
            catch(Exception e){
                return Result.failure("Unexpected failure");
            }
        }
        else{
            return Result.failure("Forum exists");
        }

    }

}
