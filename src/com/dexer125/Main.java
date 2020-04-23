package com.dexer125;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BufferedReader reader;
        String path;
        String outPath;
        String command;
        String scontext;
        String tcontext;
        String tclass;
        String output;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path to log file: ");
        path = scanner.nextLine();
        System.out.println("Enter the output path: ");
        outPath = scanner.nextLine();

        try {
            reader = new BufferedReader(new FileReader(path));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outPath + "output.txt"));
            String line = reader.readLine();
            while (line != null) {

                if (line.contains("avc: denied")){

                    command = line.substring(line.indexOf("denied")+7, line.indexOf(" for"));
                    scontext = line.substring(line.indexOf("scontext=u:r:")+13, line.indexOf(":s0 tcontext"));
                    tcontext = line.substring(line.indexOf("tcontext=u:object_r:")+20, line.indexOf(":s0 tclass"));
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        }

    }
