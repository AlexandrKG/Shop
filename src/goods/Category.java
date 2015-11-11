package goods;


import utl.Entry;

import java.util.ArrayList;

public class Category extends Entry {

    protected ArrayList<Entry> subcategories;

    public Category(String data) {
        this.name = data;
        subcategories = new ArrayList<>();
    }

    public ArrayList<Entry> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(ArrayList<Entry> subcategories) {
        this.subcategories = subcategories;
    }

    private boolean existSubcategory(String subcategoryName) {
        for(Entry e : subcategories) {
            if(e.getName().equals(subcategoryName)) {
                return true;
            }
        }
        return false;
    }

    public void addSubcategory(String subcategory) {
        if (!existSubcategory(subcategory)) {
            Entry e = new Entry();
            e.setName(subcategory);
            e.setId(System.currentTimeMillis());
            subcategories.add(e);
        }
    }

    public void addSubcategory(Entry e) {
        subcategories.add(e);
    }


    public Entry getSubcategory(String subcategory) {
        Entry result = null;
        for(Entry e : subcategories) {
            if(e.getName().equals(subcategory)) {
                result = e;
                break;
            }
        }
        return result;
    }
}
