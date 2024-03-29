/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.POS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Word {
    private String surface_form;
    private String wordID;
    private String stem;
    private ArrayList<ISynsetID> synsetIDs;
    private POS pos;
    private Boolean inWN;
    
    public Word(String sf, String stem, POS pos){
        surface_form=sf;
        this.stem=stem;
        this.pos=pos;
    }
    
    public Boolean isInWN(){
        return inWN;
    }
    
    public POS getPOS(){
        return pos;
    }
    public void findSynsets(IDict dict){
        synsetIDs = (ArrayList<ISynsetID>)dict.getSynonymSets(this);
        if (synsetIDs.isEmpty()){
            inWN = false;
        }else{
            inWN = true;
        }
        
        //Not implemented yet
    }
    
    public List<ISynsetID> getSynsetIDs(){
        return synsetIDs;
    }
    
    public String getStem(){
        return stem;
    }
    
    public String getSurface(){
        return surface_form;
    }
}
