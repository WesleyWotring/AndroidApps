package com.example.GradeCalculatorDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "courses")
public class Course {

    @PrimaryKey (autoGenerate = true)
    public long id;

    @ColumnInfo
    public String courseNum;

    @ColumnInfo
    public String courseName;

    @ColumnInfo
    int courseHours;

    @ColumnInfo
    char courseGrade;

    public Course(long id, String courseNum, String courseName, int courseHours, char courseGrade) {
        this.id = id;
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.courseHours = courseHours;
        this.courseGrade = courseGrade;
    }

    public Course(String courseNum, String courseName, int courseHours, char courseGrade) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.courseHours = courseHours;
        this.courseGrade = courseGrade;
    }

    public Course() {
    }


    @Override
    public String toString() {
        return "Course{" +
                "courseNum='" + courseNum + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseHours=" + courseHours +
                ", courseGrade=" + courseGrade +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    public char getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(char courseGrade) {
        this.courseGrade = courseGrade;
    }
}
