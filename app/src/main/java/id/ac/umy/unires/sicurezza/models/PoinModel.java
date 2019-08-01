package id.ac.umy.unires.sicurezza.models;

public class PoinModel {

    private String id;
    private String nama;
    private String kamar;
    private String poin;

    public PoinModel(String id, String nama, String kamar, String poin) {
        this.id = id;
        this.nama = nama;
        this.kamar = kamar;
        this.poin = poin;
    }

    public PoinModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKamar() {
        return kamar;
    }

    public void setKamar(String kamar) {
        this.kamar = kamar;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

}
