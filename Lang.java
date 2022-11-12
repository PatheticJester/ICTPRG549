// Importing packages
import java.awt.Font;

public class Lang{
    // Settings the defaults for this class. Using arrays for when text must change to help optimisation.
    String btnCoinAction;
    String[] OutcomeL;
    String[] Coin1L;
    String[] Coin2L;
    String Settings;
    String Darkchck;
    String Langmenu;
    String Englishchck;
    String Thaichck;
    String Mandarinchck;
    String Score;
    String Highscore;
    Font Font;

    public void setEnglish(){
        // This method sets all properties of Lang to be English
        Font = new Font("Dialog", java.awt.Font.PLAIN, 12);
        btnCoinAction = "Spin!";
        Coin1L = new String[] {"Coin 1:", "Coin 1: Heads", "Coin 1: Tails"};
        Coin2L = new String[] {"Coin 2:", "Coin 2: Heads", "Coin 2: Tails"};
        Settings = "Settings";
        Darkchck = "Dark Mode";
        Langmenu = "Languages";
        Englishchck = "English";
        Thaichck = "Thai";
        Mandarinchck = "Mandarin";
        OutcomeL = new String[] {"You win!", "Draw! Spin again!", "You lose!"};
        Score = "Score:";
        Highscore = String.format("Highscore: %d", SQLHandle.sqlselectscore());
    }

    public void setThai(){
        // This method sets all properties of Lang to be Thai. Note the font change. This is for language compataibilty
        Font = new Font("Tahoma", java.awt.Font.PLAIN, 12);
        btnCoinAction = "หมุน!";
        Coin1L = new String[] {"เหรียญแรก", "เหรียญแรก: หน้า", "เหรียญแรก: กลับด้าน"};
        Coin2L = new String[] {"เหรียญที่สอง", "เหรียญที่สอง: หน้า", "เหรียญที่สอง: กลับด้าน"};
        Settings = "การตั้งค่า";
        Darkchck = "โหมดมืด";
        Langmenu = "ภาษา";
        Englishchck = "ภาษาอังกฤษ";
        Thaichck = "ไทย";
        Mandarinchck = "ชาวจีน";
        OutcomeL = new String[] {"คุณชนะ!", "หมุนอีกครั้ง!", "คุณแพ้!"};
        Score = "คะแนน:";
        Highscore = String.format("คะแนนสูง: %d", SQLHandle.sqlselectscore());
    }

    public void setMandarin(){
        // This method sets all properties of Lang to be Mandarin.
        Font = new Font("Dialog", java.awt.Font.PLAIN, 12);
        btnCoinAction = "旋转！";
        Coin1L = new String[] {"第一枚硬币", "第一枚硬币: 正面", "第一枚硬币: 倒转"};
        Coin2L = new String[] {"第二枚硬币", "第二枚硬币: 正面", "第二枚硬币: 倒转"};
        Settings = "设置";
        Darkchck = "黑暗模式";
        Langmenu = "语言";
        Englishchck = "英语";
        Thaichck = "泰国";
        Mandarinchck = "中国人";
        OutcomeL = new String[] {"你赢了！", "再次旋转！", "你输了！"};
        Score = "分数:";
        Highscore = String.format("高分: %d", SQLHandle.sqlselectscore());
    }
}