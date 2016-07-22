package br.com.pre.dojo.test;

import java.io.IOException;

import br.com.pre.dojo.log.processor.ProcessadorLogPartidaTexto;
import br.com.pre.dojo.model.Jogador;
import br.com.pre.dojo.model.Partida;

public class MainJogo {

	public static void main(String... aArgs) throws IOException {
		Partida partida = new Partida();
		ProcessadorLogPartidaTexto parser = new ProcessadorLogPartidaTexto("resources/LogTeste.txt");

		parser.processarLogPartida(partida);

		partida.exibirRanking();
		
		Jogador jogadorRoman = new Jogador("Roman");
		
		jogadorRoman = partida.buscarJogadorNaPartida(jogadorRoman);
		jogadorRoman.exibeAwards();
	}
}
