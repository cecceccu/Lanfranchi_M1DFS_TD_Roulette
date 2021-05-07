import java.util.Random;


public class Roulette {
    private Random seed;
    private boolean isSpinning;


    public Roulette(int randomSeed) {
        this.seed = new Random(randomSeed);
    }


    public boolean isSpinning() {
        return isSpinning;
    }

    public int spin() {
        spinFor20Seconds();
        return calculateResult();

    }

    public void spinFor20Seconds()
    {
        isSpinning = true;

        try
        {
            Thread.sleep(20000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        isSpinning = false;
    }

    public int calculateResult()
    {
        return seed.nextInt(37);
    }
}
