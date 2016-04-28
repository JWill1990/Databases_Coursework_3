package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.PersonView;

public class CheckExists {


    private static final String checkPost =
        "SELECT id FROM Post WHERE Post.id=?";
    private static final String checkTopic =
        "SELECT id FROM Topic WHERE Topic.id=?";
    private static final String checkUsername =
        "SELECT id FROM Person WHERE Person.username=?";



    public static boolean post(Connection c, long postId) {
        try (PreparedStatement pstmt = c.prepareStatement(checkPost)) {
            pstmt.setLong(1, postId);
            ResultSet rst = pstmt.executeQuery();
            if(rst.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            return false;
        }
    }

    public static boolean topic(Connection c, long topicId) {
        try (PreparedStatement pstmt = c.prepareStatement(checkTopic)) {
            pstmt.setLong(1, topicId);
            ResultSet rst = pstmt.executeQuery();
            if(rst.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            return false;
        }
    }

    public static boolean username(Connection c, String username) {
        try (PreparedStatement pstmt = c.prepareStatement(checkUsername)) {
            pstmt.setString(1, username);
            ResultSet rst = pstmt.executeQuery();
            if(rst.next()){
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            return false;
        }
    }

}
