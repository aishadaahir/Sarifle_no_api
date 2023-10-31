package com.example.sarifle.api;

public class Post {
    private String  _id , Name ,Sarifle , Tell,Tell2,Acountnum,Datereg;

    public Post(String id,String Name,String Sarifle, String Tell,String Tell2,String Acountnum, String Datereg) {
        this._id = id;
        this.Sarifle = Sarifle;
        this.Name = Name;
        this.Tell = Tell;
        this.Tell2 = Tell2;
        this.Acountnum = Acountnum;
        this.Datereg = Datereg;
    }


    public String get_id() {
        return _id;
    }

    public String getName() {
        return Name;
    }

    public String getSarifle() {
        return Sarifle;
    }

    public String getTell() {
        return Tell;
    }

    public String getTell2() {
        return Tell2;
    }

    public String getAccountnum() {
        return Acountnum;
    }

    public String getDatereg() {
        return Datereg;
    }
}
