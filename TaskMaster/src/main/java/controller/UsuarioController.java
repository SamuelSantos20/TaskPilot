package controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Projeto;
import domain.Usuario;
import jakarta.servlet.http.HttpSession;
import service.ProjetoService;
import service.UsuarioService;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ProjetoService projetoService;

	@GetMapping("/login")
	public ModelAndView requestLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login-Logout/login");
		mv.addObject("Usuarios", new Usuario());
		return mv;
	}

	@GetMapping("/registrar")
	public ModelAndView requestRegistro() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Registro/register");
		mv.addObject("register", new Usuario());
		return mv;
	}

	@GetMapping("/Perfil")
	public ModelAndView requestProfile(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> usuario = Optional.of((Usuario) session.getAttribute("usuarioLogado"));
		mv.addObject("usuario_credencias", usuario.get());
		mv.addObject("Usuarios", new Usuario());
		mv.addObject("projetos", projetoService.findAllByUsuarioId(usuario.get().getId()));
		mv.addObject("projeto", new Projeto());
		mv.setViewName("Perfil/profile");
		return mv;
	}

	@PostMapping("/logar")
	public ModelAndView login(@RequestParam String email, @RequestParam String senha, HttpSession session,
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();

		Optional<Usuario> usuarioOptional = usuarioService.login(email, senha);

		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			session.setAttribute("usuarioLogado", usuario);
			mv.addObject("usuario_credencias", usuario);
			mv.addObject("projetos", projetoService.findAllByUsuarioId(usuario.getId()));
			mv.addObject("projeto", new Projeto()); // Se necessário
			mv.setViewName("Perfil/profile");
		} 
		else {
			mv.addObject("error", "Credenciais inválidas");
			mv.addObject("Usuarios", new Usuario());
			mv.setViewName("Login-Logout/login");
		}

		return mv;
	}

	@PostMapping("/registrado")
	public ModelAndView logon(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		usuarioService.save(usuario);
		mv.addObject("message", "Usuário registrado com sucesso");
		mv.setViewName("Registro/register");
		mv.addObject("register", new Usuario());
		return mv;
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login-Logout/logout");
		return mv;
	}

	@PostMapping("/update_profile/editar")
	public ModelAndView editProfile(Usuario usuario, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		usuarioService.editar(usuario);
		session.setAttribute("usuarioLogado", usuario);
		mv.addObject("usuario_credencias", usuario);
		mv.addObject("projetos", projetoService.findAllByUsuarioId(usuario.getId()));
		mv.setViewName("redirect:/Perfil");
		return mv;
	}
	
	
	

}
