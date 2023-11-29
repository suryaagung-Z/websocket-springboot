package com.example.notification.model.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public class Siswa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nama;

    private String kelas;

    private Integer status;

    public String getId(){ return Integer.toString(this.id); }
    public String getNama(){ return this.nama; }
    public String getKelas(){ return this.kelas; }
    public Integer getStatus(){ return this.status; }


    public void setNama(String nama){
        this.nama = nama;
    }
    public void setKelas(String kelas){
        this.kelas = kelas;
    }
    public void setStatus(Integer status){
        this.status = status;
    }
}
