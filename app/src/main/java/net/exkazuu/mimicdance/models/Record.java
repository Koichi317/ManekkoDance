package net.exkazuu.mimicdance.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Record")
public class Record {
    @Column(name = "LessonNumber")
    public int lessonNumber;

    //@Column(name = "LessonNumber")
    //public int lessonNumber;

}
