package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.DoctorRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.LoginDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.PatientRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.models.Role;
import com.springboot.hospitalmgmt.HospitalManagement.models.User;
import com.springboot.hospitalmgmt.HospitalManagement.repository.DocterRepository;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import com.springboot.hospitalmgmt.HospitalManagement.repository.UserRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DocterRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerPatient(PatientRegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        Patient patient = new Patient();
        patient.setFullName(dto.getFullName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setEmail(dto.getEmail());
        patient.setAdmissionDate(dto.getAdmissionDate());
        patient.setUser(user);
        patientRepository.save(patient);

        return "Patient registered successfully with username: " + dto.getUsername();
    }

    @Override
    public String registerDoctor(DoctorRegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.DOCTOR);
        userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpacility(dto.getSpacility());
        doctor.setUser(user);
        doctorRepository.save(doctor);

        return "Doctor registered successfully with username: " + dto.getUsername();
    }

    @Override
    public String login(LoginDTO dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if (userOpt.isEmpty() || !passwordEncoder.matches(dto.getPassword(), userOpt.get().getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return "Login successful for user: " + userOpt.get().getUsername();
    }
}
