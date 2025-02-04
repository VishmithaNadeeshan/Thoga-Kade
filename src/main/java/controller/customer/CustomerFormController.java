package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {

    public TableView tblCustomer;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;


    @FXML
    void btnAddAction(ActionEvent event) {
        boolean isCustomerAdd = new CustomerController().addCustomer(
                new Customer(
                        txtId.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                )
        );
        if (isCustomerAdd){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added!").show();
        }
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {

    }

    @FXML
    void btnReloadAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {

    }

    @FXML
    void btnViewAction(ActionEvent event) {


    }
    private  void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<Customer>customerObservableList= FXCollections.observableArrayList();
        new CustomerController().getAll().forEach((customer)->{
            customerObservableList.add(customer);
        });
        tblCustomer.setItems(customerObservableList);
    }
}
