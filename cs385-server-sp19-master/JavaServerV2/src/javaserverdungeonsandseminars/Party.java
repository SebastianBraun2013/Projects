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
public class Party {

    private SQLHandler sql;
    private boolean isClosed = false;
    private int id;
    private String partyName;
    private ArrayList<Player> playerList = new <Player>ArrayList();
    private int DMID;
    private MSGHandler msg = new MSGHandler();
    //private Player dm;

    public Party(int id, String name, Player dm, SQLHandler sql) {
        this.id = id;
        this.partyName = name;
        this.playerList.add(dm);
        this.sql = sql;
        this.DMID = dm.getPlayerID();
    }

    public String getMsg(int partyID, String playerTo) {
        return this.msg.removeMsg(playerTo);
    }

    public void addMSG(String msg, String toName, String fromName) {
        this.msg.addMsg(msg, toName, fromName);
    }

    public void removeMSG() {

    }

    public void addPlayer(String name, int playerID) {
        Player player = new Player(name, playerID);
        this.playerList.add(player);
    }

    public Player findPlayer(int id) {
        Iterator<Player> a = this.playerList.iterator();
        while (a.hasNext()) {
            Player b = a.next();
            if (b.getPlayerID() == id) {
                return b;
            }
        }
        if (id == this.getDMID()) {
            return this.playerList.get(0);
        }
        return null;
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void deletePlayer(Player player) {
        if (player.getPlayerID() == this.getDMID()) {
            this.isClosed = true;
            this.sql.closeParty("" + this.id, "" + this.getDMID());

        }
        this.playerList.remove(player);
        this.sql.closePlayer("" + player.getPlayerID());
    }

    public String getDMName() {
        return this.getDm().getPlayerName();
    }

    public int getDMID() {
        return this.DMID;
    }

    public void closeParty() {
        //send close to clients        
        this.isClosed = true;
    }

    public int getNumOfPlayers() {
        return this.playerList.size();
    }

    public void checkPlayerConnection() {
        try{
        Iterator<Player> a = this.playerList.iterator();
        if(!this.isClosed){
        while (a.hasNext()) {
            long timeToCheck = System.currentTimeMillis() - this.playerList.get(0).getLastTimeConnected();
            Player current = a.next();
            System.out.println("Player: " + current.getPlayerID() + ": time since last connection: " + timeToCheck);
            if (timeToCheck > 60000) {
                this.deletePlayer(current);
            }
            if (timeToCheck > 60000) {
                this.isClosed = true;
                this.deletePlayer(current);
            }
        }
        }
        } catch (Exception e){
            //e.printStackTrace();
        }
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * @return the dm
     */
    public Player getDm() {
        if (this.playerList.size() < 1) {
            return null;
        } else {
            return this.playerList.get(0);
        }
    }

}
