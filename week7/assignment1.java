package week7;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

        public class assignment1{

            public static void main(String[] args) {
                File folder = new File("C:\\Users\\User\\IdeaProjects\\Realtime\\src");// Change to the folder path where is targeted

                int numJavaFileCount = countNumJavaFiles(folder);
                int numSolvedProblemCount = countNumFilesWithMainMethod(folder);

                System.out.println("Number of Java Files = " + numJavaFileCount);
                System.out.println("Number of Issues = " +  numSolvedProblemCount);
            }

            //1: Count num of Java Files (with .Java)
            public static int countNumJavaFiles(File folder) {
                File[] files = folder.listFiles();
                int count = 0;
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        if (file.isDirectory()) {
                            count += countNumJavaFiles(file);
                        } else if (file.getName().endsWith(".java")) {
                            count++;
                        }
                    }
                }
                return count;
            }

            //2: Count num of problem solved (with Main method means solved else not included in problem solved)
            public static int countNumFilesWithMainMethod(File folder) {
                int count = 0;
                File[] files = folder.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        if (file.isDirectory()) {
                            count += countNumFilesWithMainMethod(file);
                        } else if (file.getName().endsWith(".java")) {
                            try (Stream<String> lines = Files.lines(file.toPath())) {
                                if (lines.anyMatch(line -> line.contains("public static void main"))) {
                                    count++;
                                }
                            } catch (IOException e) {
                                System.err.println("This file cannot be read: " + file.getName());
                            }
                        }
                    }
                }
                return count;
            }
        }

