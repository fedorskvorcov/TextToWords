package com.github.fedorskvorcov;

import com.github.fedorskvorcov.wordscounter.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Main {

    private static int OUTPUT_SIZE_LIMIT = 20;

    public static void main(String[] args) {
        List<File> inputFiles = getInputFiles(args);
        if (inputFiles.isEmpty()) {
            System.out.println("No input files provided");
            return;
        }
        WordsCounter wordsCounter = new WordsCounter();
        ExceptionsProvider exceptionsProvider = new ExceptionsProvider();
        List<Set<String>> listOfSets = new ArrayList<Set<String>>();
        for (File file : inputFiles) {
            CounterResult counterResult;
            try {
                String[] arrayOfWords = parseTextToArrayOfWords(file, exceptionsProvider);
                counterResult = wordsCounter.countWordsInStringStream(Arrays.stream(arrayOfWords));
                listOfSets.add(counterResult.keySet());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot count words in file " + file.getAbsolutePath());
                continue;
            }

            // Output for task 1
            List<Map.Entry<String, Integer>> sortedCounterResultList = counterResult.toSortedList();
            showResultForFile(file, sortedCounterResultList, OUTPUT_SIZE_LIMIT);
        }
        // Output for task 2
        Set<String> uniqueWords = computeUniqueWords(listOfSets);
        showUniqueWords(uniqueWords, inputFiles);
    }

    private static Set<String> computeUniqueWords(List<Set<String>> listOfSets) {
        Set<String> result = new HashSet<String>();
        for (int i = 0; i < listOfSets.size(); i++) {
            Set<String> currentSet = listOfSets.get(i);
            if (result.size() == 0) {
                result.addAll(currentSet);
                continue;
            }
            Set<String> intersect = new HashSet<String>(result);
            intersect.retainAll(currentSet);
            result.addAll(currentSet);
            result.removeAll(intersect);
        }
        return result;
    }

    private static List<File> getInputFiles(String[] args) {
        if (args.length == 0){
            return new ArrayList<>();
        }
        List<File> inputFiles = new ArrayList<File>();
        for (String currentInputParameter : args) {
            File inputFile = new File(currentInputParameter);
            if (inputFile.isFile()) {
                inputFiles.add(inputFile);
            }
        }
        return inputFiles;
    }

    private static void showUniqueWords(Set<String> uniqueWords, List<File> inputFiles) {
        if (uniqueWords.size() == 0) {
            System.out.println("No unique words found");
            return;
        }
        if (inputFiles.size() == 1) {
            System.out.print("\nList of unique words for file ");
        } else {
            System.out.print("\nList of unique words for files ");
        }
        for(File file : inputFiles) {
            System.out.print(file.getName() + " ");
        }
        System.out.println("\n");
        for (String string : uniqueWords) {
            System.out.println(string);
        }
        System.out.println();
    }

    private static void showResultForFile(File file, List<Map.Entry<String, Integer>> sortedCounterResultList, int outputSizeLimit) {
        System.out.println("\nOutput for file " + file.getAbsolutePath() + "\n");
        int outputSize = Math.min(outputSizeLimit, sortedCounterResultList.size());
        if (outputSize == 0) {
            System.out.println("No words found");
            return;
        }
        for (int i = 0; i < outputSize; i++) {
            Map.Entry<String, Integer> currentEntry = sortedCounterResultList.get(i);
            System.out.println(currentEntry.getKey() + " – " + currentEntry.getValue());
        }
    }

    private static String[] parseTextToArrayOfWords(File file, ExceptionsProvider exceptionsProvider) throws IOException {
        List<String> wordsToExclude = exceptionsProvider.provideExceptions();
        StringToWordsParser stringToWordsParser = new StringToWordsParser();
        return Files.lines(file.toPath())
                .flatMap(stringToWordsParser)
                .filter(currentWord -> !wordsToExclude.contains(currentWord) && !currentWord.isEmpty())
                .map(String::toLowerCase)
                .toArray(size -> new String[size]);
    }
}
