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


    private static final String checkPost =
        "SELECT id FROM Post WHERE Post.id=?";
    private static final String getLikersStatement = 
        "SELECT name, username, stuID " +
        "FROM Person " +
        "JOIN PostLikers ON PostLikers.personID = Person.id " +
        "JOIN Post ON PostLikers.postID = Post.id " +
        "WHERE Post.id=?" +
        "ORDER BY Person.name";


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

        //Check Topic exists
        try (PreparedStatement pstmt = c.prepareStatement(checkPost)) {
            rst = pstmt.executeQuery();
            pstmt.setLong(1, postId);
            if(!rst.next()){
                return Result.failure("Post does not exist");
            }
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }

        //Get likers
        try (PreparedStatement pstmt = c.prepareStatement(getLikersStatement)) {
            rst = pstmt.executeQuery();
            pstmt.setLong(1, postId);
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
