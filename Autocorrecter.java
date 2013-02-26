package edu.brown.cs32.evenitz.autocorrect;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Autocorrecter {
     PrefixTree tree;
    private Suggester suggester;
    private Ranker ranker;
     HashMap<String, Integer> unigrams;
     HashMap<String, Integer> bigrams;
    
    public Autocorrecter(String fileName, boolean prefix, boolean whitespace, int led, boolean smart) {
        this.suggester = new StandardSuggester(fileName);
        this.ranker = new SmartRanker();
        //this.ranker = new StandardRanker();
    }
    
    public ArrayList<Suggestion> suggestionsForPrefix(String prefix, String previous) {
        HashSet<Suggestion> resultsSet = suggester.suggestionsForPrefix(prefix, previous);
        ArrayList<Suggestion> results = ranker.rankSuggestions(prefix, resultsSet);
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
