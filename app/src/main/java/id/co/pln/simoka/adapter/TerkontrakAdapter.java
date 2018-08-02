package id.co.pln.simoka.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.pln.simoka.NotaDinasActivity;
import id.co.pln.simoka.R;
import id.co.pln.simoka.TerkontrakActivity;
import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classutama.AnggaranTotal;

/**
 * Created by Suleiman on 03/02/17.
 */

public class TerkontrakAdapter extends RecyclerView.Adapter<TerkontrakAdapter.ItemAnggaran> {

    //  Data
    private List<AnggaranTotal> _ListAnggaranTotals;
    private JSONObject _JsonObj;
    private String _TotalAnggaran;

    private Context context;

    public TerkontrakAdapter(Context context) {
        this.context = context;
        prepareAnggaranTotal( );
    }

    public String get_TotalAnggaran() {
        return _TotalAnggaran;
    }

    private void prepareAnggaranTotal() {
        config._Database = new database( );
        String aurl = config._BaseIP + "Terkontrak/listAnggaranTerkontrak";
        _JsonObj = config._Database.getData(aurl);
        AnggaranTotal anggaranTotal;

        try {

            if (_JsonObj != null) {
                _ListAnggaranTotals = new ArrayList<>( );

                //set total anggaran
                _TotalAnggaran = convertStringToCurr(_JsonObj.getString("total"));

                JSONArray ajsonarr = new JSONArray(_JsonObj.getString("listanggaran"));
                for (int i = 0; i < ajsonarr.length( ); i++) {
                    anggaranTotal = new AnggaranTotal( );

                     String acurrency = convertStringToCurr(ajsonarr.getJSONObject(i).getString("jumlahNilai"));
                    String alabel1 = "B1 Perkuatan Jaringan";
                    String alabel2 = "B2 Pemasaran";
                    String alabel = ajsonarr.getJSONObject(i).getString("basket").equals("1") ? alabel1 : alabel2;
                    anggaranTotal.setSkki(alabel);
                    anggaranTotal.setPagu_anggaran(acurrency);
                    anggaranTotal.setPersentase(ajsonarr.getJSONObject(i).getString("persentase")+"%");
                    _ListAnggaranTotals.add(anggaranTotal);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace( );
        }
    }

    private String convertStringToCurr(String inumber){
        String ahasil;

        Double anumber = Double.valueOf(inumber);
        NumberFormat aformat = NumberFormat.getInstance(Locale.GERMANY);
        ahasil = aformat.format(anumber);

        return ahasil;
    }

    @Override
    public ItemAnggaran onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext( ))
                .inflate(R.layout.cv_terkontrak, parent, false);

        return new ItemAnggaran(v);
    }

    @Override
    public void onBindViewHolder(ItemAnggaran holder, int position) {
        final AnggaranTotal anggaranTotal = _ListAnggaranTotals.get(position);

        holder.txtPersen.setText(anggaranTotal.getPersentase());
        holder.txtSKKI.setText(anggaranTotal.getSkki( ));
        holder.txtHarga.setText(anggaranTotal.getPagu_anggaran( ));
        holder.linearLayout.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent aintent = new Intent(view.getContext(), TerkontrakActivity.class);
                aintent.putExtra("param", anggaranTotal.getSkki());
                view.getContext().startActivity(aintent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _ListAnggaranTotals != null ? _ListAnggaranTotals.size( ) : 0;
    }

    protected static class ItemAnggaran extends RecyclerView.ViewHolder {
        TextView txtSKKI, txtHarga, txtPersen;
        LinearLayout linearLayout;

        public ItemAnggaran(View itemView) {
            super(itemView);

            txtPersen = (TextView) itemView.findViewById(R.id.itemPersen);
            txtSKKI = (TextView) itemView.findViewById(R.id.itemSKKI);
            txtHarga = (TextView) itemView.findViewById(R.id.itemHarga);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }
    }
}
