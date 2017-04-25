//Copyright (c) 2017 Lily Corporation. All rights reserved.
import java.util.*;
import java.io.*;

public class ParseJson {
    public static Map<String, String> map;
    public static List<Problem> parseJson(String file) throws Exception {
        Scanner scan = new Scanner(new File(file));
        List<Problem> problems = new JsonArrayList<Problem>();
        while(scan.hasNextLine()) {
            String next = scan.nextLine();
            if(next.indexOf("{") == 1) {
                map = new LinkedHashMap<String, String>();
                String keyValuePair = scan.nextLine();
                while(keyValuePair.indexOf("}") != 1) {
                    map.put(keyValuePair.substring(3, keyValuePair.indexOf(": ")-1), keyValuePair.substring(keyValuePair.indexOf(": ")+2, keyValuePair.length()-1));
                    keyValuePair = scan.nextLine();
                }
                Problem prob = new Problem(map);
                problems.add(prob);
            }
        }
        return problems;
    }
}
