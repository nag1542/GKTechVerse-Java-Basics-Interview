package com.gktechverse.corejava.collections.hashmap;

import com.gktechverse.corejava.collections.concurrenthashmap.ConcurrentHashMapBenefitsDemo;
import com.gktechverse.corejava.collections.hashmap.equalsandhashcode.EqualsHashCodeContractDemo;
import com.gktechverse.corejava.collections.hashmap.internalworking.HashMapCollisionAndTreeificationDemo;
import com.gktechverse.corejava.collections.hashmap.nullsandduplicates.HashMapNullAndDuplicateKeyDemo;
import com.gktechverse.corejava.collections.hashmap.threadsafety.HashMapThreadSafetyDemo;

/**
 * High-level series runner for HashMap interview concepts.
 */
public class HashMapTeachingSeriesRunner {

    public static void main(String[] args) {
        System.out.println("\n=== HashMap Teaching Series (Detailed for Students) ===");
        HashMapNullAndDuplicateKeyDemo.main(new String[]{});
        HashMapCollisionAndTreeificationDemo.main(new String[]{});
        HashMapThreadSafetyDemo.main(new String[]{});
        EqualsHashCodeContractDemo.main(new String[]{});
        ConcurrentHashMapBenefitsDemo.main(new String[]{});
    }
}
