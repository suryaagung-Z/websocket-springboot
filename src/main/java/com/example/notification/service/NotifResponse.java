package com.example.notification.service;

import com.example.notification.model.models.Notifikasi;
import lombok.Data;

import java.util.List;

@Data
public class NotifResponse {
    List<Notifikasi> notifikasis;

    public List<Notifikasi> getNotifikasis() {
        return notifikasis;
    }

    public void setNotifikasis(List<Notifikasi> notifikasis) {
        this.notifikasis = notifikasis;
    }
}
