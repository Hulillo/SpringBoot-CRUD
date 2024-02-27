package org.example.springboot_crud.Datos;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static org.example.springboot_crud.Datos.Conexion.COLLECTION_NAME;

public class Operaciones {

    private static MongoCollection<Document> collection;


    public static void insertarLibro(MongoDatabase database, String nombre, String id, String nombreLibro, String autor, String sinopsis) {


        Document nuevoLibro;
        Document filtroUsuario;
        Document actualizacion;

        try {
            collection = database.getCollection(COLLECTION_NAME);


            nuevoLibro = new Document()
                    .append("titulo", nombreLibro)
                    .append("autor", autor)
                    .append("sinopsis", sinopsis)
                    .append("id", id);

            filtroUsuario = new Document("usuario", nombre);

            actualizacion = new Document("$push", new Document("libros", nuevoLibro));

            collection.updateOne(filtroUsuario, actualizacion);

            System.out.println("Libro insertado correctamente para el usuario: " + nombre + " Id: " + id);
        } catch (MongoException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
        }
    }

}
