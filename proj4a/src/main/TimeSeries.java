package main;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * 一个用于将年份（例如 1996）映射到数值数据的对象。
 * 提供了对数据分析有用的工具方法。
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /**
     * 如果这有助于加快你的代码运行速度，你可以假设传入 NGramMap 的年份参数
     * 在 1400 到 2100 之间。我们已将这些值存储为常量 MIN_YEAR 和 MAX_YEAR。
     */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * 构造一个新的空 TimeSeries。
     */
    public TimeSeries() {
        super();
    }

    /**
     * 创建 TS 的副本，但仅包含 STARTYEAR 和 ENDYEAR 之间的数据，
     * 包含两端端点（即闭区间）。
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        //方法一，直接使用TreeMap的方法
        NavigableMap<Integer, Double> newts = ts.subMap(startYear, true, endYear, true);
        this.putAll(newts);

        //方法二， for循环挨个put入新对象
//        for (int year : ts.keySet()) {
//            if (year >= startYear && year <= endYear) {
//                this.put(year, ts.get(year));
//            }
//        }
    }

    /**
     * 按升序返回此时间序列中的所有年份。
     */
    public List<Integer> years() {
        List<Integer> Years = new ArrayList<>();
        for (int year : this.keySet()) {
            Years.add(year);
        }
        return Years;
    }

    /**
     * 返回此时间序列中的所有数据。
     * 数据的顺序必须与 years() 方法返回的年份顺序相对应。
     */
    public List<Double> data() {
        List<Double> Datas = new ArrayList<>();
        for (int year : this.keySet()) {
            Datas.add(this.get(year));
        }
        return Datas;
    }

    /**
     * 返回此 TimeSeries 与给定 TS 的按年求和结果。
     * 换句话说，对于每一年，将此 TimeSeries 的数据与 TS 的数据相加。
     * 应返回一个新的 TimeSeries（不修改当前的 TimeSeries）。
     *
     * 如果两个 TimeSeries 都不包含任何年份，则返回一个空的 TimeSeries。
     * 如果一个 TimeSeries 包含某一年份，而另一个不包含，
     * 则返回的 TimeSeries 应存储包含该年份的那个 TimeSeries 的值。
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries plusTs = new TimeSeries();
        if (this.size() == 0 && ts.size() == 0) {
            return plusTs;
        }
        plusTs.putAll(this);
        for (int year : ts.keySet()) {
            if (plusTs.get(year) == null) {
                plusTs.put(year, ts.get(year));
            } else {
                plusTs.put(year, plusTs.get(year) + ts.get(year));
            }
        }

        return plusTs;
    }

    /**
     * 返回此 TimeSeries 中每年的值除以 TS 中同一年份的值的商。
     * 应返回一个新的 TimeSeries（不修改当前的 TimeSeries）。
     *
     * 如果 TS 缺少此 TimeSeries 中存在的某一年份，则抛出 IllegalArgumentException。
     * 如果 TS 包含此 TimeSeries 中没有的年份，则忽略该年份。
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries dividedTs = new TimeSeries();
        for (int year : this.keySet()) {
            if (ts.get(year) == null) {
                throw new IllegalArgumentException();
            }
            dividedTs.put(year, this.get(year) / ts.get(year));
        }
        return dividedTs;

    }

}