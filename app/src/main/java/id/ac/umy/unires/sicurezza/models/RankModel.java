package id.ac.umy.unires.sicurezza.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RankModel implements Parcelable {

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

    private RankModel(Parcel in) {
        nama = in.readString();
        poin = in.readString();
        usroh = in.readString();
    }

    public static final Creator<RankModel> CREATOR = new Creator<RankModel>() {
        @Override
        public RankModel createFromParcel(Parcel in) {
            return new RankModel(in);
        }

        @Override
        public RankModel[] newArray(int size) {
            return new RankModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(poin);
        dest.writeString(usroh);
    }
}
