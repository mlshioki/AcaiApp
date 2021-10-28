package br.senac.pi4acai.models

data class Produto(
	val prodPreco: Double,
	val prodAval: Int,
	val prodNome: String,
	val descontoPromo: Double,
	val prodId: Int,
	val prodDesc: String,
	val prodVar: List<String>,
	val prodAtivo: Boolean
)
