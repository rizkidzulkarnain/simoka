package id.co.pln.simoka.classumum;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 4741G on 14/10/2017.
 */

public class webserviceGET extends AsyncTask<String, Void, JSONObject>{
    JSONObject _JsonObj;
    @Override
    protected JSONObject doInBackground(String... iparams) {
        try {
            String aurl = iparams[0];
            connection(aurl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _JsonObj;
    }

    public void connection(String iurl){
        StringBuffer asb = new StringBuffer("");
        try {
            URL aurl = new URL(iurl);
            HttpURLConnection connection = (HttpURLConnection) aurl.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStream ainputStream  = connection.getInputStream();

            BufferedReader ard = new BufferedReader(new InputStreamReader(ainputStream));
            String aline = "";
            while ((aline = ard.readLine()) != null) {
                asb.append(aline);
            }
            _JsonObj = new JSONObject(asb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
