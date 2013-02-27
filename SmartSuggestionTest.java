package edu.brown.cs32.evenitz.autocorrect;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmartSuggestionTest {

    @Test
    public void calculateScorePrefix() {
        SmartSuggestion suggestion = new SmartSuggestion("word", 4, 2, true);
        suggestion.calculateSmartScore();
        suggestion.setMistakeDistance(4);
        assertTrue(suggestion.getScore() == 236);
    }

}
