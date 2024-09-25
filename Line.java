public class Line {
    private StringBuilder line;
    private int cursorPosition;

    public Line() {
        line = new StringBuilder();
        cursorPosition = 0;
    }

    public void insertChar(char ch) {
        line.insert(cursorPosition, ch);
        cursorPosition++;
    }

    public void moveCursorLeft() {
        if (cursorPosition > 0) cursorPosition--;
    }

    public void moveCursorRight() {
        if (cursorPosition < line.length()) cursorPosition++;
    }

    public void deleteChar() {
        if (cursorPosition < line.length()) {
            line.deleteCharAt(cursorPosition);
        }
    }

    public void backspace() {
        if (cursorPosition > 0) {
            line.deleteCharAt(cursorPosition - 1);
            cursorPosition--;
        }
    }

    @Override
    public String toString() {
        return line.toString();
    }
}
