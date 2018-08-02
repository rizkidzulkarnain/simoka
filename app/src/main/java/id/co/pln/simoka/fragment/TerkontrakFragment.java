package id.co.pln.simoka.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.co.pln.simoka.R;
import id.co.pln.simoka.adapter.NotaDinasAwalAdapter;
import id.co.pln.simoka.adapter.TerkontrakAdapter;

/**
 * Created by 4741G on 28/02/2018.
 */

public class TerkontrakFragment extends Fragment{
    private RecyclerView recyclerView;
    private TextView txtTotalHarga;

    public TerkontrakFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rcv_terkontrak, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_terkontrak);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccentOpacity));
        TerkontrakAdapter adapter = new TerkontrakAdapter(getContext());
        recyclerView.setAdapter(adapter);

        txtTotalHarga = (TextView) rootView.findViewById(R.id.footerHargaTotal);
        txtTotalHarga.setText(adapter.get_TotalAnggaran());

        return rootView;
    }
}
