package ge.tsu.model.repository;

import ge.tsu.model.Client;
import ge.tsu.model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements IRepository {

    protected final Connection conn = Database.getConnection();
    protected final String tableName;

    public RepositoryImpl(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public List<Client> getClients() throws Exception {
        List<Client> result = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s;", tableName));
        ) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getObject("ID", Integer.class));
                client.setName(resultSet.getObject("NAME", String.class));
                result.add(client);
            }
        }
        return result;
    }

    @Override
    public int deleteById(Integer id) throws Exception {
//        validateNotNull(id);
        try (PreparedStatement preparedStatement = conn.prepareStatement(String.format("DELETE FROM %s WHERE ID=?;", tableName))) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }

    @Override
    public int addClient(Client client) throws Exception {
//        validateNotNull(client);
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(String.format("INSERT INTO %s (NAME) VALUES ('%s');", tableName, client.getName()))) {
            return preparedStatement.executeUpdate();
        }
    }

    protected void validateNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("%s must not be null", obj));
        }
    }
}
