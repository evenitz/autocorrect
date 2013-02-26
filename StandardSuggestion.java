package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public class StandardSuggestion implements Suggestion {
    private String word;
    private int unigramScore;
    private int bigramScore;
    
    //private StandardSuggestion() {}
    
    public StandardSuggestion(String word, int unigram, int bigram, boolean prefix) {
        this.word = word;
        this.unigramScore = unigram;
        this.bigramScore = bigram;
    }
    
    public String getWord() {
        return this.word;
    }
    
    public int getUnigram() {
        return this.unigramScore;
    }
    
    public int getBigram() {
        return this.bigramScore;
    }
    
    @Override
    public String toString() {
        return "{ " + this.word + " unigram: " + this.unigramScore + " bigram: " + this.bigramScore + " }";
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public void calculateMistakeDistanceScore(ArrayList<Integer> prefixMistakes, ArrayList<Integer> suggestionMistakes) {
        //
    }
}