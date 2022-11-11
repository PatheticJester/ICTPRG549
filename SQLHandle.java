// Importing packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class SQLHandle {
    static Connection connection;
    static String query;
    static PreparedStatement pst;
    String connectionString;

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

    public boolean sqlrequest(String User, String Pass){
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

    public boolean sqlinsert(String User, String Pass){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);
        if(sqlcheck(User) == true){
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

    public boolean sqlcheck(String User){
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

    public static boolean sqlout(String User, int score){
        query = "SELECT [UID],[Username],[Password],[GID],[Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID";
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
}