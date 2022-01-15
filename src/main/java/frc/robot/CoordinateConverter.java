package frc.robot;

public class CoordinateConverter {

    //private static char yList[] = { 'A', 'B', 'C', 'D', 'E' };

    public static int[] alphaNumbaToInches(char y, int x) {
        int yVal;
        switch (y) {
        case 'A':
            yVal = 150;
            break;
        case 'B':
            yVal = 120;
            break;
        case 'C':
            yVal = 90;
            break;
        case 'D':
            yVal = 60;
            break;
        case 'E':
            yVal = 30;
            break;
        default:
            yVal = 0;
        }
        int xVal = x * 30;
        int[] values = { xVal, yVal };
        return values;
    }
}