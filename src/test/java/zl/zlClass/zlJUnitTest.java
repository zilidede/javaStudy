package zl.zlClass;

import org.junit.Test;

import static org.junit.Assert.*;

public class zlJUnitTest {

    @Test
    public void add() {
        zlJUnit jUnit = new zlJUnit();
        System.out.println(jUnit.add(1,2));
    }

    @Test
    public void dec() {
        zlJUnit jUnit = new zlJUnit();
        System.out.println(jUnit.dec(1,2));
    }
}