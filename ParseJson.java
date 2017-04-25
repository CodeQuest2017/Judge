//Copyright (c) 2017 Lily Corporation. All rights reserved.
import java.util.*;
import java.io.*;

public class ParseJson {
    public static List<Problem> parseJson(String file) throws Exception {
        Scanner scan = new Scanner(new File(file));
        Map<String, String> map = new HashMap<String, String>();
        List<Problem> problems = new ArrayList<Problem>();
        while(scan.hasNextLine()){
            String next = scan.nextLine();
            int c = 0;
            if(next.equals("{")) {
                System.out.println("Got here at: " + ++c);
                String keyValuePair = scan.nextLine();
                while(keyValuePair.indexOf("}") != 0) {
                    map.put(keyValuePair.substring(keyValuePair.indexOf(": ")), keyValuePair.substring(keyValuePair.indexOf(": ")+1, keyValuePair.length()-1));
                    keyValuePair = scan.nextLine();
                }
                Problem prob = new Problem(map);
                problems.add(prob);
            }
        }
        return problems;
    }
}

// {

// }