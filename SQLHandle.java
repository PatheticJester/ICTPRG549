// Importing packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Map;

public class SQLHandle {
    static Connection connection;
    static String query;
    static PreparedStatement pst;
    static String connectionString;


    // Main for testing
    public static void main(String[] args) {
        try{
            connectionString = "jdbc:sqlserver://DESKTOP-45VAQ6U:1433;DatabaseName=ICTPRG549;integratedSecurity=true;trustServerCertificate=true";
            connection = DriverManager.getConnection(connectionString);
            sqlleaderboard();
        }   catch (SQLException f){
            System.out.println("Error");
            f.printStackTrace();
        }
    }
    
    /*public SQLHandle(){
        // This method connects to the sql server by creating a connectioning string then using the wonderful driver manager. If there is an error it catches it.
        connectionString = "jdbc:sqlserver://DESKTOP-45VAQ6U:1433;DatabaseName=ICTPRG549;integratedSecurity=true;trustServerCertificate=true";
        try{
            connection = DriverManager.getConnection(connectionString);
        }   catch (SQLException f){
            System.out.println("Error");
            f.printStackTrace();
        }
    } */

    public boolean sqlcheckuserexists(String User, String Pass){
        // Request the username and password entered if it's a match return true if it is not correct or no such user exists return false.
        query = String.format("SELECT * FROM [dbo].[User] WHERE Username='%s' AND Password='%s'", User, Pass);
        try{
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.next();
            if(User.equals(rs.getString("Username"))){
                return(true);
            } else{
                return(false);
            }
        } catch(SQLException f){
            return(false);
        }
    }

    public boolean sqlregisteruser(String User, String Pass){
        // Insert user into the system
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);
        if(sqlcheckuser(User) == true){
          return(false);
        }else{
            query = String.format("INSERT INTO [dbo].[User] (UID, Username, Password) VALUES(%d, '%s', '%s') INSERT INTO [dbo].[Game] (GID) VALUES(%d)", rand_int1, User, Pass, rand_int1);
            try{
                pst = connection.prepareStatement(query);
                pst.execute();
                return(true);
            } catch(SQLException f){
                System.out.println("Data entry failed");
                f.printStackTrace();
                return(false);
            }
        }
    }

    public boolean sqlcheckuser(String User){
        // Check the username doesn't already exist
        query = String.format("SELECT * FROM [dbo].[User] WHERE Username='%s'", User);
        try{
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return(true);
            } else{
                return(false);
            }
        } catch(SQLException f){
            System.out.println(f);
            return(false);
        }
    }

    public boolean sqlupdateuser(String User, int Score){
        // Sqlupdate user updates the users highscore
        query = String.format("SELECT [Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID WHERE Username = '%s'",User);
        try{
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.next();
            if(rs.getInt("Highscore") > Score){
                return(false);
            }
        } catch(SQLException f){
            System.out.println(f);
            return(false);
        }

        query = String.format("UPDATE [Game] set Highscore = '%d' FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID WHERE Username = '%s'", Score, User); 
        try{
            pst = connection.prepareStatement(query);
            pst.execute();
            return(true);
        } catch(SQLException f){
            System.out.println(f);
            return(false);
        }     
    }

    public static int sqlselectscore(){
        // Returns the high score
        query = "SELECT [Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID WHERE UID=GID";
        try{
            ArrayList<Integer> scores = new ArrayList<Integer>();
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               scores.add(rs.getInt("Highscore"));
            }
            int Highscore = Collections.max(scores);
            return(Highscore);
        } catch(SQLException f){
            System.out.println(f);
            return 0;
        }
    }

    public static HashMap<String, Integer> sqlleaderboard(){
        // Returns the high score
        query = "SELECT [Username], [Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID";
        try{
            HashMap<String, Integer> Leaderboard = new HashMap<>();
            LinkedHashMap<String, Integer> LeaderboardSorted = new LinkedHashMap<>();
            ArrayList<Integer> scorelist = new ArrayList<>();
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Leaderboard.put(rs.getString("Username"),rs.getInt("Highscore"));
            }
            for (Map.Entry<String, Integer> entry :Leaderboard.entrySet()) {
                scorelist.add(entry.getValue());
            }
            Collections.sort(scorelist); 
            for (int num : scorelist) {
                for (Entry<String, Integer> entry : Leaderboard.entrySet()) {
                    if (entry.getValue().equals(num)) {
                        LeaderboardSorted.put(entry.getKey(), num);
                    }
                }
            }
            System.out.println(LeaderboardSorted);
            return(Leaderboard);
        } catch(SQLException f){
            System.out.println(f);
            return(null);
        }
    }
}