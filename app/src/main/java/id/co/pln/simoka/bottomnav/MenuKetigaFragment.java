package id.co.pln.simoka.bottomnav;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import id.co.pln.simoka.R;
import id.co.pln.simoka.adapter.AnggaranTotalAdapter;
import id.co.pln.simoka.adapter.MenuKetigaAdapter;
import id.co.pln.simoka.classutama.AnggaranSaldo;


public class MenuKetigaFragment extends Fragment {
    public static final String TAG = MenuKetigaFragment.class.getSimpleName( );
    private static final String ARG_COLOR = "color";
    private int color;

    private RecyclerView recyclerView;

    private TextView txtPaguAnggaran;
    private TextView txtJumlahPenngeluaran;
    private TextView txtFooterHargaTotal;

    public MenuKetigaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments( ) != null) {
            color = getArguments( ).getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.content_menuketiga, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext( ), LinearLayoutManager.VERTICAL, false));
        recyclerView.setBackgroundColor(getLighterColor(color));

//        SimpleAdapter adapter = new SimpleAdapter(getContext());
        MenuKetigaAdapter adapter = new MenuKetigaAdapter(getContext( ));
        recyclerView.setAdapter(adapter);

        txtPaguAnggaran = (TextView) rootView.findViewById(R.id.headerPaguAnggaran);
        txtJumlahPenngeluaran = (TextView) rootView.findViewById(R.id.jumlahPengeluaran);
        txtFooterHargaTotal = (TextView) rootView.findViewById(R.id.footerHargaTotal);

        AnggaranSaldo anggaranSaldo = adapter.get_AnggaranSaldo( );
        txtPaguAnggaran.setText(convertStringToCurr(anggaranSaldo.getPaguAnggaran( )));

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

    private String convertStringToCurr(String inumber) {
        String ahasil = "";
        try {
            Double anumber = Double.valueOf(inumber);
            NumberFormat aformat = NumberFormat.getInstance(Locale.GERMANY);
            ahasil = aformat.format(anumber);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ahasil;
    }

}
