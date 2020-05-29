package com.bi183.yuda;

// membuat object Buku
public class Book {
    private Integer idbuku;
    private String judul, penulis, penerbit, tahun, tebal, isbn;
    private byte[] img;

    public Book(Integer idbuku, String judul, String penulis, String penerbit, String tahun, String tebal, String isbn, byte[] img) {
        this.idbuku = idbuku;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.tebal = tebal;
        this.isbn = isbn;
        this.img = img;
    }

    public Integer getIdbuku() {
        return idbuku;
    }

    public void setIdbuku(Integer idbuku) {
        this.idbuku = idbuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getTebal() {
        return tebal;
    }

    public void setTebal(String tebal) {
        this.tebal = tebal;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
