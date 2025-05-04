package com.example.demo;

import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.PrePersist;

// ada import yang kurang !
@Entity
public class ModelKaryawan {
    @Id
    // Auto Increment
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private String nip;
    private String nama;
    private Double gaji;

    public String getId() {
        return nip;
    }

    public void setId(String id) {
        this.nip = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Double getGaji() {
        return gaji;
    }

    public void setGaji(Double gaji) {
        this.gaji = gaji;
    }

}
