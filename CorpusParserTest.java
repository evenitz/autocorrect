package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class CorpusParserTest {

    @Test
    public void corpusInitialization() {
        try {
            CorpusParser parser = new CorpusParser("/Users/ethanvenitz/Documents/workspace/Autocorrect/test.txt");
            assertTrue(parser.hasNext());
        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    @Test
    public void corpusReadsUnigrams() {
        try {
            CorpusParser parser = new CorpusParser("/Users/ethanvenitz/Documents/workspace/Autocorrect/test.txt");
            String[] first = parser.next();
            String[] second = parser.next();
            assertTrue(first[0].equals("test") && second[0].equals("dictionary"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void corpusGoesTillEnd() {
        try {
            CorpusParser parser = new CorpusParser("/Users/ethanvenitz/Documents/workspace/Autocorrect/test.txt");
            String[] strings;
            while (parser.hasNext()) {
                strings = parser.next();
            }
            assertTrue(parser.next() == null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
