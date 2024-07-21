package com.example.javaproject2024.receive_allocate_orders;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Receive_Allocate_Orders_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAllocate;

    @FXML
    private Button btnReceive;

    @FXML
    private Button btnSwitch;

    @FXML
    private ComboBox<String> cmbWrkr;

    @FXML
    private Label lblHeading;

    @FXML
    private ListView<Date> lstDOD;

    @FXML
    private ListView<String> lstDress;

    @FXML
    private ListView<Integer> lstOID;

    Connection con;
    PreparedStatement stmt;
    @FXML
    void doAddtoList(ActionEvent event) {
            lstOID.getItems().removeAll(lstOID.getItems());
            lstDOD.getItems().removeAll(lstDOD.getItems());
            lstDress.getItems().removeAll(lstDress.getItems());
            if (lblHeading.getText().equals("Allocate Orders")) {
                try {
                    stmt = con.prepareStatement("select * from measurements where status=1 and worker=?");
                    stmt.setString(1, cmbWrkr.getSelectionModel().getSelectedItem());
                    ResultSet records = stmt.executeQuery();
                    while (records.next()) {
                        lstOID.getItems().add(records.getInt("orderid"));
                        lstDress.getItems().add(records.getString("dress"));
                        lstDOD.getItems().add(records.getDate("dodel"));
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            } else if (lblHeading.getText().equals("Receive Orders")) {
                try {
                    stmt = con.prepareStatement("select * from measurements where status=2 and worker=?");
                    stmt.setString(1, cmbWrkr.getSelectionModel().getSelectedItem());
                    ResultSet records = stmt.executeQuery();
                    while (records.next()) {
                        lstOID.getItems().add(records.getInt("orderid"));
                        lstDress.getItems().add(records.getString("dress"));
                        lstDOD.getItems().add(records.getDate("dodel"));
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
    }

    @FXML
    void doAllocateAll(ActionEvent event) {
        lstOID.getSelectionModel().select(0);
        while (!lstOID.getItems().isEmpty())
        {
            try{
                stmt=con.prepareStatement("update measurements set status=2 where orderid=?");
                stmt.setInt(1,lstOID.getSelectionModel().getSelectedItem());
                stmt.executeUpdate();
                lstDress.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                lstDOD.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                lstOID.getItems().remove(lstOID.getSelectionModel().getSelectedItem());
                lstDress.getItems().remove(lstDress.getSelectionModel().getSelectedItem());
                lstDOD.getItems().remove(lstDOD.getSelectionModel().getSelectedItem());
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }

    @FXML
    void doReceiveAll(ActionEvent event) {
        lstOID.getSelectionModel().select(0);
        while (!lstOID.getItems().isEmpty())
        {
            try{
                stmt=con.prepareStatement("update measurements set status=3 where orderid=?");
                stmt.setInt(1,lstOID.getSelectionModel().getSelectedItem());
                stmt.executeUpdate();
                lstDress.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                lstDOD.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                lstOID.getItems().remove(lstOID.getSelectionModel().getSelectedItem());
                lstDress.getItems().remove(lstDress.getSelectionModel().getSelectedItem());
                lstDOD.getItems().remove(lstDOD.getSelectionModel().getSelectedItem());
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }

    @FXML
    void doSelect(MouseEvent event) {
        if(event.getClickCount()==2) {
            if (lblHeading.getText().equals("Allocate Orders")) {
                try{
                    stmt=con.prepareStatement("update measurements set status=2 where orderid=?");
                    stmt.setInt(1,lstOID.getSelectionModel().getSelectedItem());
                    stmt.executeUpdate();
                    lstDress.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                    lstDOD.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                    lstOID.getItems().remove(lstOID.getSelectionModel().getSelectedItem());
                    lstDress.getItems().remove(lstDress.getSelectionModel().getSelectedItem());
                    lstDOD.getItems().remove(lstDOD.getSelectionModel().getSelectedItem());
                }
                catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
            else if (lblHeading.getText().equals("Receive Orders")) {
                try{
                    stmt=con.prepareStatement("update measurements set status=3 where orderid=?");
                    stmt.setInt(1,lstOID.getSelectionModel().getSelectedItem());
                    stmt.executeUpdate();
                    lstDress.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                    lstDOD.getSelectionModel().select(lstOID.getSelectionModel().getSelectedIndex());
                    lstOID.getItems().remove(lstOID.getSelectionModel().getSelectedItem());
                    lstDress.getItems().remove(lstDress.getSelectionModel().getSelectedItem());
                    lstDOD.getItems().remove(lstDOD.getSelectionModel().getSelectedItem());
                }
                catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
        }
    }

    @FXML
    void doSwitch(ActionEvent event) {
        if(lblHeading.getText().equals("Allocate Orders"))
        {
            lblHeading.setText("Receive Orders");
            btnAllocate.setDisable(true);
            btnReceive.setDisable(false);
            btnSwitch.setText("Allocate Orders");
            FillWorkers();
        }
        else if(lblHeading.getText().equals("Receive Orders"))
        {
            lblHeading.setText("Allocate Orders");
            btnReceive.setDisable(true);
            btnAllocate.setDisable(false);
            btnSwitch.setText("Receive Orders");
            FillWorkers();
        }
    }

    @FXML
    void initialize() {
        con = MySQLConnection.doconnect();
        if (con == null) {
            System.out.println("Connection Didn't Established");
        } else {
            System.out.println("Connection Established");
        }
        FillWorkers();
    }

    void ShowMessage(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    void FillWorkers()
    {
        cmbWrkr.getItems().removeAll(cmbWrkr.getItems());
        lstOID.getItems().removeAll(lstOID.getItems());
        lstDOD.getItems().removeAll(lstDOD.getItems());
        lstDress.getItems().removeAll(lstDress.getItems());
        if(lblHeading.getText().equals("Allocate Orders"))
        {
            try {
                stmt = con.prepareStatement("select distinct worker from measurements where status=1");
                ResultSet record=stmt.executeQuery();
                while(record.next())
                {
                    cmbWrkr.getItems().add(record.getString("worker"));
                }
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }
        else if(lblHeading.getText().equals("Receive Orders"))
        {
            try {
                stmt = con.prepareStatement("select distinct worker from measurements where status=2");
                ResultSet record=stmt.executeQuery();
                while(record.next())
                {
                    cmbWrkr.getItems().add(record.getString("worker"));
                }
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }
}
