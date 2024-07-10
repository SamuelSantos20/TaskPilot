package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import dao.ComentarioRepository;
import domain.Comentario;
import domain.Tarefa;
import domain.Usuario;

@Service
@org.springframework.transaction.annotation.Transactional(readOnly = false)
public class ComentarioService {

	@Autowired
	  private ComentarioRepository comentarioRepository;
	

	public void save(String  comentario ,Long id_usuario , String autor , Long id_tarefa) {
		Usuario usuario = new Usuario();
		Tarefa tarefa = new Tarefa();
		Comentario comentario2 = new Comentario();
		
		usuario.setId(id_usuario);
		tarefa.setId(id_tarefa);
		comentario2.setConteudo(comentario);
		comentario2.setAutor(autor);
		comentario2.setTarefa(tarefa);
		comentario2.setUsuario(usuario);
		
		comentarioRepository.save(comentario2);

	}

	public void editar(Comentario comentario) {

		comentarioRepository.save(comentario);
	}

	public void excluir(Long id) {

		comentarioRepository.deleteById(id);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Optional<Comentario> listarPorId(Long id) {

		return comentarioRepository.findById(id);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Comentario> listarTodos() {
		return comentarioRepository.findAll();
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Comentario> ListarPorTarefaId(Long id){
		
		return 		comentarioRepository.findByTarefaId(id);
	}
}
