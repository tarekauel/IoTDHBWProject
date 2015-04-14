package de.dhbw.mannheim.iot.db;


import com.google.gson.*;
import com.mongodb.MongoTimeoutException;
import de.caluga.morphium.MorphiumConfig;
import de.caluga.morphium.MorphiumSingleton;
import de.caluga.morphium.query.Query;
import de.dhbw.mannheim.iot.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author Tarek Auel
 * @since March 05, 2015.
 */
// TODO: refactor exception concept
// TODO: do we need selection with inheritance?

/**
 * database wrapper for connection with mongoDB, using POJO-Mapper "morphium"
 * @param <T> the top level type that should be stored, e.g. Model
 */
@Slf4j
public class DBHandler<T> {

    public static void main(String[] args) throws Exception {
        new DBHandler<Model>();
    }

    private static Gson gson = new GsonBuilder().create();

    /**
     * Default constructor. Initialize the database with the title "IoT"
     * @throws UnknownHostException
     */
    public DBHandler() throws UnknownHostException {
        this("IoT");
    }

    /**
     * Constructor for creating the connection to the mongoDB database
     * @param database name of the mongoDB database, e.g. "IoT" or "Test"
     * @throws UnknownHostException is thrown, if mongoDB can't be reached
     */
    public DBHandler(@NotNull String database) throws UnknownHostException {
        try {
            // morphium configuration
            MorphiumConfig cfg = new MorphiumConfig();
            cfg.setDatabase(database);
            cfg.addHost("localhost", 27017); //TODO: runs the database always on the same machine and port?

            MorphiumSingleton.setConfig(cfg);

        } catch (UnknownHostException | MongoTimeoutException  e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Store object of type T in database
     * @param o the object that should be stored
     */
    public void store(@NotNull T o) {
        MorphiumSingleton.get().store(o);
    }

    /**
     * Selects a list of entities from the database. Filter can be specified by providing the properties
     * as key-value map. All properties will be tested on equality. Properties for embedded objects are
     * not supported yet
     * @param cls the class of the object. Has to be T or a subclass of T.
     * @param properties a map of properties that will be used in the query.*
     * @param <S> type of the response objects
     * @return a list of the entities that matches the properties
     */
    @NotNull
    public <S extends T> List<S> getEntity(@NotNull Class<S> cls, @NotNull Map<String,Object> properties) {
        try {
            Query<S> query = MorphiumSingleton.get().createQueryFor(cls);

            // add properties to query
            for (Map.Entry<String, Object> entry : properties.entrySet()) {

                // if value is primitive or string
                if (entry.getValue().getClass().isPrimitive() || entry.getValue() instanceof String) {
                    query = query.f(entry.getKey()).eq(entry.getValue());
                } else {
                    // embedded objects has to be parsed
                    // it's parse to json and back to loose for example transient members
                    JsonElement jsonElement = new JsonParser().parse(gson.toJson(entry.getValue()));
                    if (jsonElement.isJsonObject()) {
                        query = addEmbeddedObjectsToQuery(query, entry.getKey(), jsonElement.getAsJsonObject());
                    }
                    //TODO: else: throw exception?
                }
            }
            // execute query and return result
            return query.asList();
        } catch (RuntimeException e) {
            // is thrown if a field doesn't exist
            log.warn(e.getMessage());

            //maybe this should be double checked
            throw new IllegalArgumentException("Property doesn't exist or is a reference");
        }
    }

    /**
     * add members of an embedded object to a query
     * @param query the query object
     * @param prefix a prefix for the current element level, e.g. objects A.b.c = 10, a is the current prefix
     *               b has a member called c with the value 10*
     * @param object the json object which contains the properties
     * @param <S> the type of response class
     * @return the query object
     */
    @NotNull
    private <S> Query<S> addEmbeddedObjectsToQuery(@NotNull Query<S> query, @NotNull String prefix, @NotNull JsonObject object) {
        for (Map.Entry<String, JsonElement> jsonEntry : object.entrySet()) {
            if (jsonEntry.getValue().isJsonPrimitive()) {
                JsonPrimitive p = jsonEntry.getValue().getAsJsonPrimitive();
                if (p.isNumber()) {
                    query = query.f(prefix + "." + jsonEntry.getKey()).eq(jsonEntry.getValue().getAsNumber());
                } else if (p.isBoolean()) {
                    query = query.f(prefix + "." + jsonEntry.getKey()).eq(jsonEntry.getValue().getAsBoolean());
                } else if (p.isString()) {
                    query = query.f(prefix + "." + jsonEntry.getKey()).eq(jsonEntry.getValue().getAsString());
                }
            } else if (jsonEntry.getValue().isJsonObject()) {
                // parse embedded objects in embedded objects
                query = addEmbeddedObjectsToQuery(query, prefix + "." + jsonEntry.getKey(), jsonEntry.getValue().getAsJsonObject());
            } else {
                log.warn("Unsupported type of json");
            }
        }
        return query;
    }
}
