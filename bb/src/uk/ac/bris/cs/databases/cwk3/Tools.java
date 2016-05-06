package uk.ac.bris.cs.databases.cwk3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Tools {

    private static final String usernameToID = 
        "SELECT id FROM Person " +
        "WHERE username=?";

    public static int usernameToID(Connection c, String username){
        try (PreparedStatement pstmt = c.prepareStatement(usernameToID)) {
            pstmt.setString(1, username);
            ResultSet rst = pstmt.executeQuery();
            if(rst.next()){
                return rst.getInt("id");
            }
            else{
                return 0;
            }
        }
        catch (SQLException e) {
            return 0;
        }
    }


}
