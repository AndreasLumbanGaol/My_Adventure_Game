package model;

public class Item {

    private int id;
    private String name;
    private int tipe;
    private int nilaiAttack;
    private int nilaiDefense;
    private String deskripsi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public int getNilaiAttack() {
        return nilaiAttack;
    }

    public void setNilaiAttack(int nilaiAttack) {
        this.nilaiAttack = nilaiAttack;
    }

    public int getNilaiDefense() {
        return nilaiDefense;
    }

    public void setNilaiDefense(int nilaiDefense) {
        this.nilaiDefense = nilaiDefense;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
