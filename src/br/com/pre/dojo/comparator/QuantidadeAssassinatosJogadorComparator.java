package br.com.pre.dojo.comparator;

import java.util.Comparator;

import br.com.pre.dojo.model.Jogador;

public class QuantidadeAssassinatosJogadorComparator implements Comparator<Jogador> {

	@Override
	public int compare(Jogador j1, Jogador j2) {
		return ((Integer) j2.getAssassinatos()).compareTo(j1.getAssassinatos());
	}
}
