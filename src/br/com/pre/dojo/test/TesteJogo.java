package br.com.pre.dojo.test;

import org.junit.Assert;
import org.junit.Test;

import br.com.pre.dojo.log.processor.ProcessadorLogPartidaTexto;
import br.com.pre.dojo.model.Jogador;
import br.com.pre.dojo.model.Partida;

public class TesteJogo {

	@Test
	public void jogadorTest() {
		Partida partida = new Partida();
		Jogador jogadorRoman = new Jogador("Roman");
		ProcessadorLogPartidaTexto parser = new ProcessadorLogPartidaTexto("resources/LogTeste.txt");

		parser.processarLogPartida(partida);

		jogadorRoman = partida.buscarJogadorNaPartida(jogadorRoman);
		Assert.assertEquals("Roman deveria ter 5 assassinato de acordo com o LogTeste!", 5, jogadorRoman.getAssassinatos());
		Assert.assertEquals("Roman deveria ter 1 morte de acordo com o LogTeste!", 1, jogadorRoman.getMortes());
	}
}