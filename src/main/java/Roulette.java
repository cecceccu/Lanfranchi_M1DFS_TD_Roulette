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
        int result = -1;
        isSpinning = true;

        try
        {
            Thread.sleep(20000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        result = seed.nextInt(37);
        isSpinning = false;
        return result;

    }
}
