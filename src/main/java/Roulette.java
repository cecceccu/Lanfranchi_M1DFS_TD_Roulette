import java.util.Random;


public class Roulette {
    private Random seed;
    private boolean isSpinning;
    public String color = "";
    public int value = -1;


    public Roulette(int randomSeed) {
        this.seed = new Random(randomSeed);
    }


    public boolean isSpinning() {
        return isSpinning;
    }

    public void spin() {
        spinFor20Seconds();
        calculateResult();
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

    public void calculateResult()
    {
        value = seed.nextInt(37);
        color = value % 2 == 0?"Black":"Red";
        if (value == 0)
            color = "Green";
    }

    public int getValue()
    {
        return value;
    }

    public String getColor()
    {
        return color;
    }
}
