package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Ranker {    
    abstract ArrayList<Suggestion> rankSuggestions(String word, HashSet<Suggestion> suggestions);
}
