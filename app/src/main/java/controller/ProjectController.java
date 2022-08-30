package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Project;
import util.ConnectionFactory;

public class ProjectController {
    public void save(Project project) {
        String sql = "INSERT INTO projects(name, description, createdAt, updatedAt) "
                + "VALUES(?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            // Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET name = ?, description = ?, "
                + "updatedAt = ? WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(4, project.getId());

            // Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int projectId) {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Setando o valor do id a ser deletado
            statement.setInt(1, projectId);

            // Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        // Lista com os projetos
        List<Project> projects = new ArrayList<>();

        try {
            // Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            // Preparando a query
            statement = connection.prepareStatement(sql);

            // Retorno da execução da query
            resultSet = statement.executeQuery();

            // Enquanto houverem valores no resultSet será criado um projeto e adicionado à
            // lista
            while (resultSet.next()) {
                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mostrar os projetos " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        // Lista de projetos carregada do banco de dados
        return projects;
    }
}
