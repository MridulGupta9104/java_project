package com.example.javaproject2024.customer_enrollment;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Customer_Enrollment_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnclear;

    @FXML
    private Button btnenroll;

    @FXML
    private Button btnfetch;

    @FXML
    private Button btnupdate;

    @FXML
    private ComboBox<String> cmbgender;

    @FXML
    private DatePicker dtdob;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtcity;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtno;

    @FXML
    void doClear(ActionEvent event) {
        txtno.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txtcity.setText("");
        cmbgender.getSelectionModel().select(0);
        dtdob.setValue(null);
    }

    PreparedStatement stmt;

    @FXML
    void doEnroll(ActionEvent event) {
        //mobile,cname,address,city,gender,dob,doenroll
        try {
            stmt=con.prepareStatement("insert into customers values(?,?,?,?,?,?,current_date)");
            stmt.setString(1,txtno.getText());
            stmt.setString(2,txtname.getText());
            stmt.setString(3,txtaddress.getText());
            stmt.setString(4,txtcity.getText());
            stmt.setString(5,cmbgender.getSelectionModel().getSelectedItem());
            LocalDate local=dtdob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(6,date);
            stmt.executeUpdate();
            ShowMessage("Customer Added Successfully");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doFetch(ActionEvent event) {
        //mobile,cname,address,city,gender,dob,doenroll
        try{
            stmt=con.prepareStatement("select * from customers where mobile=?");
            stmt.setString(1,txtno.getText());
            ResultSet record= stmt.executeQuery();
            while(record.next())
            {
                String name=record.getString("cname");
                String adrs=record.getString("address");
                String cty=record.getString("city");
                String gndr=record.getString("gender");
                Date dt=record.getDate("dob");
                cmbgender.getSelectionModel().select(gndr);
                txtname.setText(name);
                txtaddress.setText(adrs);
                txtcity.setText(cty);
                dtdob.setValue(dt.toLocalDate());
            }
            btnupdate.setDisable(false);
            btnclear.setDisable(false);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        //mobile,cname,address,city,gender,dob,doenroll
        try
        {
            stmt=con.prepareStatement("update customers set cname=?,address=?,city=?,gender=?,dob=? where mobile=?");
            stmt.setString(1,txtname.getText());
            stmt.setString(2,txtaddress.getText());
            stmt.setString(3,txtcity.getText());
            stmt.setString(4,cmbgender.getValue());
            LocalDate local=dtdob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(5,date);
            stmt.setString(6,txtno.getText());
            stmt.executeUpdate();
            ShowMessage("Record Updated Successfully");
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    Connection con;
    @FXML
    void initialize() {
        con=MySQLConnection.doconnect();
        if(con==null)
        {
            System.out.println("Connection Didn't Established");
        }
        else
        {
            System.out.println("Connection Established");
        }
        FillGender();
        cmbgender.getSelectionModel().select(0);
    }
    void FillGender()
    {
        ArrayList<String> ary=new ArrayList<>();
        ary.add("Select");
        ary.add("Male");
        ary.add("Female");
        ary.add("Transgender");
        ary.add("Prefer Not To Say");
        cmbgender.getItems().addAll(ary);
    }

    void ShowMessage(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
