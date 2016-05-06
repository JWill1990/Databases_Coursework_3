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

    private final static String addPerson=
    	"INSERT INTO Person values (null,?,?,?)";

    /**
     * Get a list of all users in the system as a map username -> name.
     * @return A map with one entry per user of the form username -> name
     * (note that usernames are unique).
     */
    public static  Result<Map<String, String>> getUsers(Connection c){
        PreparedStatement pstmt;
        ResultSet rst;
        Map<String, String> maps=new HashMap<String,String>();  //Maps
        try {
            pstmt = c.prepareStatement(getUserStatement);
            rst=pstmt.executeQuery();
            while(rst.next()){
                String name=rst.getString("name").trim();
                String username=rst.getString("username").trim();
                System.out.println(name+","+username);
                System.out.println(rst.getRow());
                maps.put(username,name);
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

    /**
     * Get a PersonView for the person with the given username.
     * @param username - the username to search for, cannot be empty.
     * @return If a person with the given username exists, a fully populated
     * PersonView. Otherwise, failure (or fatal on a database error).
     */
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

    public static Result addNewPerson(Connection c, String name, String username, String studentId ){
        try(PreparedStatement pstmt=c.prepareStatement(addPerson)){
            pstmt.setString(1,username);
            pstmt.setString(2,name);
            pstmt.setString(3,studentId);

            if(!CheckExists.username(c, username)){
                if(TestValidInput.Validator(name) && TestValidInput.Validator(username) && TestValidInput.Validator(studentId)){
                    pstmt.executeUpdate();
                    c.commit();
                    return Result.success();
                }
                else{
                    return Result.failure("Invalid String used");
                }
            }
            else{
                return Result.failure("User Exists");
            }
        }
        catch(Exception e){
            System.out.print("Excpetion is "+e);
            return Result.fatal("Unexpected error");
        }
    }


}
