package com.gktechverse.corejava.collections.hashmap.internalworking;

/**
 * Custom key that intentionally creates collisions to explain bucket behavior.
 */
public class StudentBucketKey {
    private final String section;
    private final int rollNumber;

    public StudentBucketKey(String section, int rollNumber) {
        this.section = section;
        this.rollNumber = rollNumber;
    }

    @Override
    public int hashCode() {
        return 100;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StudentBucketKey that)) {
            return false;
        }
        return rollNumber == that.rollNumber && section.equals(that.section);
    }

    @Override
    public String toString() {
        return section + "-" + rollNumber;
    }
}
