package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ProjetoRepository;
import dao.UsuarioRepository;
import domain.Projeto;
import domain.Usuario;

@Service
@org.springframework.transaction.annotation.Transactional(readOnly = false)
public class ProjetoService {
	@Autowired
	ProjetoRepository projetoRepository;

	@Autowired
	UsuarioRepository usuarioRepository;
	public void save(Projeto projeto) {

		projetoRepository.save(projeto);

	}

	public void editar(Projeto projeto) {

		projetoRepository.save(projeto);
	}

	public void excluir(Long id) {

		projetoRepository.deleteById(id);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Optional<Projeto> listarPorId(Long id) {
		return projetoRepository.findById(id);

	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Projeto> listarTodos() {

		return projetoRepository.findAll();
	}

	
	
	 public Projeto createProjeto(String nome, String descricao, Long usuarioId) {
	        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
	        if (usuario.isPresent()) {
	            Projeto projeto = new Projeto();
	            projeto.setNome(nome);
	            projeto.setDescricao(descricao);
	            projeto.setUsuario(usuario.get());
	            return projetoRepository.save(projeto);
	        }
	        return null; // ou lançar uma exceção
	    }

	 public List<Projeto> findAllByUsuarioId(Long usuarioId) {
	        return projetoRepository.findByUsuarioId(usuarioId);
	    }

	public Optional<Projeto> findById(Long id_projeto) {
		// TODO Auto-generated method stub
		return projetoRepository.findById(id_projeto);
	}

	}

