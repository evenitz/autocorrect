package edu.brown.cs32.evenitz.autocorrect;

import java.util.HashSet;

public interface Suggester {
    public HashSet<Suggestion> suggestionsForPrefix(String prefix, String previous);
    public void addFile(String fileName);
}
