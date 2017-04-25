import java.util.*;
import java.io.*;

public class TestDriver {
	public static void main(String[] args) throws Exception {
		Map<String, String> options = new HashMap<String, String>();
		// All the directories depend on the directory in which Judge is
		// ran. TODO: Figure out a decent way to implement this
		// It actually shouldn't matter much since we can do ../
		// when the parent folder is Judge and nothing when there is no
		// parent folder. Since we only care about finding the directory
		// for automation, we don't need to worry about the particular
		// case when the user chooses the inDir, outDir, and compileDir
		// directly (we will instruct them that the directory depends
		// on this file's location, and they can change it as needed)
		// most of the time, everything will be in the same folder
		options.put("inDir", "../Cases/2013/sample/in/");
		options.put("outDir", "../Cases/2013/sample/out/");
		options.put("compileDir", "../2013/Donovan/");
		Test2 b = new Test2(16, options);
		b.test();
	}
}