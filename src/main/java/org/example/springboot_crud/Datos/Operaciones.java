package org.example.springboot_crud.Datos;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static org.example.springboot_crud.Datos.Conexion.COLLECTION_NAME;

public class Operaciones {

    private static MongoCollection<Document> collection;


    public static void insertarLibro(MongoDatabase database, String nombre, String nombreLibro, String autor, String sinopsis) {


        Document nuevoLibro;

        try {
            // Obtener la colección "usuarios"
            collection = database.getCollection(COLLECTION_NAME);

            // Crear un nuevo documento para el libro
            nuevoLibro = new Document()
                    .append("titulo", nombreLibro)
                    .append("autor", autor)
                    .append("sinopsis", sinopsis);

            // Crear un filtro para encontrar al usuario por su nombre
            Document filtroUsuario = new Document("usuario", nombre);

            // Crear un documento de actualización para agregar el nuevo libro al array "libros"
            Document actualizacion = new Document("$push", new Document("libros", nuevoLibro));

            // Actualizar el documento del usuario con el nuevo libro
            collection.updateOne(filtroUsuario, actualizacion);

            System.out.println("Libro insertado correctamente para el usuario: " + nombre);
        } catch (MongoException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
        }
    }

}
