import java.io.Serializable;

public class NameValuePair implements Serializable {
    private String name;
    private String value;

    public NameValuePair() {
        this((String) null, (String) null);
    }

    public NameValuePair(String name, String value) {
        this.name = null;
        this.value = null;
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "name=" + name + ", " + "value=" + value;
    }

}
