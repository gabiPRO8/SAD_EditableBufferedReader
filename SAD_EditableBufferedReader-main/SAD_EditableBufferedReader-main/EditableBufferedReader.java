import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class EditableBufferedReader extends BufferedReader {

    static final int DRETA = 67; //La fletxa dreta es representa al terminal 
    // com "^[[C", de tal manera que representarem DRETA com 'C'
    static final int ESQUERRA = 68; //La fletxa esquerra es representa al 
    // terminal com "^[[D", de tal manera que representarem ESQUERRA com 'D'
    static final int INICI = 72; //Tecla inicio Printea ^[[H, utilitzarem la H
    static final int FINAL = 70; //Tecla fin Printea ^[[F, utilitzarem la F
    static final int DEL = 51; //Tecla supr Printea ^[[3~, utilitzarem el 3
    static final int BPSK = 127; //Tecla <-- backspace, te numero en ASCII, no caldria ja que exixsteix
    static final int INSERT = 50; //Tecla insert Printea ^[[2~, utilitzarem el 2
    static final int ENTER = 13; //Útil per saber quan s'introdueix un CR i hem d'acabar
   
    static final int VIRGULILLA = 126; //Útil per fer DEL e INSERT, ja que cal "~"
    static final int ESC = 27; //Útil per fer les fletxes, ja que ens cal "^["
    static final int CORXET = 91; //Útile per representar les fletxes ja que ens cal "["

    //Definim els valors a retornar per la funció read, escollim valors a partir del
    //256, ja que son valors lliures a la taula ASCII, no definim BPSK, ja que es un 
    //valor existent i el podem retornar a ell mateix
    static final int RET_DRETA = -1;
    static final int RET_ESQUERRA = -2;
    static final int RET_INICI = -3;
    static final int RET_FINAL = -4;
    static final int RET_DEL = -5;
    static final int RTE_INSERT = -6;

    EditableBufferedReader(InputStreamReader in){
        super(in);
    }

    public static void setRaw() throws IOException {
        //Passar de mode Cooked a mode Raw
        //String amb la seqüència necessaria per canviar de mode Cooked a mode Raw al terminal i treutre l'echo
        String [] modeRaw = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
        try {
            //getRuntime().exec() serveix per poder executar la linea de comandes
            //waitFor() espera hasta que el subproceso termine
            Runtime.getRuntime().exec(modeRaw).waitFor();  
        }catch (Exception e) {
            //Comuniquem l'error
            System.out.println("Error");
        } 
    }
    
    public static void unsetRaw() throws IOException {
        //Passar de mode Raw a mode Cooked
        //String amb la seqüència necessaria per canviar de mode Raw a mode Cooked al terminal
        String[] modeCooked = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
        try{
            //getRuntime().exec() serveix per poder executar la linea de comandes
            //waitFor() espera hasta que el subproceso termine
            Runtime.getRuntime().exec(modeCooked).waitFor();
        } catch (IOException | InterruptedException e){
            //Comuniquem l'error
            System.out.println("Error");
        }
    }

    public int read() throws IOException {
        int lectura = 0;

        //Llegim el caràcter amb la funció main de BufferedReader
        lectura = super.read();
        //Mirem si els caràcters enviats són ^[[
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
        
        //Varibale per poder usar metodes de la classe Line()
        Line line = new Line();

        //Ens fiquem en mode raw perque funcioni el programa i poder llegir la linea correctament
        this.setRaw();

        while((lectura = this.read()) != ENTER){
            switch(lectura){
                case RET_DRETA:
                    if(line.dreta()){
                        System.out.print("\033[C");
                    } else {
                        System.out.print('\007');
                    }
                break;
                case RET_ESQUERRA:
                    if(line.esquerra()){
                        System.out.print("\033[D");
                    } else {
                        System.out.print('\007');
                    }
                break;
                case RET_INICI:
                    aux = line.start();
                    //Anem al inici, movent-nos cap a l'esquerra les posicions del cursor retornat
                    System.out.print("\033[" + aux + "D");
                break;
                case RET_FINAL:
                    aux = line.end();
                    //Anem al final, movent-nos cap a la dreta les posicions del cursor retornat
                    System.out.print("\033[" + aux + "C");
                break;
                case RET_DEL:
                    if(line.del()){
                        //Borrem el caracter de l'esquerra
                        System.out.print("\033[P");
                    } else {
                        System.out.print('\007');
                    }
                break;
                case RTE_INSERT:
                    line.ins();
                    System.out.print("\033[4l");
                break;
                case BPSK:
                    if(line.bksp()){
                        //Ens desplacem cap a la dreta
                        System.out.print("\033[D");
                        //Borrem el caracter de l'esquerra
                        System.out.print("\033[P");
                    } else {
                        System.out.print('\007');
                    }
                break;
                default:
                    //Per convertir el int llegit a un char utilitzem (char) int
                    boolean insert = line.add((char) lectura);
                    if(insert){
                        System.out.print("\033[4h");
                    }
                    System.out.print((char) lectura);
                break;
            }
        }
        //Tornem al mode per defecte de la consola
        this.unsetRaw();

        return line.toString();
    }
}
