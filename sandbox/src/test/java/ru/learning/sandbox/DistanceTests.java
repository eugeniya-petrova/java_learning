package ru.learning.sandbox;

import org.testng.annotations.Test;

public class DistanceTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(2.1, 2.5);
        Point p2 = new Point(2.1, 2.5);
        assert p1.distance(p2) == 0.0;
    }
}
