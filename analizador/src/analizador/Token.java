package analizador;

public class Token {

    private boolean reservada;
    private String token;
    private String tipo;
    private int linea;

    public Token(int l, String t,boolean r,String ti){
    	linea = l;
    	reservada=r;
        token=t;
        tipo=ti;
        if(tipo.equals("identifier") && reservada)
            tipo=token;
        
    }

    public String getToken() {
        return token;
    }

    public boolean isReservada() {
        return reservada;
    }

    public String getTipo() {
        return tipo;
    }

    public String toString(){
        return token+"\t"+reservada+"\t"+tipo+"\t"+linea;
    }
}
