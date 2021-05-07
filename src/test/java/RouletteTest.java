import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_TIME;

@ExtendWith(MockitoExtension.class)
public class RouletteTest {



    Roulette roulette;

    ArrayList<Integer> values;

    @AfterEach
    void cleanUpAfterEach()
    {
        roulette = null;
    }
    @BeforeEach
    void init()
    {
        values = new ArrayList<Integer>();
    }
    @AfterEach
    void cleanUpResultsList()
    {
        values = null;
    }

    @ParameterizedTest
    @Timeout(value = 22000, unit = TimeUnit.MILLISECONDS)
    @ValueSource(ints = {3})
    void testRouletteSpinsFor20Secs(int randomSeed)
    {
        long start = System.currentTimeMillis();
        roulette = new Roulette(randomSeed);
        roulette.spinFor20Seconds();
        long end = System.currentTimeMillis();
        long duration = end-start;
        Assertions.assertTrue(duration>19000);
        Assertions.assertFalse(roulette.isSpinning());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 42})
    void testResultIsBetweenBounds(int randomSeed)
    {
        roulette = new Roulette(randomSeed);
        roulette.calculateResult();
        int result = roulette.getValue();
        Assertions.assertTrue(result>=0);
        Assertions.assertTrue(result <= 36);

    }

    @Ignore("Test ignored for now as it would require 500 000 spins lasting 20 seconds each")
    @ParameterizedTest
    @ValueSource(ints = {77})
    void testResultNotConstant(int randomSeed)
    {

        ArrayList<Integer> expectedValues = new ArrayList<>();
        for (int i=0; i<=36; i++)
        {
            expectedValues.add(i);
        }

        Random random = new Random();
        int spinNumber = 200000;
        int result;
        roulette = new Roulette(randomSeed);

        for (int j=0; j<spinNumber; j++)
        {
            roulette.calculateResult();
            values.add(roulette.getValue());
        }

        //With 200 000 iterations, there should be at least one of each value in the results list
        assertThat(values).containsOnlyElementsOf(expectedValues).containsAll(expectedValues);



    }

    @ParameterizedTest
    @ValueSource(ints = {-5, 52, 31, 22, 15, 18})
    void testColorIsCorrect(int randomSeed)
    {
        roulette = new Roulette(randomSeed);
        roulette.calculateResult();
        int result = roulette.getValue();
        Assertions.assertTrue((result % 2 == 0 && result != 0 && roulette.getColor() == "Black")
                                || (result % 2 == 1 && roulette.getColor() == "Red")
                                || (result == 0 && roulette.getColor() == "Green"));

    }



}
