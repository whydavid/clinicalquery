package edu.asu.bmi.dyauch.clinicalquery;

import edu.asu.bmi.dyauch.clinicalquery.ui.*;

/**
 * Launches app and interprets command line parameters.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
        
    }
}
