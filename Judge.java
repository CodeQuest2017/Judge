import java.util.*;
import java.io.*;
import java.security.*;
import java.nio.file.*;
import javax.xml.bind.*;
import java.math.*;

public class Judge {
	private int prob;
	private Map<String, String> opts;
	private String num;
	private static final String Ss = "============= ";
	private static final String Se = " =============";
	private Map<String, String> data = new LinkedHashMap<String, String>();

	public Judge(int n, Map<String, String> o) {
		this.prob = n;
		this.opts = o;
		this.setDefault();
	}

	public Judge(int n, Map<String, String> o, Map<String, String> p) {
		this.prob = n;
		this.opts = o;
		p.forEach((k, v) -> {
			this.data.put(k, v);
		});
		this.setDefault();
	}

	public void setDefault() {
		this.data.put("hash", ParseJson.escape(""));
		this.data.put("compileTimeError", "null");
		this.data.put("runTimeError", "null");
		this.data.put("lineCountError", "null");
		this.data.put("expected", "[]");
		this.data.put("actual", "[]");
		this.data.put("counter", String.valueOf(0));
	}

	public Map<String, String> getData() {
		return this.data;
	}

	public Problem getProblem() {
		return new Problem(this.data);
	}

	public void test() throws Exception {
		this.num = (("" + this.prob).length() < 2 ? "0" : "") + this.prob;
		
		boolean didNotCompile = compileProgram();
		if(didNotCompile) return;

		InputStream fileOutput = runProgram();
		if(fileOutput == null) return;

		BufferedReader out = new BufferedReader(new InputStreamReader(fileOutput));
		String nextOut = null;
		List<String> output = new DataArrayList<String>();
		while((nextOut = out.readLine()) != null) output.add(nextOut);

		if(output.size() < 1) {
			String noOutput = "File compiled and ran, but did not produce any output.";
			Print.red(Ss + noOutput + Se);
			this.data.put("lineCountError", ParseJson.escape(noOutput));
			return;
		}

		HashMap<Boolean, List<String[]>> result = check(output);
		if(result == null) return;

		List<String[]> correct = result.get(false);
		List<String[]> incorrect = result.get(true);

		if(incorrect.size() == 0) {
			
			Print.green(Ss + "All correct." + Se);
			for(int i = 0, x = correct.size(); i < x; i++) {
				String[] current = correct.get(i);
				Print.green("Line #" + current[0] + ": " + current[1]);
			}

		} else {
			
			String howBadIsIt = (correct.size() == 0) ? "All" : "Some";

			Print.red(Ss + howBadIsIt + " test cases failed. Details below." + Se);
			for(int i = 0, x = incorrect.size(); i < x; i++) {
				String[] current = incorrect.get(i);
				Print.red("Line #" + current[0] + ": Expected: " + current[2] + " | Got: " + current[1]);
			}

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
		
		List<String> input = new DataArrayList<String>();
		int numLines = 0;
		while(scan.hasNext()) {
			input.add(scan.nextLine());
			numLines++;
		}

		// Store both actual and expected (the input)
		this.data.put("expected", input.toString());
		this.data.put("actual", actualvals.toString());

		if(actualvals.size() != numLines) {
			String lcError = "File compiled and ran, but the number of lines do not match.";
			Print.red(Ss + lcError + Se);
			for(int i = 0; i < actualvals.size(); i++) {
				Print.blue("Line #" + (i + 1) + ": " +  actualvals.get(i));
			}
			this.data.put("lineCountError", ParseJson.escape(lcError));
			return null;
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

	private boolean compileProgram() throws Exception {
		String compileDir = (this.opts.containsKey("compileDir") ? this.opts.get("compileDir") : "");
		
		byte[] javaBytes = Files.readAllBytes(Paths.get(compileDir + "Prob" + this.num + ".java"));
		byte[] javaHash = MessageDigest.getInstance("MD5").digest(javaBytes);
		String hash = DatatypeConverter.printHexBinary(javaHash);
		this.data.put("hash", ParseJson.escape(hash));
		
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
		StringBuilder theError = new StringBuilder();
		while((nextLineOfError = thisError.readLine()) != null) {
			if(!hasError) Print.red(Ss + "Compile-time Error in Prob" + this.num + ".java. Details below:" + Se);
			hasError = true;
			Print.red(nextLineOfError);
			theError.append(nextLineOfError + "\n");
		}
		compileProc.waitFor();
		if(hasError) {
			this.data.put("compileTimeError", ParseJson.escape(theError.toString()));
		}
		return hasError;
	}

	private InputStream runProgram() throws Exception {
		String cp = (this.opts.containsKey("compileDir") ? "-classpath " + this.opts.get("compileDir") + " " : "");
		String dir = (this.opts.containsKey("inDir") ? " " + this.opts.get("inDir") : "");
		Process runProc = Runtime.getRuntime().exec("java " + cp + "Prob" + this.num + dir);
		InputStream error = runProc.getErrorStream();
		BufferedReader thisError = new BufferedReader(new InputStreamReader(error));
		String nextLineOfError = null;
		boolean hasError = false;
		StringBuilder theError = new StringBuilder();
		while((nextLineOfError = thisError.readLine()) != null) {
			if(!hasError) Print.red(Ss + "Run-time Error in Prob" + this.num + ".java. Details below:" + Se);
			hasError = true;
			Print.red(nextLineOfError);
			theError.append(nextLineOfError + "\n");
		}
		runProc.waitFor();
		if(hasError) {
			this.data.put("runTimeError", ParseJson.escape(theError.toString()));
			return null;
		}
		return runProc.getInputStream();
	}
}
