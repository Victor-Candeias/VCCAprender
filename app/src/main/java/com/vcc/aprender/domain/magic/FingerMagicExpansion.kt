package com.vcc.aprender.domain.magic

import com.vcc.aprender.domain.model.LearningTopic

/**
 * VA-033: 60 additional questions. The first 51 remain in FingerMagicContent unchanged.
 * All images resolve locally. These lists are the canonical order; sessions shuffle copies.
 */
object FingerMagicExpansion {
    val questions: List<MagicQuestion> = listOf(
        MagicQuestion(
            id = "magic-52",
            prompt = "Qual é o maior planeta do Sistema Solar?",
            options = listOf(MagicPicture.SPACE_JUPITER, MagicPicture.SPACE_TERRA, MagicPicture.MARS),
            correctIndex = 0,
            explanation = "Júpiter é o maior planeta do Sistema Solar.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-53",
            prompt = "Qual é conhecido como o planeta vermelho?",
            options = listOf(MagicPicture.SPACE_VENUS, MagicPicture.MARS, MagicPicture.SPACE_NEPTUNO),
            correctIndex = 1,
            explanation = "Marte é chamado de planeta vermelho pela sua aparência avermelhada.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-54",
            prompt = "Qual é o planeta mais próximo do Sol?",
            options = listOf(MagicPicture.SPACE_TERRA, MagicPicture.SATURN, MagicPicture.SPACE_MERCURIO),
            correctIndex = 2,
            explanation = "Mercúrio é o planeta mais próximo do Sol.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-55",
            prompt = "Em que planeta vivemos?",
            options = listOf(MagicPicture.SPACE_TERRA, MagicPicture.SPACE_JUPITER, MagicPicture.SATURN),
            correctIndex = 0,
            explanation = "A Terra é o nosso planeta!",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-56",
            prompt = "Qual é a estrela do nosso Sistema Solar?",
            options = listOf(MagicPicture.MOON, MagicPicture.SUN, MagicPicture.MARS),
            correctIndex = 1,
            explanation = "O Sol é uma estrela e dá luz e calor à Terra.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-57",
            prompt = "Qual é o oitavo planeta a contar do Sol?",
            options = listOf(MagicPicture.URANUS, MagicPicture.SATURN, MagicPicture.SPACE_NEPTUNO),
            correctIndex = 2,
            explanation = "Neptuno é o oitavo planeta do Sistema Solar.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-58",
            prompt = "Qual é o satélite natural da Terra?",
            options = listOf(MagicPicture.MOON, MagicPicture.SUN, MagicPicture.SPACE_MERCURIO),
            correctIndex = 0,
            explanation = "A Lua é o satélite natural da Terra.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-59",
            prompt = "Quantos planetas tem o Sistema Solar?",
            options = listOf(MagicPicture.SPACE_9, MagicPicture.SPACE_8, MagicPicture.SPACE_7),
            correctIndex = 1,
            explanation = "O Sistema Solar tem oito planetas.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-60",
            prompt = "Qual é o planeta mais quente do Sistema Solar?",
            options = listOf(MagicPicture.SPACE_MERCURIO, MagicPicture.MARS, MagicPicture.SPACE_VENUS),
            correctIndex = 2,
            explanation = "Vénus é o planeta mais quente devido à sua atmosfera.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-61",
            prompt = "Como se chama a nossa galáxia?",
            options = listOf(MagicPicture.SPACE_VIA_LACTEA, MagicPicture.SPACE_ANDROMEDA, MagicPicture.SPACE_GALAXIA_DO_TRIANGULO),
            correctIndex = 0,
            explanation = "Vivemos na galáxia chamada Via Láctea.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-62",
            prompt = "Qual planeta gira quase de lado?",
            options = listOf(MagicPicture.MARS, MagicPicture.URANUS, MagicPicture.SPACE_JUPITER),
            correctIndex = 1,
            explanation = "Urano tem o eixo de rotação muito inclinado.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-63",
            prompt = "O que é uma estrela cadente?",
            options = listOf(MagicPicture.SPACE_UM_PLANETA, MagicPicture.SPACE_UMA_LUA, MagicPicture.SPACE_UM_METEORO),
            correctIndex = 2,
            explanation = "Uma estrela cadente é o brilho causado por um meteoro na atmosfera.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.SPACE
        ),
        MagicQuestion(
            id = "magic-64",
            prompt = "Qual marca tem quatro anéis no símbolo?",
            options = listOf(MagicPicture.AUTO_AUDI, MagicPicture.AUTO_FIAT, MagicPicture.AUTO_BMW),
            correctIndex = 0,
            explanation = "O símbolo da Audi é formado por quatro anéis.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-65",
            prompt = "Qual marca utiliza um losango como símbolo?",
            options = listOf(MagicPicture.AUTO_VOLVO, MagicPicture.AUTO_RENAULT, MagicPicture.AUTO_FORD),
            correctIndex = 1,
            explanation = "A Renault é conhecida pelo seu emblema em forma de losango.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-66",
            prompt = "Qual marca tem uma estrela de três pontas?",
            options = listOf(MagicPicture.AUTO_HYUNDAI, MagicPicture.AUTO_KIA, MagicPicture.AUTO_MERCEDES_BENZ),
            correctIndex = 2,
            explanation = "A Mercedes-Benz usa uma estrela de três pontas.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-67",
            prompt = "Qual marca usa um leão como símbolo?",
            options = listOf(MagicPicture.AUTO_PEUGEOT, MagicPicture.AUTO_NISSAN, MagicPicture.AUTO_TOYOTA),
            correctIndex = 0,
            explanation = "A Peugeot utiliza um leão como símbolo.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-68",
            prompt = "Qual marca fabrica o Golf?",
            options = listOf(MagicPicture.AUTO_SEAT, MagicPicture.AUTO_VOLKSWAGEN, MagicPicture.AUTO_HONDA),
            correctIndex = 1,
            explanation = "O modelo Golf é fabricado pela Volkswagen.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-69",
            prompt = "Qual marca fabrica o Clio?",
            options = listOf(MagicPicture.AUTO_FORD, MagicPicture.AUTO_CITROEN, MagicPicture.AUTO_RENAULT),
            correctIndex = 2,
            explanation = "O Clio é um modelo da Renault.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-70",
            prompt = "Qual marca fabrica o modelo 500?",
            options = listOf(MagicPicture.AUTO_FIAT, MagicPicture.AUTO_OPEL, MagicPicture.AUTO_MAZDA),
            correctIndex = 0,
            explanation = "O Fiat 500 é um modelo da marca Fiat.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-71",
            prompt = "Qual marca nasceu no Japão?",
            options = listOf(MagicPicture.AUTO_VOLVO, MagicPicture.AUTO_TOYOTA, MagicPicture.AUTO_RENAULT),
            correctIndex = 1,
            explanation = "A Toyota é uma marca japonesa de automóveis.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-72",
            prompt = "Qual marca tem origem alemã?",
            options = listOf(MagicPicture.AUTO_HYUNDAI, MagicPicture.AUTO_FIAT, MagicPicture.AUTO_BMW),
            correctIndex = 2,
            explanation = "A BMW é uma marca de automóveis da Alemanha.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-73",
            prompt = "Qual marca usa um cavalo empinado no emblema?",
            options = listOf(MagicPicture.AUTO_FERRARI, MagicPicture.AUTO_NISSAN, MagicPicture.AUTO_DACIA),
            correctIndex = 0,
            explanation = "A Ferrari tem um cavalo empinado como símbolo.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-74",
            prompt = "Qual marca tem origem na Coreia do Sul?",
            options = listOf(MagicPicture.AUTO_MAZDA, MagicPicture.AUTO_HYUNDAI, MagicPicture.AUTO_PEUGEOT),
            correctIndex = 1,
            explanation = "A Hyundai é uma marca originária da Coreia do Sul.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-75",
            prompt = "Qual marca tem origem sueca?",
            options = listOf(MagicPicture.AUTO_VOLVO, MagicPicture.AUTO_TOYOTA, MagicPicture.AUTO_FIAT),
            correctIndex = 0,
            explanation = "A Volvo é uma marca originária da Suécia.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.CAR
        ),
        MagicQuestion(
            id = "magic-76",
            prompt = "Qual clube é conhecido pelas águias?",
            options = listOf(MagicPicture.CLUB_BENFICA, MagicPicture.CLUB_FC_PORTO, MagicPicture.CLUB_SPORTING_CP),
            correctIndex = 0,
            explanation = "O Benfica é conhecido pelas águias.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-77",
            prompt = "Qual clube é conhecido pelos dragões?",
            options = listOf(MagicPicture.CLUB_BENFICA, MagicPicture.CLUB_FC_PORTO, MagicPicture.CLUB_SC_BRAGA),
            correctIndex = 1,
            explanation = "O FC Porto é conhecido pelos dragões.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-78",
            prompt = "Qual clube é conhecido pelos leões?",
            options = listOf(MagicPicture.CLUB_SC_BRAGA, MagicPicture.CLUB_FC_PORTO, MagicPicture.CLUB_SPORTING_CP),
            correctIndex = 2,
            explanation = "O Sporting CP é conhecido pelos leões.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-79",
            prompt = "Qual clube é conhecido pelos axadrezados?",
            options = listOf(MagicPicture.CLUB_BOAVISTA, MagicPicture.CLUB_BENFICA, MagicPicture.CLUB_SC_BRAGA),
            correctIndex = 0,
            explanation = "O Boavista FC é conhecido pelos axadrezados.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-80",
            prompt = "Qual clube é da cidade do Porto?",
            options = listOf(MagicPicture.CLUB_FAMALICAO, MagicPicture.CLUB_FC_PORTO, MagicPicture.CLUB_SPORTING_CP),
            correctIndex = 1,
            explanation = "O FC Porto é um clube da cidade do Porto.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-81",
            prompt = "Qual clube é conhecido como os encarnados?",
            options = listOf(MagicPicture.CLUB_SPORTING_CP, MagicPicture.CLUB_FC_PORTO, MagicPicture.CLUB_BENFICA),
            correctIndex = 2,
            explanation = "O Benfica é conhecido como os encarnados.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-82",
            prompt = "Qual clube é espanhol?",
            options = listOf(MagicPicture.CLUB_REAL_MADRID, MagicPicture.CLUB_CHELSEA, MagicPicture.CLUB_JUVENTUS),
            correctIndex = 0,
            explanation = "O Real Madrid é um clube de Espanha.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-83",
            prompt = "Qual clube pertence à cidade de Barcelona?",
            options = listOf(MagicPicture.CLUB_BAYERN_MUNIQUE, MagicPicture.CLUB_FC_BARCELONA, MagicPicture.CLUB_ROMA),
            correctIndex = 1,
            explanation = "O FC Barcelona é um clube da cidade de Barcelona.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-84",
            prompt = "Qual clube é inglês?",
            options = listOf(MagicPicture.CLUB_PSG, MagicPicture.CLUB_NAPOLI, MagicPicture.CLUB_LIVERPOOL),
            correctIndex = 2,
            explanation = "O Liverpool é um clube de Inglaterra.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-85",
            prompt = "Qual clube é alemão?",
            options = listOf(MagicPicture.CLUB_BAYERN_MUNIQUE, MagicPicture.CLUB_ARSENAL, MagicPicture.CLUB_NAPOLI),
            correctIndex = 0,
            explanation = "O Bayern Munique é um clube da Alemanha.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-86",
            prompt = "Qual clube está associado à cidade de Paris?",
            options = listOf(MagicPicture.CLUB_AC_MILAN, MagicPicture.CLUB_PARIS_SAINT_GERMAIN, MagicPicture.CLUB_BENFICA),
            correctIndex = 1,
            explanation = "O Paris Saint-Germain é um clube associado à cidade de Paris.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-87",
            prompt = "Qual clube é italiano?",
            options = listOf(MagicPicture.CLUB_LYON, MagicPicture.CLUB_AJAX, MagicPicture.CLUB_JUVENTUS),
            correctIndex = 2,
            explanation = "A Juventus é um clube de Itália.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.FOOTBALL
        ),
        MagicQuestion(
            id = "magic-88",
            prompt = "Quanto é 1 + 2?",
            options = listOf(MagicPicture.NUMBER_3, MagicPicture.NUMBER_4, MagicPicture.NUMBER_2),
            correctIndex = 0,
            explanation = "A soma é 3.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-89",
            prompt = "Quanto é 2 + 2?",
            options = listOf(MagicPicture.NUMBER_5, MagicPicture.NUMBER_4, MagicPicture.NUMBER_3),
            correctIndex = 1,
            explanation = "A soma é 4.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-90",
            prompt = "Quanto é 3 + 2?",
            options = listOf(MagicPicture.NUMBER_6, MagicPicture.NUMBER_4, MagicPicture.NUMBER_5),
            correctIndex = 2,
            explanation = "A soma é 5.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-91",
            prompt = "Quanto é 4 + 3?",
            options = listOf(MagicPicture.SPACE_7, MagicPicture.NUMBER_6, MagicPicture.SPACE_8),
            correctIndex = 0,
            explanation = "A soma é 7.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-92",
            prompt = "Quanto é 5 + 4?",
            options = listOf(MagicPicture.SPACE_8, MagicPicture.SPACE_9, MagicPicture.NUMBER_10),
            correctIndex = 1,
            explanation = "A soma é 9.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-93",
            prompt = "Quanto é 6 + 2?",
            options = listOf(MagicPicture.SPACE_9, MagicPicture.SPACE_7, MagicPicture.SPACE_8),
            correctIndex = 2,
            explanation = "A soma é 8.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-94",
            prompt = "Quanto é 7 + 3?",
            options = listOf(MagicPicture.NUMBER_10, MagicPicture.NUMBER_11, MagicPicture.SPACE_9),
            correctIndex = 0,
            explanation = "A soma é 10.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-95",
            prompt = "Quanto é 8 + 4?",
            options = listOf(MagicPicture.NUMBER_11, MagicPicture.NUMBER_12, MagicPicture.NUMBER_13),
            correctIndex = 1,
            explanation = "A soma é 12.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-96",
            prompt = "Quanto é 9 + 5?",
            options = listOf(MagicPicture.NUMBER_13, MagicPicture.NUMBER_15, MagicPicture.NUMBER_14),
            correctIndex = 2,
            explanation = "A soma é 14.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-97",
            prompt = "Quanto é 6 + 7?",
            options = listOf(MagicPicture.NUMBER_13, MagicPicture.NUMBER_12, MagicPicture.NUMBER_14),
            correctIndex = 0,
            explanation = "A soma é 13.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-98",
            prompt = "Quanto é 10 + 5?",
            options = listOf(MagicPicture.NUMBER_14, MagicPicture.NUMBER_15, MagicPicture.NUMBER_16),
            correctIndex = 1,
            explanation = "A soma é 15.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-99",
            prompt = "Quanto é 12 + 8?",
            options = listOf(MagicPicture.NUMBER_18, MagicPicture.NUMBER_19, MagicPicture.NUMBER_20),
            correctIndex = 2,
            explanation = "A soma é 20.",
            topic = LearningTopic.NUMBERS,
            category = MagicCategory.MATH
        ),
        MagicQuestion(
            id = "magic-100",
            prompt = "Se hoje é segunda-feira, que dia vem a seguir?",
            options = listOf(MagicPicture.FACT_TERCA_FEIRA, MagicPicture.FACT_DOMINGO, MagicPicture.FACT_SEXTA_FEIRA),
            correctIndex = 0,
            explanation = "Depois de segunda-feira vem terça-feira.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-101",
            prompt = "Qual sentido usamos para ouvir sons?",
            options = listOf(MagicPicture.FACT_VISAO, MagicPicture.FACT_AUDICAO, MagicPicture.FACT_PALADAR),
            correctIndex = 1,
            explanation = "A audição é o sentido que permite ouvir sons.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-102",
            prompt = "Qual instrumento tem teclas pretas e brancas?",
            options = listOf(MagicPicture.FACT_VIOLINO, MagicPicture.FACT_TAMBOR, MagicPicture.FACT_PIANO),
            correctIndex = 2,
            explanation = "O piano tem teclas pretas e brancas.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-103",
            prompt = "Qual animal nasce de um ovo?",
            options = listOf(MagicPicture.FACT_PINTAINHO, MagicPicture.CAT, MagicPicture.COW),
            correctIndex = 0,
            explanation = "O pintainho sai de um ovo.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-104",
            prompt = "Qual material é atraído por um íman comum?",
            options = listOf(MagicPicture.FACT_MADEIRA, MagicPicture.FACT_FERRO, MagicPicture.FACT_PLASTICO),
            correctIndex = 1,
            explanation = "O ferro é atraído por um íman comum.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-105",
            prompt = "O que usamos para proteger a cabeça ao andar de bicicleta?",
            options = listOf(MagicPicture.FACT_CACHECOL, MagicPicture.FACT_OCULOS_DE_SOL, MagicPicture.FACT_CAPACETE),
            correctIndex = 2,
            explanation = "O capacete ajuda a proteger a cabeça ao andar de bicicleta.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-106",
            prompt = "Qual destes elementos é uma planta?",
            options = listOf(MagicPicture.TREE, MagicPicture.BALL, MagicPicture.FACT_AUTOMOVEL),
            correctIndex = 0,
            explanation = "A árvore é uma planta.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-107",
            prompt = "Em que estação faz normalmente mais calor em Portugal?",
            options = listOf(MagicPicture.FACT_INVERNO, MagicPicture.FACT_VERAO, MagicPicture.FACT_OUTONO),
            correctIndex = 1,
            explanation = "Em Portugal, o verão é normalmente a estação mais quente.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-108",
            prompt = "Em que continente fica Portugal?",
            options = listOf(MagicPicture.FACT_AFRICA, MagicPicture.FACT_ASIA, MagicPicture.FACT_EUROPA),
            correctIndex = 2,
            explanation = "Portugal fica no continente europeu.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-109",
            prompt = "O que ajuda a formar um arco-íris?",
            options = listOf(MagicPicture.FACT_LUZ_DO_SOL_E_GOTAS_DE_AGUA, MagicPicture.FACT_VENTO_E_NEVE, MagicPicture.FACT_TROVAO_E_GELO),
            correctIndex = 0,
            explanation = "Um arco-íris pode aparecer quando a luz do Sol atravessa gotas de água.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-110",
            prompt = "O que usamos para apagar uma palavra escrita a lápis?",
            options = listOf(MagicPicture.FACT_LAPIS, MagicPicture.FACT_BORRACHA, MagicPicture.FACT_TESOURA),
            correctIndex = 1,
            explanation = "A borracha apaga palavras escritas a lápis.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        ),
        MagicQuestion(
            id = "magic-111",
            prompt = "Qual animal passa pela fase de girino?",
            options = listOf(MagicPicture.FACT_GALINHA, MagicPicture.CAT, MagicPicture.FACT_RA),
            correctIndex = 2,
            explanation = "A rã passa pela fase de girino.",
            topic = LearningTopic.WORDS,
            category = MagicCategory.GENERAL
        )
    )
}
