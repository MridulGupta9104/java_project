package com.example.javaproject2024.measurements_explorer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import com.example.javaproject2024.see_workers.WorkerBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Measurements_Explorer_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnftch;

    @FXML
    private ComboBox<String> cmbOrdrSts;

    @FXML
    private ComboBox<String> cmbWrkr;

    @FXML
    private TableView<MeasurementsBean> tblView;

    @FXML
    private TextField txtno;

    PreparedStatement stmt;
    @FXML
    void doEnb(ActionEvent event) {
        btnftch.setDisable(false);
    }

    @FXML
    void doExport(ActionEvent event) {
        try {
            writeExcel();
            System.out.println("Exported To Excel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String filee="";
    public void writeExcel() throws Exception {
        Writer writer = null;
        try {
            TextInputDialog dialog=new TextInputDialog("");
            dialog.setTitle("Input File Name");
            dialog.setContentText("Enter Here:");

            Optional<String> name=dialog.showAndWait();

            if(name.isPresent()) {
                if (name.get().equals("")) {
                    ShowMessage("Enter File Name");
                }
                else {
                    filee=name.get().toString();
                }
            }
            File file = new File(filee+".csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Order ID,Mobile,Email,Dress,Pic,Date Of Delivery,Quantity,Price Per Unit,Bill,Advance,Pending,Measurements,Requirements,Worker Name,Date Of Order,Status\n";
            writer.write(text);
            for (MeasurementsBean p : tblView.getItems())
            {
                text = p.getOrderid()+ "," + p.getMobile().trim()+ "," + p.getEmail().trim()+ "," + p.getDress().trim()+ "," + p.getPic().trim()+ "," + p.getDodel()+ "," + p.getQty()+ "," + p.getPrice()+ "," + p.getBill()+ "," + p.getAdv()+ "," + p.getPending()+ "," + p.getMeasurements().trim()+ "," + p.getRequirements().trim()+ "," + p.getWorker().trim()+ "," + p.getDoorder()+ "," + p.getStatus()+"\n";
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
    void doFetch(ActionEvent event) {
        tblfetch();
    }

    @FXML
    void doShow(ActionEvent event) {
        tblsel();
    }

    @FXML
    void doShowAll(ActionEvent event) {
        tblView.getColumns().clear();
        tblView.getItems().clear();
        addcolms();
        tblView.setItems(getRecords());
    }

    ObservableList<MeasurementsBean> getRecords()
    {
        ObservableList<MeasurementsBean> ary= FXCollections.observableArrayList();
        try
        {
            stmt=con.prepareStatement("select * from measurements");
            ResultSet records=stmt.executeQuery();
            while(records.next())
            {
                //orderid,mobile,email,dress,pic,dodel,qty,price,bill,adv,pending,measurements,requirements,worker,doorder,status
                int oid=records.getInt("orderid");
                String mob=records.getString("mobile");
                String em=records.getString("email");
                String drs=records.getString("dress");
                String path=records.getString("pic");
                Date dodl=records.getDate("dodel");
                int qt=records.getInt("qty");
                int prc=records.getInt("price");
                int bl=records.getInt("bill");
                int ad=records.getInt("adv");
                int pnd=records.getInt("pending");
                String msrmnt=records.getString("measurements");
                String req=records.getString("requirements");
                String wrk=records.getString("worker");
                Date dor=records.getDate("doorder");
                int sts=records.getInt("status");
                ary.add(new MeasurementsBean(oid,mob,em,drs,path,dodl,qt,prc,bl,ad,pnd,msrmnt,req,wrk,dor,sts));
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
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
        fillWorkers();
        fillsts();
    }

    void fillsts()
    {
        String stss[]={"Order Placed","In Progress","Product Ready","Delivered"};
        cmbOrdrSts.getItems().addAll(stss);
    }
    void fillWorkers()
    {
        try {
            stmt = con.prepareStatement("select distinct worker from measurements");
            ResultSet record=stmt.executeQuery();
            while (record.next())
            {
                cmbWrkr.getItems().add(record.getString("worker"));
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
    }

    ObservableList<MeasurementsBean> arr=FXCollections.observableArrayList();
    int statuss=1;
    void  tblsel()
    {
        tblView.getColumns().clear();
        tblView.getItems().clear();
        addcolms();
        String ordrsts=cmbOrdrSts.getSelectionModel().getSelectedItem();
        String selwrkr=cmbWrkr.getSelectionModel().getSelectedItem();
        if(ordrsts==null && selwrkr!=null)
        {
            try {
                stmt = con.prepareStatement("select * from measurements where worker=?");
                stmt.setString(1,selwrkr);
                ResultSet records=stmt.executeQuery();
                while (records.next())
                {
                    int oid=records.getInt("orderid");
                    String mob=records.getString("mobile");
                    String em=records.getString("email");
                    String drs=records.getString("dress");
                    String path=records.getString("pic");
                    Date dodl=records.getDate("dodel");
                    int qt=records.getInt("qty");
                    int prc=records.getInt("price");
                    int bl=records.getInt("bill");
                    int ad=records.getInt("adv");
                    int pnd=records.getInt("pending");
                    String msrmnt=records.getString("measurements");
                    String req=records.getString("requirements");
                    String wrk=records.getString("worker");
                    Date dor=records.getDate("doorder");
                    int sts=records.getInt("status");
                    arr.add(new MeasurementsBean(oid,mob,em,drs,path,dodl,qt,prc,bl,ad,pnd,msrmnt,req,wrk,dor,sts));
                }
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
        else if(ordrsts!=null && selwrkr==null)
        {
            if(ordrsts=="In Progress")
            {
                statuss=2;
            }
            else if(ordrsts=="Product Ready")
            {
                statuss=3;
            }
            else if(ordrsts=="Delivered")
            {
                statuss=4;
            }
            try {
                stmt = con.prepareStatement("select * from measurements where status=?");
                stmt.setInt(1,statuss);
                ResultSet records=stmt.executeQuery();
                while (records.next())
                {
                    int oid=records.getInt("orderid");
                    String mob=records.getString("mobile");
                    String em=records.getString("email");
                    String drs=records.getString("dress");
                    String path=records.getString("pic");
                    Date dodl=records.getDate("dodel");
                    int qt=records.getInt("qty");
                    int prc=records.getInt("price");
                    int bl=records.getInt("bill");
                    int ad=records.getInt("adv");
                    int pnd=records.getInt("pending");
                    String msrmnt=records.getString("measurements");
                    String req=records.getString("requirements");
                    String wrk=records.getString("worker");
                    Date dor=records.getDate("doorder");
                    int sts=records.getInt("status");
                    arr.add(new MeasurementsBean(oid,mob,em,drs,path,dodl,qt,prc,bl,ad,pnd,msrmnt,req,wrk,dor,sts));
                }
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
        else if(ordrsts!=null && selwrkr!=null)
        {

            if(ordrsts=="In Progress")
            {
                statuss=2;
            }
            else if(ordrsts=="Product Ready")
            {
                statuss=3;
            }
            else if(ordrsts=="Delivered")
            {
                statuss=4;
            }
            try {
                stmt = con.prepareStatement("select * from measurements where worker=? and status=?");
                stmt.setString(1,selwrkr);
                stmt.setInt(2,statuss);
                ResultSet records=stmt.executeQuery();
                while (records.next())
                {
                    int oid=records.getInt("orderid");
                    String mob=records.getString("mobile");
                    String em=records.getString("email");
                    String drs=records.getString("dress");
                    String path=records.getString("pic");
                    Date dodl=records.getDate("dodel");
                    int qt=records.getInt("qty");
                    int prc=records.getInt("price");
                    int bl=records.getInt("bill");
                    int ad=records.getInt("adv");
                    int pnd=records.getInt("pending");
                    String msrmnt=records.getString("measurements");
                    String req=records.getString("requirements");
                    String wrk=records.getString("worker");
                    Date dor=records.getDate("doorder");
                    int sts=records.getInt("status");
                    arr.add(new MeasurementsBean(oid,mob,em,drs,path,dodl,qt,prc,bl,ad,pnd,msrmnt,req,wrk,dor,sts));
                }
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
        tblView.setItems(arr);
    }

    ObservableList<MeasurementsBean> arr1=FXCollections.observableArrayList();
    void tblfetch()
    {
        tblView.getColumns().clear();
        tblView.getItems().clear();
        addcolms();
        if(txtno.getText()!=null)
        {
            try {
                stmt = con.prepareStatement("select * from measurements where mobile=?");
                stmt.setString(1,txtno.getText().toString());
                ResultSet records=stmt.executeQuery();
                while (records.next())
                {
                    int oid=records.getInt("orderid");
                    String mob=records.getString("mobile");
                    String em=records.getString("email");
                    String drs=records.getString("dress");
                    String path=records.getString("pic");
                    Date dodl=records.getDate("dodel");
                    int qt=records.getInt("qty");
                    int prc=records.getInt("price");
                    int bl=records.getInt("bill");
                    int ad=records.getInt("adv");
                    int pnd=records.getInt("pending");
                    String msrmnt=records.getString("measurements");
                    String req=records.getString("requirements");
                    String wrk=records.getString("worker");
                    Date dor=records.getDate("doorder");
                    int sts=records.getInt("status");
                    arr1.add(new MeasurementsBean(oid,mob,em,drs,path,dodl,qt,prc,bl,ad,pnd,msrmnt,req,wrk,dor,sts));
                }
            }
            catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
        tblView.setItems(arr1);
    }

    void ShowMessage(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    void addcolms()
    {
        tblView.getItems().clear();
        tblView.getColumns().clear();
        TableColumn<MeasurementsBean,String> oidC=new TableColumn<MeasurementsBean,String>("Order ID");
        oidC.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        oidC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> mobC=new TableColumn<MeasurementsBean,String>("Mobile");
        mobC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> emC=new TableColumn<MeasurementsBean,String>("Email");
        emC.setCellValueFactory(new PropertyValueFactory<>("email"));
        emC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> drsC=new TableColumn<MeasurementsBean,String>("Dress");
        drsC.setCellValueFactory(new PropertyValueFactory<>("dress"));
        drsC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> pcC=new TableColumn<MeasurementsBean,String>("Pic");
        pcC.setCellValueFactory(new PropertyValueFactory<>("pic"));
        pcC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> ddlC=new TableColumn<MeasurementsBean,String>("Date Of Delivery");
        ddlC.setCellValueFactory(new PropertyValueFactory<>("dodel"));
        ddlC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> qtC=new TableColumn<MeasurementsBean,String>("Quantity");
        qtC.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qtC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> prC=new TableColumn<MeasurementsBean,String>("Price Per Unit");
        prC.setCellValueFactory(new PropertyValueFactory<>("price"));
        prC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> blC=new TableColumn<MeasurementsBean,String>("Bill");
        blC.setCellValueFactory(new PropertyValueFactory<>("bill"));
        blC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> adC=new TableColumn<MeasurementsBean,String>("Advance");
        adC.setCellValueFactory(new PropertyValueFactory<>("adv"));
        adC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> pndC=new TableColumn<MeasurementsBean,String>("Pending");
        pndC.setCellValueFactory(new PropertyValueFactory<>("pending"));
        pndC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> msrC=new TableColumn<MeasurementsBean,String>("Measurements");
        msrC.setCellValueFactory(new PropertyValueFactory<>("measurements"));
        msrC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> reqC=new TableColumn<MeasurementsBean,String>("Requirements");
        reqC.setCellValueFactory(new PropertyValueFactory<>("requirements"));
        reqC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> wrkC=new TableColumn<MeasurementsBean,String>("Worker");
        wrkC.setCellValueFactory(new PropertyValueFactory<>("worker"));
        wrkC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> dooC=new TableColumn<MeasurementsBean,String>("Date Of Order");
        dooC.setCellValueFactory(new PropertyValueFactory<>("doorder"));
        dooC.setMinWidth(100);
        TableColumn<MeasurementsBean,String> stsC=new TableColumn<MeasurementsBean,String>("Status");
        stsC.setCellValueFactory(new PropertyValueFactory<>("status"));
        stsC.setMinWidth(100);
        tblView.getColumns().addAll(oidC,mobC,emC,drsC,pcC,ddlC,qtC,prC,blC,adC,pndC,msrC,reqC,wrkC,dooC,stsC);
    }
}
