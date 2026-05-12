package org.example.exercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Exercicio1 {

    private static final List<String> listaNomes = List.of(
            "Pedro", "Mateus", "Tigas", "La", "Leras", "Mike", "Aristoteles", "Lim");

    public static void executarAtividade1_1() {
//        List<String> list1 = listaNomes
//                .stream()
//                .filter(nome -> nome.length() > 4)
//                .toList();
//
//        System.out.println("Nomes com mais de 4 caracteres: " + list1);
//
//        List<String> list2 = listaNomes
//                .stream()
//                .map(String::toUpperCase)
//                .toList();
//
//        System.out.println("Nomes em maiusculo: " + list2);
//
//        List<String> list3 = listaNomes
//                .stream()
//                .sorted()
//                .toList();
//
//        System.out.println("Nomes em ordem: " + list3);
//
//        String nomes = listaNomes.stream().collect(Collectors.joining(", "));
//
//        System.out.println("Nomes em string: " + nomes);

        String exercicioCompleto = listaNomes
                .stream()
                .sorted()
                .filter(nome -> nome.length() > 4)
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));

        System.out.println("Nomes em string: " + exercicioCompleto);

    }

    public static void executarAtividade1_2() {
        System.out.println("Executando atividade 2...");
    }

    public static void executarAtividade1_3() {
        System.out.println("Executando atividade 3...");
    }

    public static void executarAtividade1_4() {
        System.out.println("Executando atividade 4...");
    }

}
