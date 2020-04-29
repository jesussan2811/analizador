package analizador;
import analizador.AppCompilador;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexico {
    private ArrayList<String> PalabrasReservadas;
    private String[] tiposDeTokens={"modificador","identificador","tipo","relational operador","operador aritmetico",
                                    "boolean literal","integer literal","key inicial",";","=","parentesis inicial","key cierre",
                                    "parentesis cierre","declaracion class","declaracion if"};

    public Lexico(){
        PalabrasReservadas=new ArrayList<>();
        llenarArray();
    }

    //Crea los tokens y muestra errores de una linea del archivo
    public void analizarLinea(String linea,int l,int c){
        while (linea!=null && linea.length() > 0) {
            int corte = linea.indexOf(" ");
            String palabra;
            if (corte == -1) {
                palabra=linea;
                if(palabra.indexOf("{")>=0 || palabra.indexOf("=")>=0 ||
                        palabra.indexOf(";")>=0 || palabra.indexOf(">")>=0 || palabra.indexOf("<")>=0){
                    separaTokens(palabra,l);
                    linea = "";
                    continue;
                }
                linea = "";
            }else {
                palabra = linea.substring(0, corte);
                if(palabra.indexOf("{")>=0 || palabra.indexOf("=")>=0 ||
                        palabra.indexOf(";")>=0 || palabra.indexOf(">")>=0 || palabra.indexOf("<")>=0){
                    separaTokens(palabra,l);
                    linea = linea.substring(corte + 1);
                    continue;
                }
                if(palabra.equals("")) {
                    linea = linea.substring(corte + 1);
                    continue;
                }
                linea = linea.substring(corte + 1);
            }
            boolean pres = comprobarPalabra(palabra);
            String tipo = tipoDeToken(palabra);
            if (tipo.equals("Error")){
                mostrarError(palabra.charAt(0),l,c+1);
                continue;
            }
            c=c+corte+1;
            AppCompilador.agregarToken(l,palabra,pres,tipo);
        }
    }

    private void llenarArray(){
        PalabrasReservadas.add("boolean");
        PalabrasReservadas.add("class");
        PalabrasReservadas.add("if");
        PalabrasReservadas.add("else");
        PalabrasReservadas.add("int");
        PalabrasReservadas.add("private");
        PalabrasReservadas.add("public");
        PalabrasReservadas.add("while");
    }

    //Retorna el tipo de token en base a la palabra
    public String tipoDeToken(String palabra){
        if (palabra.equals("("))
            return tiposDeTokens[10];
        if (palabra.equals(")"))
            return tiposDeTokens[12];
        if(palabra.equals("="))
            return tiposDeTokens[9];
        if(palabra.equals(";"))
            return tiposDeTokens[8];
        if (palabra.equals("{"))
            return tiposDeTokens[7];
        if(palabra.equals("}"))
            return tiposDeTokens[11];
        if(palabra.equals("+") || palabra.equals("-") || palabra.equals("*") || palabra.equals("/"))
            return tiposDeTokens[4];
        if(palabra.equals("public") || palabra.equals("private"))
            return tiposDeTokens[0];
        if (palabra.equals("class"))
            return tiposDeTokens[13];
        if (palabra.equals("if") || palabra.equals("else"))
                return tiposDeTokens[14];
        if(palabra.equals("int") || palabra.equals("boolean") || palabra.equals("String"))
            return tiposDeTokens[2];
        if(palabra.equals("<") || palabra.equals("==") || palabra.equals("!=") || palabra.equals(">") || palabra.equals("<=") || palabra.equals(">="))
            return tiposDeTokens[3];
        if(palabra.equals("true") || palabra.equals("false"))
            return tiposDeTokens[5];
        try{
            Integer.parseInt(palabra);
            return tiposDeTokens[6];
        }catch (Exception e){}
        if(validarExpresion(palabra))
            return tiposDeTokens[1];
        return "Error";
    }

    //Primer llamado de la busqueda binaria
    public boolean comprobarPalabra(String palabra){
        return busquedaBinaria(palabra,0,PalabrasReservadas.size());
    }

    //Busqueda para verificar palabra reservada
    private boolean busquedaBinaria(String palabra,int izq,int der){
        int medio=(der+izq)/2;
        int band=palabra.compareTo(PalabrasReservadas.get(medio));
        if(band==0)
            return true;
        if(izq==der || (izq==der-1))
            return false;
        if(band>0)
            return busquedaBinaria(palabra,medio,der);
        return busquedaBinaria(palabra,izq,medio);
    }

    //Muestra errores lexicos
    public void mostrarError(char token,int linea,int columna){
        System.out.println("Error en la linea "+linea+", "+token+" es un simbolo no permitido");
    }

    //Valida que el identifier cumpla con la expresion
    public boolean validarExpresion(String palabra) {
        Pattern pat = Pattern.compile("^[^0-9][A-Za-z0-9]");
        Matcher mat = pat.matcher(palabra);
        if(mat.find())
            return true;
        return false;
    }

    //Separa tokens sin espacios entre ellos
    public void separaTokens(String palabra,int l){
        int i;
        String t;
        while(palabra.length()!=0) {
            if(palabra.charAt(0)=='(' || palabra.charAt(0)==')'){
                AppCompilador.agregarToken(l,palabra.charAt(0)+"",false,tipoDeToken(palabra.charAt(0)+""));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.indexOf("{")==0 || palabra.indexOf("}")==0){
                AppCompilador.agregarToken(l,palabra.substring(0,1),false,tipoDeToken(palabra.charAt(0)+""));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.indexOf(";")==0){
                AppCompilador.agregarToken(l,";",false,";");
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(
                    (palabra.charAt(0)=='=' || palabra.charAt(0)=='>' || palabra.charAt(0)=='<')
                            &&
                            (palabra.charAt(1)=='=' || palabra.charAt(1)=='>' || palabra.charAt(1)=='<')
            ){
                String aux=palabra.substring(0,2);
                AppCompilador.agregarToken(l,aux,false,tipoDeToken(aux));
                if (palabra.length()!=2) {
                    palabra=palabra.substring(2);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.charAt(0)=='='){
                AppCompilador.agregarToken(l,palabra.charAt(0)+"",false,tipoDeToken(palabra.charAt(0)+""));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            for (i = 0; validarExpresion(palabra.charAt(i)); i++) ;
            t = palabra.substring(0, i);
            boolean pres = comprobarPalabra(t);
            String tipo = tipoDeToken(t);
            AppCompilador.agregarToken(l, t, pres, tipo);
            palabra = palabra.substring(i);
        }
    }

    //Valida que sean Letras de la a-z mayusculas o minusculas o numeros del 0-9
    public static boolean validarExpresion(char c) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        Matcher mat = pat.matcher(c+"");
        if(mat.find())
            return true;
        return false;
    }
}
