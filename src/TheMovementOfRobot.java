import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TheMovementOfRobot {

    /**
     There is a robot which is trying to move inside a square board. The board is a grid of size  where the point  is in its upper-left corner and the point  is in its bottom-right corner. The robot will move only by following some rules:

     It will always move according to the current direction of the wind (possible directions are North, South, West, East, North-East, North-West, South-East, South-West).
     It can visit a specific cell of the board only once.
     It can not go out of the borders of the board.
     If it reaches at a point where it can not move according to the rules, it will stay at the current position.
     It can move only to the neighbouring cells (8 possible moves according to the above directions).
     It will always start its journey from the point  (the upper-left corner of the board).
     It can only make one movement per second.
     In order to make things more difficult for it, the wind will change its direction multiple times during the robot's journey. You will always be given the direction of the wind before the robot starts to move (0-th second) and then you will be given the specific time in seconds when the wind is going to change its direction (in a strictly increasing sequence).

     After following all the journey of the robot, you will have to specify how many positions inside the board were not visited by the robot.

     Input Format

     In the first line you will be given two integers  and  where  represents the dimension of the board. Then in the following  lines you will be given an integer  and a string .  represents the time in seconds in which on the board a wind of direction  will be applied.  might take a value of N, S, W, E, NE, NW, SE, or SW (representing North, South, West, East, North-East, North-West, South-East or South-West, respectively).

     Constraints

     Output Format

     Output the number of positions inside the board that were not visited by the robot.

     Sample Input 0

     5 6
     0 SE
     1 NE
     2 E
     6 SW
     15 N
     20 W
     Sample Output 0

     13
     Explanation 0

     image

     The board is a square of size 5 and you will be given the direction of the wind on 6 different periods. Before the robot starts to move (in the 0-th second), the wind will be pointing to South-East so the robot will move from its starting position (0,0) to (1,1). At the 1-st second the wind will point to North-East so the robot will go to position (0,2). At the 2-nd second the wind will change its direction to East so the robot will start moving to East until it reaches the right border of the board since the wind will not change its direction until the 6-th second. At that time the wind will point to South-West which will make the robot to move to that direction until it reaches the lower left border of the board. At the 15-th second the robot will start moving upwards since the wind will start pointing to North. There will come a point when the robot will stop moving since it can not move to a position which he has visited before (0,0). Even at the 20-th second when the wind will change its direction to West, the robot will not be able to move since he is at the left border of the board. At this point, there are 13 positions of the board which have not been visited by the robot during its journey.

     Sample Input 1

     7 10
     0 E
     3 N
     7 NW
     10 SE
     13 W
     20 S
     24 NE
     32 W
     40 N
     43 SE
     Sample Output 1

     28
     Explanation 1

     image
     */


        private int n, k;
        private int currentPosition[] = {0, 0};
        private int currentTime = 0;
        private String currentWind;
        private Byte[] curreWindArray = {0, 0};
        private long visited = 1;
        private WindProvider windProvider;
        private Scanner scanner;

        public void start() {
            initFields();
            getNumbers();
            getData(new HashMap<Integer, LinkedHashSet<Integer>>());
            printNotVisited();
        }

        private void getNumbers() {
            n = scanner.nextInt();
            if (n < 3 || n > 100000)
                Utils.runError();
            k = scanner.nextInt();
            if (k < 2 || k > 1000000)
                Utils.runError();
        }

        private void printNotVisited() {

            System.out.println(((long) n) * ((long) n) - visited);
        }

        private void runRobot(HashMap<Integer, LinkedHashSet<Integer>> table, int time, String wind) {
            curreWindArray = windProvider.getMovement(currentWind);
            move(table, wind, time, curreWindArray);

        }

        private void lastMove(HashMap<Integer, LinkedHashSet<Integer>> table) {
            curreWindArray = windProvider.getMovement(currentWind);
            move(table, currentWind, currentTime + n, curreWindArray);
        }

        private int stepsToMove(Byte[] movement) {
            int min = n;
            if (movement[0] != 0) {
                if (movement[0] < 0) {
                    min = currentPosition[0];
                } else {
                    min = (n - 1) - currentPosition[0];
                }
            }
            if (movement[1] != 0) {
                if (movement[1] < 0) {
                    min = (currentPosition[1] < min ? currentPosition[1] : min);
                } else {
                    min = (((n - 1) - currentPosition[1]) < min ? ((n - 1) - currentPosition[1]) : min);
                }
            }
            return min;
        }

        private void move(HashMap<Integer, LinkedHashSet<Integer>> table, String wind, int time, Byte[] movement) {
            int min = stepsToMove(movement);
            min = (time - currentTime) < min ? time - currentTime : min;
            for (int i = 0; i < min; i++) {
                if (!(table.get(currentPosition[0] + movement[0]) != null && table.get(currentPosition[0] + movement[0]).contains(currentPosition[1] + movement[1]))) {
                    currentPosition[0] = currentPosition[0] + movement[0];
                    currentPosition[1] = currentPosition[1] + movement[1];
                    if (table.get(currentPosition[0]) == null) {
                        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();
                        hashSet.add(currentPosition[1]);
                        table.put(currentPosition[0], hashSet);
                    } else {
                        table.get(currentPosition[0]).add(currentPosition[1]);
                    }
                    visited++;
                } else
                    break;
            }
            currentWind = wind;
            currentTime = time;
        }

        private void getData(HashMap<Integer, LinkedHashSet<Integer>> table) {
            int time;
            LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();
            hashSet.add(0);
            table.put(0, hashSet);
            int nextInt = scanner.nextInt();
            currentWind = scanner.next();
            runRobot(table, nextInt, currentWind);
            for (int i = 1; i < k; i++) {
                time = scanner.nextInt();
                if (time <= nextInt || time >= 1000000)
                    Utils.runError();
                runRobot(table, time, scanner.next());
                nextInt = time;
            }
            scanner.close();
            lastMove(table);
        }

        private void initFields() {
            windProvider = new WindProvider();
            scanner = new Scanner(System.in);
        }
    }
    class Utils {

        public static void runError() {
            throw new IllegalArgumentException();
        }
    }

    class WindProvider {
        HashMap<String, Byte[]> windMap;

        public WindProvider() {
            initMap();
        }

        private void initMap() {
            windMap = new HashMap<>();
            windMap.put("N", new Byte[]{-1, 0});
            windMap.put("S", new Byte[]{1, 0});
            windMap.put("W", new Byte[]{0, -1});
            windMap.put("E", new Byte[]{0, 1});
            windMap.put("NE", new Byte[]{-1, 1});
            windMap.put("NW", new Byte[]{-1, -1});
            windMap.put("SE", new Byte[]{1, 1});
            windMap.put("SW", new Byte[]{1, -1});
        }

        public Byte[] getMovement(String currentWind) {
            return windMap.get(currentWind);

        }

}
