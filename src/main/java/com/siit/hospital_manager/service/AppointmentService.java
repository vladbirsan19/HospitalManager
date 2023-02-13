package com.siit.hospital_manager.service;

import com.siit.hospital_manager.model.dto.UpdateAppointmentDto;
import com.siit.hospital_manager.repository.PatientRepository;
import com.siit.hospital_manager.exception.BusinessException;
import com.siit.hospital_manager.model.*;
import com.siit.hospital_manager.model.dto.AppointmentDto;
import com.siit.hospital_manager.model.dto.CreateAppointmentDto;
import com.siit.hospital_manager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialisationsRepository specialisationsRepository;
    private final AppointmentRepository appointmentRepository;

    public List<AppointmentDto> findAllByPatientId(Integer id, AppointmentStatus appointmentStatus) {
        List<Appointment> appointments = appointmentRepository.findAllByPatientIdAndAppointmentStatus(id, appointmentStatus);
        return appointments
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAllByDoctorId(Integer id, AppointmentStatus appointmentStatus) {
        List<Appointment> appointments = appointmentRepository.findAllByDoctorIdAndAppointmentStatus(id, appointmentStatus);
        return appointments
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAllByPatientUserNameAndActiveDoctorAndAppointmentStatus(String userName, AppointmentStatus appointmentStatus) {
        User patient = userRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")
        );

        List<Appointment> appointments = appointmentRepository.findAllByPatientIdAndDoctorIsActiveAndAppointmentStatus(patient.getId(), true, appointmentStatus);
        return appointments.stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAllByDoctorUserName(String userName, AppointmentStatus appointmentStatus) {
        User doctor = userRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")
        );

        List<Appointment> appointments = appointmentRepository.findAllByDoctorIdAndPatientIsActiveAndAppointmentStatus(doctor.getId(), true, appointmentStatus);
        return appointments.stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Transactional
    public void deleteAppointmentByIdAndPatient(Integer id, String userName) {
        Patient patient = patientRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid patient username"));

        Appointment appointment = appointmentRepository.findAppointmentByIdAndPatient(id, patient).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Appointment not found"));

        appointmentRepository.deleteByIdNativeQuery(appointment.getId());
    }

    @Transactional
    public void deleteAppointmentByIdAndDoctor(Integer id, String userName) {
        Doctor doctor = doctorRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid doctor username"));

        Appointment appointment = appointmentRepository.findAppointmentByIdAndDoctor(id, doctor).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Appointment not found"));

        appointmentRepository.deleteByIdNativeQuery(appointment.getId());
    }

    @Transactional
    public void deleteAppointmentById(Integer id) {
        appointmentRepository.deleteByIdNativeQuery(id);
    }

    public Integer createAppointment(CreateAppointmentDto createAppointmentDto) {
        appointmentRepository
                .findByDate(createAppointmentDto.getDate())
                .ifPresent(s -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment with the date " + createAppointmentDto.getDate() + " is already present");
                });
        if (LocalDateTime.now().isAfter(createAppointmentDto.getDate())) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Appointments can be created starting with current date and time " + LocalDateTime.now());
        }

        Appointment appointment = Appointment.builder()
                .patient(createAppointmentDto.getPatient())
                .doctor(createAppointmentDto.getDoctor())
                .date(createAppointmentDto.getDate())
                .appointmentStatus(AppointmentStatus.CONFIRMED)
                .build();
        return appointmentRepository.save(appointment).getId();
    }

    public CreateAppointmentDto addDoctorToCreateAppointmentDto(CreateAppointmentDto createAppointmentDto, Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor with id " + id + " not found"));
        createAppointmentDto.setDoctor(doctor);
        return createAppointmentDto;
    }

    public CreateAppointmentDto addPatientToCreateAppointmentDto(CreateAppointmentDto createAppointmentDto, String userName) {
        Patient patient = patientRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid patient userName"));
        createAppointmentDto.setPatient(patient);
        return createAppointmentDto;
    }

    public Appointment findAppointmentById(Integer id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        return appointment;
    }
    public void updateAppointment(UpdateAppointmentDto updateAppointmentDto) {
        Appointment appointment = appointmentRepository
                .findById(updateAppointmentDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Appointment with id " + updateAppointmentDto.getId() + " not found"));

        if(updateAppointmentDto.getNote() != null) {
            appointment.setNote(updateAppointmentDto.getNote());
        }

        if(updateAppointmentDto.getDiagnostic() != null) {
            appointment.setDiagnostic(updateAppointmentDto.getDiagnostic());
        }
        if(updateAppointmentDto.getTreatment() != null) {
            appointment.setTreatment(updateAppointmentDto.getTreatment());
        }
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);

        appointmentRepository.save(appointment);
    }
    public List<AppointmentDto> findAllByDoctorAndStatus(String doctorName, AppointmentStatus appointmentStatus) {
        Doctor doctor = doctorRepository.findByName(doctorName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Doctor not found")
        );

        List<Appointment> appointments = appointmentRepository.findAllByDoctorAndAppointmentStatus(doctor, appointmentStatus);

        return appointments.stream()
                .map(Appointment::toDto)
                .toList();

    }
    public List<AppointmentDto> findAllByUserName(String userName, AppointmentStatus appointmentStatus) {
        User patient = userRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")
        );

        List<Appointment> appointments = appointmentRepository.findAllByPatientIdAndAppointmentStatus(patient.getId(), appointmentStatus);
        return appointments.stream()
                .map(Appointment::toDto)
                .toList();

    }
}
