package com.example.javaproject2024.see_workers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class See_Workers_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbSplz;

    @FXML
    private TableView<WorkerBean> tblView;

    PreparedStatement stmt;

    @FXML
    void doExport(ActionEvent event) {
        try {
            writeExcel();
            System.out.println("Exported To Excel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            File file = new File("Users.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Worker Name,Address,Mobile,Specialization\n";
            writer.write(text);
            for (WorkerBean p : tblView.getItems())
            {
                text = p.getWname().trim()+ "," + p.getAddress().trim()+ "," + p.getMobile().trim()+ "," + p.getSplz().trim()+"\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            writer.flush();
            writer.close();
        }
    }
    @FXML
    void doShowAll(ActionEvent event) {
        tblView.getColumns().clear();
        tblView.getItems().clear();
        TableColumn<WorkerBean,String> wnmC=new TableColumn<WorkerBean,String>("Worker Name");
        wnmC.setCellValueFactory(new PropertyValueFactory<>("wname"));
        wnmC.setMinWidth(100);

        TableColumn<WorkerBean,String> adrC=new TableColumn<WorkerBean,String>("Address");
        adrC.setCellValueFactory(new PropertyValueFactory<>("address"));
        adrC.setMinWidth(100);

        TableColumn<WorkerBean,String> mobC=new TableColumn<WorkerBean,String>("Mobile");
        mobC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobC.setMinWidth(100);

        TableColumn<WorkerBean,String> splC=new TableColumn<WorkerBean,String>("Specialization");
        splC.setCellValueFactory(new PropertyValueFactory<>("splz"));
        splC.setMinWidth(100);

        tblView.getColumns().addAll(wnmC,adrC,mobC,splC);
        tblView.setItems(getRecords());
    }

    @FXML
    void showSel(ActionEvent event) {
        tblSel();
    }

    ObservableList<WorkerBean> getRecords()
    {
        ObservableList<WorkerBean> ary= FXCollections.observableArrayList();
        try
        {
            stmt=con.prepareStatement("select * from workers");
            ResultSet records=stmt.executeQuery();
            while(records.next())
            {
                String name=records.getString("wname");
                String adrs=records.getString("address");
                String mob=records.getString("mobile");
                String spl=records.getString("splz");
                ary.add(new WorkerBean(name,adrs,mob,spl));
                //System.out.println(name+" "+adrs+" "+mob+" "+spl);
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        //System.out.println(ary.size());
        return ary;
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

    void FillSplz()
    {
        String splz[]={"Shirt","Coat","Pent","Lehanga","Jacket","Tuxedo","Suit"};
        cmbSplz.getItems().addAll(splz);
    }
    ObservableList<WorkerBean> arr= FXCollections.observableArrayList();
    void tblSel()
    {
        tblView.getColumns().clear();
        tblView.getItems().clear();
        String selsplz=cmbSplz.getSelectionModel().getSelectedItem().toString();
        if(selsplz!=null)
        {
            try {
                stmt = con.prepareStatement("select * from workers where splz like ?");
                stmt.setString(1,"%"+selsplz+"%");
                ResultSet record = stmt.executeQuery();
                while (record.next()) {
                    String name=record.getString("wname");
                    String adrs=record.getString("address");
                    String mob=record.getString("mobile");
                    String spl=record.getString("splz");
                    arr.add(new WorkerBean(name,adrs,mob,spl));
                    //System.out.println(record.getString("wname")+" "+record.getString("address")+" "+record.getString("mobile")+" "+record.getString("splz"));
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        TableColumn<WorkerBean,String> wnmC=new TableColumn<WorkerBean,String>("Worker Name");
        wnmC.setCellValueFactory(new PropertyValueFactory<>("wname"));
        wnmC.setMinWidth(100);

        TableColumn<WorkerBean,String> adrC=new TableColumn<WorkerBean,String>("Address");
        adrC.setCellValueFactory(new PropertyValueFactory<>("address"));
        adrC.setMinWidth(100);

        TableColumn<WorkerBean,String> mobC=new TableColumn<WorkerBean,String>("Mobile");
        mobC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobC.setMinWidth(100);

        TableColumn<WorkerBean,String> splC=new TableColumn<WorkerBean,String>("Specialization");
        splC.setCellValueFactory(new PropertyValueFactory<>("splz"));
        splC.setMinWidth(100);

        tblView.getColumns().addAll(wnmC,adrC,mobC,splC);
        tblView.setItems(arr);
    }
}