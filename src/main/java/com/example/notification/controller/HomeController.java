package com.example.notification.controller;

import com.example.notification.model.models.Siswa;
import com.example.notification.model.models.Notifikasi;
import com.example.notification.model.repository.SiswaRepository;
import com.example.notification.model.repository.NotifikasiRepository;
import com.example.notification.service.MultiResponse;
import com.example.notification.service.NotifResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {
    private final SimpMessagingTemplate messagingTemplate;
    private final SiswaRepository siswaRepository;
    private final NotifikasiRepository notifikasiRepository;

    @Autowired
    public HomeController(SimpMessagingTemplate messagingTemplate, SiswaRepository siswaRepository, NotifikasiRepository notifikasiRepository){
        this.messagingTemplate = messagingTemplate;
        this.siswaRepository = siswaRepository;
        this.notifikasiRepository = notifikasiRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public MultiResponse getMessage(@Payload Map<String, String> payload) throws InterruptedException {
        Thread.sleep(1000);

        String id = payload.get("id");
        String status = payload.get("status");

        Optional<Siswa> optionalSiswa = siswaRepository.findById(Long.parseLong(id));
        optionalSiswa.ifPresent(siswa -> {
            siswa.setStatus(Integer.parseInt(status));
            siswaRepository.save(siswa);
        });

        String namaSiswa = optionalSiswa.map(Siswa::getNama).orElse("Error Unknown");
        int siswaStatus = optionalSiswa.map(Siswa::getStatus).orElse(0);
        String approvalStatus = siswaStatus == 0 ? "Reject" : "Approve";

        Notifikasi statusEntity = new Notifikasi();
        statusEntity.setWaktu(LocalDateTime.now().toString());
        statusEntity.setNama(namaSiswa);
        statusEntity.setStatus(approvalStatus);
        notifikasiRepository.save(statusEntity);

        NotifResponse notifResponse = new NotifResponse();
        notifResponse.setNotifikasis(notifikasiRepository.findAll());

        MultiResponse multiResponse = new MultiResponse();
        multiResponse.setNama(namaSiswa);
        multiResponse.setStatus(approvalStatus);
        multiResponse.setSiswa(siswaRepository.findAll());
        multiResponse.setNotifResponse(notifResponse);
        return multiResponse;
    }

    @MessageMapping("/global")
    @SendTo("/topic/global")
    public NotifResponse getGlobal(@Payload Map<String, String> payload) throws InterruptedException{
        Thread.sleep(1000);

        NotifResponse notifResponse = new NotifResponse();
        List<Notifikasi> notifikasis = notifikasiRepository.findAll();
        notifResponse.setNotifikasis(notifikasis);
        return notifResponse;
    }
}