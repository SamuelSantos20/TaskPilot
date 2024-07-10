package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Comentario;
import domain.Tarefa;
import jakarta.servlet.http.HttpSession;
import service.ComentarioService;
import service.ProjetoService;
import service.TarefaService;


@Controller
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;

	@Autowired
	ProjetoService projetoService;
	
	@Autowired
	private ComentarioService comentarioService;

	// FUNCAO PARA RESGATAR O ID DO PROJETO E ENVIAR PARA O METODO DE LISTA DE
	// TAREFAS
	@GetMapping("/Listar_tarefas_projeto/{id_projeto}")
	public String preListarTarefas(@PathVariable("id_projeto") Long id_projeto, HttpSession sesao) {
		sesao.setAttribute("lista_de_Tarefas", id_projeto);
		return "redirect:/listar_tarefas";
	}

	// FUCAO PARA LISTAR AS TAREFAS
	@GetMapping("/listar_tarefas")
	public ModelAndView Listar_Tarefas(HttpSession sesao) {

		Long id = (Long) sesao.getAttribute("lista_de_Tarefas");
		ModelAndView mv = new ModelAndView();

		List<Tarefa> tarefa = tarefaService.ListarProjetoId(id);
		mv.addObject("tarefas", tarefa);
		mv.addObject("tarefa", new Tarefa());
		mv.setViewName("Projeto/project.html");
		return mv;

	}
	
	@GetMapping("/new_task")
	public ModelAndView preNewTask() {
		ModelAndView mv = new ModelAndView();

		mv.addObject("task", new Tarefa());
		mv.addObject("projects", projetoService.listarTodos());
        mv.setViewName("Tarefas/new_task.html");
		return mv;
		
	}

	@PostMapping("/tasks/create")
	public ModelAndView NewTask(Tarefa tarefa) {
		ModelAndView mv = new ModelAndView();
		tarefaService.save(tarefa);
		mv.addObject("task", new Tarefa());
		mv.setViewName("redirect:/Listar_tarefas_projeto/new_task");
		return mv;
	}

	// METODO PARA REGATAR O ID DA TAREFA PARA LISTAR SUAS INFORMAÇÕES
	@GetMapping("/Tarefas_valores/{id}")
	public String ListTasks(@PathVariable("id") Long id, HttpSession sesao) {
		sesao.setAttribute("tarefa_id", id);

		return "redirect:/List_informacoes";
	}

	@GetMapping("/List_informacoes")
	public ModelAndView List_informacoes(HttpSession sesao) {

		Long tarefa_Id = (Long) sesao.getAttribute("tarefa_id");
		ModelAndView mv = new ModelAndView();

		Optional<Tarefa> tarefa = tarefaService.listarPorId(tarefa_Id);
		mv.addObject("lista_comentarios", comentarioService.ListarPorTarefaId(tarefa_Id));
		mv.addObject("tarefas", tarefa.get());
		mv.addObject("tarefa", new Tarefa());
		mv.addObject("comentario", new Comentario());
		mv.setViewName("Tarefas/task.html");
		return mv;

	}

}
