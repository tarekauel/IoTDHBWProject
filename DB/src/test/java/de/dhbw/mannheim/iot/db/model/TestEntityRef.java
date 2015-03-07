package de.dhbw.mannheim.iot.db.model;

import de.caluga.morphium.annotations.Entity;
import de.caluga.morphium.annotations.Id;
import de.caluga.morphium.annotations.Reference;

import java.util.Objects;

/**
 * @author Tarek Auel
 * @since March 08, 2015.
 */
@Entity
public class TestEntityRef {

    @Id
    private org.bson.types.ObjectId id;

    @Reference
    private TestEntity reference;

    public TestEntityRef(TestEntity reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEntityRef that = (TestEntityRef) o;

        return (Objects.equals(this.reference,that.reference));
    }
}
