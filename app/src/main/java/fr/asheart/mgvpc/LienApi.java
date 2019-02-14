package fr.asheart.mgvpc;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LienApi {
    private String urlEncode(String s) {
        String res="";
        try {
            res=URLEncoder.encode(s, "utf-8");

        }catch(Exception x){
            Log.e("CLIENTFACADE","UTF8 NON SUPPORTE?????");
        }
        return res;
    }


    private String creeParamDpt(String dpt){
        String res=dpt;
        return urlEncode(res);
    }
    private String creeParamArv(String arv){
        String res=arv;
        return urlEncode(res);
    }

    private String getReponse(String url) throws IOException {
        /*envoi de la requete*/
        URLConnection connection = new URL(url).openConnection();
        InputStream input = connection.getInputStream();

        /*lecture de la reponse*/
        BufferedReader rd;

        rd=new BufferedReader(new InputStreamReader(input));
        String reponse="";
        String ligne=rd.readLine();

        while (ligne !=null) {
            reponse += ligne;
            ligne=rd.readLine();
        }
        rd.close();

        return  reponse;
    }

    public JSONArray toApiRequest(String link)  throws IOException,JSONException {
        /* preparation de la requete */
        String urlTrous = "http://172.16.220.225/ApiMGVPCv2/%s";
        String url=String.format(urlTrous,link);

        Log.v("YOUSK2", url);

        /*on fait la requête http et on demande la réponse*/
        String rep = getReponse(url);

        /*le json retourné*/
        JSONArray jo = new JSONArray(rep);

        return jo;
    }
}
