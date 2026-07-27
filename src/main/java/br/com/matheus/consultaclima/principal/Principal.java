package br.com.matheus.consultaclima.principal;

import br.com.matheus.consultaclima.model.DadosClimaAtual;
import br.com.matheus.consultaclima.model.DadosLocalizacao;
import br.com.matheus.consultaclima.model.ResultadoClima;
import br.com.matheus.consultaclima.model.ResultadoLocalizacoes;
import br.com.matheus.consultaclima.service.ConsumoApi;
import br.com.matheus.consultaclima.service.ConverteDados;
import br.com.matheus.consultaclima.service.TradutorCodigoClima;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();

    private static final String ENDERECO_GEOCODIFICACAO =
            "https://geocoding-api.open-meteo.com/v1/search";

    private static final String ENDERECO_CLIMA =
            "https://api.open-meteo.com/v1/forecast";

    public void exibeMenu() {

        System.out.println("""
                
                ============================
                     CONSULTA DE CLIMA
                ============================
                """);

        System.out.print("Digite o nome da cidade: ");
        String cidadeDigitada = leitura.nextLine().trim();

        if (cidadeDigitada.isBlank()) {
            System.out.println("O nome da cidade não pode ficar vazio.");
            return;
        }

        String cidadeCodificada = URLEncoder.encode(
                cidadeDigitada,
                StandardCharsets.UTF_8
        );

        String enderecoLocalizacao = ENDERECO_GEOCODIFICACAO
                + "?name=" + cidadeCodificada
                + "&count=10"
                + "&language=pt"
                + "&format=json";

        String jsonLocalizacao =
                consumo.obterDados(enderecoLocalizacao);

        ResultadoLocalizacoes resposta =
                conversor.obterDados(
                        jsonLocalizacao,
                        ResultadoLocalizacoes.class
                );

        if (resposta.resultados() == null
                || resposta.resultados().isEmpty()) {

            System.out.println("Nenhuma cidade encontrada.");
            return;
        }

        List<DadosLocalizacao> cidades =
                resposta.resultados();

        System.out.println("\nCidades encontradas:");

        for (int i = 0; i < cidades.size(); i++) {

            DadosLocalizacao cidade = cidades.get(i);

            System.out.printf(
                    "%d - %s, %s, %s%n",
                    i + 1,
                    cidade.nome(),
                    cidade.estado() != null
                            ? cidade.estado()
                            : "Estado não informado",
                    cidade.pais()
            );
        }

        System.out.print("\nEscolha uma cidade: ");

        int opcao;

        try {
            opcao = Integer.parseInt(
                    leitura.nextLine().trim()
            );
        } catch (NumberFormatException e) {
            System.out.println(
                    "Digite apenas o número da cidade."
            );
            return;
        }

        if (opcao < 1 || opcao > cidades.size()) {
            System.out.println("Opção inválida.");
            return;
        }

        DadosLocalizacao cidadeSelecionada =
                cidades.get(opcao - 1);

        String enderecoClima = ENDERECO_CLIMA
                + "?latitude="
                + cidadeSelecionada.latitude()
                + "&longitude="
                + cidadeSelecionada.longitude()
                + "&current="
                + "temperature_2m,"
                + "apparent_temperature,"
                + "relative_humidity_2m,"
                + "wind_speed_10m,"
                + "weather_code"
                + "&timezone=auto";

        String jsonClima =
                consumo.obterDados(enderecoClima);

        ResultadoClima resultadoClima =
                conversor.obterDados(
                        jsonClima,
                        ResultadoClima.class
                );

        if (resultadoClima.climaAtual() == null) {
            System.out.println(
                    "Não foi possível obter os dados climáticos."
            );
            return;
        }

        DadosClimaAtual clima =
                resultadoClima.climaAtual();

        String condicaoClimatica =
                TradutorCodigoClima.traduzir(
                        clima.codigoClima()
                );

        System.out.println("""
                
                ============================
                      CLIMA ATUAL
                ============================
                """);

        System.out.printf(
                "Localização: %s, %s, %s%n",
                cidadeSelecionada.nome(),
                cidadeSelecionada.estado() != null
                        ? cidadeSelecionada.estado()
                        : "Estado não informado",
                cidadeSelecionada.pais()
        );

        System.out.printf(
                "Temperatura: %.1f °C%n",
                clima.temperatura()
        );

        System.out.printf(
                "Sensação térmica: %.1f °C%n",
                clima.sensacaoTermica()
        );

        System.out.printf(
                "Umidade: %d%%%n",
                clima.umidade()
        );

        System.out.printf(
                "Velocidade do vento: %.1f km/h%n",
                clima.velocidadeVento()
        );

        System.out.println(
                "Condição climática: "
                        + condicaoClimatica
        );
    }
}