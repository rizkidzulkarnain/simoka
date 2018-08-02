package id.co.pln.simoka.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.co.pln.simoka.R;
import id.co.pln.simoka.adapter.TerkontrakDetAdapter;
import id.co.pln.simoka.classumum.alertDialog;
import id.co.pln.simoka.classumum.config;

/**
 * Created by 4741G on 28/02/2018.
 */

public class TerkontrakDetailFragment extends Fragment{
    private RecyclerView recyclerView;
    public static TerkontrakDetAdapter _Adapter;

    public TerkontrakDetailFragment() {
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
        View rootView = inflater.inflate(R.layout.rcv_terkontrak_detail, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_terkontrak_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccentOpacity));
        _Adapter = new TerkontrakDetAdapter(getContext());
        recyclerView.setAdapter(_Adapter);
        if(_Adapter.getItemCount() == 0){
            alertDialogFunc();
        }
        return rootView;
    }

    public void alertDialogFunc(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage("Tidak Ada Data Terkontrak " + config.data_tahun);
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
