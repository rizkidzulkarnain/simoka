package id.co.pln.simoka;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classutama.user;

/**
 * Created by 4741G on 06/12/2017.
 */

public class LoginActivity extends AppCompatActivity {
    TextView _Username;
    TextView _Password;
    Button _Button;

    JSONObject _JsonObj;
    String _Msg;

    private ProgressBar spinner;

    public void onCreate(Bundle savedInstanceState) {
        config._Database = new database( );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        changeActionBarFont( );

        spinner = (ProgressBar) findViewById(R.id.progressBar);
//        DrawableCompat.setTint(spinner.getIndeterminateDrawable(), getResources().getColor(R.color.colorPrimaryDark));
        spinner.setVisibility(View.GONE);

        _Button = (Button) findViewById(R.id.btn_login);
        _Button.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Vibrator avibra = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                avibra.vibrate(100);
                spinner.setVisibility(View.VISIBLE);
                new Handler( ).postDelayed(new Runnable( ) {
                    @Override
                    public void run() {
                        try {
                            if (Click( )) {
                                if (!_JsonObj.equals("null")) {
                                    config._User = new user( );
                                    config._User.set_username(_Username.getText( ).toString( ));
                                    config._User.set_password(_Password.getText( ).toString( ));
                                    config._User.set_role(_JsonObj.get("role").toString( ));
                                    config._User.set_status(Integer.parseInt(_JsonObj.get("status").toString( )));
                                    _Msg = _JsonObj.getString("message").toString( );

                                    if (config._User.get_status( ) == 1) {
                                        Intent intent = new Intent(getApplicationContext( ), CoorActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getBaseContext( ), _Msg, Toast.LENGTH_LONG).show( );
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace( );
                        } finally {
                            spinner.setVisibility(View.GONE);
                        }
                    }
                }, 2000);
            }
        });
    }

    public boolean Click() throws JSONException {
        boolean astat = false;

        if (!validate( )) {
            onLoginFailed( );
            return astat;
        }

        JSONObject ajsonobj;
        JSONObject bjsonobj;

        ajsonobj = new JSONObject( );
        ajsonobj.put("username", _Username.getText( ).toString( ));
        ajsonobj.put("password", _Password.getText( ).toString( ));

        String aurl = config._BaseIP + "Login/LoginApi";
        _JsonObj = config._Database.postData(aurl, ajsonobj.toString( ));
        try {
            if (_JsonObj == null) {
                Toast.makeText(getBaseContext( ), "No Internet Connection", Toast.LENGTH_LONG).show( );
            } else {
                astat = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace( );
        }
        return astat;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext( ), "Login failed", Toast.LENGTH_LONG).show( );

        _Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        _Username = (TextView) findViewById(R.id.input_username);
        _Password = (TextView) findViewById(R.id.input_password);

        if (_Username.getText( ).toString( ).isEmpty( )) {
            _Username.setError("enter a valid username");
            valid = false;
        } else {
            _Username.setError(null);
        }

        if (_Password.getText( ).toString( ).isEmpty( )) {
            _Password.setError("enter valid password");
            valid = false;
        } else {
            _Password.setError(null);
        }
        return valid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );

        if (id == R.id.logo) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeActionBarFont() {
        TextView tv = new TextView(getApplicationContext( ));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText("SIMOKA");
        tv.setTextSize(14);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(getAssets( ), "fonts/pacifico.ttf");
        tv.setTypeface(tf);
        getSupportActionBar( ).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar( ).setCustomView(tv);

//        SpannableString s = new SpannableString("SIMOKA");
//        s.setSpan(new TypefaceSpan("pacifico.ttf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // Update the action bar title with the TypefaceSpan instance
//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle(s);
    }
}
