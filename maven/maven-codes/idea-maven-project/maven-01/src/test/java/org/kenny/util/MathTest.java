package org.kenny.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//测试类，是我们的上面的类名+Test
public class MathTest {
    @Test
    public void testAdd() {
        int a = 10;
        int b = 20;
        int c = 30;
        int expected = Math.add(a, b);
        Assertions.assertEquals(c, expected);
    }

    @Test
    public void testSub() {
        int a = 30;
        int b = 20;
        int c = a - b;
        int expected = Math.sub(a, b);
        Assertions.assertEquals(c, expected);
    }
}
