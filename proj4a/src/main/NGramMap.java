package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * 一个提供对Google NGrams数据集（或其子集）进行查询的工具类。
 *
 * NGramMap存储了"words文件"和"counts文件"中的关键数据。
 * 严格来说它不是传统意义上的Map，但提供了额外的功能。
 *
 * @author Josh Hug
 */
public class NGramMap {
    // TODO: 添加必要的静态/实例变量
    private Map<String, TimeSeries> wordCount;
    private TimeSeries totalWords;
    /**
     * 通过WORDHISTORYFILENAME（词频历史文件）和YEARHISTORYFILENAME（年份统计文件）构建NGramMap
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        wordCount = new HashMap<>();
        totalWords = new TimeSeries();

        In in = new In(wordHistoryFilename);
        while (!in.isEmpty()) {

            String word = in.readString();
            Integer year = in.readInt();
            Double count = in.readDouble();
            in.readInt();

            TimeSeries ts = wordCount.get(word);
            if (ts == null) {
                ts = new TimeSeries();
                wordCount.put(word, ts);
            }
            ts.put(year, count);
        }

        in = new In(yearHistoryFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] line = nextLine.split(",");
            Integer year = Integer.parseInt(line[0]);
            Double total = Double.parseDouble(line[1]);
            totalWords.put(year, total);
        }
    }

    /**
     * 返回WORD在STARTYEAR到ENDYEAR年份区间（含首尾）的词频历史记录。
     * 返回的TimeSeries应为副本而非原始数据的引用（即"防御性拷贝"）。
     * 换言之，对该方法返回对象的修改不应影响NGramMap内部数据。
     * 若单词不存在于数据文件中，返回空TimeSeries。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = wordCount.get(word);
        if (ts == null) {
            return new TimeSeries();
        }
        ts = new TimeSeries(ts, startYear, endYear);
        return ts;
    }

    /**
     * 返回WORD的完整词频历史记录（全时间范围）。
     * 返回的TimeSeries应为防御性拷贝。
     * 若单词不存在于数据文件中，返回空TimeSeries。
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ts = wordCount.get(word);
        if (ts == null) {
            return new TimeSeries();
        }
        TimeSeries newTs = new TimeSeries();
        newTs.putAll(ts);
        return newTs;
    }

    /**
     * 返回每年所有语料库中记录的总词频的防御性拷贝
     */
    public TimeSeries totalCountHistory() {
        TimeSeries ts = new TimeSeries();
        ts.putAll(totalWords);
        return ts;
    }

    /**
     * 返回WORD在STARTYEAR到ENDYEAR年份区间（含首尾）的相对词频时间序列。
     * 相对词频 = 该词年频次 / 当年总词频
     * 若单词不存在，返回空TimeSeries。
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordTs = wordCount.get(word);
        if (wordTs == null) {
            return new TimeSeries();
        }
        TimeSeries totalTs = new TimeSeries();
        for (int year : wordTs.keySet()) {
            totalTs.put(year, wordTs.get(year) / totalWords.get(year));
        }
        return new TimeSeries(totalTs, startYear, endYear);
    }

    /**
     * 返回WORD相对于各年总词频的相对词频时间序列。
     * 若单词不存在，返回空TimeSeries。
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordTs = wordCount.get(word);
        if (wordTs == null) {
            return new TimeSeries();
        }
        TimeSeries totalTs = new TimeSeries();
        for (int year : wordTs.keySet()) {
            totalTs.put(year, wordTs.get(year) / totalWords.get(year));
        }
        return totalTs;
    }

    /**
     * 返回WORDS集合中所有单词在STARTYEAR到ENDYEAR年份区间的相对词频总和。
     * 若单词在该时间段不存在，则忽略而非抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries Ts = new TimeSeries();
        for (String word : words) {
            TimeSeries wordTs = wordCount.get(word);
            if (wordTs == null) {
                continue;
            }
            wordTs = new TimeSeries(wordTs, startYear, endYear);
            for (int year : wordTs.keySet()) {
                if (Ts.get(year) == null) {
                    Ts.put(year, wordTs.get(year));
                } else {
                    Ts.put(year, Ts.get(year) + wordTs.get(year));
                }
            }
        }
        for (int year : Ts.keySet()) {
            Ts.put(year, Ts.get(year) / totalWords.get(year));
        }
        return Ts;
    }

    /**
     * 返回WORDS集合中所有单词的相对词频总和（全时间范围）。
     * 若单词不存在，则忽略而非抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {

        TimeSeries Ts = new TimeSeries();
        for (String word : words) {
            TimeSeries wordTs = wordCount.get(word);
            if (wordTs == null) {
                continue;
            }
            for (int year : wordTs.keySet()) {
                if (Ts.get(year) == null) {
                    Ts.put(year, wordTs.get(year));
                } else {
                    Ts.put(year, Ts.get(year) + wordTs.get(year));
                }
            }
        }
        for (int year : Ts.keySet()) {
            Ts.put(year, Ts.get(year) / totalWords.get(year));
        }
        return Ts;
    }
}