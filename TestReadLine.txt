public class TestReadLine {
    public static void main(String[] args) throws IOException {
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));
        System.out.println("Escriu una línia (pots editar-la): ");
        String line = reader.readLine();
        System.out.println("Línia final: " + line);
    }
}
