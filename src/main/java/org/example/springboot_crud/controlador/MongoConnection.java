package org.example.springboot_crud.controlador;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {


    public static void conection(){
        final String DATABASE_NAME = "usuarios";
        final String USER_MONGO_CONNECT = "mongodb://localhost:27017";
        MongoClient mongoClient;
        MongoDatabase database;

        mongoClient = MongoClients.create(USER_MONGO_CONNECT);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

}
