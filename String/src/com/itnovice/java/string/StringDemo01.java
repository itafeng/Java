package com.itnovice.java.string;

        import javax.print.attribute.HashPrintJobAttributeSet;
        import java.awt.datatransfer.StringSelection;
        import java.util.HashMap;
        import java.util.Map;

public class StringDemo01 {

    public static void main(String[] args) {
        runTest();
    }

    private static void runTest() {
        Map<String, String> hash = new HashMap<>();
        hash.put("1", "ABC");
        hash.put("2", "BCD");
        hash.put("3", "CDE");
        hash.put("4", "DEF");
        hash.put("5", "EFG");

        String string = String.join("\n",hash.values());

        System.out.println(string);
    }

}
