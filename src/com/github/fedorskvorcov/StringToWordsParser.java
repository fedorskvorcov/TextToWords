package com.github.fedorskvorcov;

import java.util.function.Function;
import java.util.stream.Stream;

public class StringToWordsParser implements Function<String, Stream<String>> {
    @Override
    public Stream<String> apply(String source) {
        String[] words = source.split("[^\\p{L}]+"); // \p{L} - Unicode letter
        return Stream.of(words);
    }
}
