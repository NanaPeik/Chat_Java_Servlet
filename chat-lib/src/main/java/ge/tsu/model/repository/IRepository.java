package ge.tsu.model.repository;

import ge.tsu.model.Client;

import java.util.List;

public interface IRepository {
    List<Client> getClients() throws Exception;

    int deleteById(Integer id) throws Exception;

    int addClient(Client client) throws Exception;
}
