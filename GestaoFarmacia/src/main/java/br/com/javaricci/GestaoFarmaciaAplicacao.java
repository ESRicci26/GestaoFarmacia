package br.com.javaricci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Ponto de entrada da aplicação web de gestão de farmácias. */
@SpringBootApplication
public class GestaoFarmaciaAplicacao {

    public static void main(String[] args) {
        SpringApplication.run(GestaoFarmaciaAplicacao.class, args);
    }
}
