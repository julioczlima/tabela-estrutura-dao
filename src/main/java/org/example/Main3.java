package org.example;

import java.sql.Connection;
import java.util.List;

public class Main3 {

    public static void main(String[] args) {
        try (Connection connection = ConexaoDB.conectar()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(connection);

            mostrarProdutos(produtoDAO);

            Produto produtoAtualizar = produtoDAO.consultarPorId(1);
            produtoAtualizar.setPreco(1099.99);
            produtoDAO.atualizar(produtoAtualizar);

            mostrarProdutos(produtoDAO);

        } catch (Exception e) {
            System.out.println("Erro de execução: " + e.getMessage());
        }

    }

    public static void mostrarProdutos (ProdutoDAO produtoDAO) {
        List<Produto> listaProdutos = produtoDAO.listarTodos();

        if (listaProdutos.isEmpty()) {
            System.out.println("A lista de produtos está vazia.");
        } else {
            for (Produto produto : listaProdutos) {
                System.out.printf("Produto: %s | R$ %.2f\n", produto.getNome(), produto.getPreco());
            }
        }
    }

}
