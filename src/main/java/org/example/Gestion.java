package org.example;

import org.example.DAO.AnimalDAO;
import org.example.DAO.AnimalImpl;
import org.example.DAO.FamiliaDAO;
import org.example.DAO.FamiliaImpl;
import org.example.entities.Animal;
import org.example.entities.Animales;
import org.example.entities.Estado;
import org.example.entities.Familia;

import java.util.List;
import java.util.Scanner;

public class Gestion {

    private final AnimalDAO animalDAO = new AnimalImpl();
    private final FamiliaDAO familiaDAO = new FamiliaImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void gestionar() {
        int opcion;

        do {
            System.out.println("========= MENÚ DE GESTIÓN =========");
            System.out.println("1. Registrar un nuevo animal");
            System.out.println("2. Buscar animales por especie");
            System.out.println("3. Actualizar estado de un animal");
            System.out.println("4. Mostrar animales de una familia");
            System.out.println("5. Asignar un animal a una familia");
            System.out.println("6. Registrar una nueva familia");
            System.out.println("7. Eliminar una familia");
            System.out.println("8. Mostrar todas las familias");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarNuevoAnimal();
                case 2 -> buscarAnimalesPorEspecie();
                case 3 -> actualizarEstadoAnimal();
                case 4 -> mostrarAnimalesDeFamilia();
                case 5 -> asignarAnimalAFamilia();
                case 6 -> registrarNuevaFamilia();
                case 7 -> eliminarFamilia();
                case 8 -> mostrarTodasLasFamilias();
                case 0 -> System.out.println("Saliendo del sistema. ¡Hasta luego!");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void registrarNuevoAnimal() {
        System.out.println("Registrar un nuevo animal:");
        System.out.print("Nombre del animal: ");
        String nombre = scanner.next();

        String especie = obtenerEspecieValida();
        System.out.print("Edad del animal: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.print("Descripción de cómo se perdió: ");
        String descripcion = scanner.nextLine();

        String estado = obtenerEstadoValido();
        Animal nuevoAnimal = new Animal();
        nuevoAnimal.setNombre(nombre);
        nuevoAnimal.setEspecie(Animales.valueOf(especie));
        nuevoAnimal.setEdad(edad);
        nuevoAnimal.setDescripcionPerdida(descripcion);
        nuevoAnimal.setEstado(Estado.valueOf(estado));

        animalDAO.create(nuevoAnimal);
        System.out.println("¡Animal registrado con éxito!");
    }

    private String obtenerEspecieValida() {
        String especie;
        while (true) {
            System.out.print("Especie (Perro, Gato, Pájaro, CerdoVietnamita, Serpiente, Camaleón, Araña): ");
            especie = scanner.next();
            try {
                Animales.valueOf(especie);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Especie inválida. Intente nuevamente.");
            }
        }
        return especie;
    }

    private String obtenerEstadoValido() {
        String estado;
        while (true) {
            System.out.print("Estado actual (RecienAbandonado, TiempoRefugio, ProximamenteAcogida): ");
            estado = scanner.next();
            try {
                Estado.valueOf(estado);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido. Intente nuevamente.");
            }
        }
        return estado;
    }

    private void buscarAnimalesPorEspecie() {
        String especie = obtenerEspecieValida();
        List<Animal> animales = animalDAO.findByEspecie(Animales.valueOf(especie));
        if (animales.isEmpty()) {
            System.out.println("No se encontraron animales de esta especie.");
        } else {
            System.out.println("Animales encontrados:");
            animales.forEach(System.out::println);
        }
    }

    private void actualizarEstadoAnimal() {
        System.out.print("Ingrese el ID del animal a actualizar: ");
        int idAnimal = scanner.nextInt();

        String estado = obtenerEstadoValido();
        Animal animalActualizado = animalDAO.update(Estado.valueOf(estado), idAnimal);
        if (animalActualizado != null) {
            System.out.println("Estado del animal actualizado: " + animalActualizado);
        } else {
            System.out.println("No se encontró el animal con el ID especificado.");
        }
    }

    private void mostrarAnimalesDeFamilia() {
        System.out.print("Ingrese el ID de la familia: ");
        int idFamilia = scanner.nextInt();
        List<Animal> animales = animalDAO.findByFamilia(idFamilia);
        if (animales.isEmpty()) {
            System.out.println("No hay animales asociados a esta familia.");
        } else {
            System.out.println("Animales encontrados:");
            animales.forEach(System.out::println);
        }
    }

    private void asignarAnimalAFamilia() {
        System.out.print("Ingrese el ID del animal: ");
        int idAnimal = scanner.nextInt();
        System.out.print("Ingrese el ID de la familia que acogerá al animal: ");
        int idFamilia = scanner.nextInt();

        Familia familia = new Familia();
        familia.setId(idFamilia);
        Animal animalAsignado = animalDAO.update(familia, idAnimal);
        if (animalAsignado != null) {
            System.out.println("Animal asignado a la familia con éxito: " + animalAsignado);
        } else {
            System.out.println("No se pudo asignar el animal a la familia.");
        }
    }

    private void registrarNuevaFamilia() {
        System.out.println("Registrar una nueva familia:");
        System.out.print("Nombre de la familia: ");
        String nombre = scanner.next();
        System.out.print("Edad promedio de los miembros: ");
        int edad = scanner.nextInt();
        System.out.print("Ciudad: ");
        String ciudad = scanner.next();

        Familia nuevaFamilia = new Familia();
        nuevaFamilia.setNombre(nombre);
        nuevaFamilia.setEdad(edad);
        nuevaFamilia.setCiudad(ciudad);

        familiaDAO.create(nuevaFamilia);
        System.out.println("¡Familia registrada con éxito!");
    }

    private void eliminarFamilia() {
        System.out.print("Ingrese el ID de la familia a eliminar: ");
        int idFamilia = scanner.nextInt();
        Familia familia = new Familia();
        familia.setId(idFamilia);
        Familia familiaEliminada = familiaDAO.delete(familia);
        if (familiaEliminada != null) {
            System.out.println("Familia eliminada con éxito.");
        } else {
            System.out.println("No se encontró la familia con el ID especificado.");
        }
    }

    private void mostrarTodasLasFamilias() {
        System.out.println("Mostrando todas las familias registradas:");
        try {
            List<Familia> familias = familiaDAO.findAll();
            if (familias.isEmpty()) {
                System.out.println("No hay familias registradas.");
            } else {
                familias.forEach(familia -> {
                    System.out.println("ID: " + familia.getId());
                    System.out.println("Nombre: " + familia.getNombre());
                    System.out.println("Edad promedio: " + familia.getEdad());
                    System.out.println("Ciudad: " + familia.getCiudad());
                    System.out.println("--------------------------");
                });
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las familias: " + e.getMessage());
        }
    }
}