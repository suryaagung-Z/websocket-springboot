package com.example.notification.model.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Notifikasi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String waktu;
    private String nama;
    private String status;

    public Integer getId() { return id; }
    public String getWaktu() { return waktu; }
    public String getNama() { return nama; }
    public String getStatus() { return status; }

    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setNama(String nama) { this.nama = nama; }
    public void setStatus(String status) { this.status = status; }
}
