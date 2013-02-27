package edu.brown.cs32.evenitz.autocorrect;


import java.util.ArrayList;
import java.util.HashSet;

public class Autocorrecter {
    private Suggester suggester;
    private Ranker ranker;
    
    public Autocorrecter(String fileName, boolean prefix, boolean whitespace, int led, boolean smart) {
        this.suggester = new StandardSuggester(fileName, prefix, whitespace,led);
        
        if (smart) {
            this.ranker = new SmartRanker();
        } else {
            this.ranker = new StandardRanker();
        }
    }
    
    /**
     * suggestionsForPrefix
     * generates all of the suggestions for a given prefix
     * 
     * @param prefix   the word to compare to
     * @param previous word before the prefix
     * @return an array of Suggestion objects containing data on
     * each suggestion
     */
    private ArrayList<Suggestion> suggestionsForPrefix(String prefix, String previous) {
        HashSet<Suggestion> resultsSet = suggester.suggestionsForPrefix(prefix, previous);
        ArrayList<Suggestion> results = ranker.rankSuggestions(prefix, resultsSet);
        return results;
    }
    
    /**
     * topSuggestions
     * returns the top suggestions for a given word
     * 
     * @param prefix    the user inputted word
     * @param previous  the word before the prefix
     * @param number    the number of suggetions to return
     * @return the top 5 suggestions
     */
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
