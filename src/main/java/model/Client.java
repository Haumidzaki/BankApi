package model;

import java.util.List;
import java.util.Objects;

public class Client extends AbstractBaseEntity {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id= " + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
