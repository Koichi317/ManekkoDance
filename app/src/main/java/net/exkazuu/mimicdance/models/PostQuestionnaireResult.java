package net.exkazuu.mimicdance.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by t-yokoi on 2015/08/01.
 */
@Table(name = "PostQuestionnaireResult")
public class PostQuestionnaireResult extends Model{
    @Column(name = "Id")
    public String id;

    @Column(name = "Opinion")
    public String opinion;

    @Column(name = "Gladness")
    public int gladness;

    @Column(name = "Fun")
    public int fun;

    @Column(name = "Feasibility")
    public int feasibility;

    @Column(name = "Usefulness")
    public int usefulness;

    @Column(name = "Vexation")
    public int vexation;

    @Column(name = "DesireToPlay")
    public int desireToPlay;

    @Column(name = "DesireToStudy")
    public int desireToStudy;

    @Column(name = "Time")
    public String time;
}
