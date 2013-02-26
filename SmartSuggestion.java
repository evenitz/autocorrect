package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public class SmartSuggestion implements Suggestion {
    private String word;
    private int unigramScore;
    private int bigramScore;
    private final int hashRowLength = 10;
    
    private double rankScore;
    private double mistakeDistanceScore;
    private boolean isPrefix;
    
    public SmartSuggestion(String word, int unigram, int bigram, boolean prefix) {
        this.word = word;
        this.unigramScore = unigram;
        this.bigramScore = bigram;
        this.isPrefix = prefix;
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
    
    /**
     * calculateMistakeDistanceScore
     * Takes an array of mistake locations represented as integers and calculates the relative
     * distance of the mistake across the keyboard for this suggestion.
     * 
     * @param prefixMistakes
     * @param suggestionMistakes
     */
    public void calculateMistakeDistanceScore(ArrayList<Integer> prefixMistakes, ArrayList<Integer>suggestionMistakes) {
        int totalDistance = 0;
        
        int prefLen = prefixMistakes.size();
        int suggestionLen = suggestionMistakes.size();
        
        int i = 0;
        int pref;
        int sugg;
        double x1;
        double x2;
        double y1;
        double y2;
        while (i < prefLen && i < suggestionLen) {
            pref = prefixMistakes.get(i);
            sugg = suggestionMistakes.get(i);
            
            x1 = pref % hashRowLength;
            x2 = sugg % hashRowLength;
            y1 = pref / hashRowLength;
            y2 = sugg / hashRowLength;
            
           // System.out.println("x1 " + x1 + ", x2 " + x2 + ", y1 " + y1 + ", y2 " + y2);
            
            totalDistance += Math.pow(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0), 0.5);
            i++;
        }
        
        int diff = Math.abs(prefLen - suggestionLen);
        totalDistance += diff * 3;
        
        this.mistakeDistanceScore = totalDistance;
        this.calculateSmartScore();
    }
    
    /**
     * calculateSmartScore
     * calculates and sets this suggestion's score for smart rank
     */
    public void calculateSmartScore() {
        double score = 0;
        
        score += this.bigramScore * 3;
        score += this.unigramScore;
        
        if (isPrefix) {
            score += 200;
        }
        
        score += (100 / (this.mistakeDistanceScore + 1));
        
        this.rankScore = score;
    }
    
    public double getScore() {
        return this.rankScore;
    }
    
    @Override
    public String toString() {
        return "{ " + this.word + " unigram: " + this.unigramScore + " bigram: " + this.bigramScore + " Prefix: " + this.isPrefix + " mistakeDistance: " + this.mistakeDistanceScore + " score: " + this.rankScore + " }";
    }
}
