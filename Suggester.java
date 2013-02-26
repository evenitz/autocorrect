package edu.brown.cs32.evenitz.autocorrect;

import java.util.ArrayList;

public interface Suggester {
    public ArrayList<Suggestion> suggestionsForPrefix(String prefix, String previous);
    public void addFile(String fileName);
}
