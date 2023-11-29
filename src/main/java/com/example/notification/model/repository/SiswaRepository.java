package com.example.notification.model.repository;

import com.example.notification.model.models.Siswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SiswaRepository extends JpaRepository<Siswa, Long>, JpaSpecificationExecutor<Siswa> {
}
