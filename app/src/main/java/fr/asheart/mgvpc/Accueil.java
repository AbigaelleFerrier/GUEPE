package fr.asheart.mgvpc;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class Accueil extends AppCompatActivity {

    private LienApi LA  = new LienApi();
    private String  mdp = "";

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        /*pour rendre android plus tolérant au sujet de l'alourdissement de la gui */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public  void ClickMdpButton(View view) {
        String btnPush = view.getTag().toString();
        this.mdp += btnPush;

        TextView mdpZone = findViewById(R.id.mdpZone);
                 mdpZone.setText(this.mdp);

        try {
            String urlTrous = "connexionApp.php?key=%s";
            String url      = String.format(urlTrous, this.mdp);
            JSONArray jo    = LA.toApiRequest(url);

            boolean go      = jo.getBoolean(0);

            if (go) {
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public  void ClickSuppButton(View view) {
        this.mdp = "";
        TextView mdpZone = findViewById(R.id.mdpZone);
                 mdpZone.setText(this.mdp);
    }
}
