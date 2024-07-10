package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.TarefaRepository;
import domain.Tarefa;

@Service
@org.springframework.transaction.annotation.Transactional(readOnly = false)
public class TarefaService {

	@Autowired
	TarefaRepository tarefaRepository;

	public void save(Tarefa tarefa) {

		tarefaRepository.save(tarefa);
	}

	public void editar(Tarefa tarefa) {

		tarefaRepository.save(tarefa);
	}

	public void excluir(Long id) {

		tarefaRepository.deleteById(id);
	}

	public Optional<Tarefa> listarPorId(Long id) {

		return tarefaRepository.findById(id);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Tarefa> listarTodos() {

		return tarefaRepository.findAll();

	}
	
	public List<Tarefa> ListarProjetoId(Long id){
		return tarefaRepository.findByProjetoId(id);
	}

}
