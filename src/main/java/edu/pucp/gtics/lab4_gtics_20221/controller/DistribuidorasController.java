package edu.pucp.gtics.lab4_gtics_20221.controller;

import edu.pucp.gtics.lab4_gtics_20221.entity.Distribuidoras;
import edu.pucp.gtics.lab4_gtics_20221.entity.Paises;
import edu.pucp.gtics.lab4_gtics_20221.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab4_gtics_20221.repository.PaisesRepository;
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
@RequestMapping("/distribuidoras")
public class DistribuidorasController {

    @Autowired
    DistribuidorasRepository distribuidorasRepository;

    @Autowired
    PaisesRepository paisesRepository;


    @GetMapping("/lista")
    public String listaDistribuidoras (Model model){
        model.addAttribute("listadistribuidoras",distribuidorasRepository.findAll(Sort.by("nombre")));

        return "distribuidoras/lista";
    }

    @GetMapping("/editar")
    public String editarDistribuidoras(@RequestParam("id")int id, Model model, @ModelAttribute("distribuidora") Distribuidoras distribuidoras){
        Optional<Distribuidoras> optDist = distribuidorasRepository.findById(id);
        if(optDist.isEmpty()){
            return "redirect:/distribuidoras/lista";}
            model.addAttribute("distribuidora", optDist.get());
            model.addAttribute("listaSedes", paisesRepository.findAll());
            return "distribuidoras/editarFrm";
    }

   @GetMapping("/nuevo")
   public String nuevaDistribuidora(@ModelAttribute ("distribuidora") Distribuidoras distribuidoras, Model model){
        model.addAttribute("listaSedes", paisesRepository.findAll());
       return "distribuidoras/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora( @ModelAttribute("distribuidora") @Valid Distribuidoras distribuidoras, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("listaSedes", paisesRepository.findAll());
            return "distribuidoras/editarFrm";
        }else {

            if(distribuidoras.getIddistribuidora()==0){
                redirectAttributes.addFlashAttribute("msg","Distribuidora creada exitosamente");
            }else{
                redirectAttributes.addFlashAttribute("msg","Distribuidora actualizada exitosamente");
            }
            distribuidorasRepository.save(distribuidoras);
            return "redirect:/distribuidoras/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id,RedirectAttributes redirectAttributes){
        Optional<Distribuidoras> opt = distribuidorasRepository.findById(id);
        if (opt.isPresent()) {
            distribuidorasRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("msg","Distribuidora eliminada exitosamente");

        }
        return "redirect:/distribuidoras/lista";
    }

}
