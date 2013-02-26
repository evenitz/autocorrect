package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public interface Suggestion {
    public String getWord();    
    public int getUnigram();   
    public int getBigram();
    
    public double getScore();
    public void calculateMistakeDistanceScore(ArrayList<Integer> prefixMistakes, ArrayList<Integer>suggestionMistakes);
}
