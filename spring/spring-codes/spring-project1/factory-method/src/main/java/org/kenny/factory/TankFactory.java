package org.kenny.factory;

public class TankFactory implements WeaponFactory{
    @Override
    public Weapon create() {
        return new Tank();
    }
}
