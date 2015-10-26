package utl;

import java.util.ArrayList;
import java.util.List;

import goods.Goods;

public class Bag<T extends Goods> {

    List<T> list;

    public Bag() {
        list = new ArrayList<>();
    }

    public void add(T item) {

        list.add(item);
    }


    public <T> T get(int indx) {
        return (T) list.get(indx);
    }

    public <T> T find(Class<T> obj) {
        T item;
        for (int i = 0; i < list.size(); i++) {
            if (obj == list.get(i).getClass()) {
                return (T) list.get(i);
            }
        }
        return null;
    }

    public void printAll() {
        for (T item : list) {
            System.out.println(item.toString());
        }
    }

    public int getSize() {

        return list.size();
    }

    public List<T> getList() {
        return list;
    }
}
