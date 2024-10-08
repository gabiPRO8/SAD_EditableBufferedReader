/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testreadline;

public class Line {
    private StringBuilder text;
    private int cursorPosition;

    public Line() {
        this.text = new StringBuilder();
        this.cursorPosition = 0;
    }

    public void insertChar(char c) {
        text.insert(cursorPosition, c);
        cursorPosition++;
    }

    public void deleteChar() {
        if (cursorPosition > 0) {
            text.deleteCharAt(cursorPosition - 1);
            cursorPosition--;
        }
    }

    public String getText() {
        return text.toString();
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    // Otros métodos relacionados con la gestión de la línea y el cursor
}
