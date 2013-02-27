package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CorpusParser {
    
    private String line;
    private String previousWord;
    private String[] lineWords;
    private int index;
    private int lineSize;
    BufferedReader reader;
    
    public CorpusParser(String fileName) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(fileName));
        this.previousWord = "";
        try {
            this.line = this.reader.readLine().toLowerCase();
            this.index = 0;
            this.lineWords = line.split("\\s+");
            this.lineSize = lineWords.length;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * hasNext
     * checks to see if there are any more words to be parsed
     * 
     * @return true if there are more words
     */
    public boolean hasNext() {
        return line != null;
    }
    
    /**
     * next
     * grabs the next unigram and bigram to be parsed from the file
     * 
     * @return an array with the next unigram and next bigram
     */
    public String[] next() {
        if (line != null) {
            String unigram = lineWords[index].replaceAll("[^A-Za-z]", "").trim();
            String bigram = this.previousWord;
            this.index++;
        
            if (this.index >= this.lineSize) {
                this.readNextLine();
            }
        
            this.previousWord = unigram;
        
            String[] grams = {unigram, bigram};
            return grams;
        } else {
            return null;
        }
    }
    
    /**
     * newLine
     * reads a new line and updates the class
     */
    private void readNextLine() {
        try {
            this.line = this.reader.readLine();
            
            if (line != null) {
                line = line.toLowerCase();
                this.lineWords = this.line.split("\\s+");
                this.lineSize = this.lineWords.length;
                this.index = 0;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
