package com.example.schoolapp;

public class School {
    private int _id;
    private String Schoolname;
    private String SchoolAddress;
    private String SchoolForm;
    private String SchoolSpec;
    private byte[] SchoolLogo;


    public School() {
    }

    public School(int _id, String schoolname, String schoolAddress, String schoolForm, String schoolSpec, byte[] schoolLogo) {
        this._id = _id;
        Schoolname = schoolname;
        SchoolAddress = schoolAddress;
        SchoolForm = schoolForm;
        SchoolSpec = schoolSpec;
        SchoolLogo = schoolLogo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSchoolname() {
        return Schoolname;
    }

    public void setSchoolname(String schoolname) {
        Schoolname = schoolname;
    }

    public String getSchoolAddress() {
        return SchoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        SchoolAddress = schoolAddress;
    }

    public String getSchoolForm() {
        return SchoolForm;
    }

    public void setSchoolForm(String schoolForm) {
        SchoolForm = schoolForm;
    }

    public String getSchoolSpec() {
        return SchoolSpec;
    }

    public void setSchoolSpec(String schoolSpec) {
        SchoolSpec = schoolSpec;
    }

    public byte[] getSchoolLogo() {
        return SchoolLogo;
    }

    public void setSchoolLogo(byte[] schoolLogo) {
        SchoolLogo = schoolLogo;
    }
}
