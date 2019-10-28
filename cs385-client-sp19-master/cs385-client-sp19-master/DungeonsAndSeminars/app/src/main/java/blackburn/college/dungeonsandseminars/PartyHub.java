package blackburn.college.dungeonsandseminars;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

public class PartyHub extends AppCompatActivity {
    private Date calendar = Calendar.getInstance().getTime();

    //true for player, false for dm
    private boolean playerORDM;
    private String name;
    private String partyName;
    private String partyID;
    private String nameID;

    private Button closeButton;
    private TextView showName;
    private TextView showPartyName;
    private boolean isConnected = true;
    //change partyList to playersInParty and fill the ArrayList with players
    private ArrayList<String> playerList = new ArrayList<String>();
    private FragmentManager fm = getSupportFragmentManager();
    final private Intent toButton = new Intent();

    KeepAliveThread keepMeAlive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_hub);


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.name = getIntent().getStringExtra("name");
        this.partyName = getIntent().getStringExtra("partyName");
        this.partyID = getIntent().getStringExtra("partyID");
        this.nameID = getIntent().getStringExtra("nameID");
        this.playerORDM = getIntent().getBooleanExtra("playerORDM", false);


        showName = findViewById(R.id.ShowName);
        showName.setText("You are " + name);
        showPartyName = findViewById(R.id.ShowPartyName);
        showPartyName.setText("Party: " + partyName);
        closeButton = findViewById(R.id.ClosePartyHub);
        closeButton.setText("Exit Party");
        keepMeAlive = new KeepAliveThread(nameID, partyID, false);
        keepMeAlive.start();
        getPlayerList(this.getCurrentFocus());
        setScroller();

    }

    public void setScroller() {
        showName = findViewById(R.id.ShowName);
        showName.setText(name);
        showPartyName = findViewById(R.id.ShowPartyName);
        showPartyName.setText("Party: " + partyName);
        LinearLayout layout = findViewById(R.id.partyLayout);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(30);
        layout.setBaselineAligned(true);
        layout.removeAllViews();
        if (playerList == null) {
            System.out.println("playerList null");
        } else if (playerList.isEmpty()) {
            System.out.println("Player List Empty");
            TextView msg = new TextView(this);
            msg.setText("No Players Yet! Try refreshing!");
            layout.addView(msg);
        } else {
            System.out.println("Players Found");
            for (int i = 0; i < playerList.size(); i++) {
                Button toadd = new Button(this);
                System.out.println(playerList.get(i));
                toadd.setText(playerList.get(i).split(",")[1]);
                toButton.putExtra(playerList.get(i).split(",")[1], playerList.get(i).split(",")[0]);
                layout.addView(toadd);
            }
        }
    }

    public void closeConnection(View v) {
        finish();
    }


    public void getPlayerList(View v) {

        WorkerThread thread = new WorkerThread("PlayerList:" + partyID);
        thread.start();
        //wait for worker to get info
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = thread.getServerResponse();
        System.out.println(response);
        String[] players = response.split(":")[1].split("-");
        System.out.println(response);
        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i]);
        }
        if (playerList != null) {
            playerList.clear();
            for (int i = 0; i < players.length; i++) {
                playerList.add(players[i]);
            }
        }
        setContentView(R.layout.activity_party_hub);
        setScroller();
    }

    public void toMessages(View v) {
        Intent intent = new Intent(this, PartyMessages.class);
        intent.putExtra("name", name);
        intent.putExtra("partyName", partyName);
        intent.putExtra("nameID", nameID);
        intent.putExtra("partyID", partyID);
        keepMeAlive.close();
        startActivity(intent);
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

        WorkerThread thread = new WorkerThread("Close:" + nameID + "," + partyID);
        thread.start();
        Intent intent = new Intent(this, IntroScreen.class);
        startActivity(intent);
    }
}






