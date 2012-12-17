/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery.ui;

import edu.asu.bmi.dyauch.clinicalquery.Dict;
import edu.asu.bmi.dyauch.clinicalquery.QueryEngine;
import edu.asu.bmi.dyauch.clinicalquery.Word;
import edu.mit.jwi.item.ISynsetID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;

/**
 *
 * @author David
 */
public class SynSetChooser extends javax.swing.JFrame {

    /**
     * Creates new form SynSetChooser
     */
    
    private BooleanQuery query;
    private String fq;
    private HashSet<Term> allTerms;
    private HashSet<ISynsetID> wordsToTest;
    private HashSet<Term> doneTerms;
    private Dict dict;
    private ISynsetID curSyn;
    private Term curterm;
    private BooleanQuery callBackQuery;
    private Set<Term> terms;
    
    public SynSetChooser(String fq) throws ParseException, IOException {
        initComponents();
        this.fq = fq;
        dict = Dict.getInstance();
        wordsToTest = new HashSet<ISynsetID>();
        allTerms = new HashSet<Term>();
        doneTerms = new HashSet<Term>();
        query = new BooleanQuery();
    }
    
    public void getUserInput(BooleanQuery bq) throws ParseException, IOException{
        //A lot of this code can be moved to its own method so it doesn't run every time...but this is a low priority.
        if (bq != null){
            callBackQuery = bq;
        }
        QueryParser qp = new QueryParser(Version.LUCENE_40, "contents",new StandardAnalyzer(Version.LUCENE_40));
        Query q1 = qp.parse(fq);
        Query q2 = q1.rewrite(QueryEngine.getInstance().ir2);
        BooleanQuery q3 = new BooleanQuery();
        terms = new HashSet<Term>();
        ArrayList<Word> results;
        q2.extractTerms(terms);
        int i=0; //Part of an ugly hack to escape a loop....
        if (doneTerms.size() == terms.size() && wordsToTest.isEmpty()){
            this.setVisible(false); //CHANGE THIS
            callBackQuery = getExpandedQuery();
        }
        else {
            if (wordsToTest.isEmpty())
            {
                for (Term term:terms){
                    if(!doneTerms.isEmpty() && doneTerms.contains(term)){
                
                    } else {
                    if (i < 1){
                    results = (ArrayList<Word>)dict.stemWord(term.text());
                
                    for (Word wd:results){
                        wd.findSynsets(dict);
                        if (wd.isInWN()){
                            for (ISynsetID isid: wd.getSynsetIDs()){
                                wordsToTest.add(isid);
                            }
                        }
                    }
                    curterm = term;
                    }
                    i++;
                    }
                }
                if (wordsToTest.isEmpty()){
                    doneTerms.add(curterm);
                    getUserInput(null);
                } else {
                ISynsetID cur = null;
                for (ISynsetID isid: wordsToTest){
                    cur = isid;                
                }
                Boolean success = wordsToTest.remove(cur);
                jTextArea1.setText(dict.getGlossBySynsetID(cur));
                lblWord.setText(curterm.text());
                curSyn = cur;
                }
            }
            else {
                ISynsetID cur = null;
                for (ISynsetID isid: wordsToTest){
                    cur = isid;                
                }
                Boolean success = wordsToTest.remove(cur);
                jTextArea1.setText(dict.getGlossBySynsetID(cur));
                lblWord.setText(curterm.text());
                curSyn = cur;
            }
        }
    }
    public BooleanQuery getExpandedQuery(){
        HashSet<String> checkUnique = new HashSet<String>();
        Boolean skip = false;
        for (Term t:allTerms){
            if (!checkUnique.isEmpty()) {
                for (String s:checkUnique){
                    if (s.equals(t.text())){
                        skip = true;
                    }
                }
            }
            if (!skip){
                checkUnique.add(t.text());
                if (t.text().contains("_")){
                    String[] tokens = t.text().split("_");
                    PhraseQuery pq = new PhraseQuery();
                    for (String tok:tokens){
                        pq.add(new Term("contents",tok));
                    }
                    query.add(pq,BooleanClause.Occur.SHOULD);
                } else {
                    TermQuery newtq = new TermQuery(t);
                    
                    for (Term te:terms){
                        if (te.text().equals(t.text())){
                            newtq.setBoost(5.0f);
                        }
                    }
                    query.add(newtq,BooleanClause.Occur.SHOULD);
                    System.out.println(t.text());    
                }
            }
            skip = false;
        }
        return query;
    }
    
    public HashSet<Term> getExpandedQueryTerms(){
        return allTerms;
    }
    
    public HashSet<Term> expandQuery(Query q) throws InterruptedException{
        HashSet<Term> ret = new HashSet<Term>();
        Thread.sleep(5000);
        return ret;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lblWord = new javax.swing.JLabel();
        btnYes = new javax.swing.JButton();
        btnNo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Does the following definition correspond with the intended sense of:");

        lblWord.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWord.setText("Word");

        btnYes.setBackground(java.awt.Color.green);
        btnYes.setText("Yes");
        btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYesActionPerformed(evt);
            }
        });

        btnNo.setBackground(new java.awt.Color(255, 0, 0));
        btnNo.setText("No");
        btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblWord)
                    .addComponent(jScrollPane1))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                        .addComponent(btnNo)
                        .addGap(62, 62, 62)
                        .addComponent(btnYes))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.CENTER))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblWord)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnYes)
                    .addComponent(btnNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYesActionPerformed
        try {
            for (String str:dict.getSynonymSetById(curSyn)){
                boolean add = allTerms.add(new Term("contents",str));
            }
            wordsToTest.clear();
            doneTerms.add(curterm);
            getUserInput(null);
        } catch (ParseException ex) {
            Logger.getLogger(SynSetChooser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SynSetChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnYesActionPerformed

    private void btnNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoActionPerformed
        wordsToTest.remove(curSyn);
        int i = 0;
        for (ISynsetID isi:wordsToTest){
            if (i < 1){
                curSyn = isi;
                i++;
            }
        }
        jTextArea1.setText(dict.getGlossBySynsetID(curSyn));
    }//GEN-LAST:event_btnNoActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNo;
    private javax.swing.JButton btnYes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblWord;
    // End of variables declaration//GEN-END:variables
}
