package id.ac.umy.unires.sicurezza.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PoinModel implements Parcelable {

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

    private PoinModel(Parcel in) {
        id = in.readString();
        nama = in.readString();
        kamar = in.readString();
        poin = in.readString();
    }

    public static final Creator<PoinModel> CREATOR = new Creator<PoinModel>() {
        @Override
        public PoinModel createFromParcel(Parcel in) {
            return new PoinModel(in);
        }

        @Override
        public PoinModel[] newArray(int size) {
            return new PoinModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nama);
        dest.writeString(kamar);
        dest.writeString(poin);
    }
}
