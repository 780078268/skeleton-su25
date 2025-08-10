package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    TimeSeries yearData = new TimeSeries();
    TreeMap<String, TimeSeries> wordData = new TreeMap<>();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In inCount = new In(countsFilename);
        In inWords = new In(wordsFilename);
        while (inCount.hasNextLine()) {
            String line = inCount.readLine();
            String[] words = line.split(",");
            int year = Integer.parseInt(words[0]);
            double data = Double.parseDouble(words[1]);
            this.yearData.put(year, data);
        }
        while (inWords.hasNextLine()) {
            String line = inWords.readLine();
            String[] words = line.split("\\s+");
            String word = words[0];
            int year = Integer.parseInt(words[1]);
            double data = Double.parseDouble(words[2]);
            if (wordData.containsKey(word)) {
                wordData.get(word).put(year, data);
            } else {
                TimeSeries wordYearData = new TimeSeries();
                wordYearData.put(year, data);
                wordData.put(word, wordYearData);
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries returnData = new TimeSeries();
        if (wordData.containsKey(word)) {
            TimeSeries sourceTS = wordData.get(word);
            for (java.util.Map.Entry<Integer, Double> entry : sourceTS.entrySet()) {
                int year = entry.getKey();
                if (year >= startYear && year <= endYear) {
                    returnData.put(year, entry.getValue());
                }
            }
        }
        return returnData;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries returnData = new TimeSeries();
        if (wordData.containsKey(word)) {
            returnData =  countHistory(word, MIN_YEAR, MAX_YEAR);
        }
        return returnData;
    }

    /**
     * Returns a defensive copy of the yearData number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries returnData = new TimeSeries();
        returnData.putAll(this.yearData);
        return returnData;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries returnData = new TimeSeries();
        TimeSeries byData = new TimeSeries();
        returnData = countHistory(word, startYear, endYear);
        byData = totalCountHistory();
        byData = new TimeSeries(byData, startYear, endYear);
        returnData = returnData.dividedBy(byData);
        return returnData;

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries returnData = new TimeSeries();
        returnData = countHistory(word);
        TimeSeries byData = new TimeSeries();
        byData = totalCountHistory();
        returnData = returnData.dividedBy(byData);
        return returnData;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries returnData = new TimeSeries();
        for (String word : words) {
            returnData = returnData.plus(weightHistory(word, startYear, endYear));
        }
        return returnData;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries returnData = new TimeSeries();
        for (String word : words) {
            returnData = returnData.plus(weightHistory(word));
        }
        return returnData;
    }

}

