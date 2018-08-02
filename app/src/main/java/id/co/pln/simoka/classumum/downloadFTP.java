package id.co.pln.simoka.classumum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.adeel.library.easyFTP;

import java.io.File;
import java.io.InputStream;

import id.co.pln.simoka.R;

/**
 * Created by 4741G on 04/04/2018.
 */

public class downloadFTP extends AsyncTask<String, Void, String> {
    Context _Context;
    private ProgressDialog _Prg;
    easyFTP _Ftp;
    String _NamaFile;

    public downloadFTP(Context icontext) {
        this._Context = icontext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute( );
        _Prg = new ProgressDialog(_Context);
        _Prg.setMessage("Downloading...");
        _Prg.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String address, ausername, apassword, aserverpath, adestination;
            address = params[0];
            ausername = params[1];
            apassword = params[2];
            aserverpath = params[3];
            adestination = params[4];
            _NamaFile = params[5];

            _Ftp = new easyFTP( );
            _Ftp.connect(address, ausername, apassword);
            _Ftp.setWorkingDirectory(aserverpath);

            String alist[] = _Ftp.listName();
            if(isAdaFile(alist, _NamaFile)){
                _Ftp.downloadFile(_NamaFile+".pdf", adestination + _NamaFile+".pdf");
                return new String("Download Berhasil");
            }else{
                return new String("Download Gagal");
            }
        } catch (Exception e) {
            String t = "Gagal : " + e.getLocalizedMessage( );
            return t;
        }
    }

    boolean isAdaFile(String [] alist, String inamafile){
        boolean astat = false;

        for(String aserverFile : alist){
            if(aserverFile.contains(inamafile)){
                astat = true;
                break;
            }
        }
        return astat;
    }

    public void disconnect() {
        try {
            _Ftp.disconnect( );
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

    @Override
    protected void onPostExecute(String istr) {
        if (istr.toLowerCase( ).contains("berhasil")) {
            _Prg.dismiss( );
            showPDF(_NamaFile);
        } else {
            alertDialog.showAlertDialog(_Context, "Tidak ada file dengan no. spk " + _NamaFile);
        }
        Toast.makeText(_Context, istr, Toast.LENGTH_LONG).show( );
    }

    void showPDF(String inamafile) {
        PackageManager packageManager = _Context.getPackageManager( );
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        File file = new File(Environment.getExternalStorageDirectory( ), "simoka/" + inamafile + ".pdf");
        Intent pdfintent = new Intent( );
        pdfintent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        pdfintent.setDataAndType(uri, "application/pdf");
        _Context.startActivity(pdfintent);
    }
}
