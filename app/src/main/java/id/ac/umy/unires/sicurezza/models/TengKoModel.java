package id.ac.umy.unires.sicurezza.models;

public class TengKoModel {

    private String namapelanggaran;
    private String point;

    public String getIdpelanggaran() {
        return idpelanggaran;
    }

    public void setIdpelanggaran(String idpelanggaran) {
        this.idpelanggaran = idpelanggaran;
    }

    private String idpelanggaran;

    public TengKoModel(String namapelanggaran, String point) {
        this.namapelanggaran = namapelanggaran;
        this.point = point;
    }

    public  TengKoModel(){

    }

    public String getNamapelanggaran() {
        return namapelanggaran;
    }

    public void setNamapelanggaran(String namapelanggaran) {
        this.namapelanggaran = namapelanggaran;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
