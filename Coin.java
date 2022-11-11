// Importing packages
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

    public void Resultout(String user, int runningscore){
        SQLHandle.sqlout(user, runningscore);
    }
}
