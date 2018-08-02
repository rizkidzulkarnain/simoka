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

/**
 * Created by 4741G on 28/02/2018.
 */

public class NotaDinasFragment extends Fragment{
    public static final String TAG = NotaDinasFragment.class.getSimpleName();
    private static final String ARG_COLOR = "color";
    private int color;

    private RecyclerView recyclerView;
    private TextView txtTotalHarga;

    public NotaDinasFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_awal_anggaran, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_notadinasawal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccentOpacity));
        NotaDinasAwalAdapter adapter = new NotaDinasAwalAdapter(getContext());
        recyclerView.setAdapter(adapter);

        txtTotalHarga = (TextView) rootView.findViewById(R.id.footerHargaTotal);
        txtTotalHarga.setText(adapter.get_TotalAnggaran());

        return rootView;
    }

    public void updateColor(int color) {
        recyclerView.setBackgroundColor(getLighterColor(color));
    }
    private int getLighterColor(int color) {
        return Color.argb(30,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }
}
