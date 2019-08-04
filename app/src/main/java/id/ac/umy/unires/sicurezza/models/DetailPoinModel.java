package id.ac.umy.unires.sicurezza.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailPoinModel implements Parcelable {

    private String penjelasan;
    private String tanggal;
    private String poin;

    private DetailPoinModel(Parcel in) {
        penjelasan = in.readString();
        tanggal = in.readString();
        poin = in.readString();
        keterangan = in.readString();
    }

    public static final Creator<DetailPoinModel> CREATOR = new Creator<DetailPoinModel>() {
        @Override
        public DetailPoinModel createFromParcel(Parcel in) {
            return new DetailPoinModel(in);
        }

        @Override
        public DetailPoinModel[] newArray(int size) {
            return new DetailPoinModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(penjelasan);
        dest.writeString(tanggal);
        dest.writeString(poin);
        dest.writeString(keterangan);
    }
}
