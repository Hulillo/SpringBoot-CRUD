package org.example.springboot_crud.controlador;

import com.google.gson.*;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.springboot_crud.Datos.Conexion;
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
        Conexion conexion = new Conexion();
        Document filtro, usuario;
        String datos = null;
        String usuarioMongo, contrasenaMongo, emailMongo;
        List<Libro> libros = new ArrayList<>();



        try {
            filtro = new Document("usuario", nombre);
            usuario = conexion.getCollection().find(filtro).first();
            JsonParser jsonParser = new JsonParser();
            JsonObject libroObj;
            String titulo,autor,sinopsis;
            JsonObject jsonObject = (JsonObject) jsonParser.parse(usuario.toJson());
            usuarioMongo = jsonObject.get("usuario").getAsString();
            contrasenaMongo = jsonObject.get("password").getAsString();
            emailMongo = jsonObject.get("email").getAsString();
            JsonArray librosJson = jsonObject.getAsJsonArray("libros");



            for (JsonElement libroJson : librosJson) {
                libroObj = libroJson.getAsJsonObject();
                titulo = libroObj.get("titulo").getAsString();
                autor = libroObj.get("autor").getAsString();
                sinopsis = libroObj.get("sinopsis").getAsString();
                libros.add(new Libro(titulo,autor,sinopsis));
            }
            if(usuarioMongo.equals(nombre) && contrasenaMongo.equals(password)){
                model.addAttribute("nombre", usuarioMongo);
                model.addAttribute("password", contrasenaMongo);
                model.addAttribute("email", emailMongo);
                model.addAttribute("libros", libros);
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
