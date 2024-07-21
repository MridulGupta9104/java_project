package com.example.javaproject2024.measurements_explorer;

import java.sql.Date;

public class MeasurementsBean {
    //orderid,mobile,email,dress,pic,dodel,qty,price,bill,adv,pending,measurements,requirements,worker,doorder,status
    int orderid;
    String mobile;
    String email;
    String dress;
    String pic;
    Date dodel;
    int qty;
    int price;
    int bill;
    int adv;
    int pending;
    String measurements;
    String requirements;
    String worker;
    Date doorder;
    int status;

    public MeasurementsBean() {
    }

    public MeasurementsBean(int orderid, String mobile, String email, String dress, String pic, Date dodel, int qty, int price, int bill, int adv, int pending, String measurements, String requirements, String worker, Date doorder, int status) {
        this.orderid = orderid;
        this.mobile = mobile;
        this.email = email;
        this.dress = dress;
        this.pic = pic;
        this.dodel = dodel;
        this.qty = qty;
        this.price = price;
        this.bill = bill;
        this.adv = adv;
        this.pending = pending;
        this.measurements = measurements;
        this.requirements = requirements;
        this.worker = worker;
        this.doorder = doorder;
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getDodel() {
        return dodel;
    }

    public void setDodel(Date dodel) {
        this.dodel = dodel;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public int getAdv() {
        return adv;
    }

    public void setAdv(int adv) {
        this.adv = adv;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Date getDoorder() {
        return doorder;
    }

    public void setDoorder(Date doorder) {
        this.doorder = doorder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
