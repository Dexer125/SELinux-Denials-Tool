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

        //Ask for paths
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path to log file: ");
        path = scanner.nextLine();
        System.out.println("Enter the output path: ");
        outPath = scanner.nextLine();

        try {
            //Read file line by line
            reader = new BufferedReader(new FileReader(path));
            //Write to output file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outPath + "outputTemp.txt"));
            String line = reader.readLine();
            while (line != null) {

                if (line.contains("avc: denied")&& line.contains("tcontext=u:object_r:")){

                    command = line.substring(line.indexOf("denied")+7, line.indexOf(" for"));
                    scontext = line.substring(line.indexOf("scontext=u:r:")+13, line.indexOf(" tcontext"));
                    tcontext = line.substring(line.indexOf("tcontext=u:object_r:")+20, line.indexOf(":s0 tclass"));
                    tclass = line.substring(line.indexOf("tclass=")+7, line.indexOf("permissive"));
                    output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                    System.out.println("allow " + scontext + " " + tcontext + ":" + tclass + command + ";");

                    writer.write(output);
                    writer.newLine();
                    writer.flush();

                }

                //AVC log contains two types of tcontext
                else if (line.contains("avc: denied")&& line.contains("tcontext=u:r:")){

                    command = line.substring(line.indexOf("denied")+7, line.indexOf(" for"));
                    scontext = line.substring(line.indexOf("scontext=u:r:")+13, line.indexOf(" tcontext"));
                    tcontext = line.substring(line.indexOf("tcontext=u:r:")+13, line.indexOf(":s0 tclass"));
                    tclass = line.substring(line.indexOf("tclass=")+7, line.indexOf("permissive"));
                    output = "allow " + scontext + " " + tcontext + ":" + tclass + command + ";";

                    System.out.println("allow " + scontext + " " + tcontext + ":" + tclass + command + ";");

                    writer.write(output);
                    writer.newLine();
                    writer.flush();

                }

                line = reader.readLine();
            }
            writer.close();
            reader.close();
        } catch (StringIndexOutOfBoundsException | IOException e) {
            System.out.println("Exception occured...");
            e.printStackTrace();
        }

        try {
            // Code for duplicate line removing
            System.out.println("Removing duplicates...");
            PrintWriter printWriter = new PrintWriter(outPath + "output.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outPath + "outputTemp.txt"));
            String line = bufferedReader.readLine();

            HashSet<String> hashSet = new HashSet<String>();

            while (line != null){

                // Write only if not present in hashset
                if (hashSet.add(line)){
                    printWriter.println(line);
                }
                line = bufferedReader.readLine();
            }

            printWriter.flush();
            bufferedReader.close();
            printWriter.close();
            File tempFile = new File(outPath + "outputTemp.txt");
            if (tempFile.delete()){
                System.out.println("Temporary file removed.");
            }
            System.out.println("Duplicates removed.");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        }

    }
/* Coded by Dexer125 */