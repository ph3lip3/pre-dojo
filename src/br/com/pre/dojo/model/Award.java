package br.com.pre.dojo.model;

import br.com.pre.dojo.enums.TipoAward;

public class Award {
	private TipoAward tipoAward;

	public Award(TipoAward tipoAward) {
		this.tipoAward = tipoAward;
	}

	public TipoAward getTipoAward() {
		return tipoAward;
	}

	public String getDescricao() {
		return this.tipoAward.getDescricao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipoAward == null) ? 0 : tipoAward.hashCode());
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
		Award other = (Award) obj;
		if (tipoAward != other.tipoAward)
			return false;
		return true;
	}
}