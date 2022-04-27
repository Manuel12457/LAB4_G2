package edu.pucp.gtics.lab4_gtics_20221.repository;

import edu.pucp.gtics.lab4_gtics_20221.entity.Juegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface JuegosRepository extends JpaRepository<Juegos,Integer> {

    @Query(nativeQuery = true, value = "select * from juegos order by precio DESC ")
    List<Juegos> listaJuegosDescentendementePorPrecio();

}
