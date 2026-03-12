package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CriadorTabela {

    public static void criarTabela() {

        try (Connection conexao = ConexaoDB.conectar();
             Statement statement = conexao.createStatement()) {

            // SQL para criar a tabela
            String createSqlTable = "CREATE TABLE IF NOT EXISTS produtos (" +
                    "id_produto INTEGER PRIMARY KEY, " +
                    "nome_produto TEXT NOT NULL, " +
                    "quantidade INTEGER, " +
                    "preco REAL, " +
                    "status TEXT" +
                    ");";
            System.out.println(createSqlTable);

            statement.execute(createSqlTable);

            System.out.println("Tabela 'Produtos' criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar a tabela: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
