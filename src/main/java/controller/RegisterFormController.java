package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnRegisterCustomer(ActionEvent event) throws SQLException {
        String key="#12235435";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);


        String SQL = "INSERT INTO users (username,email,password) VALUES(?,?,?)";

        if (txtPassword.getText().equals(txtPassword.getText())){

            Connection connection = DBConnection.getInstance().getConnection();

            ResultSet resultSet = connection
                    .createStatement()
                    .executeQuery("SELECT * FROM users WHERE email=" + "'" + txtEmail.getText() + "'");

            if (!resultSet.next()){
                User user = new User(
                        txtUsername.getText(),
                        txtEmail.getText(),
                        txtPassword.getText()
                );

                PreparedStatement psTm = connection.prepareStatement(SQL);
                psTm.setString(1,user.getUsername());
                psTm.setString(2,user.getEmail());
                psTm.setString(3,basicTextEncryptor.encrypt(user.getPassword()));
                psTm.executeUpdate();

            }else{
                new Alert(Alert.AlertType.ERROR,"user found!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Check your password!!").show();
        }
    }


}
