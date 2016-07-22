package br.com.pre.dojo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import br.com.pre.dojo.enums.TipoAward;

public class Jogador {
	private final String nome;
	private HashSet<ArmaJogador> armasDoJogador;
	private HashSet<AwardJogador> awardsDoJogador;
	private int assassinatos;
	private int mortes;
	private int maiorStreak;
	private int streak;
	private Date ultimoAssassinatoEm5min;

	public Jogador(String nome) {
		this.nome = nome;
		this.armasDoJogador = new HashSet<ArmaJogador>();
		this.awardsDoJogador = new HashSet<AwardJogador>();
	}

	public void assassinar(Date dataAssassinato) {
		this.assassinatos++;
		
		this.processarStreak();
		this.processarAward5Em1Min(dataAssassinato);
	}

	public void morrer() {
		this.mortes++;
		this.streak = 0;
	}

	public String getNome() {
		return nome;
	}

	public int getMortes() {
		return mortes;
	}

	public int getAssassinatos() {
		return assassinatos;
	}
	
	public void exibeMaiorStreak() {
		System.out.println("O jogador " + this.nome + " cometeu uma sequência de " + this.maiorStreak + " assassinatos sem morrer!");
	}
	
	private void processarStreak(){
		this.streak++;
		if(this.streak > this.maiorStreak){
			this.maiorStreak = this.streak;
		}
	}
	
	private void processarAward5Em1Min(Date dataAssassinato) {
		if ((this.assassinatos % 5) == 0) {
			long diferencaEntreAssassinatos = this.ultimoAssassinatoEm5min.getTime() - dataAssassinato.getTime();
			long cincoMinEmMilisegundos = 5 * 60 * 1000;

			if (diferencaEntreAssassinatos <= cincoMinEmMilisegundos) {
				this.conquistarAward(TipoAward.ASSASSINATOS_5_EM_1_MIN);
			}
		} else if (this.assassinatos == 1) {
			this.ultimoAssassinatoEm5min = dataAssassinato;
		}
	}
	
	public void conquistarAward(TipoAward tipoAward) {
		AwardJogador awardDoJogador = new AwardJogador(tipoAward);
		if (this.awardsDoJogador.contains(awardDoJogador)) {
			Iterator<AwardJogador> i = this.awardsDoJogador.iterator();
			while (i.hasNext()) {
				awardDoJogador = (AwardJogador) i.next();
				if ((tipoAward.equals(awardDoJogador.getTipoAward()))) {
					awardDoJogador.acrescentarAward();
					break;
				}
			}
		} else {
			this.awardsDoJogador.add(awardDoJogador);
			awardDoJogador.acrescentarAward();
		}
	}
	
	public void exibeAwards() {
		AwardJogador awardDoJogador;
		Iterator<AwardJogador> i = this.awardsDoJogador.iterator();
		while (i.hasNext()) {
			awardDoJogador = (AwardJogador) i.next();
			System.out.println("O jogador " + this.nome + " possui " + awardDoJogador.getQuantidade() + " award(s) por " + awardDoJogador.getDescricao() + "!");
		}
	}
	
	public void assassinarPelaArma(String nomeArma) {
		ArmaJogador armaJogador = new ArmaJogador(nomeArma);
		if (this.armasDoJogador.contains(armaJogador)) {
			Iterator<ArmaJogador> i = this.armasDoJogador.iterator();
			while (i.hasNext()) {
				armaJogador = (ArmaJogador) i.next();
				if ((nomeArma.equals(armaJogador.getNomeArma()))) {
					armaJogador.acrescentarAssassinato();
					break;
				}
			}
		} else {
			this.armasDoJogador.add(armaJogador);
			armaJogador.acrescentarAssassinato();
		}
	}
	
	public void exibeArmaMaisAssassinou() {
		if (!this.armasDoJogador.isEmpty() && this.assassinatos > 0) {
			ArrayList listaArmasOrdenadas = new ArrayList<ArmaJogador>(this.armasDoJogador);
			Collections.sort(listaArmasOrdenadas);

			ArmaJogador armaMaisAssassinou = (ArmaJogador) listaArmasOrdenadas.get(0);

			System.out.println("A arma que o jogador " + this.nome + " mais utilizou em seus assassinatos foi "
					+ armaMaisAssassinou.getNomeArma() + " com " + armaMaisAssassinou.getQuantidadeAssassinatos() + " assassinato(s)!");
		} else {
			System.out.println(this.nome + " não cometeu assassinatos durante a partida!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogador other = (Jogador) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	private class ArmaJogador implements Comparable<ArmaJogador>{
		Arma arma;
		int quantidadeAssassinatos;
		
		ArmaJogador(String nomeArma){
			this.arma = new Arma(nomeArma);
		}
		
		void acrescentarAssassinato(){
			this.quantidadeAssassinatos++;
		}
		
		int getQuantidadeAssassinatos() {
			return quantidadeAssassinatos;
		}
		
		String getNomeArma(){
			return this.arma.getNome();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((arma == null) ? 0 : arma.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ArmaJogador other = (ArmaJogador) obj;
			if (arma == null) {
				if (other.arma != null)
					return false;
			} else if (!arma.equals(other.arma))
				return false;
			return true;
		}

		@Override
		public int compareTo(ArmaJogador outraArmaJogador) {
			return ((Integer) outraArmaJogador.quantidadeAssassinatos).compareTo(this.quantidadeAssassinatos);
		}
	}
	
	private class AwardJogador {
		Award award;
		int quantidade;
		
		AwardJogador(TipoAward tipoAward) {
			this.award = new Award(tipoAward);
		}

		void acrescentarAward() {
			this.quantidade++;
		}

		int getQuantidade() {
			return quantidade;
		}

		TipoAward getTipoAward() {
			return this.award.getTipoAward();
		}
		
		String getDescricao() {
			return this.award.getDescricao();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((award == null) ? 0 : award.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AwardJogador other = (AwardJogador) obj;
			if (award == null) {
				if (other.award != null)
					return false;
			} else if (!award.equals(other.award))
				return false;
			return true;
		}
	}
}
