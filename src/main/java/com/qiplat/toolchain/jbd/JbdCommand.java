package com.qiplat.toolchain.jbd;

/**
 * Stub生成命令实体
 *
 * @author Scave
 */
public class JbdCommand {

    private static final String OUT_PATH = "-o";
    private static final String VERBOSE = "-v";
    private static final String HELP = "--help";

    private boolean printHelp;
    private String outputPath;
    private boolean verbose;
    private String inputPath;

    public JbdCommand() {
    }

    public JbdCommand(String[] args) {
        processCommand(args);
    }

    private void processCommand(String[] args) {
        for (int i = 0, length = args.length; i < length; i++) {
            switch (args[i]) {
                case OUT_PATH:
                    if (i + 1 < length) {
                        this.outputPath = args[++i];
                    } else {
                        throw new RuntimeException("output path error");
                    }
                    break;
                case HELP:
                    this.printHelp = true;
                    return;
                case VERBOSE:
                    this.verbose = true;
                    break;
                default:
                    this.inputPath = args[i];
                    break;
            }
        }
        if (!printHelp && this.outputPath == null) {
            throw new RuntimeException("output path must not be null");
        }
    }

    public boolean isPrintHelp() {
        return printHelp;
    }

    public void setPrintHelp(boolean printHelp) {
        this.printHelp = printHelp;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public enum Level {
        PRIVATE,
        PROTECTED,
        PUBLIC
    }
}
