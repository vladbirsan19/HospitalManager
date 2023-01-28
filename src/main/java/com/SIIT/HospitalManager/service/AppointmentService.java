package com.SIIT.HospitalManager.service;
import com.SIIT.HospitalManager.exception.BusinessException;
import com.SIIT.HospitalManager.model.Appointment;
import com.SIIT.HospitalManager.model.Patient;
import com.SIIT.HospitalManager.model.dto.AppointmentDto;
import com.SIIT.HospitalManager.model.User;
import com.SIIT.HospitalManager.repository.AppointmentsRepository;
import com.SIIT.HospitalManager.repository.DoctorRepository;
import com.SIIT.HospitalManager.repository.PatientRepository;
import com.SIIT.HospitalManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentsRepository appointmentsRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<AppointmentDto> findAllByPatientId(Integer id) {
        List<Appointment> appointments = appointmentsRepository.findAllByPatientId(id);

        return appointments
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAll() {
        return appointmentsRepository.findAll()
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    public List<AppointmentDto> findAllByUserName(String userName) {
        User patient = userRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "User not found")
        );

        List<Appointment> appointments = appointmentsRepository.findAllByPatientId(patient.getId());
        return appointments.stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Transactional
    public void deleteAppointmentByIdAndPatient(Integer id, String userName) {
        Patient patient = patientRepository.findByUserName(userName).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Invalid patient id"));

        Appointment appointment = appointmentsRepository.findAppointmentByIdAndPatient(id, patient).orElseThrow(
                () -> new BusinessException(HttpStatus.NOT_FOUND, "Appointment not found"));

        appointmentsRepository.deleteByIdNativeQuery(appointment.getId());
    }

    public List<AppointmentDto> create(String name) {

        return null;
    }
}
