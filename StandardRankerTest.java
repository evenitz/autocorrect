package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class StandardRankerTest {

    @Test
    public void fiveWordRank() {
        StandardSuggester suggester = new StandardSuggester("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardRanker ranker = new StandardRanker();
        ArrayList<Suggestion> suggestions = ranker.rankSuggestions("simpl", suggester.suggestionsForPrefix("simpl", "phrase"));
        
        System.out.println(suggestions);
        ArrayList<String> words = new ArrayList<String>();
        for (Suggestion s : suggestions) {
            words.add(s.getWord());
        }
        
        assertTrue(words.toString().equals("[simple, simplify, simplistic, simply, simpler]"));
    }

}
