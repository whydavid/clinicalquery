/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.POS;
import java.util.List;

/**
 *
 * @author David
 */
public interface IDict {
    public List<Word> stemWord(String word);
    public List<ISynsetID> getSynonymSets(Word word);
    public List<String> getSynonymSetById(ISynsetID ID);
}
