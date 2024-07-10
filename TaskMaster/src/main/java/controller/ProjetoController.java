package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Projeto;
import domain.Usuario;
import jakarta.servlet.http.HttpSession;
import service.ProjetoService;



@Controller
public class ProjetoController {

	@Autowired
	private ProjetoService projetoService;
	
	

	/* METOD RETIRADO POR ENQUANTO */
	@GetMapping("/painel")
	public ModelAndView listProjetos(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView mv = new ModelAndView();

		if (usuarioLogado != null) {
			mv.addObject("projetos", projetoService.findAllByUsuarioId(usuarioLogado.getId()));
			mv.addObject("projeto", new Projeto());
			mv.setViewName("Painel-Principal(Dashboard)/dashboard.html");
			return mv;
		}
		return null;
	}

	@GetMapping("criar_projeto")
	public ModelAndView requestNewProjeto() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("projeto", new Projeto());
		mv.setViewName("Projeto/new_project.html");
		return mv;
	}

	@PostMapping("/create_project")
	public ModelAndView createProjeto(@RequestParam String nome, @RequestParam String descricao, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Projeto/new_project.html");
		mv.addObject("projeto", new Projeto());
		if (usuarioLogado != null) {
			Projeto projeto = projetoService.createProjeto(nome, descricao, usuarioLogado.getId());
			if (projeto != null) {
				mv.addObject("message", "Projeto criado com sucesso");
				
			} else {
				redirectAttributes.addFlashAttribute("error", "Erro ao criar projeto");
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "Usuário não está logado");
		}
		return mv;
	}
	

	
	
}
