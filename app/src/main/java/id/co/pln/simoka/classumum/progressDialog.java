package id.co.pln.simoka.classumum;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 4741G on 02/05/2018.
 */

public class progressDialog {
    private static ProgressDialog _Prg;
    public static void showProgressDialog(Context icontext){
        _Prg = new ProgressDialog(icontext);
        _Prg.setMessage("Please wait...");
        _Prg.show();
    }

    public static void dismissProgressDialog(){
        _Prg.dismiss( );
    }
}
