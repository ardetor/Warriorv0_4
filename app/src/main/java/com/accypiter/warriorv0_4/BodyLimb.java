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
    double damageTotal;    //For effectiveness calculation purposes
    double effectiveness;  //TAKES INTO ACCOUNT BODY AND HEAD, though to a smaller extent.

    double worstSharp;     //For diagnostic screen purposes
    double worstBleed;
    double worstBlunt;
    ArrayList<Integer> severed;    //Empty if nothing is severed. Updates to include index numbers of severed parts.
    ArrayList<Integer> fractured;  //Same.





    //Constructor
    BodyLimb(){

    }


    //Updates aggregate statistics based on its parts
    public void update(){

    }

    public BodyPart selectRandom(){
        //Returns a random member of this.parts

        //placeholder return statement
        return new BodyPart();
    }



}
