db.itens.insert(
	[
  {
    "tipo": "filme",
    "nome": "Duro de Matar",
    "genero": "acao",
    "sinopse": "foda",
    "anoLancamento": "1988",
    "classificacao": 18,
    "plataforma": "Netflix",
    "duracao": "120",
    "idItem": 1
  },
  {
    "tipo": "filme",
    "nome": "Titanic",
    "genero": "Romance",
    "sinopse": "triste",
    "anoLancamento": "1997",
    "classificacao": 12,
    "plataforma": "Filmes online gratis HD",
    "duracao": "155",
    "idItem": 2
  },
  {
    "tipo": "filme",
    "nome": "Senhor dos aneis",
    "genero": "Fantasia",
    "sinopse": "muito foda",
    "anoLancamento": "2001",
    "classificacao": 12,
    "plataforma": "Amazon prime",
    "duracao": "220",
    "idItem": 3
  },
  {
    "tipo": "serie",
    "nome": "Game of Thrones",
    "genero": "Drama",
    "sinopse": "Um monte gente brigando pra ver quem vai ser Rei",
    "anoLancamento": "2011",
    "classificacao": 16,
    "plataforma": "HBO MAX",
    "temporadas": 7,
    "episodios": 73,
    "idItem": 4
  },
  {
    "tipo": "serie",
    "nome": "The Office",
    "genero": "Comedia",
    "sinopse": "Mostra a rotina de um escritorio",
    "anoLancamento": "2005",
    "classificacao": 14,
    "plataforma": "HBO MAX",
    "temporadas": 9,
    "episodios": 120,
    "idItem": 5
  },
  {
    "tipo": "serie",
    "nome": "Breaking Bad",
    "genero": "Drama",
    "sinopse": "Um professor de quimica, que descobriu que tem um cancêr. E está fazendo de tudo para deixar dinheiro pra sua família",
    "anoLancamento": "2008",
    "classificacao": 16,
    "plataforma": "HBO MAX",
    "temporadas": 5,
    "episodios": 62,
    "idItem": 6
  },
  {
    "tipo": "serie",
    "nome": "O Exorcista",
    "genero": "Terror",
    "sinopse": "Dois homens muito diferentes dirigem seus esforços a um caso terrível de possessão demoníaca numa família local",
    "anoLancamento": "2019",
    "classificacao": 18,
    "plataforma": "Netflix",
    "temporadas": 2,
    "episodios": 12,
    "idItem": 7
  },
  {
    "tipo": "serie",
    "nome": "Supernatural",
    "genero": "Terror",
    "sinopse": "Os irmãos Sam e Dean Winchester encaram cenários sinistros caçando monstros",
    "anoLancamento": "2005",
    "classificacao": 18,
    "plataforma": "Netflix",
    "temporadas": 15,
    "episodios": 200,
    "idItem": 8
  },
  {
    "tipo": "serie",
    "nome": "A Maldição da Mansão Bly",
    "genero": "Terror",
    "sinopse": "Uma babá norte-americana chega a Bly Manor e começa a ver aparições em uma propriedade inglesa",
    "anoLancamento": "2020",
    "classificacao": 18,
    "plataforma": "Netflix",
    "temporadas": 1,
    "episodios": 6,
    "idItem": 9
  },
  {
    "tipo": "filme",
    "nome": "Logan",
    "genero": "Acao",
    "sinopse": "Em 2024, os mutantes estão em franco declínio, e as pessoas não sabem o real motivo.",
    "anoLancamento": "2019",
    "classificacao": 18,
    "plataforma": "Cinema",
    "duracao": "160",
    "idItem": 10
  }
]
)


db.itens.find()

db.itens.find({
	$or : [
		{"genero" : "Terror"},
		{"classificacao" : 18}
	]
})

db.itens.find({
	"plataforma" : {
		$in : ["Netflix", "HBO MAX"]
	}
})