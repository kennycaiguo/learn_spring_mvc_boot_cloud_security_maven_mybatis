package org.kenny.factory;

public class WeaponFactory {
    public static Weapon create(String weaponType){
        if(weaponType==null || weaponType.trim().isEmpty()){
           return null;
        }
        Weapon weapon = null;
        String wType = weaponType.toLowerCase();
        return switch (wType) {
            case "tank" -> new Tank();
            case "fighter" -> new Fighter();
            case "dagger" -> new Dagger();
            default -> null;
        };
    }
}
