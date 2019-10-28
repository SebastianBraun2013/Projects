package javaserverdungeonsandseminars;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicholas.bohm
 */
public class WorkerThread implements Runnable {

    private Server client;
    private SQLHandler sql;
    private PartyController pControl;
    private int thread;

    public WorkerThread(Server client, SQLHandler sql, int thread, PartyController pControl) {
        this.client = client;
        this.sql = sql;
        this.thread = thread;
        this.pControl = pControl;
    }

    @Override
    public void run() {
        Long startTime = System.currentTimeMillis();
        Long endTime = startTime + 10000;
        while (!this.client.inputReady() || (System.currentTimeMillis() == endTime)) {
        }
        
        try{
        String toProcess = this.client.readLine().toLowerCase();
            System.out.println("\n" + "Client Request: " + toProcess + "\n");
        if (toProcess.contains("partylist")) {
            //System.out.println("Partylist request: " + this.thread);
            this.client.sendLine(this.partyList());
        }
        if (toProcess.contains("join")) {
           // System.out.println("join request: " + this.thread);
            this.client.sendLine(this.joinParty(toProcess));
        }
        if (toProcess.contains("playerlist")) {
           // System.out.println("player list request: " + this.thread);
            this.client.sendLine(this.playerList(toProcess));
        }
        if (toProcess.contains("createparty")) {
            //System.out.println("createparty: " + this.thread);
            this.client.sendLine(this.createParty(toProcess));
        }
        if (toProcess.contains("keepalive")) {
            System.out.println("keepalive: " + this.thread);
            this.client.sendLine(this.keepAlive(toProcess));
        }

        if (toProcess.contains("close")) {
          //  System.out.println("Leave Party: " + this.thread);
            this.leaveParty(toProcess);
        }
        if (toProcess.contains("sendmsg")) {
           // System.out.println("sendmsgtoserver: " + this.thread);
            this.client.sendLine(this.sendMsg(toProcess));
        }
        if (toProcess.contains("getmsg")) {
           // System.out.println("receiveingmsgtoclient: " + this.thread);
            this.client.sendLine(this.receiveMsg(toProcess));
        }

        
        } catch(Exception e){
            this.client.sendLine("Crashed");
        }
        //System.out.println("Command: " + toProcess);
        this.close();
    }
    
    private String receiveMsg(String toProcess){
        return this.pControl.sendMsg(toProcess);
    }
    
    private String sendMsg(String toProcess){
        this.pControl.delvierMsg(toProcess);
        return "received";
    }

    private void leaveParty(String toProcess) {
        String[] componets = toProcess.split(":")[1].split(",");
        String playerID = componets[0];
        String partyID = componets[1];
        this.pControl.leaveParty(playerID, partyID);
    }

    private String keepAlive(String toProcess) {
        String[] componets = toProcess.split(":");
        componets = componets[1].split(",");
        System.out.println("Keep alive for: " + Integer.parseInt(componets[0]));
        this.pControl.refreshPlayerTimer(Integer.parseInt(componets[0]), componets[1]);
        return "OK:" + componets[0] + "," + componets[1];
    }

    /**
     *
     * @param toProcess
     * @return
     */
    private String createParty(String toProcess) {
        String[] componets = toProcess.split(":");
        String dmName = componets[1].split(",")[0];
        String partyName = componets[1].split(",")[1];
        return this.pControl.addAParty(dmName, partyName);
    }

    /**
     * processes the playerlist request
     *
     * @param toProcess
     * @return
     */
    private String playerList(String toProcess) {
        String[] componets = toProcess.split(":");
        String partyID = componets[1];
        String error = "error: coult not retrieve players from party "
                + this.sql.getPartyName(partyID);
        String playersConnected = this.sql.GetPlayersConnectedToParty(partyID);
        playersConnected = playersConnected.split(":")[1];
        String[] players = playersConnected.split(", ");
        //System.out.println("PlayersSize: " + players.length);
        String toReturn = "PlayerList:";
        for (int i = 0; i <= players.length-1; i++) {
            
            toReturn = toReturn + this.sql.getPlayerInfo(players[i]) + "-";
        }
        return toReturn;
    }

    /**
     * processes the join request
     *
     * @param toProcess
     * @return
     */
    private String joinParty(String toProcess) {

        String toReturn = "";
        String[] list = toProcess.split(":");
        list = list[1].split(",");
        String playerName = list[1];
        String partyID = list[0];

        return this.pControl.joinAParty(playerName, partyID);
    }

    /**
     * processes the partylist request
     *
     * @return
     */
    private String partyList() {
        String partylist = sql.getPartylist();
        if (!partylist.split(":")[0].contains("error")) {
            return this.sql.getPartylist();
        } else {
            return "Error: failed to get the party list";
        }

    }

    /**
     * closes the socket on thread
     */
    private void close() {
        try {
            this.client.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

}
