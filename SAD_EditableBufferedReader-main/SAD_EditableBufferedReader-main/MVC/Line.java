package MVC;

import java.util.ArrayList;
import java.util.Observable;

public class Line extends Observable {
    private int pos;
    private boolean insert;
    private ArrayList<Character> linia;

    public Line() {
        this.pos = 0;
        this.insert = true;
        this.linia = new ArrayList<Character>();
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int posicio) {
        this.pos = posicio;
    }

    public int start() {
        int aux = pos;
        // Ens movem al inici
        this.pos = 0;
        // Identifiquem el canvi d'estat
        setChanged();
        // Notifiquem al observador/observadors que volem
        notifyObservers();
        // Retornem el numero de valors que s'ha de desplaçar per arribar al inici
        return aux;
    }

    public int end() {
        int aux = pos;
        // Ens movem al final
        this.pos = this.linia.size();
        // Identifiquem el canvi d'estat
        setChanged();
        // Notifiquem al observador/observadors que volem
        notifyObservers();
        // Retornem el numero de valors que s'ha de desplaçar cap a la dreta
        return (this.linia.size() - aux);
    }

    public boolean dreta() {
        if (this.pos < this.linia.size()) {
            this.pos++;
            // Identifiquem el canvi d'estat
            setChanged();
            // Notifiquem al observador/observadors que volem
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public boolean esquerra() {
        if (this.pos > 0) {
            this.pos--;
            // Identifiquem el canvi d'estat
            setChanged();
            // Notifiquem al observador/observadors que volem
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public boolean bksp() {
        // Borrem la posicio de l'esquerra del cursor
        if (!linia.isEmpty()) {
            this.linia.remove(this.pos - 1);
            this.esquerra();
            // Identifiquem el canvi d'estat
            setChanged();
            // Notifiquem al observador/observadors que volem
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public boolean del() {
        // Borrem la posicio de la dreta del cursor
        if (this.pos < this.linia.size() && !linia.isEmpty()) {
            this.linia.remove(this.pos);
            // Identifiquem el canvi d'estat
            setChanged();
            // Notifiquem al observador/observadors que volem
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public void ins() {
        insert = !insert;
    }

    public boolean add(char c) {
        // Mirem si estem en mode insercio
        if (insert) {
            this.linia.add(pos, c);
            // Mirem si estem en mode sobre-escriptura
        } else {
            // Comprovem que no ens trobem al final, ja que set() donaria error
            if (pos < this.linia.size()) {
                this.linia.set(pos, c);
            } else {
                this.linia.add(pos, c);
            }
        }
        //Identifiquem el canvi d'estat
        setChanged();
        //Notifiquem al observador/observadors que volem
        notifyObservers();
        this.dreta();
        return insert;
    }

    public String toString() {
        // Mostrar la linea de caràcters
        String str = "";
        for (Character c : linia) {
            str = str + c;
        }
        return str;
    }
}
