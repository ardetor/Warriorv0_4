package com.accypiter.warriorv0_4;

import java.util.ArrayList;

public class BodyLimb {
    //Admin
    String name;
    String Name;

    //Parent reference
    BodyBase parent;

    //Child parts -- in order of increasing distance. Index 0 will be the shoulder/hip/neck.
    ArrayList<BodyPart> parts;

    //It has no innate statistics.

    //Aggregate statistics -- to be updated on each tick/SaveGame.update()
    double size;                   //Total size of components

    double damageTotal;            //For effectiveness calculation purposes
    double effectiveness;          //TAKES INTO ACCOUNT BODY AND HEAD, though to a smaller extent.

    double worstSharp;             //For diagnostic screen purposes
    double worstBleed;
    double worstBlunt;
    ArrayList<Integer> severed;    //Empty if nothing is severed. Updates to include index numbers of severed parts.
    ArrayList<Integer> fractured;  //Same.






    //Constructor
    public BodyLimb(String name, String Name, BodyBase parent){
        this.name = name;
        this.Name = Name;
        this.parent = parent;
        this.parts = new ArrayList<BodyPart>();

        this.size = 0;
        this.damageTotal = 0;
        this.effectiveness = 0;

        this.worstSharp = 0;
        this.worstBleed = 0;
        this.worstBlunt = 0;

        this.severed = new ArrayList<Integer>();
        this.fractured = new ArrayList<Integer>();

    }

    public BodyLimb(String name, BodyBase parent){
        this(name, name.substring(0,1).toUpperCase() + name.substring(1), parent);
    }















    //Updates aggregate statistics based on its parts
    public void update(){

    }

/*    public BodyPart selectRandom(){
        //Returns a random member of this.parts

    }*/



}
