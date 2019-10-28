package blackburn.college.dungeonsandseminars;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class PartyMessages extends AppCompatActivity {


    private String name;
    private String partyName;
    private String partyID;
    private String nameID;
    private EditText messageField;
    private boolean isConnected = true;
    private ArrayList<String> messageLog = new ArrayList<String>();
    KeepAliveThread keepMeAlive;
    private Random generator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_messages);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.name = getIntent().getStringExtra("name");
        this.partyName = getIntent().getStringExtra("partyName");
        this.partyID = getIntent().getStringExtra("partyID");
        this.nameID = getIntent().getStringExtra("nameID");
        keepMeAlive = new KeepAliveThread(nameID, partyID, true);
        keepMeAlive.start();
        setScroller();

    }

    public void setScroller() {
        TextView partyLabel = findViewById(R.id.PartyLabel);
        partyLabel.setText(partyName);
        LinearLayout layout = findViewById(R.id.partyLayout);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(30);
        layout.setBaselineAligned(true);
        layout.removeAllViews();
        if (messageLog == null) {
            System.out.println("messageLog null");
        } else if (messageLog.isEmpty()) {
            System.out.println("Message Log Empty");
            TextView msg = new TextView(this);
            msg.setText("No Messages Here Yet!");
            layout.addView(msg);
        } else {
            System.out.println("Messages Found");
            String[] messageList = new String[messageLog.size()];
            for (int i = 0; i < messageList.length; i++) {
                messageList[i] = messageLog.get(i);
            }
            for (int i = 1; i < messageList.length; i++) {
                TextView toadd = new TextView(this);
                toadd.setText(messageList[i]);
                layout.addView(toadd);
            }
        }
    }

    public void getMessageLog(View v) {

        messageLog = keepMeAlive.getLog();
        setContentView(R.layout.activity_party_messages);
        setScroller();
    }

    public void sendMessage(View v){
        messageField = findViewById(R.id.messageToSend);
        String message = messageField.getEditableText().toString();
        putMessageToThread(message);
    }

    public void putMessageToThread(String msg) {
        String task = "SendMSG:" + partyID + ",@GLOBAL," + nameID + "," + msg;
        WorkerThread worker = new WorkerThread(task);
        worker.start();

        try {
            worker.join();
            System.out.println(worker.getServerResponse());
        } catch (InterruptedException e) {
            System.out.println("Issue sending message");
        }
        getMessageLog(this.getCurrentFocus());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (keepMeAlive.isAlive()) {
            keepMeAlive.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keepMeAlive.isAlive()) {
            keepMeAlive.close();
        }
    }

    public void rollD4(View v){
        String task = "I rolled a d4 and got a " + (generator.nextInt(4) + 1);
        putMessageToThread(task);
    }

    public void rollD6(View v){
        String task = "I rolled a d6 and got a " + (generator.nextInt(6) + 1);
        putMessageToThread(task);
    }

    public void rollD8(View v){
        String task = "I rolled a d8 and got a " + (generator.nextInt(8) + 1);
        putMessageToThread(task);
    }

    public void rollD10(View v){
        String task = "I rolled a d10 and got a " + (generator.nextInt(10) + 1);
        putMessageToThread(task);
    }

    public void rollD12(View v){
        String task = "I rolled a d12 and got a " + (generator.nextInt(12) + 1);
        putMessageToThread(task);
    }

    public void rollD20(View v){
        String task = "I rolled a d20 and got a " + (generator.nextInt(20) + 1);
        putMessageToThread(task);
    }

    public void rollPercentile(View v){
        int tens = (generator.nextInt(10));
        int ones = (generator.nextInt(10));
        String result;
        if(tens == 0 && ones == 0){
            result = "100";
        } else {
            result = tens + "" + ones;
        }
        String task = "I rolled a Percentile and got a " + result;
        putMessageToThread(task);
    }

    public void returnToParty(View v) {
        if (keepMeAlive.isAlive()) {
            keepMeAlive.close();
        }
        finish();
    }
}
