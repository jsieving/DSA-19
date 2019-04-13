import java.util.ArrayList;
import java.util.List;

public class CoinsOnAClock {

    public static void placeCoin(int val, char[] curr, int hours_open, int hour, int n_hours, int p, int n, int d, List<char[]> answers) {

        char c = '\0';
        if (val == 1) {c = 'p'; p--;}
        else if (val == 5) {c = 'n'; n--;}
        else if (val == 10) {c = 'd'; d--;}
        int next_hour = (hour + val) % n_hours;
        if (curr[hour] == '\0') {
            System.out.printf("Placing %c at %d: now %d\n", c, hour, next_hour);
            char[] curr_temp = new char[curr.length];
            System.arraycopy(curr, 0, curr_temp, 0, curr.length);
            curr_temp[hour] = c;
            coinsHelper(curr_temp, hours_open - 1, next_hour, n_hours, p, n, d, answers);
        } else{
            System.out.print("Collision\n");
        }
    }

    public static void printArr(char[] chars) {
        for (char c : chars) {
            System.out.printf("%c, ", c);
        }
        System.out.print("\n");
    }

    public static void coinsHelper(char[] curr, int hours_open, int hour, int n_hours, int p, int n, int d, List<char[]> answers) {
        if (hours_open == 0) {
            printArr(curr);
            answers.add(curr);
        } else {
            if (p > 0) {
                placeCoin(1, curr, hours_open, hour, n_hours, p, n, d, answers);
            }
            if (n > 0) {
                placeCoin(5, curr, hours_open, hour, n_hours, p, n, d, answers);
            }
            if (d > 0) {
                placeCoin(10, curr, hours_open, hour, n_hours, p, n, d, answers);
            }
        }
    }

    public static List<char[]> coinsOnAClock(int pennies, int nickels, int dimes, int hoursInDay) {
        char[] curr = new char[hoursInDay];
        List<char[]> result = new ArrayList<>();
        coinsHelper(curr, hoursInDay, 0, hoursInDay, pennies, nickels, dimes, result);
        return result;
    }
}
