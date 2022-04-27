package edu.pucp.gtics.lab4_gtics_20221.controller;

import edu.pucp.gtics.lab4_gtics_20221.entity.*;
import edu.pucp.gtics.lab4_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
public class JuegosController {

    @Autowired
    JuegosRepository juegosRepository;

    @Autowired
    PlataformasRepository plataformasRepository;

    @Autowired
    DistribuidorasRepository distribuidorasRepository;

    @Autowired
    GenerosRepository generosRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = {"", "/","/juegos/lista"})
    public String listaJuegos (){
        return "";
    }

    public String vistaJuegos ( ){
        return "";
    }

    @GetMapping("/juegos/nuevo")
    public String nuevoJuegos(@ModelAttribute("juego") Juegos juego, Model model){
        model.addAttribute("plataformas",plataformasRepository.findAll());
        model.addAttribute("generos",generosRepository.findAll());
        model.addAttribute("distribuidoras",distribuidorasRepository.findAll());

        return "juegos/editarFrm";
    }

    @GetMapping("/juegos/editar")
    public String editarJuegos( @RequestParam("id") int id, Model model, @ModelAttribute("juego") Juegos juego){
        Optional<Juegos> optionalJuegos = juegosRepository.findById(id);
        if(optionalJuegos.isEmpty()){return "redirect:/juegos";}
        model.addAttribute("juego",optionalJuegos.get());
        model.addAttribute("plataformas",plataformasRepository.findAll());
        model.addAttribute("generos",generosRepository.findAll());
        model.addAttribute("distribuidoras",distribuidorasRepository.findAll());

        return "juegos/editarFrm";
    }
    @GetMapping("/juegos/guardar")
    public String guardarJuegos(@Valid Juegos juego, RedirectAttributes redirectAttributes){
        String msg;
        if(juegosRepository.findById(juego.getIdjuego()).isPresent()){
            msg="Juego actualizado exitosamente";
        } else {
            msg="Juego creado exitosamente";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/juegos";
    }

    @GetMapping("/juegos/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id){
        Optional<Juegos> opt = juegosRepository.findById(id);
        if (opt.isPresent()) {
            juegosRepository.deleteById(id);
        }
        return "redirect:/juegos/lista";
    }

}
