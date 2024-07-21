package com.example.javaproject2024.measurements;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import com.example.javaproject2024.customer_enrollment.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Measurements_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnUpload;

    @FXML
    private ComboBox<String> cmbdress;

    @FXML
    private ComboBox<String> cmbwrkr;

    @FXML
    private DatePicker dtdod;

    @FXML
    private ImageView imgPrev;

    @FXML
    private TextField txtadv;

    @FXML
    private TextField txtbill;

    @FXML
    private TextField txtemail;

    @FXML
    private TextArea txtmsrmnts;

    @FXML
    private TextField txtno;

    @FXML
    private TextField txtpndng;

    @FXML
    private TextField txtprice;

    @FXML
    private TextField txtqty;

    @FXML
    private TextArea txtreq;

    void SendMail(String to,String Subj,String emailBody) {
        String from = "mridulgupta9104@gmail.com"; // sender's email
        final String username = "mridulgupta9104@gmail.com"; // your Gmail address
        final String password = "gymm winl jwly coon"; // your app password

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(Subj);
            message.setText(emailBody);
            Transport.send(message);

            System.out.println("Message Sent Successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void doClear(ActionEvent event) {
        txtno.setText("");
        txtemail.setText("");
        txtqty.setText("");
        txtprice.setText("");
        txtbill.setText("");
        txtadv.setText("");
        txtpndng.setText("");
        txtmsrmnts.setText("");
        txtreq.setText("");
        cmbdress.getSelectionModel().select(0);
        cmbwrkr.getItems().removeAll(cmbwrkr.getItems());
        dtdod.setValue(null);
        imgPrev.setImage(null);
        btnUpdate.setText("Search");
    }

    @FXML
    void doClose(ActionEvent event) {

    }

    PreparedStatement stmt;
    @FXML
    void doSave(ActionEvent event) {
        //orderid,mobile,email,dress,pic,dodel,qty,price,bill,adv,pending,measurements,requirements,worker,doorder,status
        try{
            stmt=con.prepareStatement("insert into measurements(mobile,email,dress,pic,dodel,qty,price,bill,adv,pending,measurements,requirements,worker,doorder,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,current_date,1)",Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,txtno.getText());
            stmt.setString(2,txtemail.getText());
            stmt.setString(3,cmbdress.getSelectionModel().getSelectedItem().toString());
            stmt.setString(4,filepath);
            LocalDate local=dtdod.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(5,date);
            stmt.setInt(6,Integer.parseInt(txtqty.getText()));
            stmt.setInt(7,Integer.parseInt(txtprice.getText()));
            stmt.setInt(8,Integer.parseInt(txtbill.getText()));
            stmt.setInt(9,Integer.parseInt(txtadv.getText()));
            stmt.setInt(10,Integer.parseInt(txtpndng.getText()));
            stmt.setString(11,txtmsrmnts.getText());
            stmt.setString(12,txtreq.getText());
            stmt.setString(13,cmbwrkr.getSelectionModel().getSelectedItem().toString());
            stmt.executeUpdate();
            ResultSet rs=stmt.getGeneratedKeys();
            while(rs.next())
            {
                ShowMessage("Order Placed Successfully! Your Order ID is: "+rs.getInt(1));
                SendMail(txtemail.getText(),"Hello","Your order has been placed successfully! Your Order ID is:"+rs.getInt(1));
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    String numbr="";
    @FXML
    void doUpdate(ActionEvent event) {
        if(btnUpdate.getText().equals("Search"))
        {
            TextInputDialog dialog=new TextInputDialog("");
            dialog.setTitle("Input Order ID");
            dialog.setContentText("Enter Here:");

            Optional<String> num=dialog.showAndWait();

            if(num.isPresent())
            {
                if(num.get().equals(""))
                {
                    ShowMessage("Enter Order ID");
                }
                else
                {
                    try{
                        stmt=con.prepareStatement("select * from measurements where orderid=?");
                        stmt.setInt(1,Integer.parseInt(num.get()));
                        ResultSet record=stmt.executeQuery();
                        while(record.next())
                        {
                            String mob=record.getString("mobile");
                            String em=record.getString("email");
                            String drss=record.getString("dress");
                            String path=record.getString("pic");
                            Date dodd=record.getDate("dodel");
                            Integer qt=record.getInt("qty");
                            Integer prc=record.getInt("price");
                            Integer bl=record.getInt("bill");
                            Integer ad=record.getInt("adv");
                            Integer pnd=record.getInt("pending");
                            String msr=record.getString("measurements");
                            String rq=record.getString("requirements");
                            String wrk=record.getString("worker");
                            txtno.setText(mob);
                            txtemail.setText(em);
                            cmbdress.getSelectionModel().select(drss);
                            if(!path.equals("nopic.jpg"))
                            {
                                filepath=path;
                                imgPrev.setImage(new Image(new FileInputStream(filepath)));
                            }
                            dtdod.setValue(dodd.toLocalDate());
                            txtqty.setText(String.valueOf(qt));
                            txtprice.setText(String.valueOf(prc));
                            txtbill.setText(String.valueOf(bl));
                            txtadv.setText(String.valueOf(ad));
                            txtpndng.setText(String.valueOf(pnd));
                            txtmsrmnts.setText(msr);
                            txtreq.setText(rq);
                            cmbwrkr.getSelectionModel().select(wrk);
                        }
                        btnUpdate.setText("Update");
                        numbr=num.get();
                    }
                    catch(Exception exp)
                    {
                        exp.printStackTrace();
                    }


                }
            }
            else
            {
                ShowMessage("No Input");
            }
        }
        else if(btnUpdate.getText().equals("Update"))
        {
            //orderid,mobile,email,dress,pic,dodel,qty,price,bill,adv,pending,measurements,requirements,worker,doorder,status
            try{
                stmt=con.prepareStatement("update measurements set mobile=?,email=?,dress=?,pic=?,dodel=?,qty=?,price=?,bill=?,adv=?,pending=?,measurements=?,requirements=?,worker=?,status=1 where orderid=?");
                stmt.setString(1,txtno.getText());
                stmt.setString(2,txtemail.getText());
                stmt.setString(3,cmbdress.getSelectionModel().getSelectedItem());
                stmt.setString(4,filepath);
                LocalDate local=dtdod.getValue();
                java.sql.Date date=java.sql.Date.valueOf(local);
                stmt.setDate(5,date);
                stmt.setInt(6,Integer.parseInt(txtqty.getText()));
                stmt.setInt(7,Integer.parseInt(txtprice.getText()));
                stmt.setInt(8,Integer.parseInt(txtbill.getText()));
                stmt.setInt(9,Integer.parseInt(txtadv.getText()));
                stmt.setInt(10,Integer.parseInt(txtpndng.getText()));
                stmt.setString(11,txtmsrmnts.getText());
                stmt.setString(12,txtreq.getText());
                stmt.setString(13,cmbwrkr.getSelectionModel().getSelectedItem());
                stmt.setInt(14,Integer.parseInt(numbr));
                stmt.executeUpdate();
                ShowMessage("Order Updated Successfully");
                btnUpdate.setText("Search");
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }

    String filepath="nopic.jpg";
    @FXML
    void doUpload(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Select Design Pic");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images","*.*"),
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"),
                new FileChooser.ExtensionFilter("*.*","*.*")
        );
        File file=chooser.showOpenDialog(null);
        filepath=file.getAbsolutePath();

        try
        {
            imgPrev.setImage(new Image(new FileInputStream(file)));
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void addWorker(ActionEvent event) {
        cmbwrkr.getItems().removeAll(cmbwrkr.getItems());
        String selectedDress=cmbdress.getSelectionModel().getSelectedItem();
        if(selectedDress!=null) {

            try {
                stmt = con.prepareStatement("select wname from workers where splz like ?");
                stmt.setString(1,"%"+selectedDress+"%");
                ResultSet record = stmt.executeQuery();
                while (record.next()) {
                    cmbwrkr.getItems().add(record.getString("wname"));
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    @FXML
    void enablePPU(ActionEvent event) {
        txtprice.setDisable(false);
    }

    @FXML
    void calcBill(ActionEvent event) {
        txtbill.setText(String.valueOf(Integer.parseInt(txtqty.getText())*Integer.parseInt(txtprice.getText())));
    }

    @FXML
    void calcPndng(ActionEvent event) {
        txtpndng.setText(String.valueOf(Integer.parseInt(txtbill.getText())-Integer.parseInt(txtadv.getText())));
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
        FillDress();
    }

    void ShowMessage(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Dialog");
        alert.setHeaderText("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    void FillDress()
    {
        String drs[]={"Select","Shirt","Coat","Pent","Lehanga","Jacket","Tuxedo","Suit"};
        cmbdress.getItems().addAll(drs);
    }

}