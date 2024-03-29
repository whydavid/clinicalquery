/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.bmi.dyauch.clinicalquery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
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
    private QueryParser qp;
    public IndexReader ir2;
    
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
            String out = "";
            String curline;
            BufferedReader br = new BufferedReader(new FileReader(fi));
            while ((curline = br.readLine()) != null) 
            {
                out += (curline + "\n");
            }
            fis = new FileInputStream(fi);
            Document doc = new Document();
            doc.add(new StringField("path", fi.getAbsolutePath(), Field.Store.YES));
 //           doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
            
            FieldType ft = new FieldType();
            ft.setIndexed(true);
            ft.setTokenized(true);
            ft.setStoreTermVectors(true);
            doc.add(new Field("contents", out, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
            iw.addDocument(doc);
            fis.close();
        }
        iw.close();
        IndexReader ir = DirectoryReader.open(dir);
        ir2 = ir;
        is = new IndexSearcher(ir);
        is.setSimilarity(new DefaultSimilarity()); 
        qp = new QueryParser(Version.LUCENE_40, "contents",sa);
    }
    
    public TopDocs runQuery(Query q, Integer numResults) throws IOException{
        return is.search(q, numResults);
    }
    
    public Document getDoc(ScoreDoc sd) throws IOException{
        return is.doc(sd.doc);
    }
    
    public String getDocText(Document doc) throws FileNotFoundException, IOException{
        String out = "";
        String curline;
        BufferedReader br = new BufferedReader(new FileReader(doc.get("path")));
        while ((curline = br.readLine()) != null) 
        {
            out += (curline + "\n");
        }
            
        return out;                
    }
    
    public Query parseQuery(String q) throws ParseException{
        return qp.parse(q);
    }
}
