package net.exkazuu.mimicdance.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "PreQuestionnaireValue")
public class PreQuestionnaireValue extends Model {
    @Column(name = "id")
    public String id;

    @Column(name = "age")
    public int age;

    @Column(name = "interest")
    public int interest;

    @Column(name = "enjoyable")
    public int enjoyable;

    @Column(name = "dekisou")
    public int dekisou;

    @Column(name = "useful")
    public int useful;

    @Column(name = "sex")
    public String sex;

    @Column(name = "knowProg")
    public String knowProg;

    @Column(name = "knowMimic")
    public String knowMimic;

}

