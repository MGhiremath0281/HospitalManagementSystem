package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.models.Role;
import com.springboot.hospitalmgmt.HospitalManagement.models.User;
import com.springboot.hospitalmgmt.HospitalManagement.repository.DocterRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.DocterService;

@Service
public class DocterServiceImpl implements DocterService {

    @Autowired
    private DocterRepository docterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Doctor createDocter(Doctor doctor) {
        // Encode password inside the linked user
        User user = doctor.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.DOCTOR); // Set role
        doctor.setUser(user);

        return docterRepository.save(doctor);
    }

    @Override
    public Page<Doctor> getAllDocters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return docterRepository.findAll(pageable);
    }

    @Override
    public Optional<Doctor> getDocterById(Long id) {
        return docterRepository.findById(id);
    }

    @Override
    public Doctor updateDocter(Long id, Doctor doctor) {
        return docterRepository.findById(id).map(existingDoctor -> {
            existingDoctor.setName(doctor.getName());
            existingDoctor.setSpacility(doctor.getSpacility());

            // Optionally update username/password
            if (doctor.getUser() != null) {
                User user = existingDoctor.getUser();
                user.setUsername(doctor.getUser().getUsername());
                if (doctor.getUser().getPassword() != null && !doctor.getUser().getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(doctor.getUser().getPassword()));
                }
                existingDoctor.setUser(user);
            }
            return docterRepository.save(existingDoctor);
        }).orElse(null);
    }

    @Override
    public void deleteDocter(Long id) {
        docterRepository.deleteById(id);
    }
}
