import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Animation extends Application{
Timer timer = new Timer();
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));   
        primaryStage.setScene(new Scene(root));
        Platform.runLater(()->{
            primaryStage.show();
            timer.schedule(exitApp, new Date(System.currentTimeMillis()+5*1000));
        });
    }

    TimerTask exitApp = new TimerTask() {
        public void run() {
            Platform.exit();
        }
    };
}
