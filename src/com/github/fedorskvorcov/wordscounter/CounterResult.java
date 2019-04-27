package com.github.fedorskvorcov.wordscounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CounterResult {

    private final Map<String, Integer> resultsMap;

    public CounterResult(Map<String, Integer> resultsMap) {
        if (resultsMap == null) {
            throw new IllegalArgumentException("Results map cannot bee null");
        }
        this.resultsMap = resultsMap;
    }

    public List<Map.Entry<String, Integer>> toSortedList() {
        ArrayList<Map.Entry<String, Integer>> result = new ArrayList<>(resultsMap.entrySet());
        result.sort((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()) * (-1));
        return result;
    }

    public Set<String> keySet() {
        return resultsMap.keySet();
    }
}
