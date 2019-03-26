package org.mcalvot.formacion.AccesingDataWithMySQL;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    List<Usuario> findAllById(int i);
    List<Usuario> findByEmail(String email);
    List<Usuario> findByNombre(String nombre);

    List<Usuario> findByNombreContainingAndEmailContaining(String email, String nombre);

}
