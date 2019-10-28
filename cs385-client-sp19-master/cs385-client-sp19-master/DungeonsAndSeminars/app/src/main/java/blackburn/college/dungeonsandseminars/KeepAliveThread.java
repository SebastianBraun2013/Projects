package blackburn.college.dungeonsandseminars;

import java.util.ArrayList;

public class KeepAliveThread extends Thread {
    private long lastTime;
    private long nextMsgTime = 15000;
    private boolean run = true;
    private String playerID;
    private String partyID;
    private String task;
    private ArrayList<String> messageLog = new ArrayList<>();
    private WorkerThread worker;
    private boolean messenger;

    public KeepAliveThread(String playerID, String partyID, boolean messenger) {
        this.lastTime = System.currentTimeMillis();
        this.partyID = partyID;
        this.playerID = playerID;
        this.task = "KeepAlive:" + this.playerID + "," + this.partyID;
        this.messenger = messenger;
    }

    public void close() {
        run = false;
    }

    @Override
    public void run() {
        if (messenger) {
            getMessages();
        }
        while (run) {
            if (lastTime + nextMsgTime <= System.currentTimeMillis()) {
                lastTime = System.currentTimeMillis();
                this.task = "KeepAlive:" + this.playerID + "," + this.partyID;
                worker = new WorkerThread(task);
                worker.start();
                try {
                    worker.join();
                    String response = worker.getServerResponse().toLowerCase();
                    System.out.println(response);
                    if (response.contains("ok")) {
                        System.out.println("Server received keep alive");
                    } else {
                        System.out.println("Server keep alive not responding");
                    }

                } catch (InterruptedException e) {
                    System.out.println("Issue with keep alives");
                }
                if (messenger) {
                    getMessages();
                }


            }
        }
    }

    public void getMessages() {
        task = "GetMSG:" + this.partyID + "," + this.playerID;
        WorkerThread worker = new WorkerThread(task);
        worker.start();
        try {
            worker.join();
            String response = worker.getServerResponse();
            System.out.println(response);
            if (response != null) {
                if (response.split(":").length != 1) {
                    messageLog.clear();
                    String[] msgList = response.split(":")[1].split("-");
                    for (int i = 0; i < msgList.length; i++) {
                        messageLog.add(msgList[i]);
                    }
                } else {
                    System.out.println("messenger found no messages");
                }
            } else {
                System.out.println("messenger got null");
            }

        } catch (InterruptedException e) {

        }

    }

    public ArrayList<String> getLog() {
        getMessages();

        return messageLog;
    }
}
