package id.ac.umy.unires.sicurezza.models;

public class DetailPoinModel {

    private String penjelasan, tanggal, poin;

    public DetailPoinModel() {
    }

    public DetailPoinModel(String penjelasan, String tanggal, String poin) {
        this.penjelasan = penjelasan;
        this.tanggal = tanggal;
        this.poin = poin;
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
