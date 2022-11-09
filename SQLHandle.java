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
        connectionString = "jdbc:sqlserver://DESKTOP-45VAQ6U:1433;DatabaseName=ICTPRG549;integratedSecurity=true;trustServerCertificate=true";
        try{
            connection = DriverManager.getConnection(connectionString);
        }   catch (SQLException f){
            System.out.println("Error");
            f.printStackTrace();
        }
    }

    public static void sqlrequest(){
        query = "SELECT [UID],[Username],[Password],[GID],[Highscore] FROM [dbo].[User] INNER JOIN [dbo].[Game] ON [User].UID=[Game].GID";
        try{
        pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
            rs.next();
            System.out.println(rs.getString("UID"));
            System.out.println(rs.getString("Username"));
            System.out.println(rs.getString("Password"));
            System.out.println(rs.getString("GID"));
            System.out.println(rs.getString("Highscore"));
        } catch(SQLException f){
            System.out.println("Request failed");
            f.printStackTrace();
        }
    }

    public static void sqlinsert(String User, String Pass){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);
        query = String.format("INSERT INTO [dbo].[User] (UID, Username, Password) VALUES(%d, '%s', '%s') INSERT INTO [dbo].[Game] (GID) VALUES(%d)", rand_int1, User, Pass, rand_int1);
        try{
        pst = connection.prepareStatement(query);
        pst.execute();
        return;
        } catch(SQLException f){
            System.out.println("Data enry failed");
            f.printStackTrace();
        }
    }
}