// Importing packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.Map;

public class SQLHandle {
    // This constructor instalises everything that will be needed by the SQLHandle object
    static Connection connection;
    static String query;
    static PreparedStatement pst;
    static String connectionString;
    
    public SQLHandle(){
        // This method connects to the sql server by creating a connectioning string then using the wonderful driver manager. If there is an error it catches it.
        connectionString = "jdbc:sqlserver://DESKTOP-45VAQ6U:1433;DatabaseName=ICTPRG549;integratedSecurity=true;trustServerCertificate=true";
        try{
            connection = DriverManager.getConnection(connectionString);
        }   catch (SQLException f){
            System.out.println("Error");
            f.printStackTrace();
        }
    }

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
        // Insert user into the system. Note that we use excute instead of executeQuery. That's because there is no need to manipulate a resultset
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

    public void sqlupdateuser(String User, int Score){
        // Sqlupdateuser updates the users highscore
        query = String.format("SELECT [Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID WHERE Username = '%s'",User);
        try{
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.next();
            if(rs.getInt("Highscore") > Score){
                return;
            }
        } catch(SQLException f){
            System.out.println(f);
            return;
        }

        query = String.format("UPDATE [Game] set Highscore = '%d' FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID WHERE Username = '%s'", Score, User); 
        try{
            pst = connection.prepareStatement(query);
            pst.execute();
            return;
        } catch(SQLException f){
            System.out.println(f);
            return;
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

    public String[] sqlleaderboard(){
        /* Creates a HashMap, A linkedHashMap and an array list. The hashmap is first used to be populated with the Username and correlating highscore.
         * The array is used next to obtain every value (score) within Leaderboard. The scorelist is then sorted into highest to lowest.
         * Then for every number (score) within scroelist and for every entry within Leader board it checks to see if the leaderboards score is
         * equal to the interger number. If it is then this Entry is placed into the Linkedhashmap along with the number (score).
         * It then needs to cast the object[] to string[] for us in GameGui. It does this with a for loop. 
         * For every obejext in object array StringArray[index of current object] is equal to current object to a string
         * It then returns an object array with the entry set. For prosperity's sake, below is the for loop in a different format
         * for(int i = 0; i < ObjectArray.length; i++){
         *      StringArray[i] = ObjectArray[i].toString();
         * }
        */
        query = "SELECT [Username], [Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID";
        try{
            HashMap<String, Integer> Leaderboard = new HashMap<>();
            ArrayList<Integer> scorelist = new ArrayList<>();
            LinkedHashMap<String, Integer> LeaderboardSorted = new LinkedHashMap<>();
            Object[] ObjectArray;
            String[] StringArray;
            int x = 0;
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Leaderboard.put(rs.getString("Username"),rs.getInt("Highscore"));
            }
            for (Map.Entry<String, Integer> entry : Leaderboard.entrySet()) {
                scorelist.add(entry.getValue());
            }
            Collections.sort(scorelist, Collections.reverseOrder()); 
            for (int num : scorelist) {
                for (Entry<String, Integer> entry : Leaderboard.entrySet()) {
                    if (entry.getValue().equals(num)) {
                        LeaderboardSorted.put(entry.getKey(), num);
                    }
                }
            }
            ObjectArray = LeaderboardSorted.entrySet().toArray();
            StringArray = new String[ObjectArray.length];
            for(Object Score : ObjectArray){
                if(x >= 10){
                    break;
                }
                StringArray[Arrays.asList(ObjectArray).indexOf(Score)] = Score.toString();
                x++;
            }
            return(StringArray);
        } catch(SQLException f){
            System.out.println(f);
            return(null);
        }
    }
}