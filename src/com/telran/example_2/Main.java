package com.telran.example_2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    //<filename, content>
    //abc.txt, abc
    //abc.txt, zyx

    //abc.txt, abc\n zyx


    private static Map<String, String> fileContent = new HashMap<>();

    private static final ApplicationConfiguration configuration = ReflectionConfigurationHandler.getConfiguration(ApplicationConfiguration.class);


    public static void main(String[] args) throws InterruptedException {

        String path = configuration.getBasePath();

        // C:\\users\\
        // puller

        //C:\\userspuller

        if (path.endsWith(File.separator)) {
            path = path + configuration.getInput();
        } else {
            path = path + File.separator + configuration.getInput();
        }

        System.out.println(path);
        File pullerDirectory = new File(path);
        if (!pullerDirectory.exists()) {
            pullerDirectory.mkdir();
        }

        while (true) {

            List<File> files = Arrays.stream(pullerDirectory.listFiles()).collect(Collectors.toList());

            if (files.size() == 0) {
                Thread.sleep(1000); //do nothing for 1 second (1000 milliseconds)
                continue;
            }

            files.stream()
                    .peek(x -> {
                        try {

                            byte[] bytes = Files.readAllBytes(Paths.get(x.getAbsolutePath()));
                            String content = new String(bytes);

                            fileContent.compute(x.getName(), (filename, mapContent) -> (mapContent == null) ? content : mapContent.concat(", " + content));
                            System.out.printf("File [%s] was saved\n", x.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    //.peek()       output directory logic
                    .peek(x -> x.delete())
                    .forEach(x -> System.out.printf("File [%s] was deleted\n", x.getName()));

            System.out.println();
            System.out.println(" --- Map Content ---");
            fileContent.forEach((filename, content) -> System.out.printf("File: [%s] | Content: [%s]\n", filename, content));
            System.out.println();

            Thread.sleep(1000); //do nothing for 1 second (1000 milliseconds)
        }
    }
}

class Sandbox {
    public static void main(String[] args) throws IOException {
//        Properties properties = new Properties();
//        InputStream inputStream = Main.class.getResourceAsStream("/application.properties");
//        properties.load(inputStream);
//
//        System.out.println(properties.getProperty("basePath"));
//        System.out.println(properties.getProperty("input"));
//        System.out.println(properties.getProperty("output"));

        ApplicationConfiguration configuration = ReflectionConfigurationHandler.getConfiguration(ApplicationConfiguration.class);

        System.out.println(configuration.getBasePath());
        System.out.println(configuration.getInput());
        System.out.println(configuration.getOutput());

        System.out.println("---------------");

        AnotherConfiguration anotherConfiguration = ReflectionConfigurationHandler.getConfiguration(AnotherConfiguration.class);
        System.out.println(anotherConfiguration.getGroup());

    }
}


class AnotherConfiguration {
    private String group;

    public AnotherConfiguration() {
    }

    public String getGroup() {
        return group;
    }
}
