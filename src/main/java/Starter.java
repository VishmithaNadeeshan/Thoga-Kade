import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

public class Starter extends Application {
    public static void main(String[] args) {
        String password="vishmitha123";
        String key="#12235435";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String encrypt = basicTextEncryptor.encrypt(password);
        System.out.println("Encrypt Password : "+encrypt);

        String decrypt = basicTextEncryptor.decrypt(encrypt);
        System.out.println("Dycrypt Password : "+decrypt);

        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/login.fxml"))));
        stage.show();
    }

}
