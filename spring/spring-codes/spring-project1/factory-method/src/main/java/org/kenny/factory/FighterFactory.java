package org.kenny.factory;

public class FighterFactory implements WeaponFactory{
    @Override
    public Weapon create() {
        return new Fighter();
    }
}
