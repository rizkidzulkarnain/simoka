package id.co.pln.simoka.classumum;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 4741G on 14/10/2017.
 */

public class database {
    webserviceGET _WebServiceGET;
    webservicePOST _WebServicePOST;

    public JSONObject getData(String iurl) {
        JSONObject ajsonobj = null;
        try {
            _WebServiceGET = new webserviceGET();
            ajsonobj = _WebServiceGET.execute(iurl).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ajsonobj;
    }

    public JSONObject postData(String iurl, String ijson) {
        String ahasil = "";
        JSONObject ajsonobj = null;
        try {
            _WebServicePOST = new webservicePOST();
            ahasil = _WebServicePOST.execute(iurl, ijson).get();
            ajsonobj = new JSONObject(ahasil);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ajsonobj;
    }
}
