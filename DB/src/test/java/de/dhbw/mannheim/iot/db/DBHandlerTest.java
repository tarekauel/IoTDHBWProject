package de.dhbw.mannheim.iot.db;

import de.caluga.morphium.MorphiumSingleton;
import de.dhbw.mannheim.iot.db.model.TestEntity;
import de.dhbw.mannheim.iot.db.model.TestEntityChild;
import de.dhbw.mannheim.iot.db.model.TestEntityRef;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
public class DBHandlerTest {

    static DBHandler<Object> dbHandler;

    @BeforeClass
    public static void prepare() throws UnknownHostException {
        dbHandler = new DBHandler<>("Test");
    }

    @Before
    public void truncateDatabase() {
        MorphiumSingleton.get().getDatabase().dropDatabase();
    }

    @Test
    public void tryToStore() {
        // Store, if no error occurs, assume everything is ok
        TestEntity testEntity1 = new TestEntity("example", new TestEntity.EmbeddedObject("example2"));
        dbHandler.store(testEntity1);
    }

    @Test
    public void createAndSelectAgain() {
        TestEntity testEntity1 = new TestEntity("example3", new TestEntity.EmbeddedObject("example4"));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(1, testEntityList.size());
        Assert.assertEquals(testEntity1, testEntityList.get(0));
    }

    @Test
    public void createAndSelectAgainDeep() {
        TestEntity testEntity1 = new TestEntity("example3", new TestEntity.EmbeddedObject("example4"));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");
        properties.put("embeddedObject", new TestEntity.EmbeddedObject("example4"));

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(1, testEntityList.size());
        Assert.assertEquals(testEntity1, testEntityList.get(0));
    }

    @Test
    public void createAndSelectAgainDeep2Level() {
        TestEntity testEntity1 = new TestEntity("example3",
                new TestEntity.EmbeddedObjectWithPrimitives(123, true,
                        new TestEntity.EmbeddedObjectWithPrimitives.EmbeddedEmbedded("test", 4711)));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");
        properties.put("embeddedObjectWithPrimitives", new TestEntity.EmbeddedObjectWithPrimitives(123, true,
                new TestEntity.EmbeddedObjectWithPrimitives.EmbeddedEmbedded("test", 4711)));

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(1, testEntityList.size());
        Assert.assertEquals(testEntity1, testEntityList.get(0));
    }

    @Test
    public void createAndSelectAgainDeep2() {
        TestEntity testEntity1 = new TestEntity("example3", new TestEntity.EmbeddedObjectWithPrimitives(1,true));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");
        properties.put("embeddedObjectWithPrimitives", new TestEntity.EmbeddedObjectWithPrimitives(1,true));

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(1, testEntityList.size());
        Assert.assertEquals(testEntity1, testEntityList.get(0));
    }

    @Test
    public void createAndSelectAgainDeepNotWorking() {
        TestEntity testEntity1 = new TestEntity("example3", new TestEntity.EmbeddedObject("example4"));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");
        properties.put("embeddedObject", new TestEntity.EmbeddedObject("TRASH"));

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(0, testEntityList.size());
    }

    @Test
    public void createAndSelectAgainDeep2NotWorking() {
        TestEntity testEntity1 = new TestEntity("example3", new TestEntity.EmbeddedObjectWithPrimitives(1,true));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");
        properties.put("embeddedObjectWithPrimitives", new TestEntity.EmbeddedObjectWithPrimitives(1,false));

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(0, testEntityList.size());
    }

    @Test
    public void createAndSelectAgainDeep2LevelNotWorking() {
        TestEntity testEntity1 = new TestEntity("example3",
                new TestEntity.EmbeddedObjectWithPrimitives(123, true,
                        new TestEntity.EmbeddedObjectWithPrimitives.EmbeddedEmbedded("test", 4711)));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");
        properties.put("embeddedObjectWithPrimitives", new TestEntity.EmbeddedObjectWithPrimitives(123, true,
                new TestEntity.EmbeddedObjectWithPrimitives.EmbeddedEmbedded("test", 4712)));

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(0, testEntityList.size());
    }

    @Test
    public void selectMultiple() {
        TestEntity testEntity1 = new TestEntity("example3", (TestEntity.EmbeddedObject) null);

        dbHandler.store(new TestEntity("example3", (TestEntity.EmbeddedObject) null));
        dbHandler.store(new TestEntity("example3", (TestEntity.EmbeddedObject) null));
        dbHandler.store(new TestEntity("example3", (TestEntity.EmbeddedObject) null));
        dbHandler.store(new TestEntity("example3", (TestEntity.EmbeddedObject) null));

        dbHandler.store(new TestEntity("example4", (TestEntity.EmbeddedObject) null));
        dbHandler.store(new TestEntity("example4", (TestEntity.EmbeddedObject) null));
        dbHandler.store(new TestEntity("example4", (TestEntity.EmbeddedObject) null));
        dbHandler.store(new TestEntity("example4", (TestEntity.EmbeddedObject) null));

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");

        List<TestEntity> testEntityList = dbHandler.getEntity(TestEntity.class, properties);

        Assert.assertEquals(4, testEntityList.size());
        Assert.assertEquals(testEntity1, testEntityList.get(0));
        Assert.assertEquals(testEntity1, testEntityList.get(1));
        Assert.assertEquals(testEntity1, testEntityList.get(2));
        Assert.assertEquals(testEntity1, testEntityList.get(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectWithWrongProperties() {
        TestEntity testEntity1 = new TestEntity("example3", new TestEntity.EmbeddedObject("example4"));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayloadDoesntExist", "example3");

        dbHandler.getEntity(TestEntity.class, properties);
    }

    /*
    NOT SUPPORTED, YET
    @Test
    public void testInheritance() {
        TestEntity testEntity1 = new TestEntityChild("example3", new TestEntity.EmbeddedObject("example4"));
        dbHandler.store(testEntity1);

        Map<String, Object> properties = new HashMap<>();
        properties.put("examplePayload", "example3");

        List<? extends TestEntity> testEntityList = dbHandler.getEntity(TestEntityChild.class, properties);

        Assert.assertEquals(1, testEntityList.size());
        Assert.assertEquals(testEntity1, testEntityList.get(0));
    }*/

    @Test
    public void testReferencesCreate() {
        TestEntityRef testEntityRef = new TestEntityRef(new TestEntity("test", (TestEntity.EmbeddedObject) null));
        dbHandler.store(testEntityRef);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReferencesCreateAndRead() {
        TestEntity testEntity = new TestEntity("test", (TestEntity.EmbeddedObject) null);
        TestEntityRef testEntityRef = new TestEntityRef(testEntity);
        dbHandler.store(testEntityRef);

        Map<String, Object> properties = new HashMap<>();
        properties.put("reference", testEntity.getId());

        dbHandler.getEntity(TestEntityRef.class, properties);
    }
}
