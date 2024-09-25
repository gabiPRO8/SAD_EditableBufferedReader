public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() {
        try {
            // Canvia el mode de la consola a "raw"
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty raw -echo </dev/tty"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        try {
            // Torna la consola a "cooked"
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty cooked echo </dev/tty"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
