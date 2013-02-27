package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class StandardSuggesterTest {

    @Test
    public void fiveWordDictionarySuggestion() {
      StandardSuggester suggester = new StandardSuggester("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
      HashSet<Suggestion> set = suggester.suggestionsForPrefix("simpl", "phrase");
      ArrayList<String> strings = new ArrayList<String>();
      
      for (Suggestion s : set) {
          strings.add(s.getWord());
      }
      
      assertTrue(strings.contains("simple") && strings.contains("simply") && strings.contains("simplistic") && strings.contains("simplify") && strings.contains("simpler"));
    }

    @Test
    public void newUnigram() {
        StandardSuggester suggester = new StandardSuggester("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        
        suggester.addUnigram("first");
        
        assertTrue(suggester.getUnigram("first") == 1);
    }
    
    @Test
    public void updateUnigram() {
        StandardSuggester suggester = new StandardSuggester("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        
        suggester.addUnigram("first");
        suggester.addUnigram("first");
        
        assertTrue(suggester.getUnigram("first") == 2);
    }
    
    @Test
    public void newBigram() {
        StandardSuggester suggester = new StandardSuggester("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        
        suggester.addBigram("first", "word");
        
        assertTrue(suggester.getBigram("first word") == 1);
    }
    
    @Test
    public void updateBigram() {
        StandardSuggester suggester = new StandardSuggester("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        
        suggester.addBigram("first", "word");
        suggester.addBigram("first", "word");
        
        assertTrue(suggester.getBigram("first word") == 2);
    }
}
