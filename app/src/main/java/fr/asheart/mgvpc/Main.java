package fr.asheart.mgvpc;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.ListIterator;

public class Main extends AppCompatActivity {

    private LienApi     LA = new LienApi();
    ListIterator<Produit> pit;
    private String whoIAm = "A1";

    private String commandeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*pour rendre android plus tolérant au sujet de l'alourdissement de la gui */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Commande MaCommande = RecupMaCommandeAFaire();
        commandeId = MaCommande.getIdCmd();
        pit = MaCommande.getProduitDeMaCommande().listIterator();
        ShowMyNextProduit();


        //Log.v("YOUSK24EVER", MaCommande.getProduitDeMaCommande().get(0));

        /*ArrayList<String> alist = new ArrayList<String>();
        alist.add("A.1");
        alist.add("A.2");
        alist.add("A.3");
        alist.add("A.4");
        alist.add("B.4");
        alist.add("C.4");
        alist.add("C.3");
        alist.add("D.3");
        ShowMyAMap(alist);*/

    }


    public  void ClickButtonSuite(View view) {
        ShowMyNextProduit();
    }



    public void ShowMyNextProduit(){
        TextView    ref = findViewById(R.id.refProd);
        TextView    emp = findViewById(R.id.Emplacement);
        TextView    nom = findViewById(R.id.nomProd);
        TextView    qte = findViewById(R.id.quantite);


        Produit curProduit;
        if(pit.hasNext()){

            curProduit = pit.next();
            //Log.d("SHOWNP","emplacement: "+curProduit.getEmplacement()+" commande: "+curProduit.getIdProd()+" nom: "+curProduit.getLib()+" qt: "+curProduit.getQte());

            whatIsMyPath(whoIAm, curProduit.getEmplacement());

            ref.setText(curProduit.getIdProd());
            emp.setText(curProduit.getEmplacement());

            Log.v("YOUSK2 Emplacement", curProduit.getEmplacement());

            nom.setText(curProduit.getLib());
            qte.setText(curProduit.getQte());

            whoIAm = curProduit.getEmplacement();
        }
        else  {
            ref.setText("");
            emp.setText("Commande Suivante");
            nom.setText("");
            qte.setText("");
            NextCommande();

        }



        /* Dans l'idee sa marche mais modifier pour un ListIterator qui sais ou il est dans ma liste de prod en cour //
        //
        // Ancien code
        if (MaCommande.getNombreDeProduit() != nombreDeTourDePorduit) {
            ref.setText(MaCommande.getProduitDeMaCommande().get(nombreDeTourDePorduit).getIdProd());
            emp.setText(MaCommande.getProduitDeMaCommande().get(nombreDeTourDePorduit).getEmplacement());
            nom.setText(MaCommande.getProduitDeMaCommande().get(nombreDeTourDePorduit).getLib());
            qte.setText(MaCommande.getProduitDeMaCommande().get(nombreDeTourDePorduit).getQte());

            nombreDeTourDePorduit++;
        }
        */
    }


    public void NextCommande() {
        try {
            String urlTrous = "ChangeEtatCde.php?numCde=%s";
            String url      = String.format(urlTrous,commandeId);
            JSONArray jo    = LA.toApiRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Commande MaCommande = RecupMaCommandeAFaire();
        commandeId = MaCommande.getIdCmd();
        pit = MaCommande.getProduitDeMaCommande().listIterator();
    }



    public void ShowMyAMap(ArrayList<String> myPath) {
        ///LinearLayout MAP = findViewById(R.id.MAP);
        ///MAP.removeAllViews();

        LinearLayout ligne = findViewById(R.id.MAP);
        String iLettre;
        String search;

        ///////////
        // RESET //
        ///////////

        LinearLayout ligneY = findViewById(R.id.Y);
        LinearLayout ligneZ = findViewById(R.id.Z);

        ligneY.removeAllViews();
        ligneZ.removeAllViews();

        FrameLayout cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffaaaaaa);
            cube.setMinimumHeight(120);
            cube.setMinimumWidth(60);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            cube.setLayoutParams(lp);
        ligneY.addView(cube);
        cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffaaaaaa);
            cube.setMinimumHeight(120);
            cube.setMinimumWidth(60);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            cube.setLayoutParams(lp);
        ligneZ.addView(cube);


        cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffcc0000);
            cube.setMinimumHeight(250);
            cube.setMinimumWidth(60);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            cube.setLayoutParams(lp);
        ligneY.addView(cube);
        cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffcc0000);
            cube.setMinimumHeight(250);
            cube.setMinimumWidth(60);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            cube.setLayoutParams(lp);
        ligneZ.addView(cube);


        cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffaaaaaa);
            cube.setMinimumHeight(120);
            cube.setMinimumWidth(60);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            cube.setLayoutParams(lp);
        ligneY.addView(cube);
        cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffaaaaaa);
            cube.setMinimumHeight(120);
            cube.setMinimumWidth(60);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            cube.setLayoutParams(lp);
        ligneZ.addView(cube);


        //////////////////////////////////////
        // GESTION DE L'AFFICHAGE DE LA MAP //
        //////////////////////////////////////

        for (int i = 0; i < 6 ; i++ ) {

            switch (i) {
                case 0 : iLettre = "A"; ligne = findViewById(R.id.A); break;
                case 1 : iLettre = "B"; ligne = findViewById(R.id.B); break;
                case 2 : iLettre = "C"; ligne = findViewById(R.id.C); break;
                case 3 : iLettre = "D"; ligne = findViewById(R.id.D); break;
                case 4 : iLettre = "E"; ligne = findViewById(R.id.E); break;
                case 5 : iLettre = "F"; ligne = findViewById(R.id.F); break;

                default: iLettre = "ERREUR"; ligne = findViewById(R.id.MAP); break;
            }

            for (int j = 1; j < 5 ; j++) {

                // --- //
                if (j == 1) {
                    ligne.removeAllViews();
                }

                // --- //
                search = iLettre + "." + j;

                // --- //
                if(myPath.contains(search)){
                    /*Log.v("HELLO ", "---------------");
                    Log.v("HELLO ->", search);
                    Log.v("HELLO", "true");*/

                    cube = new FrameLayout(getApplicationContext());
                                cube.setBackgroundColor(0xff000000);
                                cube.setMinimumHeight(120);
                                cube.setMinimumWidth(60);

                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 5, 5, 5);
                                cube.setLayoutParams(lp);

                    ligne.addView(cube);
                }
                else {
                    /*Log.v("HELLO ", "---------------");
                    Log.v("HELLO ->", search);
                    Log.v("HELLO", "false");*/

                    cube = new FrameLayout(getApplicationContext());
                                cube.setBackgroundColor(0xffaaaaaa);
                                cube.setMinimumHeight(120);
                                cube.setMinimumWidth(60);

                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 5, 5, 5);
                                cube.setLayoutParams(lp);

                    ligne.addView(cube);
                }
            }
        }

        //////////////////////////////////
        // GESTION DE ENTRE LES CHEMINS //
        //////////////////////////////////


        // haut g //
        if (myPath.contains("B.1") && myPath.contains("C.1")) {
            ligne = findViewById(R.id.Y);
            ligne.removeAllViews();

            cube = new FrameLayout(getApplicationContext());
                        cube.setBackgroundColor(0xff000000);
                        cube.setMinimumHeight(120);
                        cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffcc0000);
                cube.setMinimumHeight(250);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffaaaaaa);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);
        }

        // haut droit //
        if (myPath.contains("D.1") && myPath.contains("E.1")) {
            ligne = findViewById(R.id.Z);
            ligne.removeAllViews();

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xff000000);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffcc0000);
                cube.setMinimumHeight(250);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffaaaaaa);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);
        }




        // bas g //
        if (myPath.contains("B.4") && myPath.contains("C.4")) {
            ligne = findViewById(R.id.Y);
            ligne.removeAllViews();

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffaaaaaa);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
            cube.setBackgroundColor(0xffcc0000);
                cube.setMinimumHeight(250);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xff000000);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);
        }

        // bas d //
        if (myPath.contains("D.4") && myPath.contains("E.4")) {
            ligne = findViewById(R.id.Z);
            ligne.removeAllViews();

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffaaaaaa);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xffcc0000);
                cube.setMinimumHeight(250);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);

            cube = new FrameLayout(getApplicationContext());
                cube.setBackgroundColor(0xff000000);
                cube.setMinimumHeight(120);
                cube.setMinimumWidth(60);
                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                cube.setLayoutParams(lp);
            ligne.addView(cube);
        }


    }

    public void whatIsMyPath(String dp, String ar) {
        ArrayList<String> myPath = new ArrayList<String>();

        try {
            String urlTrous = "path/calcul.php?dp=%s&ar=%s";
            Log.v("YOUSK2 -/", dp.substring(0,2) +" ::: " + ar.substring(0,2));

            String url= String.format(urlTrous, dp.substring(0,2), ar.substring(0,2));


            JSONArray jo  = LA.toApiRequest(url);
            Log.v("YOUSK2 -/--", jo.get(0)+"");
            for (int i = 0; i < jo.length(); i++) {
                myPath.add((String) jo.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("YOUSK2", String.valueOf(myPath.get(0)));
        ShowMyAMap(myPath);
    }


    public Commande RecupMaCommandeAFaire() {
        // --- Recup les donnees --- //
        Commande maCommade = new Commande();

        try {
            JSONArray jo  = LA.toApiRequest("SelectCde.php");
            JSONArray row = (JSONArray) jo.get(1);

            maCommade.setIdCmd((String) jo.get(0));

            for (int i = 0; i < row.length(); i++) {
                JSONArray prodJS = (JSONArray) row.get(i);

                Produit prod = new Produit(prodJS.getString(0), prodJS.getString(1), prodJS.getString(2), prodJS.getString(3));
                maCommade.getProduitDeMaCommande().add(prod);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        return maCommade;
    }
}
