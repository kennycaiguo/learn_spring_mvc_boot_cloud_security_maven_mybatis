package org.kenny.test;

import org.junit.jupiter.api.Test;
import org.kenny.factory.Weapon;
import org.kenny.factory.WeaponFactory;

public class SimpleFactoryTest {
    @Test
    public void test() {
        Weapon dagger = WeaponFactory.create("dagger");
        dagger.attack();
        Weapon fighter = WeaponFactory.create("Fighter");
        fighter.attack();
        Weapon tank = WeaponFactory.create("Tank");
        tank.attack();
    }
}
