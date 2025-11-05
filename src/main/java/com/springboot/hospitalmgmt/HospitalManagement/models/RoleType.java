package com.springboot.hospitalmgmt.HospitalManagement.models;

public enum RoleType {
    PATIENT,
    DOCTOR,
    STAFF,
    ADMIN;

    // This method is used to provide Spring Security compatible role names
    public String getName() {
        return "ROLE_" + this.name();
    }
}
