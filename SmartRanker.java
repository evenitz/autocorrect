package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class SmartRanker extends Ranker {
    
    private String word;
    private HashMap<Character, Integer> keyMap;
    
    public SmartRanker() {
        this.buildKeyMap();
    }
    
    public ArrayList<Suggestion> rankSuggestions(String word, HashSet<Suggestion> suggestionSet) {
        ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
        
        for (Suggestion s : suggestionSet) {
            suggestions.add(s);
        }
        
        this.word = word;
        this.calculateSuggestionErrorDistances(suggestions);
        
        Collections.sort(suggestions, new SmartRankingComparator());

        return suggestions;
    }
    
    public void buildKeyMap() {
        this.keyMap = new HashMap<Character, Integer>();
        char[] keys = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        int len = keys.length;
        
        for (int i = 0; i < len; i++) {
            keyMap.put(keys[i], i);
        }
    }
    
    public void calculateSuggestionErrorDistances(ArrayList<Suggestion> suggestions) {
        for (Suggestion s : suggestions) {
            this.setSuggestionErrorDistance(this.word, s);
        }
    }
    
    public void setSuggestionErrorDistance(String prefix, Suggestion suggestion) {
        String suggestedWord = suggestion.getWord();
        int suggestionLength = suggestedWord.length();
        
        char[] prefixChars = prefix.toCharArray();
        char[] suggestionChars = suggestedWord.toCharArray();
        ArrayList<Character> prefixErrors = new ArrayList<Character>();
        ArrayList<Character> suggestionErrors = new ArrayList<Character>();
        boolean error = true;
        
        for (char c : prefixChars) {
            for (int i = 0; i < suggestionLength; i++) {
                if (suggestionChars[i] == (c)) {
                    suggestionChars[i] = ' ';
                    error = false;
                    break;
                }
            }
            if (error) {
                prefixErrors.add(c);
            }
            error = true;
        }
        
        for (char c : suggestionChars) {
            if (c != ' ') {
                suggestionErrors.add(c);
            }
        }
        
        ArrayList<Integer> prefixNumbers = new ArrayList<Integer>();
        ArrayList<Integer> suggNumbers = new ArrayList<Integer>();
        
        for (char c : prefixErrors) {
            prefixNumbers.add(keyMap.get(c));
        }
        
        for (char c : suggestionErrors) {
            System.out.println(c);
            suggNumbers.add(keyMap.get(c));
        }
        
        suggestion.calculateMistakeDistanceScore(prefixNumbers, suggNumbers);
    }
    
    
    public class SmartRankingComparator implements Comparator<Suggestion> {
        private String word = SmartRanker.this.word;
        @Override
        public int compare(Suggestion suggestion1, Suggestion suggestion2) {
            double score1 = suggestion1.getScore();
            double score2 = suggestion2.getScore();
            
            if (suggestion1.getWord().equals(word)) {
                return -1;
            }
            
            if (suggestion2.getWord().equals(word)) {
                return 1;
            }
            
            if (score1 > score2) {
                return -1;
            } else if (score1 < score2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
}
