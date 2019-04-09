/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tele.communication.system.application;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author HEISENBERG
 */
public class SMSData {

    final private SimpleStringProperty SMS_ID;
    final private SimpleStringProperty SenderNo;
    final private SimpleStringProperty ReceiverNo;
    final private SimpleStringProperty SendingTime;
    final private SimpleStringProperty typeName;

    public SMSData(String a, String b, String c, String d, String e) {
        SMS_ID = new SimpleStringProperty(a);
        SenderNo = new SimpleStringProperty(b);
        ReceiverNo = new SimpleStringProperty(c);
        SendingTime = new SimpleStringProperty(d);
        typeName = new SimpleStringProperty(e);
    }

    public String getSMS_ID() {
        return SMS_ID.get();
    }

    public void setSMS_ID(String s) {
        SMS_ID.set(s);
    }

    public String getSenderNo() {
        return SenderNo.get();
    }

    public void setSenderNo(String s) {
        SenderNo.set(s);
    }

    public String getReceiverNo() {
        return ReceiverNo.get();
    }

    public void setReceiverNo(String s) {
        ReceiverNo.set(s);
    }

    public String getSendingTime() {
        return SendingTime.get();
    }

    public void setSendingTime(String s) {
        SendingTime.set(s);
    }

    public String getTypeName() {
        return typeName.get();
    }

    public void setTypeName(String s) {
        typeName.set(s);
    }
}
