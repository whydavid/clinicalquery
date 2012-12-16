/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * These tests are really poorly written (not proper unit tests) due to time constraints during initial development.
 * They really should be rewritten!
 */
public class DictTest extends TestCase {
    
    public DictTest(String testName) {
        super(testName);
    }

    public void testStemWord() throws IOException {
        File wn = new File("C:\\Users\\David\\Dropbox\\School\\BMI 591 Info Retrieval\\ClinicalQuery\\dict");
        Dict dict = new Dict(wn);
        ArrayList<Word> results = (ArrayList<Word>)dict.stemWord("hopes");
        for (Word res:results){
            System.out.println(res.getStem() + " " + res.getPOS());
            res.findSynsets(dict);
            System.out.println(res.isInWN());
            for (ISynsetID sid:res.getSynsetIDs()){
                System.out.println(sid.toString());
                for( String w : dict.getSynonymSetById(sid)){
                System.out .println(w);
                }
            }
        }
    }

    public void testGetSynonymSets() {
    }

    public void testGetSynonymSetById() {
    }
}
