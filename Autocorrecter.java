package edu.brown.cs32.evenitz.autocorrect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Autocorrecter {
     PrefixTree tree;
    private Suggester suggester;
    private Ranker ranker;
     HashMap<String, Integer> unigrams;
     HashMap<String, Integer> bigrams;
    
    public Autocorrecter(String fileName) {
        this.suggester = new StandardSuggester(fileName);
        this.ranker = new SmartRanker();
        //this.ranker = new StandardRanker();
    }
    
    public ArrayList<Suggestion> suggestionsForPrefix(String prefix, String previous) {
        ArrayList<Suggestion> results = suggester.suggestionsForPrefix(prefix, previous);
        results = ranker.rankSuggestions(prefix, results);
        return results;
    }
    
    public ArrayList<String> topSuggestions(String prefix, String previous, int number) {
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Suggestion> suggestions = this.suggestionsForPrefix(prefix, previous);
        
        for (int i = 0; i < number; i++) {
            if (i >= suggestions.size()) {
                break;
            }
            System.out.println(suggestions.get(i));
            words.add(suggestions.get(i).getWord());
        }
        
        return words;
    }
}
