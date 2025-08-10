package ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        TimeSeries newTs = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            if (ts.get(i) != null) {
                newTs.put(i, ts.get(i));
            }
        }
        this.putAll(newTs);
    }


    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        List<Integer> year = new ArrayList<Integer>();
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            year.add(entry.getKey());
        }
        return year;
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        List<Double> value = new ArrayList<Double>();
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            value.add(entry.getValue());
        }
        return value;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTs = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            if  (ts.years().contains(entry.getKey())) {
                newTs.put(entry.getKey(), entry.getValue() + ts.get(entry.getKey()));
            } else {
                newTs.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            if (!newTs.containsKey(entry.getKey())) {
                newTs.put(entry.getKey(), entry.getValue());
            }
        }
        return newTs;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTs = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            if (!ts.containsKey(entry.getKey())) {
                throw new IllegalArgumentException("TimeSeries contains non-existing element");
            }
            newTs.put(entry.getKey(), entry.getValue() / ts.get(entry.getKey()));
        }
        return newTs;
    }
}
