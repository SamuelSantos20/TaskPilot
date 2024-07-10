package controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Comentario;
import domain.Tarefa;
import domain.Usuario;
import jakarta.servlet.http.HttpSession;
import service.ComentarioService;
import service.TarefaService;

@Controller
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;

    @Autowired
    private TarefaService tarefaService;

    @PostMapping("/add_comment/{tarefa_id}")
    public String createComent(@PathVariable("tarefa_id") Long tarefa_id, Comentario comentario, HttpSession session, HttpSession sesao) {

        // Buscar a tarefa pelo ID
        Optional<Tarefa> tarefaOptional = tarefaService.listarPorId(tarefa_id);

        if (!tarefaOptional.isPresent()) {
            return "error";
        }

        Tarefa tarefa = tarefaOptional.get();
        sesao.setAttribute("tarefa", tarefa);

        comentario.setTarefa(tarefa);

        // Adicionar o comentário
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario != null) {
            comentarioService.save(comentario.getConteudo(), usuario.getId(), usuario.getNome(), tarefa.getId());
        } 

        return "redirect:/listar_comentarios";
    }

    // Método de retorno para a página de informações da tarefa
    @GetMapping("/listar_comentarios")
    public ModelAndView ListComentAndTask(HttpSession sesao) {
        ModelAndView mv = new ModelAndView();
        Tarefa tarefa = (Tarefa) sesao.getAttribute("tarefa");

        if (tarefa == null) {
            mv.setViewName("error");
            return mv;
        }

        mv.addObject("lista_comentarios", comentarioService.ListarPorTarefaId(tarefa.getId()));
        mv.addObject("tarefas", tarefa);
        mv.addObject("tarefa", tarefa);
        mv.addObject("comentario", new Comentario());
        mv.setViewName("Tarefas/task");

        return mv;
    }

    // Método para deletar comentários
    @PostMapping("/delete_comment/{comentario_id}")
    public String deleteComment(@PathVariable("comentario_id") Long comentarioId) {
        comentarioService.excluir(comentarioId);
        return "redirect:/List_informacoes";
    }
}
