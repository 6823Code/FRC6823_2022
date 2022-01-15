package frc.robot.util;

import java.util.ArrayDeque;
import java.util.Deque;

public class MovingAverage {
    Deque<Double> dq;
    int size;
    double sum;

    public MovingAverage(int size) {
        dq = new ArrayDeque<>();
        this.size = size;
    }

    public void nextVal(double val) {
        sum += val;
        dq.add(val);
        if (dq.size() > size) {
            sum -= dq.getFirst();
            dq.removeFirst();
        }
    }

    public double get() {
        if (size == 0)
            return 0;
        return sum / size;
    }
}