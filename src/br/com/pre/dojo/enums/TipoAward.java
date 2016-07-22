package br.com.pre.dojo.enums;

public enum TipoAward {
	PARTIDA_SEM_MORRER("Partida Sem Morrer"), ASSASSINATOS_5_EM_1_MIN("5 Assassinatos Em 1 Min");
	private String descricao;

	private TipoAward(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
