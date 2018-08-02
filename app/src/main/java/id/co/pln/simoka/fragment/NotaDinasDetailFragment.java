package id.co.pln.simoka.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.co.pln.simoka.NotaDinasActivity;
import id.co.pln.simoka.R;
import id.co.pln.simoka.adapter.NotaDinasAwalAdapter;
import id.co.pln.simoka.adapter.NotaDinasAwalDetAdapter;
import id.co.pln.simoka.classumum.config;

/**
 * Created by 4741G on 28/02/2018.
 */

public class NotaDinasDetailFragment extends Fragment{
    public static final String TAG = NotaDinasDetailFragment.class.getSimpleName();
    private final String TITLE = "INVESTASI 2017";

    private RecyclerView recyclerView;
    private TextView txtTotalHarga, txtTitle;

    public static NotaDinasAwalDetAdapter _Adapter;

    public NotaDinasDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //do something if any arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rcv_notadinas, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_notadinasawal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccentOpacity));
        _Adapter = new NotaDinasAwalDetAdapter(getContext());
        recyclerView.setAdapter(_Adapter);
        if(_Adapter.getItemCount() == 0){
            alertDialogFunc();
        }
        return rootView;
    }

    public void alertDialogFunc(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage("Tidak Ada Data Nota Dinas " + config.data_tahun);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });
        alertDialog.show();
    }
}
