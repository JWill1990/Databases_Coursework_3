package uk.ac.bris.cs.databases.cwk3;

import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class Person {

    private final static String getUserStatement =
        "SELECT name,username FROM Person";
    private final static String getPersonViewsStatement =
        "SELECT name, username, stuId " +
        "FROM Person WHERE username = ?";


    public static  Result<Map<String, String>> getUsers(Connection c){ 
        PreparedStatement pstmt;
        ResultSet rst;
        Map<String, String> maps=new HashMap<String,String>();  //Maps
        try {
            pstmt = c.prepareStatement(getUserStatement);
            rst=pstmt.executeQuery();
            System.out.println("test");
            while(rst.next()){
                String name=rst.getString("name").trim();
                String username=rst.getString("username").trim();
                System.out.println(name+","+username);
                System.out.println(rst.getRow());
                maps.put(name,username);
            }

            /*  condition to check if the map/databse is empty*/
            if(!maps.isEmpty()){
                return Result.success(maps);
            }
            else{
                return Result.failure("There are no user for this table");
            }
        }
        catch (SQLException e) {
            return Result.fatal("Unknown error");
        }
    }

    public static  Result<PersonView> getPersonView(Connection c, String uname){
        ResultSet rst;
        /*try with statement closes closes the recources after it is done*/
        try (PreparedStatement pstmt = c.prepareStatement(getPersonViewsStatement)) {
            pstmt.setString(1, uname);
            rst = pstmt.executeQuery();
            if (rst.next()) {
                String Name=rst.getString("name").trim();
                String Username=rst.getString("username").trim();
                String stuId=rst.getString("stuId").trim();
                return Result.success(new PersonView(Name,Username,stuId));
            }
            else {
                return Result.failure("There are no user for this table");
            }  
        }
        catch (SQLException e) {
            return Result.fatal("exception - " + e);
        }
        //return Result.fatal("No error ");
    }



}
