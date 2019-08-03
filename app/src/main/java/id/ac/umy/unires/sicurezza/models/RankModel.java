package id.ac.umy.unires.sicurezza.models;

public class RankModel {

    private String nama;
    private String poin;
    private String usroh;

    public RankModel(String nama, String poin, String usroh) {
        this.nama = nama;
        this.poin = poin;
        this.usroh = usroh;
    }

    public RankModel() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getUsroh() {
        return usroh;
    }

    public void setUsroh(String usroh) {
        this.usroh = usroh;
    }
}
