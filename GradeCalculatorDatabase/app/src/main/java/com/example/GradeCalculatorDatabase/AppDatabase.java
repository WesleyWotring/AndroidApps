package com.example.GradeCalculatorDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDAO courseDAO();
}
