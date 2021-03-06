package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class StandardRankerTest {

    @Test
    public void fiveWordRank() {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("/Users/ethanvenitz/Documents/workspace/Autocorrect/dictionary3.txt");
        StandardSuggester suggester = new StandardSuggester(fileNames, true, true, 2);
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
