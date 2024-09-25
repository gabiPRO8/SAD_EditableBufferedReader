@Override
public int read() throws IOException {
    int ch = super.read();
    
    // Si és un caràcter d'escape, llegeix la seqüència completa.
    if (ch == 27) { // Seqüència d'escape ESC
        if (super.read() == 91) { // Comprova si la següent és '['
            ch = super.read(); // Llegim el següent codi per la tecla especial
            switch (ch) {
                case 'A': // Cursor amunt
                    return Key.UP;
                case 'B': // Cursor avall
                    return Key.DOWN;
                case 'C': // Cursor dreta
                    return Key.RIGHT;
                case 'D': // Cursor esquerra
                    return Key.LEFT;
                // Afegeix altres tecles especials si cal
                default:
                    return ch;
            }
        }
    }
    return ch;
}
