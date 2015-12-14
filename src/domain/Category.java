package domain;


import java.util.ArrayList;

public class Category extends Entry {

    protected ArrayList<Subcategory> subcategories;

    public Category(String data) {
        this.name = data;
        subcategories = new ArrayList<>();
    }

    public ArrayList<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(ArrayList<Subcategory> subcategories) {
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
            Subcategory e = new Subcategory();
            e.setName(subcategory);
            e.setId(System.currentTimeMillis());
            subcategories.add(e);
        }
    }

    public void addSubcategory(Subcategory e) {
        subcategories.add(e);
    }


    public Subcategory getSubcategory(String subcategory) {
        Subcategory result = null;
        for(Subcategory e : subcategories) {
            if(e.getName().equals(subcategory)) {
                result = e;
                break;
            }
        }
        return result;
    }

    public Subcategory getSubcategory(long subcategoryID) {
        Subcategory result = null;
        for(Subcategory e : subcategories) {
            if(e.getId() == subcategoryID) {
                result = e;
                break;
            }
        }
        return result;
    }
}
