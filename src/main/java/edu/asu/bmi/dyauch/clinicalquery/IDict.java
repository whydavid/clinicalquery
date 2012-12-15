/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import java.util.List;

/**
 *
 * @author David
 */
public interface IDict {
    public String stemWord(String word);
    public List<String> getSynonymSets(String word);
    public List<String> getSynonymSetById(String ID);
}
