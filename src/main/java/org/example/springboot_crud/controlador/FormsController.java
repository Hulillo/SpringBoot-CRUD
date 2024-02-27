package org.example.springboot_crud.controlador;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.springboot_crud.Datos.Conexion;
import org.example.springboot_crud.Datos.Libro;
import org.springframework.boot.Banner;
import org.example.springboot_crud.Datos.Operaciones;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable("id") String id, @RequestParam String nombre, @RequestParam String password, Model model) {
        try {
            Conexion conexion = new Conexion();
            Document usuario = conexion.getCollection().find(Filters.eq("usuario", nombre)).first();

            if (usuario != null) {
                List<Document> libros = usuario.getList("libros", Document.class);
                Document libroEditar = null;
                Document libro;

                int i = 0;
                while (i < libros.size() && libroEditar == null) {
                    libro = libros.get(i);
                    if (libro.getString("id").equals(id)) {
                        libroEditar = libro;
                    }
                    i++;
                }

                if (libroEditar != null) {
                    String titulo = libroEditar.getString("titulo");
                    String autor = libroEditar.getString("autor");
                    String sinopsis = libroEditar.getString("sinopsis");

                    model.addAttribute("titulo", titulo);
                    model.addAttribute("autor", autor);
                    model.addAttribute("sinopsis", sinopsis);

                    return "editar";
                } else {
                    System.out.println("Libro no encontrado");
                }
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (MongoException e) {
            System.err.println("Error al editar el libro: " + e.getMessage());
        }

        return "redirect:/usuario";
    }

    @PostMapping("/editar-libro")
    public String editarLibro(@RequestParam("idEdit") String idEdit, @RequestParam String titulo, @RequestParam String autor, @RequestParam String sinopsis, @RequestParam String nombreEdit, @RequestParam String passwordEdit, Model model){
        try {
            Conexion conexion = new Conexion();
            Document usuario = conexion.getCollection().find(Filters.eq("usuario", nombreEdit)).first();

            if (usuario != null) {
                List<Document> libros = usuario.getList("libros", Document.class);
                List<Document> librosNuevos = new ArrayList<>();

                for (Document libro : libros) {
                    if (libro.getString("id").equals(idEdit)) {
                        libro.put("titulo", titulo);
                        libro.put("autor", autor);
                        libro.put("sinopsis", sinopsis);
                    }
                    librosNuevos.add(libro);
                }

                UpdateResult result = conexion.getCollection().updateOne(
                        Filters.eq("usuario", nombreEdit),
                        Updates.set("libros", librosNuevos)
                );

                if (result.getModifiedCount() > 0) {
                    System.out.println("Libro editado con éxito");
                } else {
                    System.out.println("No se pudo editar el libro");
                }
            } else {
                System.out.println("Usuario no encontrado");
                System.out.println(nombreEdit);
                System.out.println(passwordEdit);
            }
        } catch (MongoException e) {
            System.err.println("Error al editar el libro: " + e.getMessage());
        }

        model.addAttribute("nombre", nombreEdit);
        model.addAttribute("password", passwordEdit);

        return procesarFormulario(nombreEdit, passwordEdit, model);
    }
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") String id, @RequestParam String nombre,@RequestParam String password, Model model) {
        try {
            Conexion conexion = new Conexion();
            Document usuario = conexion.getCollection().find(Filters.eq("usuario", nombre)).first();

            if (usuario != null) {
                List<Document> libros = usuario.getList("libros", Document.class);
                List<Document> librosNuevos = new ArrayList<>();

                for (Document libro : libros) {
                    if (!libro.getString("id").equals(id)) {
                        librosNuevos.add(libro);
                    }
                }

                UpdateResult result = conexion.getCollection().updateOne(
                        Filters.eq("usuario", nombre),
                        Updates.set("libros", librosNuevos)
                );

                if (result.getModifiedCount() > 0) {
                    System.out.println("Libro eliminado con éxito");
                    return procesarFormulario(nombre, password, model);
                } else {
                    System.out.println("No se pudo eliminar el libro");
                }
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (MongoException e) {
            System.err.println("Error al eliminar el libro: " + e.getMessage());
        }

        return "redirect:/usuario";
    }
    @PostMapping("/insertar")
    public String mostrarInsertar(@RequestParam String nombre, @RequestParam String password, Model model) {

        model.addAttribute("nombre", nombre);
        model.addAttribute("password", password);

        return "insertar";
    }

    @PostMapping("/editarMostrar")
    public String mostrarEditar(@RequestParam String idEdit, @RequestParam String nombreEdit, @RequestParam String passwordEdit, Model model) {

        model.addAttribute("idEdit", idEdit);
        model.addAttribute("nombreEdit", nombreEdit);
        model.addAttribute("passwordEdit", passwordEdit);

        try {
            Conexion conexion = new Conexion();
            Document usuario = conexion.getCollection().find(Filters.eq("usuario", nombreEdit)).first();

            if (usuario != null) {
                List<Document> libros = usuario.getList("libros", Document.class);
                Document libroEditar = null;
                Document libro;

                int i = 0;
                while (i < libros.size() && libroEditar == null) {
                    libro = libros.get(i);
                    if (libro.getString("id").equals(idEdit)) {
                        libroEditar = libro;
                    }
                    i++;
                }

                if (libroEditar != null) {
                    String titulo = libroEditar.getString("titulo");
                    String autor = libroEditar.getString("autor");
                    String sinopsis = libroEditar.getString("sinopsis");

                    model.addAttribute("titulo", titulo);
                    model.addAttribute("autor", autor);
                    model.addAttribute("sinopsis", sinopsis);

                    return "editar";
                } else {
                    System.out.println("Libro no encontrado");
                }
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (MongoException e) {
            System.err.println("Error al editar el libro: " + e.getMessage());
        }

        return "editar";
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
            String id,titulo,autor,sinopsis;
            JsonObject jsonObject = (JsonObject) jsonParser.parse(usuario.toJson());
            usuarioMongo = jsonObject.get("usuario").getAsString();
            contrasenaMongo = jsonObject.get("password").getAsString();
            emailMongo = jsonObject.get("email").getAsString();
            JsonArray librosJson = jsonObject.getAsJsonArray("libros");

            for (JsonElement libroJson : librosJson) {
                libroObj = libroJson.getAsJsonObject();
                id = libroObj.get("id").getAsString();
                titulo = libroObj.get("titulo").getAsString();
                autor = libroObj.get("autor").getAsString();
                sinopsis = libroObj.get("sinopsis").getAsString();
                libros.add(new Libro(id,titulo,autor,sinopsis));
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

    @PostMapping("/insertar-libro")
    public String insertarLibro(@RequestParam("nombre") String nombre,
                                @RequestParam("password") String password,
                                Model model,
                                @RequestParam("titulo") String titulo,
                                @RequestParam("autor") String autor,
                                @RequestParam("sinopsis") String sinopsis) {

        Conexion conexion = new Conexion();

        Operaciones.insertarLibro(conexion.getDatabase(), nombre, titulo, autor, sinopsis);

        model.addAttribute("nombre", nombre);
        model.addAttribute("password", password);

        return procesarFormulario(nombre, password, model);
    }



}
