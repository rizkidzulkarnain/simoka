package id.co.pln.simoka.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.apache.commons.text.WordUtils;
import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import id.co.pln.simoka.R;
import id.co.pln.simoka.TerkontrakActivity;
import id.co.pln.simoka.classumum.alertDialog;
import id.co.pln.simoka.classumum.config;
import id.co.pln.simoka.classumum.database;
import id.co.pln.simoka.classumum.downloadFTP;
import id.co.pln.simoka.classutama.InvestasiClass;
import id.co.pln.simoka.classutama.TerkontrakClass;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Suleiman on 03/02/17.
 */

public class TerkontrakDetAdapter extends RecyclerView.Adapter<TerkontrakDetAdapter.ItemTerkontrak> implements Filterable {

    //  Data
    private List<InvestasiClass> _listTerkontrak;
    private List<InvestasiClass> _ListTerkontrakFiltered;

    private JSONObject _JsonObj;
    private Context context;

    public TerkontrakDetAdapter(Context context) {
        this.context = context;
        preprareTerkontrakDetail( );
        _ListTerkontrakFiltered = _listTerkontrak;
    }

    private void preprareTerkontrakDetail() {
        config._Database = new database( );
        String aurl = config._BaseIP + "Terkontrak2/terkontrak/" + config.data_tahun;
        _JsonObj = config._Database.getData(aurl);

        InvestasiClass investasiClass;
        try {
            if (_JsonObj != null) {
                _listTerkontrak = new ArrayList<>( );
                JSONArray ajsonarr = new JSONArray(_JsonObj.getString("listterkontrak"));
                for (int i = 0; i < ajsonarr.length( ); i++) {
                    JSONObject adata = ajsonarr.getJSONObject(i);
                    String anamaPekerjaan = capitalizeFirstEachWord(adata.getString("nama_pekerjaan"));
                    String anilaiSPK = "Rp." + convertStringToCurr(adata.getString("nilai_spk"));


                    investasiClass = new InvestasiClass( );
                    investasiClass.setNomor(String.valueOf(i + 1));
                    investasiClass.setNamaPekerjaan(anamaPekerjaan);
                    investasiClass.setNilaiSPK(anilaiSPK);
                    investasiClass.setNomorSPK(adata.getString("no_spk"));
                    investasiClass.setNomorSPJ(adata.getString("no_spj"));
                    investasiClass.setTglAwal(adata.getString("tgl_awal"));
                    investasiClass.setTglAkhir(adata.getString("tgl_akhir"));
                    investasiClass.setPelaksana(adata.getString("pelaksana"));
                    investasiClass.setProgres(adata.getString("progress") + "%");
                    investasiClass.setJenis(adata.getString("jenis_basket"));
                    investasiClass.setTipe(adata.getString("tipe"));

                    //dikomen karena keterangan berisikan status terbayar
                    //sedangkan status terbayar masuk pada bagian terbayar
                    //investasiClass.setKeterangan(adata.getString("keterangan"));
                    _listTerkontrak.add(investasiClass);
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
    public ItemTerkontrak onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext( ))
                .inflate(R.layout.cv_terkontrak_detail, parent, false);

        return new ItemTerkontrak(v);
    }

    @Override
    public void onBindViewHolder(ItemTerkontrak holder, int position) {
        final InvestasiClass investasiClass = _ListTerkontrakFiltered.get(position);

        holder.txtNomor.setText(String.valueOf(investasiClass.getNomor( )));
        holder.txtNoSPK.setText(investasiClass.getNomorSPK( ));
        holder.txtNoSPJ.setText(investasiClass.getNomorSPJ( ));
        holder.txtNamaPekerjaan.setText(investasiClass.getNamaPekerjaan( ));
        holder.txtTglAwal.setText(investasiClass.getTglAwal( ));
        holder.txtTglAkhir.setText(String.valueOf(investasiClass.getTglAkhir( )));
        holder.txtPelaksana.setText(investasiClass.getPelaksana( ));
        holder.txtNilaiSPK.setText(investasiClass.getNilaiSPK( ));
        holder.txtProgress.setText(investasiClass.getProgres( ));
        holder.txtJenisBasket.setText(investasiClass.getJenis( ));
        holder.txtTipe.setText(String.valueOf(investasiClass.getTipe( )));
        holder.txtNoSPK.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                String ahasil = "";
                String afolder = "/simoka/";
                String afilespk = holder.txtNoSPK.getText( ).toString( );

                File storageDir = new File(Environment.getExternalStorageDirectory( ).getAbsolutePath( ) + afolder);
                if (!storageDir.exists( )) {
                    storageDir.mkdir( );
                }

                downloadFTP aftpDownload = new downloadFTP(context);
                try {
                    aftpDownload.execute(
                            "ftp.kembangchristapharma.com",
                            "u153202858",
                            "h451lk4ry4",
                            "test/assets/file_spk/",
                            "/storage/emulated/0" + afolder,
                            afilespk);
                } catch (Exception e) {
                    e.printStackTrace( );
                }
            }
        });

