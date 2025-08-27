package com.exemplo.main;

import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import com.exemplo.dao.ProdutoDAO;
import com.exemplo.model.Produto;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        ProdutoDAO dao = new ProdutoDAO();
        int opc;

        do {
            System.out.println("===== MENU =====");
            System.out.println("1) Listar");
            System.out.println("2) Inserir");
            System.out.println("3) Excluir");
            System.out.println("4) Atualizar");
            System.out.println("5) Sair");
            System.out.print("Opção: ");
            while (!sc.hasNextInt()) { sc.next(); System.out.print("Opção: "); }
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    List<Produto> produtos = dao.listar();
                    if (produtos.isEmpty()) System.out.println("(vazio)");
                    else produtos.forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();
                    dao.inserir(new Produto(nome, preco));
                    System.out.println("Inserido!");
                    break;
                case 3:
                    System.out.print("ID para excluir: ");
                    int idEx = sc.nextInt();
                    dao.excluir(idEx);
                    System.out.println("Excluído (se existia).");
                    break;
                case 4:
                    System.out.print("ID para atualizar: ");
                    int idAt = sc.nextInt(); sc.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = sc.nextLine();
                    System.out.print("Novo preço: ");
                    double novoPreco = sc.nextDouble();
                    dao.atualizar(new Produto(idAt, novoNome, novoPreco));
                    System.out.println("Atualizado (se existia).");
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println();
        } while (opc != 5);

        sc.close();
    }
}
