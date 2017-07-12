package com.example.nefrin.newprojectstartup;

import com.orm.SugarRecord;

/**
 * Created by Nefrin on 7/10/2017.
 */
public class myquestionstbl extends SugarRecord {

    String firsPart;
    String secondPart;
    int key;
    Boolean ispressedp;
    Boolean ispressedn;

    public myquestionstbl(){
    }

    public myquestionstbl(String first_part, String second_part, int key){
        this.firsPart = first_part;
        this.secondPart = second_part;
        this.key = key;
    }

    public String getTitle() {
        return firsPart;
    }

    public String getNote() {
        return secondPart;
    }
}
