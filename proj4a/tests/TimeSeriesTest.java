import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void yearsTest() {
        TimeSeries population = new TimeSeries();
        population.put(1992, 0.0);
        population.put(1991, 100.0);
        population.put(1995, 200.0);
        population.put(1994, 500.0);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(population.years()).isEqualTo(expectedYears);
    }

    @Test
    public void dataTest() {
        TimeSeries population = new TimeSeries();
        population.put(1992, 0.0);
        population.put(1991, 100.0);
        population.put(1995, 200.0);
        population.put(1994, 500.0);

        List<Double> expectedDatas = new ArrayList<>();
        expectedDatas.add(100.0);
        expectedDatas.add(0.0);
        expectedDatas.add(500.0);
        expectedDatas.add(200.0);

        for (int i = 0; i < expectedDatas.size(); i += 1) {
            assertThat(population.data().get(i)).isWithin(1E-10).of(expectedDatas.get(i));
        }
    }

    @Test
    public void timeSeriesTest() {
        TimeSeries population = new TimeSeries();
        population.put(1991, 0.0);
        population.put(1992, 100.0);
        population.put(1993, 200.0);
        population.put(1994, 500.0);
        population.put(1995, 600.0);
        population.put(1996, 200.0);
        population.put(1997, 400.0);
        population.put(1998, 200.0);
        population.put(1999, 100.0);
        population.put(2000, 500.0);

        TimeSeries newPopulation = new TimeSeries(population, 1993, 1997);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1993);
        expectedYears.add(1994);
        expectedYears.add(1995);
        expectedYears.add(1996);
        expectedYears.add(1997);

        assertThat(newPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedDatas = new ArrayList<>();
        expectedDatas.add(200.0);
        expectedDatas.add(500.0);
        expectedDatas.add(600.0);
        expectedDatas.add(200.0);
        expectedDatas.add(400.0);

        for (int i = 0; i < expectedDatas.size(); i += 1) {
            assertThat(newPopulation.data().get(i)).isWithin(1E-10).of(expectedDatas.get(i));
        }
    }

    @Test
    public void plusTest() {
        TimeSeries population1 = new TimeSeries();
        TimeSeries population2 = new TimeSeries();

        assertThat(population1.plus(population2)).isEmpty();

        population1.put(1991, 0.0);
        population1.put(1992, 100.0);
        population1.put(1994, 200.0);

        population2.put(1994, 400.0);
        population2.put(1995, 500.0);

        TimeSeries totalPopulation = population1.plus(population2);

        List<Double> expected = new ArrayList<>();
        expected.add(0.0);
        expected.add(100.0);
        expected.add(600.0);
        expected.add(500.0);

        for (int i = 0; i < expected.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expected.get(i));
        }
    }

    @Test
    public void dividedByTest() {
        TimeSeries population = new TimeSeries();
        TimeSeries TS = new TimeSeries();

        population.put(1991, 224.0);
        population.put(1992, 100.0);
        population.put(1993, 121.0);
        population.put(1994, 450.0);

        TS.put(1992, 50.0);
        TS.put(1993, 11.0);
        TS.put(1994, 90.0);
        TS.put(1991, 4.0);
        TS.put(1996, 199.0);

        TimeSeries afterPopulation = population.dividedBy(TS);

        List<Double> expected = new ArrayList<>();
        expected.add(56.0);
        expected.add(2.0);
        expected.add(11.0);
        expected.add(5.0);


        for (int i = 0; i < expected.size(); i += 1) {
            assertThat(afterPopulation.data().get(i)).isWithin(1E-10).of(expected.get(i));
        }
    }
} 