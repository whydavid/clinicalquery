package edu.asu.bmi.dyauch.clinicalquery;

import edu.asu.bmi.dyauch.clinicalquery.ui.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.JFrame;

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
                UI ui = new UI();
                ui.setVisible(true);
                ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        
    }
}
