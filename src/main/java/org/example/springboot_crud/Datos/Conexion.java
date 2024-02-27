package org.example.springboot_crud.Datos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Conexion {
    private final String DATABASE_NAME = "usuarios";
    private final String USER_MONGO_CONNECT = "mongodb://localhost:27017";
    public static final String COLLECTION_NAME = "usuarios";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public Conexion() {
        mongoClient = MongoClients.create(USER_MONGO_CONNECT);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public String getDATABASE_NAME() {
        return DATABASE_NAME;
    }

    public String getUSER_MONGO_CONNECT() {
        return USER_MONGO_CONNECT;
    }

    public String getCOLLECTION_NAME() {
        return COLLECTION_NAME;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void setCollection(MongoCollection<Document> collection) {
        this.collection = collection;
    }
}
