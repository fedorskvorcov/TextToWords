package com.github.fedorskvorcov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExceptionsProvider {
    private static List<String> pretexts = new ArrayList<>(Arrays.asList("а", "но", "не", "же", "о", "в", "и", "что", "под", "над", "c", "по", "non", "со", "к", "ко", "от", "c"));

    public List<String> provideExceptions() {
        return pretexts;
    }
}
