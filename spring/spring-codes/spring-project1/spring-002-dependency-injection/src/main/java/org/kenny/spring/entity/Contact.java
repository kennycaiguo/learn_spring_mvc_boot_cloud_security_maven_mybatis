package org.kenny.spring.entity;

import java.util.Set;

public class Contact {
    private Set<String> phones;

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Phones=" + phones +
                '}';
    }
}
