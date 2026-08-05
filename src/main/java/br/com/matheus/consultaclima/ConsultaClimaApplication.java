package br.com.matheus.consultaclima;

import br.com.matheus.consultaclima.principal.Principal;
import br.com.matheus.consultaclima.repository.ConsultaClimaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultaClimaApplication
		implements CommandLineRunner {

	private final ConsultaClimaRepository repository;

	public ConsultaClimaApplication(
			ConsultaClimaRepository repository
	) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(
				ConsultaClimaApplication.class,
				args
		);
	}

	@Override
	public void run(String... args) {
		Principal principal = new Principal(repository);
		principal.exibeMenu();
	}
}