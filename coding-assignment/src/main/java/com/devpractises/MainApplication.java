package com.devpractises;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainApplication {
    private Scanner scanner;
    private PrintStream printStream;


    public MainApplication(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String promptUser() {
        printStream.println("please enter the path to log file:");
        String inputFromUser = scanner.next();
        return inputFromUser;
    }

    public void errorNotification() {
        printStream.println("File Path not valid.");
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream  = System.in;
        PrintStream printStream = System.out;

        MainApplication application = new MainApplication(inputStream, printStream);
        String inputFromUser = application.promptUser();

        InputArgumentValidator validateInput = new InputArgumentValidator();
        boolean validInput  = validateInput.test(inputFromUser);

        while(!validInput) {
            application.errorNotification();
            inputFromUser = application.promptUser();
            validInput = validateInput.test(inputFromUser);
        }

        String filePath = inputFromUser;

        LogFileParser logParser = new LogFileParser(filePath);

        HashMap<String, Event> results = logParser.parseFile();

        results.forEach((k,v) -> System.out.println(v.toString()));

    }
}
