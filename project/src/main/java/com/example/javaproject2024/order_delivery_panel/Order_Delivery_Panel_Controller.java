package com.example.javaproject2024.order_delivery_panel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Order_Delivery_Panel_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btndlvr;

    @FXML
    private Button btnno;

    @FXML
    private Label lblBill;

    @FXML
    private ListView<Integer> lstbill;

    @FXML
    private ListView<String> lstdrs;

    @FXML
    private ListView<Integer> lstoid;

    @FXML
    private ListView<Integer> lststs;

    @FXML
    private TextField txtno;

    PreparedStatement stmt;
    int bill=0;
    int i=0;
    @FXML
    void doDeliver(ActionEvent event) {
        if(!lstoid.getItems().isEmpty())
        {
            lblBill.setText(String.valueOf(bill));
            while (!lstoid.getItems().isEmpty())
            {
                try {
                    stmt=con.prepareStatement("update measurements set status=4 where orderid=? and status=3");
                    stmt.setInt(1,lstoid.getItems().get(i));
                    stmt.executeUpdate();
                    lstoid.getItems().remove(i);
                }
                catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
        }
        doFind(event);
    }

    @FXML
    void doFind(ActionEvent event) {
        lststs.getItems().clear();
        lstoid.getItems().clear();
        lstbill.getItems().clear();
        lstdrs.getItems().clear();
        lblBill.setText("");
        if(txtno.getText()!=null)
        {
            try {
                stmt=con.prepareStatement("select * from measurements where mobile=? and status=2 or status=3");
                stmt.setString(1,txtno.getText());
                ResultSet records=stmt.executeQuery();
                while (records.next())
                {
                    lststs.getItems().add(records.getInt("status"));
                    lstoid.getItems().add(records.getInt("orderid"));
                    lstbill.getItems().add(records.getInt("pending"));
                    lstdrs.getItems().add(records.getString("dress"));
                    if(records.getInt("status")==3)
                    {
                        bill+=records.getInt("pending");
                    }
                }
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }

    Connection con;
    @FXML
    void initialize() {
        con= MySQLConnection.doconnect();
        if(con==null)
        {
            System.out.println("Connection Didn't Established");
        }
        else
        {
            System.out.println("Connection Established");
        }
    }

}
