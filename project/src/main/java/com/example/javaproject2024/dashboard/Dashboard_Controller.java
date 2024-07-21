package com.example.javaproject2024.dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.javaproject2024.HelloApplication;
import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Dashboard_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart PieChart;

    @FXML
    private Button btnCE;

    @FXML
    private Button btnDO;

    @FXML
    private Button btnME;

    @FXML
    private Button btnMsr;

    @FXML
    private Button btnRAO;

    @FXML
    private Button btnUnlock;

    @FXML
    private Button btnWC;

    @FXML
    private Button btnWrkr;

    @FXML
    private TextField txtIP;

    @FXML
    private TextField txtOD;

    @FXML
    private TextField txtOP;

    @FXML
    private TextField txtPR;

    @FXML
    private PasswordField txtPass;

    PreparedStatement stmt;

    @FXML
    void doLout(MouseEvent event) {
        txtPass.setText("");
        btnCE.setDisable(true);
        btnMsr.setDisable(true);
        btnME.setDisable(true);
        btnDO.setDisable(true);
        btnRAO.setDisable(true);
        btnWrkr.setDisable(true);
        btnWC.setDisable(true);
    }

    @FXML
    void doUnlock(ActionEvent event) {
        try
        {
            stmt=con.prepareStatement("select * from adminpass where passcode=?");
            stmt.setString(1,txtPass.getText());
            ResultSet rs=stmt.executeQuery();
            while (rs.next())
            {
                if(txtPass.getText().equals(rs.getString("passcode")))
                {
                    btnCE.setDisable(false);
                    btnMsr.setDisable(false);
                    btnME.setDisable(false);
                    btnDO.setDisable(false);
                    btnRAO.setDisable(false);
                    btnWrkr.setDisable(false);
                    btnWC.setDisable(false);
                }
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openCE(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customer_enrollmentt/Customer-Enrollment-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openDO(ActionEvent event) {

        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("order_delivery_panell/Order-Delivery-Panel-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openME(ActionEvent event) {

        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("measurements_explorerr/Measurements-Explorer-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openMsr(ActionEvent event) {

        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("measurementss/Measurements-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openRAO(ActionEvent event) {

        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("receive_allocate_orderss/Receive-Allocate-Orders-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openSW(ActionEvent event) {

        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("see_workerss/See-Workers-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void openWC(ActionEvent event) {

        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("worker_consolee/Worker-Console-View.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
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
        FillData();
        FillChart();
    }

    void ShowMessage(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    int op=0,ip=0,pr=0,od=0;
    void FillData()
    {
        try {
            stmt=con.prepareStatement("select * from measurements");
            ResultSet rs=stmt.executeQuery();
            while (rs.next())
            {
                if(1==rs.getInt("status"))
                {
                    op++;
                }
                else if(2==rs.getInt("status"))
                {
                    ip++;
                }
                else if(3==rs.getInt("status"))
                {
                    pr++;
                }
                else if(4==rs.getInt("status"))
                {
                    od++;
                }
            }
            txtOP.setText(String.valueOf(op));
            txtIP.setText(String.valueOf(ip));
            txtPR.setText(String.valueOf(pr));
            txtOD.setText(String.valueOf(od));
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    int Shirt=0, Coat=0,Pent=0,Lehanga=0,Jacket=0,Tuxedo=0,Suit=0;
    void FillChart()
    {
        try {
            stmt=con.prepareStatement("select * from measurements");
            ResultSet rs=stmt.executeQuery();
            while (rs.next())
            {
                if(rs.getString("dress").equals("Shirt"))
                {
                    Shirt++;
                }
                else if(rs.getString("dress").equals("Coat"))
                {
                    Coat++;
                }
                else if(rs.getString("dress").equals("Pent"))
                {
                    Pent++;
                }
                else if(rs.getString("dress").equals("Lehanga"))
                {
                    Lehanga++;
                }
                else if(rs.getString("dress").equals("Jacket"))
                {
                    Jacket++;
                }
                else if(rs.getString("dress").equals("Tuxedo"))
                {
                    Tuxedo++;
                }
                else if(rs.getString("dress").equals("Suit"))
                {
                    Suit++;
                }
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
        ObservableList<PieChart.Data> data=FXCollections.observableArrayList(new PieChart.Data("Shirt",Shirt),new PieChart.Data("Coat",Coat),new PieChart.Data("Pent",Pent),new PieChart.Data("Lehanga",Lehanga),new PieChart.Data("Jacket",Jacket),new PieChart.Data("Tuxedo",Tuxedo),new PieChart.Data("Suit",Suit));
        PieChart.setData(data);
    }
}
