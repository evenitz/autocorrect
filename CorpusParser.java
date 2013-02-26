package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CorpusParser {
    
    private String line;
    private String previousWord;
    private String[] lineWords;
    private int index;
    private int lineSize;
    BufferedReader reader;
    
   // private String fileName;
    
    public CorpusParser(String fileName) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(fileName));
        this.previousWord = "";
        try {
            this.line = this.reader.readLine().toLowerCase();
            this.index = 0;
            this.lineWords = line.split("\\s+");
            this.lineSize = lineWords.length;
        } catch (IOException e) {
            
        }
    }
    
    public boolean hasNext() {
        return line != null;
    }
    
    public String[] next() {
        String unigram = "";
        String bigram = "";
        
        
        unigram = lineWords[index];
        unigram = unigram.replaceAll("[^A-Za-z]", "");
        unigram = unigram.trim();
        bigram = this.previousWord;
        this.index++;
        
        if (this.index >= this.lineSize) {
            try {
                this.line = this.reader.readLine();
                
                if (line != null) {
                    line = line.toLowerCase();
                    this.lineWords = this.line.split("\\s+");
                    this.lineSize = this.lineWords.length;
                    this.index = 0;
                }
            } catch (IOException e) {
                //
            }
        }
        
        this.previousWord = unigram;
        
        String[] grams = {unigram, bigram};
        return grams;
    }
    
    public static void main(String[] args) {
        try {
            CorpusParser parser = new CorpusParser("/Users/ethanvenitz/Documents/workspace/Autocorrect/sherlock2.txt");
            
            while (parser.hasNext()) {
                String[] gram = parser.next();
                System.out.println("Unigram: " + gram[0] + " bigram: " + gram[1]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
