package ui;


public class Item<T> {

    private String description;
    private T obj;

    public Item(T obj, String description) {
        this.obj = obj;
        this.description = description;
    }

    public T getObj() {
        return obj;
    }

    public String getDescription() {
        return description;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString()
    {
        return description;
    }

}