package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class StandardSuggesterTest {

    @Test
    public void fiveWordDictionarySuggestion() {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardSuggester suggester = new StandardSuggester(fileNames, true, true, 2);
        HashSet<Suggestion> set = suggester.suggestionsForPrefix("simpl", "phrase");
        ArrayList<String> strings = new ArrayList<String>();
      
        for (Suggestion s : set) {
            strings.add(s.getWord());
        }
      
        assertTrue(strings.contains("simple") && strings.contains("simply") && strings.contains("simplistic") && strings.contains("simplify") && strings.contains("simpler"));
    }

    @Test
    public void newUnigram() {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardSuggester suggester = new StandardSuggester(fileNames, true, true, 2);
        
        suggester.addUnigram("first");
        
        assertTrue(suggester.getUnigram("first") == 1);
    }
    
    @Test
    public void updateUnigram() {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardSuggester suggester = new StandardSuggester(fileNames, true, true, 2);
        
        suggester.addUnigram("first");
        suggester.addUnigram("first");
        
        assertTrue(suggester.getUnigram("first") == 2);
    }
    
    @Test
    public void newBigram() {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardSuggester suggester = new StandardSuggester(fileNames, true, true, 2);
        
        suggester.addBigram("first", "word");
        
        assertTrue(suggester.getBigram("first word") == 1);
    }
    
    @Test
    public void updateBigram() {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardSuggester suggester = new StandardSuggester(fileNames, true, true, 2);
        
        suggester.addBigram("first", "word");
        suggester.addBigram("first", "word");
        
        assertTrue(suggester.getBigram("first word") == 2);
    }
}
