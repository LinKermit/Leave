package com.android.wang.rxjavalearn.flatMap;

import java.util.List;

/**
 * Created by wang on 2016/11/23.
 */
public class Student {


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Course> courses;



    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
