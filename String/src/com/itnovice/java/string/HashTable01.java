package String.src.com.itnovice.java.string;

import com.google.common.base.Strings;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HashTable01 {
    public static void main(String[] args) {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 4);
        map.put("E", 5);


        for (Map.Entry<String, Integer> kv : map.entrySet()) {
            if (kv.getKey().equalsIgnoreCase("C")) {
                map.remove(kv.getKey());
            }
        }

        map.forEach((k, v) -> { System.out.println(k + "->" + v);});
    }
}
