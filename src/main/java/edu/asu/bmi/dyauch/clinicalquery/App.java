package edu.asu.bmi.dyauch.clinicalquery;

import edu.asu.bmi.dyauch.clinicalquery.ui.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Launches app and interprets command line parameters.
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        //Set up the QueryEngine and WordNet Dict(ionary)
        QueryEngine.initialize(new File(args[0]));
        Dict.initialize(new File(args[1]));
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
        
    }
}
