/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserverdungeonsandseminars;

/**
 *
 * @author sebastian.braun
 */
public class MSGRequest {
    private int partyID;
    private String idTo;
    private String idFrom;
    private String msg;
    private String toName;
    private String fromName;
    
    public MSGRequest(int partyID, String idTO, String idFrom, String msg, String toName, String fromName){
        this.idFrom = idFrom;
        this.idTo = "@global";
        this.msg = msg;
        this.partyID = partyID;  
        this.toName = toName;
        this.fromName = fromName;
    }

    /**
     * @return the partyID
     */
    public int getPartyID() {
        return partyID;
    }

    /**
     * @return the idTo
     */
    public String getIdTo() {
        return idTo;
    }

    /**
     * @return the idFrom
     */
    public String getIdFrom() {
        return idFrom;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
    @Override
    public String toString(){
        return this.idTo + "," + this.idFrom + "," + this.msg; 
    } 

    /**
     * @return the toName
     */
    public String getToName() {
        return toName;
    }

    /**
     * @return the fromName
     */
    public String getFromName() {
        return fromName;
    }
    
    
}
