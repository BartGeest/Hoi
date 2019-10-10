import java.util.Random;

public class DiceThrower {

    private static int[] _sides = {1,2,3,4,5,6};

    public static int throwDice(){
        Random r = new Random();
        int diceSide = r.nextInt(_sides.length);
        return _sides[diceSide];
    }
}
