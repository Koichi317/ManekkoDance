package net.exkazuu.mimicdance.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "PreQuestionnaireResult")
public class PreQuestionnaireResult extends Model {
    @Column(name = "ExamineeId")
    public String examineeId;

    @Column(name = "Age")
    public int age;

    @Column(name = "Interest")
    public int interest;

    @Column(name = "Fun")
    public int fun;

    @Column(name = "Feasibility")
    public int feasibility;

    @Column(name = "Usefulness")
    public int usefulness;

    @Column(name = "Sex")
    public String sex;

    @Column(name = "KnowledgeOfProgramming")
    public String knowledgeOfProgramming;

    @Column(name = "KnowledgeOfMimicDance")
    public String knowledgeOfMimicDance;
}

