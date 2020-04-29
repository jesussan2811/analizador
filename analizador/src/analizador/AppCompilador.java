package analizador;

import analizador.Lexico;
import analizador.LeerArchivo;
import java.io.IOException;
import java.util.ArrayList;

public class AppCompilador {

    private static Lexico analizadorLexico;
    private static ArrayList<Token> tokens;
    private static ArrayList<Simbolos> tablaDeSimbolos=new ArrayList<Simbolos>();
    static int simbolo = 0;
    static int n = 0;

    public static void main(String [] args) throws IOException {
        LeerArchivo arch=new LeerArchivo("Prueba");
        analizadorLexico=new Lexico();
        tokens=new ArrayList<>();
       
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
       
        //analizador sintactico
        
        if(tokens.get(n).getTipo().equals("modificador"))
        {
        	System.out.println("all cool");n++;
        	if(tokens.get(n).getTipo().equals("declaracion class")) 
        	{
        		System.out.println("all cooler");n++;
        		if(tokens.get(n).getTipo().equals("identificador")) 
        		{
        			System.out.println("all even cooler");n++;
        			if(tokens.get(n).getTipo().equals("key inicial"))
        			{
        				System.out.println("its the coolest");n++;
        				
        				sintaxis();
        			}
        			
        				
        		}
        	}
        	
        }
        System.out.println("error sintaxis incorrecta");
        
        for(int i1 = 0 ; i1< tablaDeSimbolos.size();i1++) {
        	System.out.println(tablaDeSimbolos.get(i1).toString());
        }
    }
    
    public static void sintaxis()
    {
	    do
	    {
	    	if(tokens.get(n).getTipo().equals("modificador"))
	    	{
	    		n++;
	    		if(tokens.get(n).getTipo().equals("tipo"))
	    		{
	    			n++;
	    			if(tokens.get(n).getTipo().equals("identificador"))
	    			{
	    				n++;
	    				if(tokens.get(n).getTipo().equals("="))
	    				{
	    					int k = n-2;
	    					String tipo=tokens.get(k).getToken();
	    					n++;
	    					switch(tipo)
	    					{
	    						case "int":
	    							if(tokens.get(n).getTipo().equals("integer literal"))
	    							{
	    								n++;
	    								if(tokens.get(n).getTipo().equals(";"))
	    								{
	    									tablaDeSimbolos.add(new Simbolos(tokens.get(n-3).getToken()//identificador
	    														,tokens.get(n-5).getToken()//modificador
	    														,tokens.get(n-4).getToken()));//tipo
	    									tablaDeSimbolos.get(simbolo).setValor(tokens.get(n-1).getToken());
	    									n++;
	    								}
	    								else if(tokens.get(n).getTipo().equals("operador aritmetico"))
	    								{
	    									operaciones();
	    								}
	    								
	    								break;	
	    							}
	    						case "boolean":
	    							if(tokens.get(n).getTipo().equals("boolean literal"))
	    							{
	    								n++;
	    								if(tokens.get(n).getTipo().equals(";"))
	    								{
	    									tablaDeSimbolos.add(new Simbolos(tokens.get(n-3).getToken()//identificador
	    														,tokens.get(n-5).getToken()//modificador
	    														,tokens.get(n-4).getToken()));//tipo
	    									//tablaDeSimbolos.get(simbolo).setValor(tokens.get(n-1).getToken());
	    									n++;
	    								}
	    								break;	
	    							}
	    							
	    						default:
	    							break;
	    					}
	    				}
	    				else if(tokens.get(n).getTipo().equals(";"))
	    				{
	    					tablaDeSimbolos.add(new Simbolos(tokens.get(n-1).getToken()//identificador
															,tokens.get(n-3).getToken()//modificador
															,tokens.get(n-2).getToken()));//tipo
	    					n++;
	    				}
	    			}
	    		}
	    	}
	    	else if(tokens.get(n).getTipo().equals("declaracion if") || tokens.get(n).getTipo().equals("declaracion while"))
	    	{
	    		
	    	}
	    	else if(tokens.get(n).getTipo().equals("tipo"))
	    	{
	    		
	    	}
	    } while(tokens.get(n).getTipo().equals("key cierre"));
    }
    
    
    static int num;
    static int num2;
    static String resultado;
    public static void	operaciones()
    {
    	String ope = tokens.get(n).getToken();
    	num = Integer.parseInt(tokens.get(n-1).getToken());
    	resultado = "";
    	n++;
    	if(tokens.get(n).getTipo().equals("integer literal"))
    	{
    		num2 = Integer.parseInt(tokens.get(n).getToken());
    		if(tokens.get(n+1).getTipo().equals("operador aritmetico"))
    		{
    			if(tokens.get(n+1).getToken().equals("*")||tokens.get(n+1).getToken().equals("/"))
    			{
    				operaciones();
    				int result = Integer.parseInt(resultado);
    				num2 = result;
    			}
    		}
    		switch(ope)
        	{
        		case "+":
        			resultado = ""+(num+num2);
        			break;
        		case "-":	
        			resultado = ""+(num-num2);
        			break;
        		case "*":
        			resultado = ""+(num*num2);
        			break;
        		case "/":
        			resultado = ""+(num/num2);
        			break;
        		default:
        			//error 
        	}
    		if(tokens.get(n+1).getTipo().equals("operador aritmetico"))
    		{
    			if(tokens.get(n+1).getToken().equals("+"))
    			{
    				int result = Integer.parseInt(resultado);
    				operaciones();
    				resultado = ""+(result+Integer.parseInt(resultado));
    			}
    			else if(tokens.get(n+1).getToken().equals("-"))
    			{
    				int result = Integer.parseInt(resultado);
    				operaciones();
    				resultado = ""+(result-Integer.parseInt(resultado));
    			}
    		}
    	}
    	tablaDeSimbolos.get(simbolo).setValor(tokens.get(n-1).getToken());
    }

    /*public static boolean SimboloExiste(String sin)
    {
    	
    }*/
    
    
    
    
    
    
    
    //Regresa en ArrayList, en orden, los tipos de tokens
    //para proceder al analisis sintactico
    private static ArrayList<String> obtenerTipos(){
        ArrayList<String> al=new ArrayList<>();
        for (Token token : tokens)
            al.add(token.getTipo());
        return al;
    }

    //Agrega un token
    public static void agregarToken(int l,String palabra,boolean pres,String tipo){
        tokens.add(new Token(l,palabra,pres,tipo));
    }
    
    
}
