package jogo_da_velha;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

// Aluno: Jonas Gabriel Sarmento de Figueiredo 20241370009

public class JogoDaVelha {
	// Declare
	private final String[] ResultadoVencedor = {"012", "345", "678", "036", "147", "258", "048", "246"};
	private String[] celulas;
	private String[] simbolos;
	private LinkedHashMap <Integer, String> historico;
	private int qtdJogadas;
	private int nivelIa;
	private String linhaVencedora;
	
	// Constructors
	public JogoDaVelha(String simbolo1, String simbolo2){
		this.celulas = new String[9];
		this.simbolos = new String[2];
		this.historico = new LinkedHashMap<>();
		this.qtdJogadas = 0;
		
		this.simbolos[0] = simbolo1;
		this.simbolos[1] = simbolo2;
	}

	public JogoDaVelha(String nomeJogador1, int nivel) {
		this.celulas = new String[9];
		this.simbolos = new String[2];
		this.historico = new LinkedHashMap<>();
		this.qtdJogadas = 0;
		
		this.simbolos[0] = nomeJogador1;
		this.simbolos[1] = "m";
		this.nivelIa = nivel;
	}
	
	// Machine Logic
	public void jogaMaquina() {
		if (nivelIa == 1) {
			Random logicaAleatoria = new Random();
			int posicao = logicaAleatoria.nextInt(9);
			while (celulas[posicao] != null) {
				posicao = logicaAleatoria.nextInt(9);
			}
			try {
				jogaJogador(2, posicao);
			} catch (Exception e) {
				System.out.println("Erro ao acessar posição: " + e);
			}
		}
		if (nivelIa == 2) {
			int posicao = getJogadaVencedora(2);

			if (posicao == -1) {posicao = getJogadaVencedora(1);}
			if (posicao == -1 && celulas[4] == null) {
				posicao = 4;
			}
			else {
			    for (int i = 0; i < celulas.length; i++) {
			        if (celulas[i] == null) {
			            posicao = i;
			            break;
			        }
			    }
			}
			try {
				jogaJogador(2, posicao);
			} catch (Exception e) {
				System.out.println("Erro ao acessar posição: " + e);
			}
		}
	}
	
	// Gets
	public String getSimbolo(int numeroJogador){
		if (numeroJogador <= 0 || numeroJogador > 2) {return  "Inválido";}
		return simbolos[numeroJogador - 1];
	}
	public String getFoto() {
	    String foto = "";

	    for (int i = 0; i < celulas.length; i++) {
	        if (celulas[i] == null) {
	            foto += i;
	        } else {
	            foto += celulas[i];
	        }
	        if ((i + 1) % 3 != 0) {
	            foto += " | ";
	        }
	        if ((i + 1) % 3 == 0 && i < 8) {
	            foto += "\n";
	            foto += "-------\n";
	        }
	    }
	    return foto;
	}
	
	public int getResultado() {
		if (qtdJogadas < 3) {
			return -1;
		}	
		for (String resultado : ResultadoVencedor) {
			int indice1 = Character.getNumericValue(resultado.charAt(0));
			int indice2 = Character.getNumericValue(resultado.charAt(1));
			int indice3 = Character.getNumericValue(resultado.charAt(2));
			
			String check1 = celulas[indice1];
			if (check1 == null) {continue;}
			String check2 = celulas[indice2];
			String check3 = celulas[indice3];
			
			if ((check1.equals(check2)) && (check2.equals(check3))) {
				 linhaVencedora = resultado;
				if (check1.equals(getSimbolo(1))) {
					return 1;
				} else { return 2;}
			}
		}
		if (qtdJogadas == 9) {
			return 0;
		}
		
		return -1;
	}
	public String getLinhaVencedora() {
	    return this.linhaVencedora;
	}
	
	public ArrayList<Integer> getPosicoesDisponiveis(){
		ArrayList<Integer> posicoesLivres = new ArrayList<>();
		for (int i = 0; i<celulas.length; i++) {
			if (celulas[i] == null) {
				posicoesLivres.add(i);
			}
		}
		return posicoesLivres;
	}
	
	public LinkedHashMap<Integer, String> getHistorico(){
		return historico;
	}
	
	public Integer getVez() {
		int vez;
		if (qtdJogadas == 0) { vez = 1;}
		else {vez = (qtdJogadas % 2) + 1;}
		return vez;
	}
	
	public String[] getCelulas() {
	    return celulas;
	}
	
	private int getJogadaVencedora(int numeroJogador) {
		for (String resultado : ResultadoVencedor) {
			String SimboloJogador = getSimbolo(numeroJogador);
			int indice1 = Character.getNumericValue(resultado.charAt(0));
			int indice2 = Character.getNumericValue(resultado.charAt(1));
			int indice3 = Character.getNumericValue(resultado.charAt(2));
			
			String check1 = celulas[indice1];
			String check2 = celulas[indice2];
			String check3 = celulas[indice3];
			
			if (SimboloJogador.equals(check1) && SimboloJogador.equals(check2) && check3 == null) return indice3;
            if (SimboloJogador.equals(check1) && SimboloJogador.equals(check3) && check2 == null) return indice2;
            if (SimboloJogador.equals(check2) && SimboloJogador.equals(check3) && check1 == null) return indice1;
		}
		return -1;
	}
	
	public int getNivelIa() {
	    return nivelIa;
	}
	
	// Checks
	public boolean terminou() {
		if (getResultado() != -1) {
			return true;
		}
		
		return false;
	}
	
	// Methods
	public void jogaJogador(int numeroJogador, int posicao) throws Exception{
		
		if ((posicao < 0) || (posicao > celulas.length)) {
			throw new Exception("Posicao fora de range: " + posicao);
		}
		if ((numeroJogador <= 0) || (numeroJogador > 2)) {
			throw new Exception("Esse Jogador não está jogando: " + numeroJogador);
		}
		else if (celulas[posicao] == null) {
			celulas[posicao] = getSimbolo(numeroJogador);
			qtdJogadas++;
			historico.put(posicao, getSimbolo(numeroJogador));
		}
		else {throw new Exception("Posição já ocupada" + "posições livres: " + getPosicoesDisponiveis());}
	}
}

