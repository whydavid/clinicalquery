/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author David
 */
public class QueryEngine {
    private static QueryEngine qe;
    
    public static QueryEngine getInstance(){
        return qe;
    }
    
    public static void initialize(File file) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        qe = new QueryEngine(file);
    }
    private Directory dir;
    private IndexSearcher is;
    private QueryEngine(File path) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        dir = new RAMDirectory();
        StandardAnalyzer sa = new StandardAnalyzer(Version.LUCENE_40);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, sa);
        IndexWriter iw = new IndexWriter(dir, iwc);

        File[] files;
        files = path.listFiles();
        iwc.setSimilarity(new DefaultSimilarity());
        FileInputStream fis;
        for (File fi:files){
            fis = new FileInputStream(fi);
            Document doc = new Document();
            doc.add(new StringField("path", fi.getAbsolutePath(), Field.Store.YES));
            doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
            iw.addDocument(doc);
            fis.close();
        }
        iw.close();
        IndexReader ir = DirectoryReader.open(dir);
        is = new IndexSearcher(ir);
        is.setSimilarity(new DefaultSimilarity());
    }
    
    public TopDocs runQuery(Query q, Integer numResults) throws IOException{
        return is.search(q, numResults);
    }
    
    public Document getDoc(ScoreDoc sd) throws IOException{
        return is.doc(sd.doc);
    }
    
}
