package org.example.springboot_crud.controlador;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormsController {
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
    @PostMapping("/submit-formulario")
    public String procesarFormulario(@RequestParam String nombre, @RequestParam String password, Model model) {
        final String DATABASE_NAME = "usuarios";
        final String USER_MONGO_CONNECT = "mongodb://localhost:27017";
        final String COLLECTION_NAME = "usuarios";
        MongoClient mongoClient;
        MongoDatabase database;
        MongoCollection<Document> collection;
        Document filtro, usuario;
        Gson gson = new Gson();

        mongoClient = MongoClients.create(USER_MONGO_CONNECT);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);

        try {
            collection = database.getCollection(COLLECTION_NAME);
            filtro = new Document("nombre", nombre);
            usuario = collection.find(filtro).first();




            gson.toJson();
            model.addAttribute("nombre", nombre);
            model.addAttribute("password", password);
            return "usuario";

        } catch (MongoException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("No existe dicho usuario.");
        }
        // Aquí puedes realizar alguna lógica con los datos del formulario
        ;
    }



}
