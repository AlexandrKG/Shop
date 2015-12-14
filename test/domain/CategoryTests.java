package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CategoryTests {

    private Category categ;

    @Before
    public void init() {
        categ = new Category("TestCategory");
    }

    @Test
    public void testCategoryName() {
        Assert.assertTrue(categ.getName().equals("TestCategory"));
    }


    @Test
    public void testAddSubcategory() {
        categ.addSubcategory("TestSubcategory");
        Assert.assertTrue(categ.getSubcategory("TestSubcategory").getName()
                .equals("TestSubcategory"));
    }


    @Test
    public void testGetSubcategoryID() {
        categ.addSubcategory("TestSubcategory1");
        categ.getSubcategory("TestSubcategory1").setId(1000);
        Assert.assertTrue(categ.getSubcategory(1000).getName()
                .equals("TestSubcategory1"));
    }

}
