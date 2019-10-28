
package javaserverdungeonsandseminars;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicholas.bohm
 */
public class JavaServerDungeonsAndSeminars {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int port = 20202;
        SQLHandler db = new SQLHandler();
        PartyController pControl = new PartyController(db);
        try {
            Thread pControlThread = new Thread(pControl);
            pControlThread.start();
            ExecutorService executor = Executors.newFixedThreadPool(5);
            Server server = new Server(port);
            int thread = 0;
            while (true) {

                String result = "";
                server.waitForConnection();

                executor.execute(new WorkerThread(server, db, thread, pControl));
                thread++;

            }
        } catch (IOException ex) {
            Logger.getLogger(JavaServerDungeonsAndSeminars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}