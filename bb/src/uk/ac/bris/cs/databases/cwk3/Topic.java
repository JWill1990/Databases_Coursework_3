package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.SimpleForumSummaryView;
import uk.ac.bris.cs.databases.api.SimpleTopicView;
import uk.ac.bris.cs.databases.api.SimplePostView;
import uk.ac.bris.cs.databases.api.PersonView;

public class Topic {

    private static final String countSQL = 
        "SELECT count(*) " +
        "FROM Topic " +
        "JOIN Post WHERE (Topic.id=? " +
            "AND Topic.id=Post.topicID)";
    private static final String getSimpleSQL = 
        "SELECT Topic.id AS Topic_id, Topic.title, " +
            "Person.name, Post.text, Post.postedAt " +
        "FROM Post " + 
        "JOIN Topic ON Post.topicID=Topic.id " +
        "JOIN Person ON Post.personID=Person.id " +
        "WHERE Topic.id=?" +
        "ORDER BY Post.postedAt";
    private final static String getLikersStatement = 
        "SELECT name, username, stuID " +
        "FROM Person " +
        "JOIN Likers ON Likers.personID = Person.id " +
        "JOIN Topic ON Likers.topicID = ? ";


    /**
     * Count the number of posts in a topic (without fetching them all).
     * @param topicId - the topic to look at.
     * @return The number of posts in this topic if it exists, otherwise a
     * failure.
     */
    public static Result<Integer> getCount(Connection c, long topicId){ 
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


    /**
     * Get a simplified view of a topic.
     * @param topicId - the topic to get.
     * @return The topic view if one exists with the given id,
     * otherwise failure or fatal on database errors. 
     */
    public static Result<SimpleTopicView> getSimpleTopic(Connection c, long topicId) {
        ResultSet rst;
        long id;
        String title;
        List<SimplePostView> posts = new ArrayList<SimplePostView>();

        try(PreparedStatement pstmt= c.prepareStatement(getSimpleSQL)){
            pstmt.setLong(1, topicId);
            rst = pstmt.executeQuery();

            //Check for at least one row
            if(rst.next()){
                //Get Topic info
                id = rst.getLong("Topic_id");
                title = rst.getString("title");
                int i = 1;
                //Get first post info
                int postNumber = i++;
                String author = rst.getString("name");
                String text = rst.getString("text");
                int postedAt = rst.getInt("postedAt");

                //Add first post to list
                SimplePostView p =
                    new SimplePostView(postNumber, author, text, postedAt);
                posts.add(p);

                //Add remaining posts
                while(rst.next()){
                    postNumber = i++;
                    author = rst.getString("name");
                    text = rst.getString("text");
                    postedAt = rst.getInt("postedAt");
                    p = new SimplePostView(postNumber, author, text, postedAt);
                    posts.add(p);
                }

                //Return result
                SimpleTopicView topic = new SimpleTopicView(id, title, posts);
                return Result.success(topic);
            }
            //Error if no data for that topic
            else{ return Result.failure("Topic does not exist"); }
        }
        catch(SQLException e) {
            System.out.println("Exception: "+e);
            return Result.fatal("Unknown error");
        }
    }


    public static Result<List<PersonView>> getLikers(Connection c, long topicId){
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
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }
    }
}
