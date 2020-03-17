package analizador;

public class Gramatica {

	//Constantes para realizar analisis sintactico
	private final String[] class_declaration = {"<modifier>", "class", "{", "field_declaration", "statement","}"};
	private final String[] field_declaration = {"variable_declaration", ";"};
	private final String[] variable_declaration = {"<modifier>","type","variable_declarator / identifier"};
	private final String[] variable_declarator = {"identifier", "=", "integer_literal / boolean_literal"};
	private final String expression = "testing_expression";
	private final String[] testing_expression = {"integer_literal / identifier","relational_operator","integer_literal / identifier"};
	private final String statement  = "variable_declaration / if_statement / while_statement";
	private final String[] while_statement =  {"while", "(", "expression", ")", "{", "statement", "}"};
	private final String type  = "type_specifier";
	private final String type_specifier =   "boolean / int";
	private final String modifier =   "public / private";
	private final String integer_literal = "1..9  / 0..9";
	private final String boolean_literal = "true / false";
	private final String identifier = "a..z / 1..9";          //(a-z)+ (1-9)+
	private final String[] aritmetica_expression = {"identifier", "=", "integer_literal", "aritmetic_operator", "integer_literal", ";"};
}
