import java.util.HashSet;
import java.util.Set;

public class Departament {
    private long id;
    private long parent;
    private String name;

    private Set<Departament> child = new HashSet<>();

    @Override
    public String toString() {
        return "Departament{" +
                "id=" + id +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }

    public Set<Departament> getChild() {
        return child;
    }

    public void setChild(Set<Departament> child) {
        this.child = child;
    }

    public Departament(long id, long parent, String name) {
        this.id = id;
        this.parent = parent;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
