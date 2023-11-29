package com.example.notification.service;

import com.example.notification.model.models.Siswa;
import lombok.Data;

import java.util.List;

@Data
public class MultiResponse {
    private String nama;
    private String _status;
    private List<Siswa> siswa;
    private NotifResponse notifResponse;

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setStatus(String status) {
        this._status = status;
    }

    public void setSiswa(List<Siswa> siswa) {
        this.siswa = siswa;
    }

    public void setNotifResponse(NotifResponse notifResponse) { this.notifResponse = notifResponse; }
}
