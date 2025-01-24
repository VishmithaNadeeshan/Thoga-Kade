package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }



    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer viewCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select * from customer where id=" +"'"+ id+"'");
            resultSet.next();
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select * from Customer");

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)

                );
                customerArrayList.add(customer);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerArrayList;
    }

    public ObservableList<String> getCustomerIDs(){
        List<Customer> customerList = getAll();
        ObservableList<String> customerIDList = FXCollections.observableArrayList();

        customerList.forEach(customer -> {
            customerIDList.add(customer.getId());

        });
        return customerIDList;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }
}
