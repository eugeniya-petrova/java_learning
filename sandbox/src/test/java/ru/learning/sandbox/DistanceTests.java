package ru.learning.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

    @Test
    public void testDistance1() {
        Point p1 = new Point(2.1, 2.5);
        Point p2 = new Point(2.1, 2.5);
        Assert.assertEquals(p1.distance(p2),0);
    }

    @Test
    public void testDistance2() {
        Point p1 = new Point(3, 2);
        Point p2 = new Point(-5, -2);
        Assert.assertEquals(p1.distance(p2),8.94427190999916);
    }

    @Test
    public void testDistance3() {
        Point p1 = new Point(3, 2);
        Point p2 = new Point(-5, 2);
        Assert.assertEquals(p1.distance(p2),8);
    }

    @Test
    public void testDistance4() {
        Point p1 = new Point(-3, -1);
        Point p2 = new Point(-3, -3);
        Assert.assertEquals(p1.distance(p2),2);
    }
}
