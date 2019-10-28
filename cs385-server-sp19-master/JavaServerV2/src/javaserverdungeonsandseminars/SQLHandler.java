package javaserverdungeonsandseminars;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebastian.braun
 */
public class SQLHandler {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "java";
    static final String PASS = "java";

    static Connection conn = null;
    static Statement stmt = null;

    //sql commands
    private String createDB = "CREATE DATABASE DUNGEONS_AND_SEMINARS;";
    private String moveToDB = "USE DUNGEONS_AND_SEMINARS;";
    private String CreateTablePartyList = "CREATE TABLE PartyList (PartyID "
            + "int NOT NULL AUTO_INCREMENT, PartyName varchar(255) NOT NULL,"
            + " DMID int NOT NULL, PRIMARY KEY(PartyId), FOREIGN KEY(DMID) "
            + "REFERENCES Players(PlayerID));";
    private String CreateTablePlayer = "CREATE TABLE Players(PlayerID "
            + "int NOT NULL AUTO_INCREMENT, PlayerName varchar(255) NOT NULL,"
            + " PRIMARY KEY(PlayerID));";
    private String CreateTablePlayerPartyConnector = "CREATE TABLE "
            + "PlayerPartyConnect(PlayerID int NOT NULL, PartyID int "
            + "NOT NULL, PRIMARY KEY(PlayerID, PartyID), FOREIGN KEY(PlayerID) REFERENCES Players(PlayerID),"
            + "FOREIGN KEY(PartyID) REFERENCES PartyList(PartyID))";

