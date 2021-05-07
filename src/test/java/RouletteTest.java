import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

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
    void testRouletteStoppedSpinning(int randomSeed)
    {
        roulette = new Roulette(randomSeed);
        roulette.spin();
        Assertions.assertFalse(roulette.isSpinning());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 42})
    void testResultIsBetweenBounds(int randomSeed)
    {
        roulette = new Roulette(randomSeed);
        int result = roulette.spin();
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
        int spinNumber = 500000;
        int result;
        roulette = new Roulette(randomSeed);

        for (int j=0; j<spinNumber; j++)
        {
            result = roulette.spin();
            values.add(result);
        }

        //With 500 000 iterations, there should be at least one of each value in the results list
        assertThat(values).containsOnlyElementsOf(expectedValues).containsAll(expectedValues);



    }



}
