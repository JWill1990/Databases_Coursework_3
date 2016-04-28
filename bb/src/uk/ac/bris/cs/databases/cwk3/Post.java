package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.PersonView;

public class Post {


    private static final String getLikersStatement = 
        "SELECT name, username, stuID " +
        "FROM Person " +
        "JOIN PostLikers ON PostLikers.personID = Person.id " +
        "JOIN Post ON PostLikers.postID = Post.id " +
        "WHERE Post.id=?" +
        "ORDER BY Person.name";

    private static final String createPostSQL = 
        "INSERT INTO Post values (?,?,?,?,?)";



    /**
     * Create a post in an existing topic.
     * @param topicId - the id of the topic to post in. Must refer to
     * an existing topic.
     * @param username - the name under which to post; user must exist.
     * @param text - the content of the post, cannot be empty.
     * @return success if the post was made, failure if any of the preconditions
     * were not met and fatal if something else went wrong.
     */
    public static Result createPost(Connection c, long topicId, String username, String text) {

        if(!CheckExists.topic(c, topicId)){
            return Result.failure("Topic does not exist");
        }
        if(!CheckExists.username(c, username)){
            return Result.failure("Username does not exist");
        }
        try (PreparedStatement pstmt = c.prepareStatement(createPostSQL)) {
            pstmt.setLong(2, topicId);
            pstmt.setString(4, text);

            pstmt.setLong(1, 1);
            pstmt.setString(3, username);
            pstmt.setLong(5, 1);

            pstmt.executeQuery();
            return Result.success();
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }
    }




    /**
     * Get all people who have liked a particular post, ordered by name
     * alphabetically.
     * @param postId The post id. Must exist.
     * @return Success (even if the list is empty) if the post exists,
     * failure if it does not, fatal in case of database errors.
     */
    public static Result<List<PersonView>> getLikers(Connection c, long postId){
        ResultSet rst;
        ArrayList<PersonView> personList = new ArrayList<PersonView>();

        if(!CheckExists.post(c, postId)){
            return Result.failure("Post does not exist");
        }

        //Get likers
        try (PreparedStatement pstmt = c.prepareStatement(getLikersStatement)) {
            pstmt.setLong(1, postId);
            rst = pstmt.executeQuery();
            while(rst.next()) {
                String name = rst.getString("name").trim();
                String username=rst.getString("username").trim();
                String stuId=rst.getString("stuId").trim();                
                personList.add(new PersonView(name, username, stuId));
            }
            return Result.success(personList);
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }
    }
}
