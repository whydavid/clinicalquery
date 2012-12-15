/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.IStemmer;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author David
 */
public class Dict implements IDict{

    private File WN_Location;
    private IDictionary jwi_Dict;
    private IStemmer stemmer;
    
    public Dict(File WN_Location) throws IOException{
        this.WN_Location = WN_Location;
        this.jwi_Dict = new Dictionary(WN_Location);
        Boolean dict_open = jwi_Dict.open();
        this.stemmer = new WordnetStemmer(jwi_Dict);
    }
    
    public List<Word> stemWord(String word) {
        ArrayList<Word> stemmed_words = new ArrayList<Word>();
        ArrayList<String> stems;
        Collection<String> temp;
        for (POS pos:POS.values()){    
            temp = stemmer.findStems(word,pos);
            if (!temp.isEmpty()){
                stems = (ArrayList<String>)temp;
                for (String stem:stems){
                    stemmed_words.add(new Word(word,stem,pos));
                }
            }
        }
        
        return stemmed_words;
    }

    public List<String> getSynonymSets(String word, POS pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getSynonymSetById(String ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
