package com.dexer125;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Initialize values
        BufferedReader reader;
        String path;
        String outPath;
        String command;
        String scontext;
        String tcontext;
        String tclass;
        String output;
        String filename;
        String answer = "y";
        StringBuilder outpuTxt = new StringBuilder("output");
        int i = 0;
        int fileNameIndex;
        int count = 0;

        while (answer.equals("y")) {
            //Ask for paths
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the path to log file: ");
            path = scanner.nextLine();
            System.out.println("Would you like to save output into same path? y/n");
            answer = scanner.nextLine();
            if (answer.equals("y")) {
                fileNameIndex = path.lastIndexOf("/");
                filename = path.substring(fileNameIndex + 1);
                outPath = path.replace(filename, "");
            } else if (answer.equals("n")) {
                System.out.println("Enter the output path: ");
                outPath = scanner.nextLine();
            } else {
                System.out.println("\nWrong argument. \nEnding script...");
                return;
            }

            // Make sure that "/" is always at the end of the path
            if (!outPath.endsWith("/")) {
                outPath += "/";
            }
            try {
                //Read file line by line
                reader = new BufferedReader(new FileReader(path));
                //Write to output file
                BufferedWriter writer = new BufferedWriter(new FileWriter(outPath + "outputTemp.txt"));
                String line;
                line = reader.readLine();

                while (line != null) {

                    if (line.contains("avc: denied") && line.contains("tcontext=u:object_r:") && !line.contains(":s0:")) {
                        command = line.substring(line.indexOf("denied") + 7, line.indexOf(" for"));
                        scontext = line.substring(line.indexOf("scontext=u:r:") + 13, line.indexOf(":s0 tcontext"));
                        tcontext = line.substring(line.indexOf("tcontext=u:object_r:") + 20, line.indexOf(":s0 tclass"));
                        tclass = line.substring(line.indexOf("tclass=") + 7, line.indexOf("permissive"));
                        output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                        writer.write(output);
                        count++;
                        writer.newLine();
                        writer.flush();

                    }

                    //AVC log contains two types of tcontext
                    else if (line.contains("avc: denied") && line.contains("tcontext=u:r:") && !line.contains(":s0:")) {
                        command = line.substring(line.indexOf("denied") + 7, line.indexOf(" for"));
                        scontext = line.substring(line.indexOf("scontext=u:r:") + 13, line.indexOf(":s0 tcontext"));
                        tcontext = line.substring(line.indexOf("tcontext=u:r:") + 13, line.indexOf(":s0 tclass"));
                        tclass = line.substring(line.indexOf("tclass=") + 7, line.indexOf("permissive"));
                        output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                        writer.write(output);
                        count++;
                        writer.newLine();
                        writer.flush();

                    }
                    //Prevent errors because of :s0:
                    else if (line.contains("avc: denied") && line.contains("tcontext=u:r:") && line.contains(":s0:") && line.contains(":s0 tclass")) {
                        command = line.substring(line.indexOf("denied") + 7, line.indexOf(" for"));
                        scontext = line.substring(line.indexOf("scontext=u:r:") + 13, line.indexOf(":s0:"));
                        tcontext = line.substring(line.indexOf("tcontext=u:r:") + 13, line.indexOf(":s0 tclass"));
                        tclass = line.substring(line.indexOf("tclass=") + 7, line.indexOf("permissive"));
                        output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                        writer.write(output);
                        count++;
                        writer.newLine();
                        writer.flush();
                    } else if (line.contains("avc: denied") && line.contains("tcontext=u:object_r:") && line.contains(":s0:") && line.contains(":s0 tclass")) {
                        command = line.substring(line.indexOf("denied") + 7, line.indexOf(" for"));
                        scontext = line.substring(line.indexOf("scontext=u:r:") + 13, line.indexOf(":s0:"));
                        tcontext = line.substring(line.indexOf("tcontext=u:object_r:") + 20, line.indexOf(":s0 tclass"));
                        tclass = line.substring(line.indexOf("tclass=") + 7, line.indexOf("permissive"));
                        output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                        writer.write(output);
                        count++;
                        writer.newLine();
                        writer.flush();
                    } else if (line.contains("avc: denied") && line.contains("tcontext=u:r:") && line.contains(":s0:") && !line.contains(":s0 tclass")) {
                        command = line.substring(line.indexOf("denied") + 7, line.indexOf(" for"));
                        scontext = line.substring(line.indexOf("scontext=u:r:") + 13, line.indexOf(":s0:"));
                        tcontext = line.substring(line.indexOf("tcontext=u:r:") + 13, line.indexOf(" tclass"));
                        tclass = line.substring(line.indexOf("tclass=") + 7, line.indexOf("permissive"));
                        output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                        // Delete unwanted characters in output
                        if (output.contains(":s0:")) {
                            output = output.replace(output.substring(output.indexOf(":s0:"), output.indexOf(";") + 1), "");
                            output = output + ":" + tclass + command + ";";
                        }

                        writer.write(output);
                        count++;
                        writer.newLine();
                        writer.flush();
                    } else if (line.contains("avc: denied") && line.contains("tcontext=u:object_r:") && line.contains(":s0:") && !line.contains(":s0 tclass")) {
                        command = line.substring(line.indexOf("denied") + 7, line.indexOf(" for"));
                        scontext = line.substring(line.indexOf("scontext=u:r:") + 13, line.indexOf(":s0:"));
                        tcontext = line.substring(line.indexOf("tcontext=u:object_r:") + 20, line.indexOf(" tclass"));
                        tclass = line.substring(line.indexOf("tclass=") + 7, line.indexOf("permissive"));
                        output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                        if (output.contains(":s0:")) {
                            output = output.replace(output.substring(output.indexOf(":s0:"), output.indexOf(";") + 1), "");
                            output = output + ":" + tclass + command + ";";
                        }

                        writer.write(output);
                        count++;
                        writer.newLine();
                        writer.flush();

                    }

                    line = reader.readLine();
                }
                writer.close();
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Prevent from running duplicates removing if no denials in log
            if (count == 0) {
                System.out.println("\nNo denials in log file, check if your kernel supports audit logging.");
                System.out.println("\nTool by @Dexer125");
                return;
            }
            else {
                System.out.println("\nResolving denials...");
                System.out.println("\nTemporary file created");

                try {
                    // Code for duplicate line removing
                    System.out.println("Removing duplicates...");
                    PrintWriter printWriter = new PrintWriter(outPath + outpuTxt + ".txt");
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(outPath + "outputTemp.txt"));
                    String line = bufferedReader.readLine();

                    HashSet<String> hashSet = new HashSet<>();

                    while (line != null) {

                        // Write only if not present in hashset
                        if (hashSet.add(line)) {
                            printWriter.println(line);
                        }
                        line = bufferedReader.readLine();
                    }

                    printWriter.flush();
                    bufferedReader.close();
                    printWriter.close();
                    File tempFile = new File(outPath + "outputTemp.txt");
                    if (tempFile.delete()) {
                        System.out.println("Temporary file removed.");
                    }
                    System.out.println("Duplicates removed.");
                    if (outPath.contains("//")) {
                        outPath = outPath.replace("//", "/");
                    }
                    System.out.println("\nYou can find your fixed denials under " + outPath + outpuTxt + ".txt");

                    System.out.println("\nWould you like to open another log? y/n");
                    answer = scanner.nextLine();
                    if (answer.equals("y")){
                        i++;
                        outpuTxt.deleteCharAt(outpuTxt.length() -1);
                        outpuTxt.append(i);
                    }
                    else if (answer.equals("n")){
                        System.out.println("\nTool by @Dexer125");
                        System.out.println("\nEnding script...");
                    }
                    else {
                        System.out.println("\nWrong argument.\nEnding script...");
                        return;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}

/* Coded by Dexer125 */