package com.springboot.hospitalmgmt.HospitalManagement.models;

public enum RoleType {
    ROLE_ADMIN,
    ROLE_DOCTOR,
    ROLE_PATIENT,
    ROLE_STAFF;

    public String getName(){
        return this.name;
    }
}
