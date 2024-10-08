package testreadline;
import java.io.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EditableBufferedReader extends BufferedReader {
    private boolean isRawMode;
    private Line line;
    private PropertyChangeSupport support;

    public EditableBufferedReader(Reader in) {
        super(in);
        this.line = new Line();
        this.support = new PropertyChangeSupport(this);
    }

    public void setRaw() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows - usar jLine o dejar la consola tal cual.
            System.out.println("Modo Raw .");
        } else if (os.contains("nix") || os.contains("nux")) {
            Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "stty raw -echo < /dev/tty"});
        }
        isRawMode = true;
    }

    public void unsetRaw() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows - usar jLine o dejar la consola tal cual.
            System.out.println("Saliendo del modo Raw en Windows.");
        } else if (os.contains("nix") || os.contains("nux")) {
            Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "stty cooked echo < /dev/tty"});
        }
        isRawMode = false;
    }

    public int read() throws IOException {
        return super.read(); // En esta implementación simple, solo leemos un carácter.
    }

    public String readLine() throws IOException {
        setRaw(); // Cambiar a modo Raw para lectura de caracteres especiales
        StringBuilder sb = new StringBuilder();
        int ch;

        while ((ch = read()) != '\n') {
            if (ch == 27) { // Secuencias de escape para flechas
                // Ignorar para simplificar o implementar detección de flechas.
            } else {
                sb.append((char) ch);
            }
        }
        
        unsetRaw(); // Regresar a modo normal
        return sb.toString();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}

