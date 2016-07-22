package br.com.pre.dojo.log.processor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.pre.dojo.model.Jogador;
import br.com.pre.dojo.model.Partida;

public class ProcessadorLogPartidaTexto implements ProcessadorLogPartida {
	private final Path caminhoArquivo;
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * Construtor
	 * 
	 * @param caminhoArquivo: caminho completo do log que será lido
	 */
	public ProcessadorLogPartidaTexto(String caminhoArquivo) {
		this.caminhoArquivo = Paths.get(caminhoArquivo);
	}


	/**
	 * Método que invoca {@link #processarLinha(String)}
	 * @throws ParseException 
	 */
	private void processarLinhaPorLinha(Partida partida) throws IOException, ParseException {
		try (Scanner scanner =  new Scanner(this.caminhoArquivo, ENCODING.name())){
			while (scanner.hasNextLine()){
				processarLinha(scanner.nextLine(), partida);
			}      
		}
	}

	/** 
	 * Método que processa a linha do arquivo de texto
	 * e informa os dados da partida
	 * @throws ParseException 
	 */
	private void processarLinha(String linha, Partida partida) throws ParseException{
		Jogador assassino = null;
		Jogador vitima = null;
		Date dataAssassinatoLog = null;

		Pattern pattern = Pattern.compile(" \\w* killed");
		Matcher matcher = pattern.matcher(linha);
		if (matcher.find()){
			String nomeAssassino = matcher.group(0).replace("killed", "").trim();
			assassino = new Jogador(nomeAssassino);
			
			Jogador jogadorJaNaPartida = partida.buscarJogadorNaPartida(assassino);
			if(jogadorJaNaPartida != null){
				assassino = jogadorJaNaPartida;
			}
			
			pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}");
			matcher = pattern.matcher(linha);
			if (matcher.find()){
				DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				dataAssassinatoLog = formatador.parse(matcher.group(0));
			}
		}

		pattern = Pattern.compile("killed \\w* ");
		matcher = pattern.matcher(linha);
		if (matcher.find()){
			String nomeVitima = matcher.group(0).replace("killed", "").trim();
			vitima = new Jogador(nomeVitima);
		}

		pattern = Pattern.compile("using \\w*");
		matcher = pattern.matcher(linha);
		if (matcher.find()){
			String nomeArmaAssassino = matcher.group(0).replace("using", "").trim();

			if(assassino != null){
				assassino.assassinarPelaArma(nomeArmaAssassino);
			}
		}
		
		pattern = Pattern.compile("Match \\d* has ended");
		matcher = pattern.matcher(linha);
		if (matcher.find()){
			partida.finalizarPartida();
		}

		partida.atualizarRanking(assassino, vitima, dataAssassinatoLog);
	}


	@Override
	public void processarLogPartida(Partida partida) {
		try {
			this.processarLinhaPorLinha(partida);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
