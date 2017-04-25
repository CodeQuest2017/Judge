import java.util.*;
import java.io.*;
import java.security.*;
import java.nio.file.*;
import javax.xml.bind.*;
import java.math.*;

public class Test2 {
	private int prob;
	private Map<String, String> opts;
	private String num;
	private static final String Ss = "============= ";
	private static final String Se = " =============";
	
	public Test2(int n, Map<String, String> o) {
		this.prob = n;
		this.opts = o;
	}

	public void test() throws Exception {
		this.num = (("" + this.prob).length() < 2 ? "0" : "") + this.prob;
		compileProgram();
		BufferedReader out = new BufferedReader(new InputStreamReader(runProgram()));
		String nextOut = null;
		List<String> output = new ArrayList<String>();
		while((nextOut = out.readLine()) != null) output.add(nextOut);

		if(output.size() < 1) {
			Print.red(Ss + "File compiled and ran, but did not produce any output." + Se);
			System.exit(1);
		}

		HashMap<Boolean, List<String[]>> result = check(output);
		List<String[]> correct = result.get(false);
		List<String[]> incorrect = result.get(true);

		if(incorrect.size() == 0) {
			Print.green(Ss + "All correct." + Se);
			for(int i = 0, x = correct.size(); i < x; i++) {
				String[] current = correct.get(i);
				Print.green("Line #" + current[0] + ": " + current[1]);
			}
			System.exit(0);
		}

		String howBadIsIt = (correct.size() == 0) ? "All" : "Some";

		Print.red(Ss + howBadIsIt + " test cases failed. Details below." + Se);
		for(int i = 0, x = incorrect.size(); i < x; i++) {
				String[] current = incorrect.get(i);
				Print.red("Line #" + current[0] + ": Expected: " + current[2] + " | Got: " + current[1]);
		}

	}

	public HashMap<Boolean, List<String[]>> check(List<String> actualvals) throws Exception {
		String outDir = (this.opts.containsKey("outDir") ? this.opts.get("outDir") : "");
		Scanner scan = new Scanner(new File(outDir + "Prob" + this.num + ".out.txt"));
		HashMap<Boolean, List<String[]>> map = new LinkedHashMap<Boolean, List<String[]>>();
		String line = "";
		
		boolean hasError = true;
		List<String[]> successinfo = new ArrayList<String[]>();
		List<String[]> failinfo = new ArrayList<String[]>();
		
		List<String> input = new ArrayList<String>();
		int numLines = 0;
		while(scan.hasNext()) {
			input.add(scan.nextLine());
			numLines++;
		}

		if(actualvals.size() != numLines) {
			Print.red(Ss + "File compiled and ran, but the number of lines do not match." + Se);
			for(int i = 0; i < actualvals.size(); i++) {
				Print.blue("Line #" + (i + 1) + ": " +  actualvals.get(i));
			}
			System.exit(1);
		}
		
		for(int i = 0; i < input.size(); i++) {
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

	private void compileProgram() throws Exception {
		String compileDir = (this.opts.containsKey("compileDir") ? this.opts.get("compileDir") : "");
		
		// This works, just test with file path specified:

		byte[] javaBytes = Files.readAllBytes(Paths.get(compileDir + "Prob" + this.num + ".java"));
		byte[] javaHash = MessageDigest.getInstance("MD5").digest(javaBytes);
		Print.blue("Hash of file is: " + DatatypeConverter.printHexBinary(javaHash));
		
		// System.exit(0);
		
		
		// TODO:
		// if(this file has a stored hash)
		// 	if(the hash is the same as the stored hash)
		// 		do not compile again, return
		// 	otherwise
		// 		compile again, continue
		// otherwise
		// 	compile again, continue
		
		Process compileProc = Runtime.getRuntime().exec("javac " + compileDir + "Prob" + this.num + ".java");
		InputStream error = compileProc.getErrorStream();
		BufferedReader thisError = new BufferedReader(new InputStreamReader(error));
		String nextLineOfError = null;
		boolean hasError = false;
		while((nextLineOfError = thisError.readLine()) != null) {
			if(!hasError) Print.red(Ss + "Compile-time Error in Prob" + this.num + ".java. Details below:" + Se);
			hasError = true;
			Print.red(nextLineOfError);
		}
		compileProc.waitFor();
		if(hasError) System.exit(1);
	}

	private InputStream runProgram() throws Exception {
		int c = 0;
		String cp = (this.opts.containsKey("compileDir") ? "-classpath " + this.opts.get("compileDir") + " " : "");
		String dir = (this.opts.containsKey("inDir") ? " " + this.opts.get("inDir") : "");
		// Print.blue("java " + cp + "Prob" + this.num + dir);
		// System.exit(0);
		Process runProc = Runtime.getRuntime().exec("java " + cp + "Prob" + this.num + dir);
		InputStream error = runProc.getErrorStream();
		BufferedReader thisError = new BufferedReader(new InputStreamReader(error));
		String nextLineOfError = null;
		boolean hasError = false;
		// Print.yellow("Got here " + ++c);
		// Print.yellow("thisError.readLine() = " + thisError.readLine());
		while((nextLineOfError = thisError.readLine()) != null) {
			if(!hasError) Print.red(Ss + "Run-time Error in Prob" + this.num + ".java. Details below:" + Se);
			hasError = true;
			Print.red(nextLineOfError);
		}
		runProc.waitFor();
		// Print.yellow("Got here " + ++c);
		if(hasError) System.exit(1);
		// Print.yellow("Got here " + ++c);
		return runProc.getInputStream();
	}
}