        if (!investasiClass.getTglAkhir( ).equals("") && cekTanggal(investasiClass.getTglAkhir( ))) {
            holder.cardView.setCardBackgroundColor(context.getResources( ).getColor(R.color.error));
        }else{
            holder.cardView.setCardBackgroundColor(context.getResources( ).getColor(R.color.regular));
        }
        //holder.txtKeternagan.setText(investasiClass.getKeterangan( ));
    }

    //proses untuk pengecekan tanggal apakah kurang dari 2 minggu dari sekarang
    //jika iya maka diberikan warna yang berbeda di bagian cardviewnya
    protected boolean cekTanggal(String itanggalAkhir) {
        boolean astat = false;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date atodayDate = new Date( );
        Date atglakhir = null;
        try {
            atglakhir = formatter.parse(itanggalAkhir);
        } catch (ParseException e) {
            e.printStackTrace( );
        }

        long diffInMillies = Math.abs(atglakhir.getTime( ) - atodayDate.getTime( ));
        long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        //14 for two weeks
        if (diffDays <= 14) {
            astat = true;
        }
        return astat;
    }

    protected boolean createFolder() {
        boolean astat = false;

        File storageDir = new File(Environment.getExternalStorageDirectory( ).getAbsolutePath( ) + "/file/");
        if (!storageDir.exists( )) {
            if (storageDir.mkdirs( )) {
                astat = true;
            }
        }
        return astat;
    }

    protected File createImageFile() throws IOException {
        //untuk menambahkan nama file
        String nameImg = "Testing"; //getNamaImg
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date( ));
        String imageFileName = nameImg + "_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory( ).getAbsolutePath( ) + "/dishub/");

        if (!storageDir.exists( )) {
            if (!storageDir.mkdirs( )) {
                Log.d("dishub", "failed to create directory");
                return null;
            }
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


//        _PhotoPath.set_path("file:" + image.getAbsolutePath());
//        _PhotoPath.set_filename(image.getName());
        return image;
    }

    @Override
    public int getItemCount() {
        return _ListTerkontrakFiltered != null ? _ListTerkontrakFiltered.size( ) : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter( ) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString( );
                if (charString.isEmpty( )) {
                    _ListTerkontrakFiltered = _listTerkontrak;
                } else {
                    _ListTerkontrakFiltered = _listTerkontrak;
                    List<InvestasiClass> filteredList = new ArrayList<InvestasiClass>( );
                    for (InvestasiClass investasiClass : _ListTerkontrakFiltered) {
                        //search by no spk
                        if (investasiClass.getNomorSPK( ).toLowerCase( ).contains(charString.toLowerCase( ))) {
                            filteredList.add(investasiClass);
                        }
                    }
                    _ListTerkontrakFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults( );
                filterResults.values = _ListTerkontrakFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _ListTerkontrakFiltered = (List<InvestasiClass>) filterResults.values;
                notifyDataSetChanged( );
            }
        };
    }

    protected static class ItemTerkontrak extends RecyclerView.ViewHolder {
        TextView txtNomor, txtNoSPK, txtNoSPJ, txtNamaPekerjaan, txtTglAwal, txtTglAkhir,
                txtPelaksana, txtNilaiSPK, txtProgress, txtJenisBasket, txtTipe, txtKeternagan;
        CardView cardView;

        public ItemTerkontrak(View itemView) {
            super(itemView);

            txtNomor = (TextView) itemView.findViewById(R.id.number);
            txtNoSPK = (TextView) itemView.findViewById(R.id.no_spk);
            txtNoSPJ = (TextView) itemView.findViewById(R.id.no_spj);
            txtNamaPekerjaan = (TextView) itemView.findViewById(R.id.nama_pekerjaan);
            txtTglAwal = (TextView) itemView.findViewById(R.id.tgl_awal);
            txtTglAkhir = (TextView) itemView.findViewById(R.id.tgl_akhir);
            txtPelaksana = (TextView) itemView.findViewById(R.id.pelaksana);
            txtNilaiSPK = (TextView) itemView.findViewById(R.id.nilai_spk);
            txtProgress = (TextView) itemView.findViewById(R.id.progress);
            txtJenisBasket = (TextView) itemView.findViewById(R.id.jenis_basket);
            txtTipe = (TextView) itemView.findViewById(R.id.tipe);
            cardView = (CardView) itemView.findViewById(R.id.parent_terkontrak);
            //txtKeternagan = (TextView) itemView.findViewById(R.id.keterangan);
        }
    }
}
