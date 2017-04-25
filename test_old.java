// How to use: java test X where X = problem number
// e.g. java test 16 // tests Prob16

import java.util.*;
import java.io.*;

public class test_old {
	public static String N;
	public static void main(String[] args) throws Exception {
        System.out.println("\033[0m BLACK");
        System.out.println("\033[31m RED");
        System.out.println("\033[32m GREEN");
        System.out.println("\033[33m YELLOW");
        System.out.println("\033[34m BLUE");
        System.out.println("\033[35m MAGENTA");
        System.out.println("\033[36m CYAN");
        System.out.println("\033[37m WHITE");
		if(args.length < 1) System.exit(0);
		N = (args[0].length() < 2 ? "0" : "") + args[0];
		
		compileProgram();
		BufferedReader out = new BufferedReader(new InputStreamReader(runProgram()));
		String nextOut = null;
		List<String> output = new ArrayList<String>();
		while((nextOut = out.readLine()) != null) {
			output.add(nextOut);
		}

		if(output.size() < 1) {
			System.out.println("============= File compiled and ran, but did not produce any output. =============");
			System.exit(1);
		}

		HashMap<Boolean, List<String[]>> result = check(output);
		List<String[]> correct = result.get(false);
		List<String[]> incorrect = result.get(true);


		if(incorrect.size() == 0) {
			System.out.println("============= All correct. =============");
			for(int i = 0, x = correct.size(); i < x; i++) {
				String[] current = correct.get(i);
				System.out.println("Line #" + current[0] + ": " + current[1]);
			}
			System.exit(0);
		}

		String howBadIsIt = (correct.size() == 0) ? "All" : "Some";

		System.out.println("============= " + howBadIsIt + " test cases failed. Details below. =============");
		for(int i = 0, x = incorrect.size(); i < x; i++) {
				String[] current = correct.get(i);
				System.out.println("Line #" + current[0] + ": Expected " + current[2] + ", Got: " + current[1]);
		}

	}

	public static HashMap<Boolean, List<String[]>> check(List<String> actualvals) throws Exception {
		Scanner scan = new Scanner(new File("Prob" + N + ".out.txt"));
		HashMap<Boolean, List<String[]>> map = new LinkedHashMap<Boolean, List<String[]>>();
		String line = "";
		
		boolean hasError = true;
		List<String[]> successinfo = new ArrayList<String[]>();
		List<String[]> failinfo = new ArrayList<String[]>();
		
		List<String> input = new ArrayList<String>();
		while(scan.hasNext()) input.add(scan.nextLine());
		
		for (int i = 0; i < input.size(); i++) {
    		if(actualvals.get(i).equals(input.get(i))) {
    		    successinfo.add(new String[]{"" + (i + 1), "" + actualvals.get(i), "" +  input.get(i)});
    		} else {
    			failinfo.add(new String[]{"" + (i + 1), "" + actualvals.get(i), "" + input.get(i)});
    		}
		}
		
		map.put(false, successinfo);
		map.put(true, failinfo);
		
		return map;
	}

	private static void compileProgram() throws Exception {
		Process compileProc = Runtime.getRuntime().exec("javac Prob" + N + ".java");
		InputStream error = compileProc.getErrorStream();
		BufferedReader thisError = new BufferedReader(new InputStreamReader(error));
		String nextLineOfError = null;
		boolean hasError = false;
		while((nextLineOfError = thisError.readLine()) != null) {
			if(!hasError) System.out.println("============= Compile-time Error in Prob" + N + ".java. Details below: =============");
			hasError = true;
			System.out.println(nextLineOfError);
		}
		// printLines("javac stderr:", compileProc.getErrorStream());
		compileProc.waitFor();
		if(hasError) System.exit(1);
	}

	private static InputStream runProgram() throws Exception {
		Process runProc = Runtime.getRuntime().exec("java Prob" + N);
		InputStream error = runProc.getErrorStream();
		BufferedReader thisError = new BufferedReader(new InputStreamReader(error));
		String nextLineOfError = null;
		boolean hasError = false;
		while((nextLineOfError = thisError.readLine()) != null) {
			if(!hasError) System.out.println("============= Run-time Error in Prob" + N + ".java. Details below: =============");
			hasError = true;
			System.out.println(nextLineOfError);
		}
		runProc.waitFor();
		if(hasError) System.exit(1);
		return runProc.getInputStream();
	}
}
