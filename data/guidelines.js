var data = [
	{
		// Identifier: this tells us exactly what problem
		// we're dealing with and who solved it
		"year": 2013,
		"name": "Donovan",
		"number": 16,

		// Hash: This is only used for the java program,
		// not the web interface. We keep track of the java
		// file to make sure whether or not we've tested the
		// file already. If we have, then just run the program
		// and return the results. Otherwise, recompile the source
		// file and return the new results
		"hash": "A539GJ40AVKG",

		// Error: Was there a compile time error? Run time error?
		// Line number error? If so, then we can't get the expected
		// and actual values. If there was an error, then 'null'
		// is replaced with the error description
		"compileTimeError": null,
		"runTimeError": null,
		"lineCountError": null,


		// Expected and Actual: We explicitly return the expected
		// and actual values so we can state "Expected ___, Got ___"
		// in the web interface. These will be arrays of strings.
		// The strings represent each individual line.
		"expected": [],
		"actual": [],

		// Counter: The number of times we've compiled and tested
		// this file. Keep incrementing this number so long as the
		// hashes are *unique* and up until the first implementation
		// that passes both the sample case and the judge case
		"counter": 6,
	}
];
