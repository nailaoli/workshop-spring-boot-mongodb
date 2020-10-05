package com.naila.workshopmongo.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URL {
	
//	Os parâmetros na URL vêm codificados, a requisição é feita utilizando esse valor
//	codificado, para evitar caracteres especiais. Podemos verificar através da função
//	encodeURIComponent("string") do javascript, que encoda o valor.
//	Assim, criamos o método decodeParam para decodificar o texto que vem da requisição.
	
	public static String decodeParam(String text) {
		try {
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return ""; // Método ou retorna o valor decodificado, ou uma string vazia
		}
	}
}
