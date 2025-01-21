package org.example.DAO;

import org.example.entities.Animal;
import org.example.entities.Animales;
import org.example.entities.Estado;
import org.example.entities.Familia;

import java.util.List;

public interface AnimalDAO {



    //Busca todos los animales
    //List<Animal> findAll();

    /**
     *
     * @param AnimalDAO
     * @return
     */
    //Registrar nuevos animales
    Animal create(Animal AnimalDAO);

    /**
     *
     * @param especie
     * @return
     */
    //Devuelve un animal por su especie
    List<Animal> findByEspecie(Animales especie);

    /**
     *
     * @param estado
     * @param id
     * @return
     */
    //Actualizar el estado en el que se encuentra el animal
    Animal update(Estado estado, Integer id);

    /**
     *
     * @param id
     * @return
     */
    //Datos de la familia que lo acoge
    List<Animal> findByFamilia(Integer id);

    /**
     *
     * @param familia
     * @param id
     * @return
     */
    //Acoger un animal a una familia
    Animal update(Familia familia, Integer id);





}
