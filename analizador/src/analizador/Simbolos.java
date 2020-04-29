package analizador;

public class Simbolos 
{
	private String identificador;
	private String modificador;
	private String tipo;
	private String valor;
	
	public Simbolos(String id, String mod, String type) 
	{
		identificador = id;
		modificador = mod;
		tipo = type;
	}
	
	public Simbolos() 
	{
		
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getModificador() {
		return modificador;
	}

	public String getTipo() {
		return tipo;
	}
	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return identificador+"\t"+modificador+"\t"+tipo+"\t"+valor;
	}
	
}