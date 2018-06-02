package com.example.asus.panic;

/**
 * Created by ASUS on 4/21/2018.
 */

public class Kontak {
    private	int	id;
    private	String nama;
    private	String phone;
    public Kontak( String nama, String phone) {

        this.nama = nama;
        this.phone = phone;
    }
    public Kontak(int id, String nama, String phone) {
        this.id = id;
        this.nama = nama;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
