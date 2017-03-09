package org.curtis.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThingTest {
    @Test
    public void getName() throws Exception {
        String name = "Thing Name";
        Thing thing = new Thing();
        thing.setName(name);

        assertEquals(name, thing.getName());
    }

    @Test
    public void setName() throws Exception {

    }

    @Test
    public void getDescription() throws Exception {
        String description = "Thing Description";
        Thing thing = new Thing();
        thing.setDescription(description);

        assertEquals(description, thing.getDescription());
    }
}