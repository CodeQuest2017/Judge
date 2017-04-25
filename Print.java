import java.util.*;
import java.io.*;

public class Print {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";
	public static void black(String message) {
		System.out.println(ANSI_BLACK + message + ANSI_RESET);
	}
	public static void red(String message) {
		System.out.println(ANSI_RED + message + ANSI_RESET);
	}
	public static void green(String message) {
		System.out.println(ANSI_GREEN + message + ANSI_RESET);
	}
	public static void yellow(String message) {
		System.out.println(ANSI_YELLOW + message + ANSI_RESET);
	}
	public static void blue(String message) {
		System.out.println(ANSI_BLUE + message + ANSI_RESET);
	}
	public static void purple(String message) {
		System.out.println(ANSI_PURPLE + message + ANSI_RESET);
	}
	public static void cyan(String message) {
		System.out.println(ANSI_CYAN + message + ANSI_RESET);
	}
	public static void white(String message) {
		System.out.println(ANSI_WHITE + message + ANSI_RESET);
	}
}