import java.util.HashMap;

public class PointAuthorizer {

    public static int _totalPoints = 0;

    public static void authorizePoints(String cc, HashMap<Integer, Integer> kv){
        switch (cc){
            case "Aces":
                addUpOnes(kv);
                break;
            case "Twos":
                addUpTwos(kv);
                break;
            case "Threes":
                addUpThrees(kv);
                break;
            case "Fours":
                addUpFours(kv);
                break;
            case "Fives":
                addUpFives(kv);
                break;
            case "Sixes":
                addUpSixes(kv);
                break;
            default:
                break;
        }
    }

    private static void addUpOnes(HashMap<Integer, Integer> kv){
        for (int value: kv.values()){
            if (value == 1) _totalPoints += value;
        }
    }

    private static void addUpTwos(HashMap<Integer, Integer> kv){
        for (int value: kv.values()){
            if (value == 2) _totalPoints += value;
        }
    }

    private static void addUpThrees(HashMap<Integer, Integer> kv){
        for (int value: kv.values()){
            if (value ==  3) _totalPoints += value;
        }
    }

    private static void addUpFours(HashMap<Integer, Integer> kv){
        for (int value: kv.values()){
            if (value ==  4) _totalPoints += value;
        }
    }

    private static void addUpFives(HashMap<Integer, Integer> kv){
        for (int value: kv.values()){
            if (value ==  5) _totalPoints += value;
        }
    }

    private static void addUpSixes(HashMap<Integer, Integer> kv){
        for (int value: kv.values()){
            if (value ==  6) _totalPoints += value;
        }
    }
}