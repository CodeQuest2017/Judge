import java.util.*;
import java.io.*;

public class client {
	public static void main(String[] args) throws Exception {
		List<Problem> data = ParseJson.inFile("data/sample.js");
		System.out.println(data);
	}
}
