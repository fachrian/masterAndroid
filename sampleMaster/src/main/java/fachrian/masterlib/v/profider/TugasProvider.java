package fachrian.masterlib.v.profider;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class TugasProvider {
    private String ID_TUGAS;
    private String JUDUL;
    private String ALAMAT;
    private String DATE;
    private String DERAJAT_X;
    private String DERAJAT_Y;
    private String NAMA;
    private String TLP;
    private String KETERANGAN;
    private String STATUS;


    public TugasProvider(String ID_TUGAS, String JUDUL, String ALAMAT, String DATE,
                         String DERAJAT_X, String DERAJAT_Y, String NAMA, String TLP,
                         String KETERANGAN, String STATUS) {
        this.ID_TUGAS = ID_TUGAS;
        this.JUDUL = JUDUL;
        this.ALAMAT = ALAMAT;
        this.DATE = DATE;
        this.DERAJAT_X = DERAJAT_X;
        this.DERAJAT_Y = DERAJAT_Y;
        this.NAMA = NAMA;
        this.TLP = TLP;
        this.KETERANGAN = KETERANGAN;
        this.STATUS = STATUS;

    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getKETERANGAN() {
        return KETERANGAN;
    }

    public String getNAMA() {
        return NAMA;
    }

    public String getTLP() {
        return TLP;
    }

    public String getID_TUGAS() {
        return ID_TUGAS;
    }

    public String getJUDUL() {
        return JUDUL;
    }

    public String getALAMAT() {
        return ALAMAT;
    }

    public String getDATE() {
        return DATE;
    }

    public String getDERAJAT_X() {
        return DERAJAT_X;
    }

    public String getDERAJAT_Y() {
        return DERAJAT_Y;
    }

}
