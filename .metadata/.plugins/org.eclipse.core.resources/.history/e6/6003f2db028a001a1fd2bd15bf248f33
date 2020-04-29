package analizador;

public class Token {

    private boolean reservada;
    private String token;
    private String tipo;

    public Token(String t,boolean r,String ti){
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
        return token+"\t"+reservada+"\t"+tipo;
    }
}
