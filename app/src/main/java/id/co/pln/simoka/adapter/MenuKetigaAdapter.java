package id.co.pln.simoka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.pln.simoka.R;
import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classutama.AnggaranClass;
import id.co.pln.simoka.classutama.AnggaranSaldo;
import id.co.pln.simoka.classutama.AnggaranTotal;

/**
 * Created by Suleiman on 03/02/17.
 */

public class MenuKetigaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //  Data
    private List<Object> anggaranClassList;
    private JSONObject _JsonObj;

    private AnggaranSaldo _AnggaranSaldo;

    private Context context;

    public MenuKetigaAdapter(Context context) {
        this.context = context;
        prepareAnggaranTotal( );
    }

    public AnggaranSaldo get_AnggaranSaldo() {
        return _AnggaranSaldo;
    }

    private void prepareAnggaranTotal() {
        _AnggaranSaldo = new AnggaranSaldo( );
        config._Database = new database( );


        String aurl = config._BaseIP + "Anggaran/anggaranPengeluaran";
        _JsonObj = config._Database.getData(aurl);
        AnggaranClass anggaranClass;

        try {

            if (_JsonObj != null) {
                anggaranClassList = new ArrayList<>( );

                //set list anggaran pengeluaran
                _AnggaranSaldo.setPaguAnggaran(_JsonObj.getString("paguanggaran"));
                _AnggaranSaldo.setJumlahPengeluaran(_JsonObj.getString("totalpengeluaran"));
                _AnggaranSaldo.setSaldoAkhir(_JsonObj.getString("saldo"));

                JSONArray ajsonarr = new JSONArray(_JsonObj.getString("listpengeluaran"));
                for (int i = 0; i < ajsonarr.length( ); i++) {
                    anggaranClass = new AnggaranClass( );

                    String acurrency = convertStringToCurr(ajsonarr.getJSONObject(i).getString("total"));
                    anggaranClass.setProses(ajsonarr.getJSONObject(i).getString("proses"));
                    anggaranClass.setNilai(acurrency);
                    anggaranClassList.add(anggaranClass);
                }
                anggaranClassList.add(_AnggaranSaldo);
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
    public int getItemViewType(int position) {
        if (anggaranClassList.get(position) instanceof AnggaranClass) {
            return 1;
        } else if (anggaranClassList.get(position) instanceof AnggaranSaldo) {
            return 2;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case 1:
                View v1 = inflater.inflate(R.layout.activity_cardmenuketiga, parent, false);
                viewHolder = new ItemAnggaran(v1);
                break;
            case 2:
                View v2 = inflater.inflate(R.layout.activity_cardmenuketigafooter, parent, false);
                viewHolder = new ItemFooter(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.activity_cardmenuketiga, parent, false);
                viewHolder = new ItemAnggaran(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                AnggaranClass anggaranClass = (AnggaranClass) anggaranClassList.get(position);
                ItemAnggaran vh1 = (ItemAnggaran) holder;

                vh1.txtProses.setText(anggaranClass.getProses( ));
                vh1.txtNilai.setText(anggaranClass.getNilai( ));
                break;
            case 2:
                AnggaranSaldo anggaranSaldo = (AnggaranSaldo) anggaranClassList.get(position);
                ItemFooter vh2 = (ItemFooter) holder;

                vh2.txtPengeluaran.setText(convertStringToCurr(anggaranSaldo.getJumlahPengeluaran()));
                vh2.txtSaldo.setText(convertStringToCurr(anggaranSaldo.getSaldoAkhir( )));
                vh2.txtPersenSaldo.setText(anggaranSaldo.getSaldoPersen()+"%");
                break;
            default:
                AnggaranClass anggaranClass3 = (AnggaranClass) anggaranClassList.get(position);
                ItemAnggaran vh3 = (ItemAnggaran) holder;

                vh3.txtProses.setText(anggaranClass3.getProses( ));
                vh3.txtNilai.setText(anggaranClass3.getNilai( ));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return anggaranClassList != null ? anggaranClassList.size( ) : 0;
    }

    protected static class ItemAnggaran extends RecyclerView.ViewHolder {
        TextView txtProses, txtNilai;

        public ItemAnggaran(View itemView) {
            super(itemView);

            txtProses = (TextView) itemView.findViewById(R.id.itemProses);
            txtNilai = (TextView) itemView.findViewById(R.id.itemNilai);
        }
    }

    protected static class ItemFooter extends RecyclerView.ViewHolder {
        TextView txtPengeluaran, txtSaldo, txtPersenSaldo;

        public ItemFooter(View itemView) {
            super(itemView);

            txtPengeluaran = (TextView) itemView.findViewById(R.id.jumlahPengeluaran);
            txtSaldo = (TextView) itemView.findViewById(R.id.jumlahSaldo);
            txtPersenSaldo = (TextView) itemView.findViewById(R.id.persenSisa);
        }
    }
}
