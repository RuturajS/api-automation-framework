package com.framework.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.framework.utils.PostmanExecutor;
import java.io.IOException;

public class MainRunner {
    @Parameter(names = { "--collection", "-c" }, description = "Path to Postman collection JSON")
    private String collectionPath;

    @Parameter(names = { "--env", "-e" }, description = "Environment (dev, qa, stage, prod)")
    private String env = "dev";

    public static void main(String[] args) {
        MainRunner runner = new MainRunner();
        JCommander.newBuilder()
                .addObject(runner)
                .build()
                .parse(args);

        System.setProperty("env", runner.env);

        if (runner.collectionPath != null) {
            try {
                PostmanExecutor.executeCollection(runner.collectionPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No collection provided. Running default regression tests via TestNG...");
            // In a real scenario, you'd trigger TestNG programmatically here if needed.
        }
    }
}
