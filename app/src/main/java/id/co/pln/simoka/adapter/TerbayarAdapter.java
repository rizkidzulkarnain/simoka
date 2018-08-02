package id.co.pln.simoka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.apache.commons.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.pln.simoka.R;
import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classutama.InvestasiClass;

/**
 * Created by Suleiman on 03/02/17.
 */

public class TerbayarAdapter extends RecyclerView.Adapter<TerbayarAdapter.ItemTerbayar> implements Filterable {

    //  Data
    private List<InvestasiClass> _listTerbayar;
    private List<InvestasiClass> _listTerbayarFiltered;

    private JSONObject _JsonObj;
    private Context context;

    public TerbayarAdapter(Context context) {
        this.context = context;
        preprareTerkontrakDetail( );
        _listTerbayarFiltered = _listTerbayar;
    }

    private void preprareTerkontrakDetail() {
        config._Database = new database( );
        String aurl = config._BaseIP + "Terbayar2/terbayar/"+config.data_tahun;
        _JsonObj = config._Database.getData(aurl);

        InvestasiClass investasiClass;
        try {
            if (_JsonObj != null) {
                _listTerbayar = new ArrayList<>( );
                JSONArray ajsonarr = new JSONArray(_JsonObj.getString("listterbayar"));
                for (int i = 0; i < ajsonarr.length( ); i++) {
                    JSONObject adata = ajsonarr.getJSONObject(i);
                    String anamaPekerjaan = capitalizeFirstEachWord(adata.getString("nama_pekerjaan"));
                    String anilai_terbayar = "Rp." + convertStringToCurr(adata.getString("nilai_terbayar"));


                    investasiClass = new InvestasiClass( );
                    investasiClass.setNomor(String.valueOf(i + 1));
                    investasiClass.setNamaPekerjaan(anamaPekerjaan);
                    investasiClass.setNilaiTerbayar(anilai_terbayar);
                    investasiClass.setNomorSPK(adata.getString("no_spk"));
                    investasiClass.setNomorSPJ(adata.getString("no_spj"));
                    investasiClass.setKeterangan(adata.getString("keterangan"));
                    _listTerbayar.add(investasiClass);
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

    private String capitalizeFirstEachWord(String istring) {
        String astring = WordUtils.capitalizeFully(istring);
        return astring;
    }

    @Override
    public ItemTerbayar onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext( ))
                .inflate(R.layout.cv_terbayar_detail, parent, false);

        return new ItemTerbayar(v);
    }

    @Override
    public void onBindViewHolder(ItemTerbayar holder, int position) {
        final InvestasiClass investasiClass = _listTerbayarFiltered.get(position);

        holder.txtNomor.setText(String.valueOf(investasiClass.getNomor( )));
        holder.txtNoSPK.setText(investasiClass.getNomorSPK( ));
        holder.txtNoSPJ.setText(investasiClass.getNomorSPJ( ));
        holder.txtNamaPekerjaan.setText(investasiClass.getNamaPekerjaan( ));
        holder.txtNilaiTerbayar.setText(investasiClass.getNilaiTerbayar( ));
        holder.txtKeterangan.setText(investasiClass.getKeterangan( ));
    }

    @Override
    public int getItemCount() {
        return _listTerbayarFiltered != null ? _listTerbayarFiltered.size( ) : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter( ) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString( );
                if (charString.isEmpty( )) {
                    _listTerbayarFiltered = _listTerbayar;
                } else {
                    _listTerbayarFiltered = _listTerbayar;
                    List<InvestasiClass> filteredList = new ArrayList<InvestasiClass>( );
                    for (InvestasiClass investasiClass : _listTerbayarFiltered) {
                        //search by no spk
                        if (investasiClass.getNomorSPK( ).toLowerCase( ).contains(charString.toLowerCase( ))) {
                            filteredList.add(investasiClass);
                        }
                    }
                    _listTerbayarFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults( );
                filterResults.values = _listTerbayarFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _listTerbayarFiltered = (List<InvestasiClass>) filterResults.values;
                notifyDataSetChanged( );
            }
        };
    }

    protected static class ItemTerbayar extends RecyclerView.ViewHolder {
        TextView txtNomor, txtNoSPK, txtNoSPJ, txtNamaPekerjaan, txtNilaiTerbayar,txtKeterangan;

        public ItemTerbayar(View itemView) {
            super(itemView);

            txtNomor = (TextView) itemView.findViewById(R.id.number);
            txtNoSPK = (TextView) itemView.findViewById(R.id.no_spk);
            txtNoSPJ = (TextView) itemView.findViewById(R.id.no_spj);
            txtNamaPekerjaan = (TextView) itemView.findViewById(R.id.nama_pekerjaan);
            txtNilaiTerbayar = (TextView) itemView.findViewById(R.id.nilai_terbayar);
            txtKeterangan = (TextView) itemView.findViewById(R.id.keterangan);
        }
    }
}
