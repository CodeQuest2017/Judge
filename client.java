import java.util.*;
import java.io.*;

public class client {
	private static final String saveFile = "../codequest2017.github.io/data/problems.js";
	protected static List<Problem> problems;
	public static void main(String[] args) throws Exception {
		File folder = new File("../");
		String year, name;
		
		// lists of directories/files in the following nested loops
		File[] listOfFiles = folder.listFiles();
		File[] secondaryList;
		File[] tertiaryList;
		
		Map<String, String> options;
		Map<String, String> parameters;
		List<Problem> d = new JsonArrayList<Problem>();

		File savedData = new File(saveFile);
		problems = (savedData.exists() && !savedData.isDirectory()) ? ParseJson.inFile(saveFile) : new JsonArrayList<Problem>();

		for(int i = 0; i < listOfFiles.length; i++) {
			
			if(listOfFiles[i].isDirectory() && listOfFiles[i].getName().length() == 4) {
				year = listOfFiles[i].getName();
				secondaryList = listOfFiles[i].listFiles();
				
				for(int b = 0; b < secondaryList.length; b++) {
					
					if(secondaryList[b].isDirectory()) {
						tertiaryList = secondaryList[b].listFiles();
						name = secondaryList[b].getName();
						
						for(int c = 0; c < tertiaryList.length; c++) {
							
							if(tertiaryList[c].isFile() && tertiaryList[c].getName().indexOf(".java") != -1) {
								
								int num = Integer.parseInt(tertiaryList[c].getName().substring(4, 6));
								
								options = new LinkedHashMap<String, String>();
								options.put("inDir", "../Cases/" + year + "/sample/in/");
								options.put("outDir", "../Cases/" + year + "/sample/out/");
								options.put("compileDir", "../" + year + "/" + name + "/");
								
								parameters = new LinkedHashMap<String, String>();
								parameters.put("year", String.valueOf(year));
								parameters.put("name", ParseJson.escape(name));
								parameters.put("number", String.valueOf(num));

								Print.purple("Currently testing: ../" + year + "/" + name + "/" + tertiaryList[c].getName());
								
								Judge judge = new Judge(num, options, parameters, problems);
								judge.test();
						
								Problem notC = judge.getProblem();
								d.add(notC);
							}
						}
					}
				}
			}
		}
		PrintWriter out = new PrintWriter(saveFile);
		out.println("var problems = " + d);
		out.close();
	}
}
