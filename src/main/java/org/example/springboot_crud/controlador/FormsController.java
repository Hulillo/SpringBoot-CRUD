package org.example.springboot_crud.controlador;

import com.google.gson.*;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.springboot_crud.Datos.Libro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


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
        mongoClient = MongoClients.create(USER_MONGO_CONNECT);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
        String datos = null;
        String usuarioMongo, contrasenaMongo, emailMongo;
        List<Libro> libros = new ArrayList<>(); // Lista para almacenar los libros


        try {
            filtro = new Document("usuario", nombre);
            usuario = collection.find(filtro).first();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(usuario.toJson());


            usuarioMongo = jsonObject.get("usuario").getAsString();
            contrasenaMongo = jsonObject.get("password").getAsString();
            emailMongo = jsonObject.get("email").getAsString();

            JsonArray librosJson = jsonObject.getAsJsonArray("libros");
            for (JsonElement libroJson : librosJson) {
                JsonObject libroObj = libroJson.getAsJsonObject();
                String titulo = libroObj.get("titulo").getAsString();
                String autor = libroObj.get("autor").getAsString();
                String sinopsis = libroObj.get("sinopsis").getAsString();
                libros.add(new Libro(titulo,autor,sinopsis));
            }

            if(usuarioMongo.equals(nombre) && contrasenaMongo.equals(password)){
                model.addAttribute("nombre", usuarioMongo);
                model.addAttribute("password", contrasenaMongo);
                model.addAttribute("email", emailMongo);
                model.addAttribute("libros", libros); // Pasar la lista de libros al modelo
                datos = "usuario";
            }
        } catch (MongoException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("No existe dicho usuario.");
        }
        return datos;
    }



}
