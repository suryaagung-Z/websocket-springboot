package com.example.notification.model.repository;

import com.example.notification.model.models.Notifikasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NotifikasiRepository extends JpaRepository<Notifikasi, Long>, JpaSpecificationExecutor<Notifikasi> {
}
