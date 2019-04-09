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
public class DialledListData {
    final private SimpleStringProperty callerNo ; 
    final private SimpleStringProperty receiverNo ;
    final private SimpleStringProperty startingTime ;
    final private SimpleStringProperty endingTime ;
    final private SimpleStringProperty callDuration ;
    final private SimpleStringProperty typeName ;
    
    public DialledListData (String a , String b , String c , String d, String e , String f)
    {
        callerNo = new SimpleStringProperty(a) ; 
        receiverNo = new SimpleStringProperty(b) ; 
        startingTime = new SimpleStringProperty(c) ; 
        endingTime = new SimpleStringProperty(d) ; 
        callDuration = new SimpleStringProperty(e) ; 
        typeName = new SimpleStringProperty(f) ; 
    }
    
    public String getCallerNo()
    {
        return callerNo.get();
    }
    public void setCallerNo(String s)
    {
        callerNo.set(s);
    }
    
    public String getReceiverNo()
    {
        return receiverNo.get();
    }
    public void setReceiverNo(String s)
    {
        receiverNo.set(s);
    }
    
    public String getStartingTime()
    {
        return startingTime.get(); 
    }
    public void setStartingTime(String s)
    {
        startingTime.set(s);
    }
    
    public String getEndingTime()
    {
        return endingTime.get();
    }
    public void setEndingTime(String s)
    {
        endingTime.set(s);
    }
    
    public String getCallDuration()
    {
        return callDuration.get();
    }
    public void setCallDuration(String s)
    {
        callDuration.set(s);
    }
    
    public String getTypeName()
    {
        return typeName.get();
    }
    public void setTypeName(String s){
        typeName.set(s);
    }
}
