package analizador;

import analizador.Lexico;
import analizador.LeerArchivo;
import java.io.IOException;
import java.util.ArrayList;

public class AppCompilador {

    private static Lexico analizadorLexico;
    private static ArrayList<Token> tokens;
    private static ArrayList<String> tablaDeSimbolos;

    public static void main(String [] args) throws IOException {
        LeerArchivo arch=new LeerArchivo("Prueba");
        analizadorLexico=new Lexico();
        tokens=new ArrayList<>();
        tablaDeSimbolos=new ArrayList<>();
        String linea="";
        int l=0;
        //Recorrido del archivo para generar tokens y encontrar errores léxicos
        while(linea!=null) {
            l++;
            linea = arch.leerSigLinea();
            analizadorLexico.analizarLinea(linea,l,0);
        }
        //Para este punto el arrayList tokens esta
        //lleno de los tokens que componen el archivo

        for(int i = 0 ; i< tokens.size();i++) {
        	System.out.println(tokens.get(i));
        }

    }

    //Regresa en ArrayList, en orden, los tipos de tokens
    //para proceder al analisis sintactico
    private static ArrayList<String> obtenerTipos(){
        ArrayList<String> al=new ArrayList<>();
        for (Token token : tokens)
            al.add(token.getTipo());
        return al;
    }

    //Agrega un token
    public static void agregarToken(String palabra,boolean pres,String tipo){
        tokens.add(new Token(palabra,pres,tipo));
    }
}
