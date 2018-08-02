package id.co.pln.simoka.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.pln.simoka.NotaDinasActivity;
import id.co.pln.simoka.R;
import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classutama.AnggaranClass;
import id.co.pln.simoka.classutama.AnggaranTotal;
import id.co.pln.simoka.classutama.InvestasiClass;

/**
 * Created by Suleiman on 03/02/17.
 */

public class NotaDinasAwalDetAdapter extends RecyclerView.Adapter<NotaDinasAwalDetAdapter.ItemNotaDinas> implements Filterable {
    private List<InvestasiClass> _listNotaDinas;
    private List<InvestasiClass> _ListNotaDinasFiltered;

    private JSONObject _JsonObj;
    private Context context;

    public NotaDinasAwalDetAdapter(Context context) {
        this.context = context;
        preprareNotaDinasDetail( );
        _ListNotaDinasFiltered = _listNotaDinas;
    }

    private void preprareNotaDinasDetail() {
        //koneksi database
        config._Database = new database( );
        String aurl = config._BaseIP + "NotaDinas2/nota_dinas/"+config.data_tahun;
        _JsonObj = config._Database.getData(aurl);

        InvestasiClass investasiClass;
        try {
            if (_JsonObj != null) {
                _listNotaDinas = new ArrayList<>( );
                JSONArray ajsonarr = new JSONArray(_JsonObj.getString("listnotadinas"));
                for (int i = 0; i < ajsonarr.length( ); i++) {
                    JSONObject adata = ajsonarr.getJSONObject(i);
                    String anilai_nodin = "Rp." + convertStringToCurr(adata.getString("nilai_nodin"));

                    investasiClass = new InvestasiClass( );
                    investasiClass.setNomor(String.valueOf(i + 1));
                    investasiClass.setNomorDinas(adata.getString("nomor_dinas"));
                    investasiClass.setNilaiNodin(anilai_nodin);
                    investasiClass.setTglNodin(adata.getString("tgl_nodin"));
                    investasiClass.setTglMasukBarjas(adata.getString("tgl_masuk_barjas"));
                    investasiClass.setNamaPekerjaan(adata.getString("nama_pekerjaan"));
                    _listNotaDinas.add(investasiClass);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace( );
        }
    }

    private String convertStringToCurr(String inumber) {
        String ahasil;

        Double anumber = Double.valueOf(inumber);
        NumberFormat aformat = NumberFormat.getInstance(Locale.GERMANY);
        ahasil = aformat.format(anumber);

        return ahasil;
    }

    @Override
    public ItemNotaDinas onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext( ))
                .inflate(R.layout.cv_notadinas, parent, false);

        return new ItemNotaDinas(v);
    }

    @Override
    public void onBindViewHolder(ItemNotaDinas holder, int position) {
        final InvestasiClass investasiClass = _ListNotaDinasFiltered.get(position);

        holder.txtNomor.setText(String.valueOf(investasiClass.getNomor( )));
        holder.txtNomorDinas.setText(investasiClass.getNomorDinas( ));
        holder.txtNilaiNodin.setText(investasiClass.getNilaiNodin( ));
        holder.txtTglNodin.setText(investasiClass.getTglNodin( ));
        holder.txtTglMasukBarjas.setText(investasiClass.getTglMasukBarjas( ));
        holder.txtNamaPekerjaan.setText(investasiClass.getNamaPekerjaan( ));
    }

    @Override
    public int getItemCount() {
        return _listNotaDinas != null ? _listNotaDinas.size( ) : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter( ) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString( );
                if (charString.isEmpty( )) {
                    _ListNotaDinasFiltered = _listNotaDinas;
                } else {
                    _ListNotaDinasFiltered = _listNotaDinas;
                    List<InvestasiClass> filteredList = new ArrayList<InvestasiClass>( );
                    for (InvestasiClass investasiClass : _ListNotaDinasFiltered) {
                        //search by nomor dinas
                        if (investasiClass.getNomorDinas( ).toLowerCase( ).contains(charString.toLowerCase( ))) {
                            filteredList.add(investasiClass);
                        }
                    }
                    _ListNotaDinasFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults( );
                filterResults.values = _ListNotaDinasFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _ListNotaDinasFiltered = (List<InvestasiClass>) filterResults.values;
                notifyDataSetChanged( );
            }
        };
    }

    protected static class ItemNotaDinas extends RecyclerView.ViewHolder {
        TextView txtNomor, txtNomorDinas, txtNilaiNodin, txtTglNodin, txtTglMasukBarjas, txtNamaPekerjaan;

        public ItemNotaDinas(View itemView) {
            super(itemView);

            txtNomor = (TextView) itemView.findViewById(R.id.number);
            txtNomorDinas = (TextView) itemView.findViewById(R.id.nomor_dinas);
            txtNilaiNodin = (TextView) itemView.findViewById(R.id.nilai_nodin);
            txtTglNodin = (TextView) itemView.findViewById(R.id.tgl_nodin);
            txtTglMasukBarjas = (TextView) itemView.findViewById(R.id.tgl_masuk_barjas);
            txtNamaPekerjaan = (TextView) itemView.findViewById(R.id.nama_pekerjaan);
        }
    }
}
