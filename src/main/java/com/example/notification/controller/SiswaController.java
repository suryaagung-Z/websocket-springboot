package com.example.notification.controller;

import com.example.notification.model.repository.NotifikasiRepository;
import com.example.notification.model.repository.SiswaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiswaController {

    private final SiswaRepository siswaRepository;

    public SiswaController(SiswaRepository siswaRepository){
        this.siswaRepository = siswaRepository;
    }
    @GetMapping("/siswa")
    public String index(Model model) {
        model.addAttribute("siswa", siswaRepository.findAll());
        return "siswa";
    }
}
