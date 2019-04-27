package com.github.fedorskvorcov.wordscounter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class WordsCounter {
    public CounterResult countWordsInStringStream(Stream<String> words) {
        Map<String, Integer> wordsWithCounters = words.collect(() -> new HashMap<>(), (map, word) -> {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }, (map1, map2) -> map1.putAll(map2));
        return new CounterResult(wordsWithCounters);
    }
}
