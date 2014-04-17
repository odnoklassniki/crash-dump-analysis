package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcMem {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
        Pattern pattern = Pattern.compile("([0-9a-f]+)-([0-9a-f]+) (....) [0-9a-f]+ ..... \\d+\\s+");
        long totalSize = 0;
        long anonymousSize = 0;

        for (String s; (s = reader.readLine()) != null; ) {
            Matcher m = pattern.matcher(s);
            if (m.matches()) {
                long size = new BigInteger(m.group(2), 16).longValue() - new BigInteger(m.group(1), 16).longValue();
                totalSize += size;
                if ("rwxp".equals(m.group(3))) {
                    anonymousSize += size;
                }
            }
        }

        reader.close();
        System.out.println("Total size     = " + totalSize / 1024 + " KB");
        System.out.println("Anonymous size = " + anonymousSize / 1024 + " KB");
    }
}
