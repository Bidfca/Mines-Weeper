package modelo;

import java.util.ArrayList;
import java.util.List;

import excecao.ExplosaoException;

public class Campo {

		private final int linha;
		private final int coluna;
		
		private boolean aberto = false;
		private boolean minado = false;
		private boolean marcado = false;
		
		private List<Campo> vizinhos = new ArrayList<>();
		
		public Campo(int linha, int coluna) {
			this.linha = linha;
			this.coluna = coluna;
		}
		
		 boolean adicionarVizinho(Campo vizinho) {
			
			if ((linha == vizinho.linha) && (Math.abs(coluna - vizinho.coluna) == 1)){
				vizinhos.add(vizinho);
				return true;
			} else if ((coluna == vizinho.coluna) && (Math.abs(linha - vizinho.linha) == 1)) {
				vizinhos.add(vizinho);
				return true;
			} else if ((Math.abs(linha - vizinho.linha) == 1) && (Math.abs(coluna - vizinho.coluna) == 1)){
				vizinhos.add(vizinho);
				return true;
			} else {
				return false;
			}
		}
		 void alternarMarcacao() {
			 if(!aberto) {
				 marcado = !marcado;
			 }
		 }
		 
		 boolean abrir() {
			 if(!aberto && !marcado) {
				 aberto = true;
				 if(minado) {
					 throw new ExplosaoException();
					 
				 }
				 if(vizinhancaSegura()) {
					 vizinhos.forEach(v-> v.abrir());
				 }
				 return true;
			 } else {
			 
			 return false;
			 }
		 }
		 
		 boolean vizinhancaSegura() {
			 return vizinhos.stream().noneMatch(v -> v.minado);
		 }
		 
		void minar() {
			 minado = true;
		 }
		
		void setAberto(boolean aberto) {
			this.aberto = aberto;
		}
		
		 public boolean isMarcado() {
			 return marcado;
		 }
		 
		 boolean isMinado() {
			 return minado;
		 }
		 
		 public boolean isAberto() {
			 return aberto;
		 }
		 
		 public boolean isFechado() {
			 return !isAberto();
		 }
		 
		 public int getLinha() {
			 return linha;
		 }
		 
		 public int getColuna() {
			 return coluna;
		 }
		 
		 boolean objetivoAlcancado() {
			 boolean desvendado = !minado && aberto;
			 boolean protegido = minado && marcado;
			 return desvendado || protegido;
		 }
		 
		 long minasNaVizinhanca() {
			 return vizinhos.stream().filter(v -> v.minado).count();
		 }
		 
		 void reiniciar() {
			 aberto = false;
			 minado = false;
			 marcado = false;
		 }
		 
		 
		 public String toString() {
			 if (marcado) {
				 return "x";
			 } else if(aberto && minado) {
				 return "*";
			 } else if(aberto && minasNaVizinhanca() > 0) {
				 return Long.toString(minasNaVizinhanca());
			 } else if(aberto) {
				 return " ";
			 } else {
				 return "?";
			 }
		 }
}
