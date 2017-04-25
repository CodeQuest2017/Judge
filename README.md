# Judge

Offline program to automate the judging of code solutions.

**Note**: In order to use this program, your ProbXX.java files must include a variation of the following such that `args[0]` is used to determine the directory.

```java
Scanner scan = new Scanner(new File(args[0] + "ProbXX.in.txt"));
```

## Usage

Before entering any of these comamands, make sure that your current directory is the Judge directory and that your folder structure follows that of the GitHub organization.

```
CodeQuest/
	- 2012/
	- 2013/
	- 2014/
	- 2015/
	- 2016/
	- Cases/
	- Judge/
	- Problems/
	- Solutions/
	- codequest2017.github.io/
```

Once these conditions have been met, you can test your code with the command below.

```java
java test <year> <name> <problem number>
```

Example:

```java
java test 2013 Donovan 3
```

> Tests the solution for problem number 3 by Donovan for the 2013 Code Quest.
