package id.co.pln.simoka.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.pln.simoka.R;
import id.co.pln.simoka.bottomnav.Dessert;
import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classumum.progressDialog;
import id.co.pln.simoka.classutama.AnggaranClass;
import id.co.pln.simoka.classutama.AnggaranSaldo;
import id.co.pln.simoka.classutama.AnggaranTotal;
import it.beppi.tristatetogglebutton_library.TriStateToggleButton;

/**
 * Created by Suleiman on 03/02/17.
 */

public class AnggaranTotalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //  Data
    private List<Object> _ListAnggaranObject; //untuk menyimpan object anggaranClass dan anggaranTotal
    private AnggaranClass _AnggaranFooter;
    private JSONObject _JsonObj;

    private Context context;

    public AnggaranTotalAdapter(Context context) {
        this.context = context;
        prepareAnggaranTotal( );
    }

    public String get_TotalAnggaran() {
        return _AnggaranFooter.getNilai();
    }

    private void prepareAnggaranTotal() {
        AnggaranTotal anggaranTotal;
        _AnggaranFooter = new AnggaranClass();

        config._Database = new database( );
        String aurl = config._BaseIP + "Anggaran/AnggaranTotal";
        _JsonObj = config._Database.getData(aurl);

        try {

            if (_JsonObj != null) {
                _ListAnggaranObject = new ArrayList<>( );

                //set total anggaran
                String atotalJson = _JsonObj.getString("total");
                String atotal = convertStringToCurr(atotalJson);
                _AnggaranFooter.setNilai(atotal); //jumlah total

                JSONArray ajsonarr = new JSONArray(_JsonObj.getString("listanggaran"));
                for (int i = 0; i < ajsonarr.length( ); i++) {
                    anggaranTotal = new AnggaranTotal( );

                    String anilai = ajsonarr.getJSONObject(i).getString("pagu_anggaran");
                    String acurrency = convertStringToCurr(anilai);
                    Double apersen =  (Double.valueOf(anilai)/Double.valueOf(atotalJson))*100;
                    String apersenRound = new DecimalFormat("##.##").format(apersen);

                    anggaranTotal.setSkki(ajsonarr.getJSONObject(i).getString("skki"));
                    anggaranTotal.setPagu_anggaran(acurrency);
                    anggaranTotal.setPersentase(apersenRound + "%");
                    _ListAnggaranObject.add(anggaranTotal);
                }
                _ListAnggaranObject.add(_AnggaranFooter);
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
    public int getItemViewType(int position) {
        if (_ListAnggaranObject.get(position) instanceof AnggaranTotal) {
            return 1;
        } else if (_ListAnggaranObject.get(position) instanceof AnggaranClass) {
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
                View v1 = inflater.inflate(R.layout.activity_cardcoorsimple, parent, false);
                viewHolder = new ItemAnggaran(v1);
                break;
            case 2:
                View v2 = inflater.inflate(R.layout.content_coor_footer, parent, false);
                viewHolder = new ItemFooter(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.activity_cardcoorsimple, parent, false);
                viewHolder = new ItemAnggaran(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                AnggaranTotal anggaranTotal  = (AnggaranTotal) _ListAnggaranObject.get(position);
                ItemAnggaran vh1 = (ItemAnggaran) holder;

                vh1.txtSKKI.setText(anggaranTotal.getSkki( ));
                vh1.txtPersen.setText(anggaranTotal.getPersentase( ));
                vh1.txtHarga.setText(anggaranTotal.getPagu_anggaran( ));
                break;
            case 2:
                AnggaranClass anggaranClass = (AnggaranClass) _ListAnggaranObject.get(position);
                ItemFooter vh2 = (ItemFooter) holder;

                String aurl = config._BaseIP + "Anggaran/chartData/2";
                JSONObject ajsonobj = config._Database.getData(aurl);

                vh2.txtTotal.setText(anggaranClass.getNilai());
                prepareChart2(vh2, ajsonobj);
                vh2.toggleButton.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
                    @Override
                    public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                        String abasket = "", aurl = "";
                        JSONObject bjsonobj = null;
                        switch (toggleStatus) {
                            case off:
                                abasket = "1";
                                aurl = config._BaseIP + "Anggaran/chartData/"+abasket;
                                bjsonobj = config._Database.getData(aurl);

                                vh2.txtTahun.setText("BASKET " + abasket);
                                vh2.txtTahun.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                                prepareChart(vh2, bjsonobj);
                                break;
                            /*case mid: break;*/
                            case on:
                                abasket = "2";
                                aurl = config._BaseIP + "Anggaran/chartData/"+abasket;
                                bjsonobj = config._Database.getData(aurl);

                                vh2.txtTahun.setText("BASKET " + abasket);
                                vh2.txtTahun.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                                prepareChart2(vh2, bjsonobj);
                                break;
                        }
                    }
                });
                break;
            default:
                AnggaranTotal anggaranTotal3  = (AnggaranTotal) _ListAnggaranObject.get(position);
                ItemAnggaran vh3 = (ItemAnggaran) holder;

                vh3.txtSKKI.setText(anggaranTotal3.getSkki( ));
                vh3.txtPersen.setText(anggaranTotal3.getPersentase( ));
                vh3.txtHarga.setText(anggaranTotal3.getPagu_anggaran( ));
                break;
        }
    }

    private void prepareChart(ItemFooter ivh2, JSONObject ijsonobj){
        ivh2.pieChart.setUsePercentValues(true);
        List<PieEntry> yValues = setYval(ijsonobj);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        data.setValueFormatter(new PercentFormatter());
        ivh2.pieChart.setData(data);
        ivh2.pieChart.setEntryLabelColor(Color.DKGRAY);

        Description desc = new Description();
        desc.setText("");
        ivh2.pieChart.setDescription(desc);

        ivh2.pieChart.notifyDataSetChanged();
        ivh2.pieChart.invalidate();
    }

    private void prepareChart2(ItemFooter ivh2, JSONObject ijsonobj){
        ivh2.pieChart.setUsePercentValues(true);
        List<PieEntry> yValues = setYval2(ijsonobj);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        data.setValueFormatter(new PercentFormatter());
        ivh2.pieChart.setData(data);
        ivh2.pieChart.setEntryLabelColor(Color.DKGRAY);

        Description desc = new Description();
        desc.setText("");
        ivh2.pieChart.setDescription(desc);

        ivh2.pieChart.notifyDataSetChanged();
        ivh2.pieChart.invalidate();
    }

    private List<PieEntry> setYval(JSONObject ijsonobj){

        double apersen_nodin = 0.00, apersen_terkontrak = 0.00, apersen_terbayar = 0.00, apersen_sisa = 0.00;
        try {
            apersen_nodin        = Double.valueOf(ijsonobj.getString("persen_nodin"));
            apersen_terkontrak   = Double.valueOf(ijsonobj.getString("persen_terkontrak"));
            apersen_terbayar     = Double.valueOf(ijsonobj.getString("persen_terbayar"));
            apersen_sisa         = Double.valueOf(ijsonobj.getString("persen_sisa"));
        } catch (JSONException e) {
            e.printStackTrace( );
        }

        /*Double atotal_persen = apersen_nodin + apersen_terkontrak + apersen_terbayar;
        Double asisa = atotal_persen < 100? 100 - atotal_persen : 0.0;*/

        List<PieEntry> Yval = new ArrayList<PieEntry>();
        Yval.add(new PieEntry((float) apersen_nodin, "Nota Dinas", 0));
        Yval.add(new PieEntry((float) apersen_terkontrak, "Terkontrak", 1));
        Yval.add(new PieEntry((float) apersen_terbayar, "Terbayar", 2));
        Yval.add(new PieEntry((float) apersen_sisa, "Sisa", 3));
        return Yval;
    }

    private List<PieEntry> setYval2(JSONObject ijsonobj){

        double apersen_nodin = 0.00, apersen_terkontrak = 0.00, apersen_terbayar = 0.00, apersen_sisa = 0.00;
        try {
            apersen_nodin        = Double.valueOf(ijsonobj.getString("persen_nodin"));
            apersen_terkontrak   = Double.valueOf(ijsonobj.getString("persen_terkontrak"));
            apersen_terbayar     = Double.valueOf(ijsonobj.getString("persen_terbayar"));
            apersen_sisa         = Double.valueOf(ijsonobj.getString("persen_sisa"));
        } catch (JSONException e) {
            e.printStackTrace( );
        }
        List<PieEntry> Yval = new ArrayList<PieEntry>();
        Yval.add(new PieEntry((float) apersen_nodin, "Nota Dinas", 0));
        Yval.add(new PieEntry((float) apersen_terkontrak, "Terkontrak", 1));
        Yval.add(new PieEntry((float) apersen_terbayar, "Terbayar", 2));
        Yval.add(new PieEntry((float) apersen_sisa, "Sisa", 3));
        return Yval;
    }

    @Override
    public int getItemCount() {
        return _ListAnggaranObject != null ? _ListAnggaranObject.size( ) : 0;
    }

    protected static class ItemAnggaran extends RecyclerView.ViewHolder {
        TextView txtSKKI, txtPersen, txtHarga;

        public ItemAnggaran(View itemView) {
            super(itemView);

            txtSKKI = (TextView) itemView.findViewById(R.id.itemSKKI);
            txtPersen = (TextView) itemView.findViewById(R.id.persenAnggaran);
            txtHarga = (TextView) itemView.findViewById(R.id.itemHarga);
        }
    }

    protected static class ItemFooter extends RecyclerView.ViewHolder {
        TextView txtTotal, txtTahun;
        PieChart pieChart, pieChart2;
        TriStateToggleButton toggleButton;

        public ItemFooter(View itemView) {
            super(itemView);
            txtTotal = (TextView) itemView.findViewById(R.id.footerHargaTotal);
            pieChart = (PieChart) itemView.findViewById(R.id.piechart);
            toggleButton = (TriStateToggleButton) itemView.findViewById(R.id.toogleTahun);
            txtTahun = (TextView) itemView.findViewById(R.id.tahunLabel);
            //pieChart2 = (PieChart) itemView.findViewById(R.id.piechart2);
        }
    }
}
