/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testreadline;

import java.io.*;

public class TestReadLine {
    public static void main(String[] args) {
        EditableBufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
            str = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nLa línea es: " + str);
    }
}
