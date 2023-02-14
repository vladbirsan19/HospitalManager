package com.siit.hospital_manager.service;


import com.siit.hospital_manager.model.dto.CreateAppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    String dateFormat = "dd-MM-yyyy HH:mm";

    public void sendAppointmentConfirmationEmail(CreateAppointmentDto createAppointmentDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        String date = createAppointmentDto.getDate().format(DateTimeFormatter.ofPattern(dateFormat));
        message.setTo(createAppointmentDto.getPatient().getEmail());
        message.setSubject("Appointment Confirmation");
        message.setText("Your appointment has been confirmed. We are waiting for you on: " + date);
        javaMailSender.send(message);
    }

}