package id.ac.umy.unires.sicurezza.models;

public class TengKoModel {

    String namapelanggaran, point;

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