    public SQLHandler() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);

            this.stmt = this.conn.createStatement();

            //check if database exists
            ResultSet results = this.conn.getMetaData().getCatalogs();
            try {
                this.stmt.executeUpdate(this.createDB);
            } catch (Exception e) {
                System.out.println("Database already exists");
            }

            this.stmt.executeUpdate(this.moveToDB);
            //creates Players table
            try {
                this.stmt.executeUpdate(this.CreateTablePlayer);
            } catch (Exception e) {
                System.out.println("Player Table already made");
            }

            //creates PartyList table
            try {
                this.stmt.executeUpdate(this.CreateTablePartyList);
            } catch (Exception e) {
                System.out.println("PartyList Table already made");
            }

            //creates PlayersPartyConnector table
            try {
                this.stmt.executeUpdate(this.CreateTablePlayerPartyConnector);
            } catch (Exception e) {
                System.out.println("PlayerPartyConnector Table already made");
            }
            this.cleanDataBase();

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void connectPlayer(String playerID, String partyID) {
        String sql = "INSERT INTO PlayerPartyConnect "
                + "(PlayerID, PartyID) Values((SELECT PlayerID FROM Players "
                + "WHERE PlayerID = '" + playerID + "'), (SELECT PartyID FROM "
                + "PartyList WHERE PartyID = '" + partyID + "'));";
        try {
            this.stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cleanDataBase() {
        String cleanPlayerPartConnect = "DELETE FROM PlayerPartyConnect";
        String cleanParty = "DELETE FROM PartyList";
        String cleanPlayers = "DELETE FROM Players";
        try {

            this.stmt.execute(cleanPlayerPartConnect);
            this.stmt.execute(cleanParty);
            this.stmt.execute(cleanPlayers);

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void closeParty(String partyID, String DMID) {
        String sql3 = "DELETE FROM PlayerPartyConnect WHERE PartyID ='" + partyID + "';";
        String sql = "DELETE FROM PartyList WHERE PartyID ='" + partyID + "';";
        String sql2 = "DELETE FROM Players WHERE PlayerID ='" + DMID + "';";
        try {
            this.stmt.execute(sql3);

            this.stmt.execute(sql);
            this.stmt.execute(sql2);
        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closePlayer(String playerID) {
        String sql = "DELETE FROM PlayerPartyConnect WHERE PlayerID = '" + playerID + "';";
        String sql2 = "DELETE FROM Players WHERE PlayerID = '" + playerID + "';";
        try {
            this.stmt.execute(sql);
            this.stmt.execute(sql2);

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * adds a player to the party
     *
     * @param playerId
     * @param partyId
     * @return true if there was a error false if sucessful
     */
    public boolean addPlayerToParty(String playerId, String partyId) {
            String sql = "INSERT INTO PlayerPartyConnect "
                    + "(PlayerID, PartyID) Values((SELECT PlayerID FROM Players "
                    + "WHERE PlayerID = '" + playerId + "'), (SELECT PartyID FROM "
                    + "PartyList WHERE PartyID = '" + partyId + "'));";
            String sqlCheck = "SELECT PlayerID, PartyID FROM PlayerPartyConnect WHERE PlayerID = '" + playerId + "' AND PartyID = '" + partyId + "';";

            try {
                ResultSet rs = this.stmt.executeQuery(sqlCheck);
                if (rs.next()) {
                    return true;
                }
                this.stmt.executeUpdate(sql);
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(SQLHandler.class.getName()).log(Level.SEVERE, null, ex);
                return true;
            }
    }

    /**
     * gets players in a party
     *
     * @param party
     * @return string of players "Players for PartyID partyID: ID, ID, ID
     */
    public String GetPlayersConnectedToParty(String party) {
        String toReturn = "Players for PartyID " + party + ": ";
        String sql = "SELECT PlayerID FROM PlayerPartyConnect "
                + "WHERE PartyID = '" + party + "';";
        try {
            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                toReturn = toReturn + rs.getInt("PlayerID") + ", ";

            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("PartyConnect: " + toReturn);
        return toReturn;
    }

    /**
     * adds a new party to the party table
     *
     * @param name
     * @param DMName
     */
    public void addAParty(String name, String DMName) {
        String sql = "INSERT INTO PartyList "
                + "(PartyName, DMID) Values('" + name + "',"
                + " (SELECT PlayerID FROM Players WHERE PlayerName = '" + DMName + "'));";

        try {
            this.stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds a player to the players table
     *
     * @param name
     * @return true if failed, false if sucessful
     */
    public boolean addAPlayer(String name) {
        String sql = "INSERT INTO Players "
                + "(PlayerName) Values('" + name + "');";
        try {
            this.stmt.executeUpdate(sql);
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }

    /**
     * gets the selected players name and id from database
     *
     * @param where
     * @return Name,ID
     */
    public String getPlayerInfo(String where) {
        String toReturn = "";
        String sql = "SELECT PlayerID, PlayerName FROM Players WHERE PlayerID = '" + where + "';";
        try {
            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("PlayerID");
                String Name = rs.getString("PlayerName");
                toReturn = id + "," + Name;

            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    /**
     * gets the selected players name and id from database
     *
     * @param where
     * @return Name,ID
     */
    public String getPlayerNameInfo(String where) {
        String toReturn = "";
        String sql = "SELECT PlayerID, PlayerName FROM Players WHERE PlayerName = '" + where + "';";
        try {
            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("PlayerID");
                String Name = rs.getString("PlayerName");
                toReturn = Name + "," + id;

            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    /**
     * gets the party info for selected party
     *
     * @param where
     * @return ID, name, DMID
     */
    public String getPartyInfo(String where) {
        String toReturn = "";
        String sql = "SELECT PartyID, PartyName, DMID FROM PartyList WHERE " + where + ";";
        try {

            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                int PartyId = rs.getInt("PartyID");
                String PartyName = rs.getString("PartyName");
                int DMID = rs.getInt("DMID");

                toReturn = PartyId + "," + PartyName + "," + DMID;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    /**
     * Gets the party name of the selected party
     *
     * @param id
     * @return name
     */
    public String getPartyName(String id) {
        String toReturn = "";
        String sql = "SELECT PartyID, PartyName FROM PartyList WHERE PartyID = '" + id + "';";
        try {

            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                String PartyName = rs.getString("PartyName");
                toReturn = PartyName;

            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

    /**
     * gets the party list from party table
     *
     * @return
     */
    public String getPartylist() {
        String toReturn = "PartyList: ";
        try {
            String sql = "SELECT PartyID, PartyName FROM PartyList;";
            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                int partyId = rs.getInt("PartyID");
                String partyName = rs.getString("PartyName");
                toReturn = toReturn + ";" + partyId + "," + partyName;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
            toReturn = "error: failed to gather partylist";
        }
        return toReturn + ";";

    }

    //gets the player list of all players
    public String getPlayerList() {
        String toReturn = "PlayerList: ";
        try {
            String sql = "SELECT PlayerID, PlayerName FROM Players;";
            ResultSet rs = this.stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("PlayerID");
                String name = rs.getString("PlayerName");
                toReturn = toReturn + "|" + id + "," + name;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn + "|";
    }

    /**
     * closes the database
     */
    public void closeDB() {
        try {
            if (this.stmt != null) {
                this.stmt.close();
            }
        } catch (SQLException se2) {
        }// nothing we can do
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
