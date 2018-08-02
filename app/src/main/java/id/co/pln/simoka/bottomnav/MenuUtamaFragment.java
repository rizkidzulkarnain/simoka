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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import id.co.pln.simoka.R;
import id.co.pln.simoka.adapter.AnggaranTotalAdapter;


public class MenuUtamaFragment extends Fragment {
    public static final String TAG = MenuUtamaFragment.class.getSimpleName();

    private static final String ARG_COLOR = "color";
    private int color;

    private RecyclerView recyclerView;
    private TextView txtTotalHarga;
    private PieChart pieChart;

    public MenuUtamaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_coor, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setBackgroundColor(getLighterColor(color));
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccentOpacity));
        AnggaranTotalAdapter adapter = new AnggaranTotalAdapter(getContext());
        recyclerView.setAdapter(adapter);

        /*txtTotalHarga = (TextView) rootView.findViewById(R.id.footerHargaTotal);
        txtTotalHarga.setText(adapter.get_TotalAnggaran());*/

        //https://www.studytutorial.in/android-pie-chart-using-mpandroid-library-tutorial
        /*pieChart = (PieChart) rootView.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        List<PieEntry> yValues = setYval();
        List<String> xValues = setXval();

        PieDataSet dataSet = new PieDataSet(yValues, "Election Results");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);*/

        return rootView;
    }

    private List<PieEntry> setYval(){
        List<PieEntry> Yval = new ArrayList<PieEntry>();
        Yval.add(new PieEntry(8f, 0));
        Yval.add(new PieEntry(15f, 1));
        Yval.add(new PieEntry(12f, 2));
        Yval.add(new PieEntry(25f, 3));
        Yval.add(new PieEntry(23f, 4));
        Yval.add(new PieEntry(17f, 5));
        return Yval;
    }

    private List<String> setXval(){
        List<String> Xval = new ArrayList<String>();
        Xval.add("January");
        Xval.add("February");
        Xval.add("March");
        Xval.add("April");
        Xval.add("May");
        Xval.add("June");
        return Xval;
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
