package de.dhbw.mannheim.iot.db.model;

import de.caluga.morphium.annotations.Embedded;
import de.caluga.morphium.annotations.Entity;
import de.caluga.morphium.annotations.Id;
import de.caluga.morphium.annotations.Reference;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Tarek Auel
 * @since März 05, 2015.
 */

@Entity
public class TestEntity {

    @Id
    org.bson.types.ObjectId id;

    private String examplePayload;

    private EmbeddedObject embeddedObject;

    private EmbeddedObjectWithPrimitives embeddedObjectWithPrimitives;

    public TestEntity(String examplePayload, EmbeddedObject embeddedObject) {
        this.examplePayload = examplePayload;
        this.embeddedObject = embeddedObject;
    }

    public TestEntity(String examplePayload, TestEntity.EmbeddedObjectWithPrimitives embeddedObjectWithPrimitives) {
        this.examplePayload = examplePayload;
        this.embeddedObjectWithPrimitives = embeddedObjectWithPrimitives;
    }

    public ObjectId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEntity that = (TestEntity) o;

        return (Objects.equals(that.examplePayload, this.examplePayload) && Objects.equals(this.embeddedObject,that.embeddedObject));
    }

    @Embedded
    public static class EmbeddedObject {

        private String innerPayload;

        public EmbeddedObject(String innerPayload) {
            this.innerPayload = innerPayload;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EmbeddedObject that = (EmbeddedObject) o;

            return Objects.equals(that.innerPayload, this.innerPayload);
        }
    }

    @Embedded
    public static class EmbeddedObjectWithPrimitives {

        private int integer;
        private boolean bool;
        private EmbeddedEmbedded embedded;

        public EmbeddedObjectWithPrimitives(int integer, boolean bool) {
            this.integer = integer;
            this.bool = bool;
        }

        public EmbeddedObjectWithPrimitives(int integer, boolean bool, EmbeddedEmbedded embedded) {
            this.integer = integer;
            this.bool = bool;
            this.embedded = embedded;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EmbeddedObjectWithPrimitives that = (EmbeddedObjectWithPrimitives) o;

            if (bool != that.bool) return false;
            if (integer != that.integer) return false;

            if (!Objects.equals(this.embedded,that.embedded)) {
                return false;
            }

            return true;
        }

        @Embedded
        public static class EmbeddedEmbedded {

            private String identifier;
            private int integer;

            public EmbeddedEmbedded(String identifier, int integer) {
                this.identifier = identifier;
                this.integer = integer;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                EmbeddedEmbedded that = (EmbeddedEmbedded) o;

                if (integer != that.integer) return false;
                if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;

                return true;
            }
        }
    }
}
