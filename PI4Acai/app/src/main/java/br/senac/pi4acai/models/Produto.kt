package br.senac.pi4acai.models

data class Produto(
	val unit: String,
	val EAN: String,
	val quantity: String,
	val categoryId: Int,
	val price: String,
	val name: String,
	val description: String,
	val id: Int
)