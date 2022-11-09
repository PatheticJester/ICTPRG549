// Importing packages
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Coin{
    // Object coin constructor
    int coin;

    public Coin(){
        // Coin constructor. Generates random numbers to coin 1 and coin 2 adds them together for the final result.
        int coin1 = new Random().nextInt(2);
        int coin2 = new Random().nextInt(2);
        coin = coin1 + coin2;
    }

    public void Resultout(String result, int runningscore){
        // Resultout gets the current time for the save file. It takes the result handed to it by Main and appends it to a txt
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        try {
            // Creates an object file writer and printwriter. Unfortunately I was unable to make printwriter append. Hence the double.
            final FileWriter fileWriter = new FileWriter("SaveFile.txt", true);
            final PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(String.format("%s: %s: Running Score %s", dtf.format(now), result, runningscore));
            printWriter.close();
            } catch (final IOException e) {
               e.printStackTrace();
         }
    }
}
