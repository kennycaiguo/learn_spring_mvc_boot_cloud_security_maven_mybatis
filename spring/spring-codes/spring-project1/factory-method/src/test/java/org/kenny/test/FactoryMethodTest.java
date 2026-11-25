package org.kenny.test;

import org.junit.jupiter.api.Test;
import org.kenny.factory.FighterFactory;
import org.kenny.factory.TankFactory;
import org.kenny.factory.Weapon;
import org.kenny.factory.WeaponFactory;

public class FactoryMethodTest {
    @Test
    public void test() {

        Weapon tank = new TankFactory().create();
        tank.attack();
        Weapon fighter = new FighterFactory().create();
        fighter.attack();
    }
}
