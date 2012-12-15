/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author David
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
        }
    }

    public void testGetSynonymSets() {
    }

    public void testGetSynonymSetById() {
    }
}
