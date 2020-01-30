package org.hsog;

import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StringBuilder inputFilePath = new StringBuilder();
        StringBuilder outputFilePath = new StringBuilder();
        try {
            getCommands(args, inputFilePath, outputFilePath);
            System.out.println("Input: " + inputFilePath);
            System.out.println("Output: " + outputFilePath);

            // Redirect System.out to output file
            PrintStream originalOut = System.out;
            PrintStream fileOut = new PrintStream(outputFilePath.toString());
            System.setOut(fileOut);

            run(inputFilePath.toString());

            // Redirect System.out to console
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getCommands(String[] args, StringBuilder inputFilePath, StringBuilder outputFilePath) {
        Options options = new Options();
        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("trace2sql", options);
            System.exit(1);
        }

        inputFilePath.append(cmd.getOptionValue("input"));
        outputFilePath.append(cmd.getOptionValue("output"));
    }

    public static void run(String inputFilePath) throws FileNotFoundException {
        List<String> lines = FileReader.readFileInList(inputFilePath);
        ArrayList<EntityT> el = Parser.parseTracefile(lines);
        Parser.genCursorIDs(el);
        Printer.printSQLcreateTable();
        Printer.printSQLinsertTable(el);
    }
}
