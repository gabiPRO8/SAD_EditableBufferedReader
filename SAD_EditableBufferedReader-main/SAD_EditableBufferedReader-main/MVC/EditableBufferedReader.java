package MVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditableBufferedReader extends BufferedReader{ 
    static final int DRETA = 67;
    static final int ESQUERRA = 68;
    static final int INICI = 72;
    static final int FINAL = 70;
    static final int DEL = 51; 
    static final int BPSK = 127; 
    static final int INSERT = 50; 
    static final int ENTER = 13;
   
    static final int VIRGULILLA = 126;
    static final int ESC = 27; 
    static final int CORXET = 91;

    static final int RET_DRETA = -1;
    static final int RET_ESQUERRA = -2;
    static final int RET_INICI = -3;
    static final int RET_FINAL = -4;
    static final int RET_DEL = -5;
    static final int RTE_INSERT = -6;

    //Variable per poder usar els metodes de la classe Console()
    Console consola;
    //Varibale per poder usar metodes de la classe Line()
    Line line;

    EditableBufferedReader(InputStreamReader in){
        super(in);
        this.consola = new Console();
        this.line = new Line();
        line.addObserver(consola);
    }
    public void setRaw() throws IOException {
        String [] modeRaw = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(modeRaw).waitFor();  
        }catch (Exception e) {
            consola.error();
        } 
    }
    public void unsetRaw() throws IOException {
        String[] modeCooked = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
        try{
            Runtime.getRuntime().exec(modeCooked).waitFor();
        } catch (IOException | InterruptedException e){
            //Comuniquem l'error
            consola.error();
        }
    }

    public int read() throws IOException {
        int lectura = 0;

        lectura = super.read();

        if(lectura == ESC){
            lectura = super.read();

            if(lectura == CORXET){
                lectura = super.read();

                switch(lectura){
                    case DRETA:
                        return RET_DRETA;
                    case ESQUERRA:
                        return RET_ESQUERRA;
                    case INICI:
                        return RET_INICI;
                    case FINAL:
                        return RET_FINAL;
                    case INSERT:
                        //Mirem si el caràcter enviat és ~
                        if(super.read() == VIRGULILLA){
                            return RTE_INSERT;
                        }
                        return -1;
                    case DEL:
                        //Mirem si el caràcter enviat és ~
                        if(super.read() == VIRGULILLA){
                            return RET_DEL;
                        }
                        return -1;
                    default:
                        //Si l'usuari prem ^[[ i un caràcter desconegut no retornem
                        return -1;
                }
            }
        //Si s'introdueix un caràcter comú el retornem
        } else {
            return lectura;
        }
        //Per si hi hagués algun error o cas no contemplat
        return -1;
    }

    public String readLine() throws IOException {
        int lectura = 0;
        int aux;

        //Ens fiquem en mode raw perque funcioni el programa i poder llegir la linea correctament
        this.setRaw();

        while((lectura = this.read()) != ENTER){
            switch(lectura){
                case RET_DRETA:
                    if(line.dreta()){
                        consola.move("C");
                    } else {
                        consola.campana();;
                    }
                break;
                case RET_ESQUERRA:
                    if(line.esquerra()){
                        consola.move("D");
                    } else {
                        consola.campana();;
                    }
                break;
                case RET_INICI:
                    aux = line.start();
                    consola.move(aux + "D");
                break;
                case RET_FINAL:
                    aux = line.end();
                    consola.move(aux + "C");
                break;
                case RET_DEL:
                    if(line.del()){
                        consola.move("P");
                    } else {
                        consola.campana();;
                    }
                break;
                case RTE_INSERT:
                    line.ins();
                    consola.modeInsertar();
                break;
                case BPSK:
                    if(line.bksp()){
                        consola.move("D");
                        consola.move("P");
                    } else {
                        consola.campana();;
                    }
                break;
                default:
                    //Per convertir el int llegit a un char utilitzem (char) int
                    boolean insert = line.add((char) lectura);
                    if(insert){
                        consola.modeNormal();
                    }
                    consola.escribir((char) lectura);
                break;
            }
        }
        //Tornem al mode per defecte de la consola
        this.unsetRaw();

        return line.toString();
    }
}
