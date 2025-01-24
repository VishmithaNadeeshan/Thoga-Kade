package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import model.Customer;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    @FXML
    private JFXComboBox cmbCustomerID;

    @FXML
    private JFXComboBox<?> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<?> tblOredrDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQTY;

    @FXML
    private JFXTextField txtStock;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        setDateAndTime();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDateAndTime();
        loadCustomerIDs();

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SearchCustomerData(newValue.toString());
            }
        });
    }

    private void SearchCustomerData(String customerId) {
        Customer customer = new CustomerController().viewCustomer(customerId);
        txtCustomerName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
    }

    private void loadCustomerIDs() {
        ObservableList<String> customerIDs = new CustomerController().getCustomerIDs();
        cmbCustomerID.setItems(customerIDs);
    }

    private void setDateAndTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String format = dateFormat.format(date);
        lblDate.setText(format);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,e ->{
                    LocalDateTime now = LocalDateTime.now();
                    lblTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1),e ->{})
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


}
