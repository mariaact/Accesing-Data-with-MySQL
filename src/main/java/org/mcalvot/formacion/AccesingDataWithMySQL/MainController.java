package org.mcalvot.formacion.AccesingDataWithMySQL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path="/informacion")
public class MainController {

    private static final Logger log =  LoggerFactory.getLogger(MainController.class);


    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping(path="/usuario/add")
    public @ResponseBody
    UsuarioDTO addNewUser (@RequestBody UsuarioDTO nuevoUsuario) {


        Usuario user = new Usuario();
        user.setEmail(nuevoUsuario.getEmail());
        user.setNombre(nuevoUsuario.getNombre());
        usuarioRepository.save(user);

        if(user != null)
        {
            nuevoUsuario.setId(user.getId());
        }

        return nuevoUsuario;

    }

    //mostrar toda la informacion de los Usuarios
    @GetMapping(path="/usuario/all")
    public @ResponseBody Iterable<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    //eliminar un usuario
    @DeleteMapping(path="/usuario/remove/{id}")
    public @ResponseBody String deleteUser (@PathVariable("id") Integer id) {

        log.info("Invocado eliminar usuario con id"+id);
        //usuarioRepository.findById(id);

        usuarioRepository.deleteById(id);

        return "Delete";

    }

    //modificar un usuario que es identificado por su id y poder añadir las columnas de apellido y la fecha
    @PutMapping(path = "/usuario/modificar/{id}")
    public @ResponseBody String modificarUser (@PathVariable("id") Integer id, @RequestBody UsuarioDTO usuario) {

        if(usuario == null){
            log.info("ERROR");
        }

        //Optional<Usuario> us = usuarioRepository.findById(id);

        List<Usuario> us =  usuarioRepository.findAllById(id);

        for (Usuario usu : us){
            usu.setNombre(usuario.getNombre());
            usu.setApellido(usuario.getApellido());
            usu.setEmail(usuario.getEmail());
            usu.setFecha_nac(usuario.getFecha_nac());
            usuarioRepository.save(usu);

        }

        return "Modificacion realizada";
    }

    //Buscar por el Email o por el nombre
    @GetMapping (path = "/usuario/buscar")
    public @ResponseBody List<Usuario> getUsuario(@RequestParam (required=false)String email,
                                                  @RequestParam (required=false) String nombre){


        List<Usuario> usu = usuarioRepository.findByNombreContainingAndEmailContaining(nombre,email);

        if(usu.size() <=0){
            return null;
        }

       return usu;
    }




}
