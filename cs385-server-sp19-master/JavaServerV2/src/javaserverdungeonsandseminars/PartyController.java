/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserverdungeonsandseminars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebastian.braun
 */
public class PartyController implements Runnable {
   // private List<Party> listOfPartys = Collections.synchronizedList(new <Party>ArrayList());
    private ArrayList listOfPartys = new <Party>ArrayList();
    private SQLHandler sql;
    

    public PartyController(SQLHandler sql) {
        this.sql = sql;
        //this.importParty();
    }
    
    public String sendMsg(String msg){
        int partyID = Integer.parseInt(msg.split(":")[1].split(",")[0]);
        String playerTo = msg.split(":")[1].split(",")[1];
        Party toParty = this.findParty(""+partyID); 
        return toParty.getMsg(partyID, playerTo);        
    }
    
    public void delvierMsg(String msg){
        int partyID = Integer.parseInt(msg.split(":")[1].split(",")[0]);
        String toID = msg.split(":")[1].split(",")[1];
        String fromID = msg.split(":")[1].split(",")[2];
        Party toParty = this.findParty(""+partyID);
        
        toParty.addMSG(msg, "TempName", this.sql.getPlayerInfo(fromID).split(",")[1]);
    }

    public String joinAParty(String playerName, String partyID) {
        String toReturn = "";
        String error = "Error: failed to join: " + this.sql.getPartyName(partyID);
        if (this.sql.addAPlayer(playerName)) {
            toReturn = error;
        }
        if (this.sql.addPlayerToParty(
                this.sql.getPlayerNameInfo(playerName).split(",")[1],
                partyID)) {
            toReturn = error;
        } else {
            toReturn = "OK:" + partyID + "," + this.sql.getPartyName(partyID) + ","
                    + this.sql.getPlayerNameInfo(playerName);
        }
        //might have null pointer
        Party current = this.findParty(partyID);
        int playerID = Integer.parseInt(this.sql.getPlayerNameInfo(playerName).split(",")[1]);

        current.addPlayer(playerName, playerID);
        this.refreshPlayerTimer(playerID, partyID);
        return toReturn;
    }

    public void refreshPlayerTimer(int playerID, String partyID) {
        Party p = this.findParty(partyID);
        try{
            Player player = p.findPlayer(playerID);
            if(p.getDMID() == playerID){
                player = p.getDm();
            }
            System.out.println("Updated playerID: " + playerID);
        player.updateTimeLastConnected();
        } catch(NullPointerException e){
            System.out.println("Null in update player timer");
        }
        
    }

    public String addAParty(String playerName, String partyName) {
        String listOfParts = this.sql.getPartylist();
        if(listOfParts.contains(partyName)){
            return "Error Party allready created";
        } else {
        
        this.sql.addAPlayer(playerName);        
        this.sql.addAParty(partyName, playerName);
        
        int partyID = Integer.parseInt(this.sql.getPartyInfo("PartyName = '" + partyName + "'").split(",")[0]);
        int playerID = Integer.parseInt(this.sql.getPlayerNameInfo(playerName).split(",")[1]);

        Party p = new Party(partyID, partyName, new Player(playerName, playerID), this.sql);
        this.sql.connectPlayer(""+playerID, ""+partyID);
        this.listOfPartys.add(p);
        this.refreshPlayerTimer(playerID, ""+partyID);
        return "PartyCreated:" + partyID + "," + playerID;
        }
    }

    public Party findParty(String id) {
        int partyID = Integer.parseInt(id);
        Iterator<Party> a = this.listOfPartys.iterator();
        while (a.hasNext()) {
            Party b = a.next();
            if (b.getId() == partyID) {
                return b;
            }
        }
        return null;
    }

    public void checkPartyLobby() {
        Iterator<Party> a = this.listOfPartys.iterator();
        while (a.hasNext()) {
            Party currentParty = a.next();
            if (currentParty.getNumOfPlayers() == 0) {
                System.out.println("Closing Party due to no players: " + currentParty.getPartyName());
                this.sql.closeParty("" + currentParty.getId(), "" + currentParty.getDMID());
                this.listOfPartys.remove(a);
            }

        }
    }

    public void checkTimes() {
            Iterator<Party> a = this.listOfPartys.iterator();
            while (a.hasNext()) {
                Party currentParty = a.next();
                currentParty.checkPlayerConnection();                
            }
    }

    public void leaveParty(String playerID, String partyID) {
        try{
        Party p = this.findParty(partyID);
        Player a = p.findPlayer(Integer.parseInt(playerID));
        p.deletePlayer(a);
        }catch(Exception e){
        }
        
    }

    public void checkForClosedPartys() {
        try{
        Iterator<Party> a = this.listOfPartys.iterator();
        while (a.hasNext()) {
            Party currentParty = a.next();
            if (currentParty.isClosed()) {
                System.out.println("Closing party: " + currentParty.getPartyName());
                this.listOfPartys.remove(currentParty);

            }
        }
        }catch(Exception a){
            //concurrency issue
        }
    }
    

    @Override
    public void run() {
        long lastClean = System.currentTimeMillis();
        while (true) {
            //check database for stray players- partys
            
            if (System.currentTimeMillis() - lastClean >= 30000) {
                this.checkTimes();
                this.checkPartyLobby();
                this.checkForClosedPartys();
                lastClean = System.currentTimeMillis();
            }

        }

    }

}
