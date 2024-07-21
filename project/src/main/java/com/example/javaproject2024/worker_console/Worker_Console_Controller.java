package com.example.javaproject2024.worker_console;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Worker_Console_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnnew;

    @FXML
    private Button btnsave;

    @FXML
    private ListView<String> lstspl;

    @FXML
    private TextField txtadrs;

    @FXML
    private TextField txtmob;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtspl;

    @FXML
    void doClear(ActionEvent event) {
        txtname.setText("");
        txtadrs.setText("");
        txtmob.setText("");
        txtspl.setText("");
    }

    PreparedStatement stmt;
    @FXML
    void doSave(ActionEvent event) {
        //wname,address,mobile,splz
        try {
            stmt=con.prepareStatement("insert into workers values(?,?,?,?)");
            stmt.setString(1,txtname.getText());
            stmt.setString(2,txtadrs.getText());
            stmt.setString(3,txtmob.getText());
            stmt.setString(4,txtspl.getText());
            stmt.executeUpdate();
            ShowMessage("Worker Added Successfully");
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doAddSplz(MouseEvent event) {
        if(event.getClickCount()==2)
        {
            txtspl.appendText(lstspl.getSelectionModel().getSelectedItem()+";");
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
        FillSplz();
    }

    void ShowMessage(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    void FillSplz()
    {
        String splz[]={"Shirt","Coat","Pent","Lehanga","Jacket","Tuxedo","Suit"};
        lstspl.getItems().addAll(splz);
    }
}