package com.qiplat.toolchain.jbd;

import com.android.tools.r8.D8;
import com.googlecode.dex2jar.tools.Dex2jarCmd;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 降级包生成器
 *
 * @author Scave
 */
public class JbdExecutor {

    private final JbdCommand command;
    private final PrintStream printStream;

    public JbdExecutor(JbdCommand command) {
        this(command, System.out);
    }

    public JbdExecutor(JbdCommand command, PrintStream printStream) {
        this.command = command;
        this.printStream = printStream;
    }

    public void runJbd() throws IOException {
        if (command.isPrintHelp()) {
            printHelp(printStream);
            return;
        }
        String inputPath = command.getInputPath();
        File inputFile = new File(inputPath);
        printStream.println("Degrade bytecode level...");
        File tmpDir = new File(inputFile.getParent(), "jbd_tmp");
        tmpDir.mkdir();
        String[] d8Commands = {
                inputPath,
                "--min-api", "21", "--release",
                "--map-diagnostics", "warning", "info",
                "--output", tmpDir.getAbsolutePath()
        };
        D8.main(d8Commands);
        List<String> d2jCommands = new ArrayList<>();
        File[] dexFiles = tmpDir.listFiles();
        for (File dexFile : dexFiles) {
            d2jCommands.add(dexFile.getAbsolutePath());
        }
        d2jCommands.add("-o");
        d2jCommands.add(command.getOutputPath());
        d2jCommands.add("--force");
        Dex2jarCmd.main(d2jCommands.toArray(new String[0]));
        for (File dexFile : dexFiles) {
            dexFile.delete();
        }
        tmpDir.delete();
        printStream.close();
    }

    public static void printHelp(PrintStream printStream) {
        printStream.println("Java bytecode degrade tool, powered by Scave, origin from Rosemoe");
        printStream.println("Usage: jbd file [options...]");
        printStream.println("There are options:");
        printStream.println("-o: specified the output path");
        printStream.println("-v: print logs");
        printStream.println("--help: print this help");
    }
}
