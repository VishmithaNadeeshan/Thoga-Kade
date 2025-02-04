package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import controller.item.ItemController;
import db.DBConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.TM.CartTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    public JFXTextField txtOrderID;
    @FXML
    private JFXComboBox cmbCustomerID;

    @FXML
    private JFXComboBox cmbItemCode;

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
    private TableView<CartTM> tblOredrDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtQTY;

    @FXML
    private JFXTextField txtStock;

    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {


        String code = cmbItemCode.getValue().toString();
        String description = txtDescription.getText();
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQTY.getText());
        Double total = qty*unitPrice;

       cartTMS.add(new CartTM(code, description, unitPrice, qty, total));

        tblOredrDetails.setItems(cartTMS);

        calcNetTotal();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String date = lblDate.getText();
        String customerId = cmbCustomerID.getValue().toString();

        List<OrderDetail>orderDetails = new ArrayList<>();

        cartTMS.forEach(CartTM -> {
           orderDetails.add(
                   new OrderDetail(
                           orderID,
                           CartTM.getItemCode(),
                           CartTM.getQuantityOnHand(),
                           CartTM.getUnitPrice()

                   )
           );
        });

        Order order = new Order(orderID, date, customerId, orderDetails);
        boolean b = false;
        try {
            b = new OrderController().placeOrder(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (b){
            new Alert(Alert.AlertType.INFORMATION,"Order placed!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Order not placed!").show();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        setDateAndTime();
        loadCustomerIDs();
        loadItemsCode();

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                SearchCustomerData(newValue.toString());
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                searchItemData(newValue.toString());
            }
        });
    }

    private void loadItemsCode() {
        cmbItemCode.setItems(new ItemController().getItemCodes());
    }

    private void searchItemData(String code) {
       Item  item = new ItemController().searchItem(code);
       txtStock.setText(item.getQuantityOnHand().toString());
       txtDescription.setText(item.getItemDescription());
       txtUnitPrice.setText(item.getUnitPrice().toString());

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    private void calcNetTotal(){

        Double netTotal = 0.0;

        for (CartTM tm : cartTMS){
            netTotal += tm.getTotal();


        }

        lblTotal.setText(netTotal.toString());




    }


    public void btnCommitOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
