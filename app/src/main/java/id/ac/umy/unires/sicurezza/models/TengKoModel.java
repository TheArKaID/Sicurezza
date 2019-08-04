package id.ac.umy.unires.sicurezza.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TengKoModel implements Parcelable {

    private String namapelanggaran;
    private String point;

    protected TengKoModel(Parcel in) {
        namapelanggaran = in.readString();
        point = in.readString();
        idpelanggaran = in.readString();
    }

    public static final Creator<TengKoModel> CREATOR = new Creator<TengKoModel>() {
        @Override
        public TengKoModel createFromParcel(Parcel in) {
            return new TengKoModel(in);
        }

        @Override
        public TengKoModel[] newArray(int size) {
            return new TengKoModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namapelanggaran);
        dest.writeString(point);
        dest.writeString(idpelanggaran);
    }
}
