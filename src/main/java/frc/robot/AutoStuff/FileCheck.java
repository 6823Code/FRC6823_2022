package frc.robot.AutoStuff;

import java.io.*;
import java.util.Scanner;

public class FileCheck {
    private File file;

    public FileCheck()
    {
        file = new File("Inputs.txt");
        Scanner in;
        try {
            in = new Scanner(file);
            while (in.hasNextLine())
            {
                System.out.println(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error found");
        }
    }

    public void doNothing()
    {
        
    }
}
