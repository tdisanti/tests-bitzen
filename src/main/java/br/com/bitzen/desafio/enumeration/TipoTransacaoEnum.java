package br.com.bitzen.desafio.enumeration;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TipoTransacaoEnum {

	CREDITO("C", "Crédito"), 
	DEBITO("D", "Débito"), 
	TRANSFERENCIA("T", "Transferência");

	private final String codigo;
	private final String descricao;

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static final TipoTransacaoEnum getByCodigo(String codigo) {
		if (codigo == null) {
			return null;
		}

		for (TipoTransacaoEnum e : values()) {
			if (StringUtils.equalsIgnoreCase(StringUtils.trim(codigo), e.getCodigo())) {
				return e;
			}
		}
		return null;
	}

}
