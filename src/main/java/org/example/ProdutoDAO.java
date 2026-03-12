package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final Connection CONEXAO_DB;

    public ProdutoDAO(Connection connection) {
        this.CONEXAO_DB = connection;
    }

    public void inserir(Produto produto) {
        String sql = """
                INSERT INTO produtos (nome_produto, quantidade, preco, status)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = CONEXAO_DB.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getQuantidade());
            statement.setDouble(3, produto.getPreco());
            statement.setString(4, produto.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o produto:" + e.getMessage());
        }

    }

    public void excluirTodos() {
        String sql = "DELETE FROM produtos";

        try (PreparedStatement statement = CONEXAO_DB.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar todos os produtos: " + e.getMessage());
        }
    }

    public Produto consultarPorId(int id) {
        String sql = "SELECT * from produtos WHERE id_produto = ?";

        try (PreparedStatement statement = CONEXAO_DB.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Produto produto = new Produto();
                    produto.setId(resultSet.getInt("id_produto"));
                    produto.setNome(resultSet.getString("nome_produto"));
                    produto.setQuantidade(resultSet.getInt("quantidade"));
                    produto.setPreco(resultSet.getDouble("preco"));
                    produto.setStatus(resultSet.getString("status"));
                    return produto;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar o produto por ID: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Produto produto) {
        String sql = """
                UPDATE produtos
                SET nome_produto = ?, quantidade = ?, preco = ?, status = ?
                WHERE id_produto = ?
                """;

        try (PreparedStatement statement = CONEXAO_DB.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getQuantidade());
            statement.setDouble(3, produto.getPreco());
            statement.setString(4, produto.getStatus());
            statement.setInt(5, produto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o produto: " + e.getMessage());
        }

    }

    public void excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id_produto = ?";

        try (PreparedStatement statement = CONEXAO_DB.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir o produto: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> listaProdutos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (PreparedStatement statement = CONEXAO_DB.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id_produto"));
                produto.setNome(resultSet.getString("nome_produto"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setStatus(resultSet.getString("status"));
                listaProdutos.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os produtos: " + e.getMessage());
        }
        return listaProdutos;
    }

}
