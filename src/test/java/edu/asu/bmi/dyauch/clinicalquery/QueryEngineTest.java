/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

/**
 *
 * @author David
 */
public class QueryEngineTest extends TestCase {
    
    public QueryEngineTest(String testName) {
        super(testName);
    }

    public void testGetInstance() {
    }

    public void testInitialize() throws Exception {
    }

    public void testRunQuery() throws Exception {
        
        QueryParser qp = new QueryParser(Version.LUCENE_40, "contents",new StandardAnalyzer(Version.LUCENE_40));
        Query q1 = qp.parse("He received Lovenox 91 mg SC prior to transport and was transferred to Arbour Hospital for further care");
        QueryEngine.initialize(new File("C:\\Users\\David\\Dropbox\\School\\BMI 591 Info Retrieval\\ClinicalQuery\\cn_corpus"));
        QueryEngine qe = QueryEngine.getInstance();
        TopDocs td = qe.runQuery(q1,10);
        ScoreDoc[] sd = td.scoreDocs;
        Document doc;
        for (ScoreDoc scd: sd){
            doc = qe.getDoc(scd);
            System.out.println(doc.get("path"));
        } 
        Query q2 = q1.rewrite(qe.ir2);
        BooleanQuery q3 = new BooleanQuery();
        Set<Term> terms = new HashSet<Term>();
        q2.extractTerms(terms);
        int i = 1;
        for (Term term:terms){
            System.out.println(term.text());
            if (i % 2 == 0){
                TermQuery tq = new TermQuery(term);
                tq.setBoost(3.0f);
                q3.add(tq,BooleanClause.Occur.SHOULD);
            }
            else{
                TermQuery tq = new TermQuery(term);
                tq.setBoost(0.5f);
                q3.add(tq,BooleanClause.Occur.MUST);
            }
            
        }
        td = qe.runQuery(q3,10);
        sd = td.scoreDocs;
        for (ScoreDoc scd: sd){
            doc = qe.getDoc(scd);
            System.out.println(doc.get("path"));
        } 
    }
}
