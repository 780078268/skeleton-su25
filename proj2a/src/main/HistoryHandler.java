package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngrams;
    public HistoryHandler(NGramMap ngrams) {
        this.ngrams = ngrams;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        ArrayList list = new ArrayList();
        ArrayList names = new ArrayList();
        for(String word : words) {
            names.add(word);
            list.add(ngrams.weightHistory(word, q.startYear(), q.endYear()));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(names,list);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
