package org.kenny.factory;

public class Fighter extends Weapon{
    @Override
    public void attack() {
        System.out.println("Fighter is dropping bomb...");
    }
}
