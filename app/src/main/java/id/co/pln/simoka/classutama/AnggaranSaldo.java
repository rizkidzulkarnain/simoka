package id.co.pln.simoka.classutama;

import java.text.DecimalFormat;

/**
 * Created by 4741G on 17/02/2018.
 */

public class AnggaranSaldo {

    private String paguAnggaran;
    private String jumlahPengeluaran;
    private String saldoAkhir;
    private String saldoPersen;

    public String getSaldoPersen(){
        double asaldoPersen;
        Long asaldoAkhir = (Long.parseLong(saldoAkhir));
        Long apaguAnggaran = (Long.parseLong(paguAnggaran));
        asaldoPersen = (double) asaldoAkhir / apaguAnggaran;
        asaldoPersen = asaldoPersen * 100;
        saldoPersen =   new DecimalFormat("##.##").format(asaldoPersen);
        return saldoPersen;
    }

    public String getPaguAnggaran() {
        return paguAnggaran;
    }

    public void setPaguAnggaran(String paguAnggaran) {
        this.paguAnggaran = paguAnggaran;
    }

    public String getJumlahPengeluaran() {
        return jumlahPengeluaran;
    }

    public void setJumlahPengeluaran(String jumlahPengeluaran) {
        this.jumlahPengeluaran = jumlahPengeluaran;
    }

    public String getSaldoAkhir() {
        return saldoAkhir;
    }

    public void setSaldoAkhir(String saldoAkhir) {
        this.saldoAkhir = saldoAkhir;
    }
}
