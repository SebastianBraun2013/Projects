/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserverdungeonsandseminars;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author sebastian.braun
 */
public class MSGHandler {
    private ArrayList<MSGRequest> msgs = new ArrayList<>();
    
    public MSGHandler(){
        
    }
    
    public void addMsg(String msg, String toName, String fromName){
        String[] msgComponets = msg.split(":")[1].split(",");
        this.msgs.add(new MSGRequest(Integer.parseInt(msgComponets[0]),
                msgComponets[1], msgComponets[2], msgComponets[3], toName, fromName));
    }
        
    public String removeMsg(String playerTo){
        String toReturn = "MSGS:" ;
        Iterator<MSGRequest> a = this.msgs.iterator();
        while(a.hasNext()){
            MSGRequest msg = a.next();
            if(/*msg.getIdTo().toLowerCase().contains(playerTo.toLowerCase()) ||*/ msg.getIdTo().toLowerCase().contains("@global")){
                toReturn = toReturn + "-" + msg.getFromName() + "[" + msg.getMsg() + "]";
            }             
        }
        return toReturn;
    }
    
    @Override
    public String toString(){
        String toReturn = "";
        Iterator<MSGRequest> a = this.msgs.iterator();
        while(a.hasNext()){
            MSGRequest b = a.next();
            toReturn = toReturn + "\n" + b.getMsg();
        }
        return toReturn;
    }
}
