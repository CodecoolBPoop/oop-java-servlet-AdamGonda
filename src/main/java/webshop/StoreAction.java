package webshop;

public class StoreAction {
    private int id;
    private String type;

    public StoreAction(int id, String action) {
        this.id = id;
        this.type = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
