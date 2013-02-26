package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public abstract class Ranker {    
    abstract ArrayList<Suggestion> rankSuggestions(String word, ArrayList<Suggestion> suggestions);
}
