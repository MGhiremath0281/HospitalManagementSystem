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
        // Check username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create User
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        // Create Patient
        Patient patient = new Patient();
        patient.setFullName(dto.getFullName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setEmail(dto.getEmail()); // maps to email_address in DB
        patient.setAdmissionDate(dto.getAdmissionDate());
        patient.setUser(user);
        patientRepository.save(patient);

        return "Patient registered successfully";
    }

    @Override
    public String login(LoginDTO dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if (userOpt.isEmpty() || !passwordEncoder.matches(dto.getPassword(), userOpt.get().getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return "Login successful for user: " + userOpt.get().getUsername();
    }

    @Override
    public String registerDoctor(DoctorRegisterDTO dto) {
        // Generate a unique username for doctor (or add username/password fields in
        // DTO)
        String username = dto.getName().toLowerCase().replace(" ", "") + System.currentTimeMillis() % 1000;

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Generated username already exists. Try again.");
        }

        // Create User
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("default123")); // default password
        user.setRole(Role.DOCTOR);
        userRepository.save(user);

        // Create Doctor
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpacility(dto.getSpacility());
        doctor.setUser(user);
        doctorRepository.save(doctor);

        return "Doctor registered successfully with username: " + username + " and default password: default123";
    }
}
