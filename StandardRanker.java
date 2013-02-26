package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class StandardRanker extends Ranker {
    private String word;
    public StandardRanker() {
        
    }
    
    /**
     * RankSuggestions
     * Takes an unsorted list of suggestions and sorts them using the basic ranking algorithm.
     * 
     * @param String word            A word with which to compare all the suggestions
     * @param ArrayList<Suggestion>  An unranked list of suggestions
     * 
     * @return ArrayList<Suggestion> The ranked list of suggestions
     */
    public ArrayList<Suggestion> rankSuggestions(String word, HashSet<Suggestion> suggestionSet) {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
        
        for (Suggestion s : suggestionSet) {
            suggestions.add(s);
        }
        
        this.word = word;
        Collections.sort(suggestions, new SimpleRankingComparator());

        
        return suggestions;
    }
    
    /**
     * 
     * Implements the simple comparison ranking
     *
     */
    public class SimpleRankingComparator implements Comparator<Suggestion> {
        private String word = StandardRanker.this.word;
        @Override
        public int compare(Suggestion suggestion1, Suggestion suggestion2) {
            
            if (suggestion1.getWord().equals(word)) {
                return -1;
            }
            
            if (suggestion2.getWord().equals(word)) {
                return 1;
            }
            
            if (suggestion1.getBigram() == 0 && suggestion2.getBigram() != 0) {
                return 1;
            }
            
            if (suggestion2.getBigram() == 0 && suggestion1.getBigram() != 0) {
                return -1;
            }
           
            if (suggestion1.getBigram() > suggestion2.getBigram()) {
                return -1;
            } else if (suggestion1.getBigram() < suggestion2.getBigram()) {
                return 1;
            }
            
            if (suggestion1.getUnigram() > suggestion2.getUnigram()) {
                return -1;
            } else if (suggestion1.getUnigram() < suggestion2.getUnigram()) {
                return 1;
            }
            
            return suggestion1.getWord().compareTo(suggestion2.getWord());
        }
    }
}
