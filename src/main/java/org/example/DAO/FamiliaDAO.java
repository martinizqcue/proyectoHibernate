package org.example.DAO;

import org.example.entities.Familia;

import java.util.List;

public interface FamiliaDAO {

    /**
     *
     * @param FamiliaDAO
     * @return
     */
    //Agregar familia
    Familia create(Familia FamiliaDAO);

    /**
     *
     * @param FamiliaDAO
     * @return
     */
    //Eliminar familia
    Familia delete(Familia FamiliaDAO);

    /**
     *
     * @return
     */
    //Mostrar familias
    List<Familia> findAll();



}
