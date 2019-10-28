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
public class Player {

    private long timeLastConnected;
    private String PlayerName;
    private int playerID;

    public Player(String name, int ID) {
        this.timeLastConnected = System.currentTimeMillis();
        this.PlayerName = name;
        this.playerID = ID;
    }
    
    public long getLastTimeConnected(){
        return this.getTimeLastConnected();
    }

    /**
     * @return the PlayerName
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     * @return the playerID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * @return the timeLastConnected
     */
    public long getTimeLastConnected() {
        return timeLastConnected;
    }

    /**
     * @param timeLastConnected the timeLastConnected to set
     */
    public void updateTimeLastConnected() {
        this.timeLastConnected = System.currentTimeMillis();
    }
    
    
}
