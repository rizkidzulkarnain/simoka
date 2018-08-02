package id.co.pln.simoka.classumum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by 4741G on 05/04/2018.
 */

public final class alertDialog {
    public static void showAlertDialog(Context icontext, String imsg){
        AlertDialog alertDialog = new AlertDialog.Builder(icontext).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(imsg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
