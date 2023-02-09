package com.qiplat.toolchain.jbd;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            JbdExecutor.printHelp(System.out);
            throw new RuntimeException("no command");
        }
        try {
            JbdCommand command = new JbdCommand(args);
            JbdExecutor generator = new JbdExecutor(command);
            generator.runJbd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}