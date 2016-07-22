package br.com.pre.dojo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import br.com.pre.dojo.comparator.QuantidadeAssassinatosJogadorComparator;
import br.com.pre.dojo.enums.TipoAward;
import br.com.pre.dojo.util.JogoUtils;

public class Partida {
	private HashSet<Jogador> jogadores;

	public Partida() {
		this.jogadores = new HashSet<Jogador>();
	}

	public void exibirRanking() {
		ArrayList<Jogador> rankingOrdenado = JogoUtils.ordenarCollectionToArrayList(this.jogadores, new QuantidadeAssassinatosJogadorComparator());

		for (Jogador jogador : rankingOrdenado) {
			System.out.println("Jogador: " + jogador.getNome() + " | Assassinatos: " + jogador.getAssassinatos() + " | Mortes: " + jogador.getMortes());
		}

		Jogador jogadorRankUm = rankingOrdenado.get(0);
		if (jogadorRankUm != null) {
			jogadorRankUm.exibeArmaMaisAssassinou();
			jogadorRankUm.exibeMaiorStreak();
		}
	}

	public void atualizarRanking(Jogador assassino, Jogador vitima, Date dataAssassinato) {
		if (assassino != null && !assassino.getNome().equals("WORLD")) {

			if (this.jogadores.contains(assassino)) {
				assassino = this.buscarJogadorNaPartida(assassino);
			}

			assassino.assassinar(dataAssassinato);
			this.jogadores.add(assassino);
		}

		if (vitima != null) {

			if (this.jogadores.contains(vitima)) {
				vitima = this.buscarJogadorNaPartida(vitima);
			}

			vitima.morrer();
			this.jogadores.add(vitima);
		}
	}

	public Jogador buscarJogadorNaPartida(Jogador jogador) {
		Iterator<Jogador> i = this.jogadores.iterator();
		Jogador jogadorDaPartida;
		while (i.hasNext()) {
			jogadorDaPartida = (Jogador) i.next();
			if ((jogador.getNome().equals(jogadorDaPartida.getNome()))) {
				return jogadorDaPartida;
			}
		}
		return null;
	}
	
	public void finalizarPartida(){
		this.premiarJogadoresSemMortes();
	}
	
	private void premiarJogadoresSemMortes() {
		Iterator<Jogador> i = this.jogadores.iterator();
		Jogador jogadorDaPartida;
		while (i.hasNext()) {
			jogadorDaPartida = (Jogador) i.next();
			if (jogadorDaPartida.getMortes() == 0) {
				jogadorDaPartida.conquistarAward(TipoAward.PARTIDA_SEM_MORRER);
				;
			}
		}
	}
}
