package id.ac.umy.unires.sicurezza.models;

public class DetailPoinModel {

    private String penjelasan;
    private String tanggal;
    private String poin;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    private String keterangan;

    public DetailPoinModel() {
    }

    public DetailPoinModel(String penjelasan, String tanggal, String poin, String keterangan) {
        this.penjelasan = penjelasan;
        this.tanggal = tanggal;
        this.poin = poin;
        this.keterangan = keterangan;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public void setPenjelasan(String penjelasan) {
        this.penjelasan = penjelasan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }
}
