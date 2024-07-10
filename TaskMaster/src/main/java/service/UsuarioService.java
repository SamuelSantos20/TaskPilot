package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UsuarioRepository;
import domain.Usuario;

@Service
@org.springframework.transaction.annotation.Transactional(readOnly = false)
public class UsuarioService {

	@Autowired
	UsuarioRepository usuario;

	public void save(Usuario usuario) {

		this.usuario.save(usuario);
	}

	public void editar(Usuario usuario) {
		this.usuario.save(usuario);

	}

	public void excluir(Long id) {

		usuario.deleteById(id);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Optional<Usuario> listarById(Long id) {

		return usuario.findById(id);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Usuario> ListarTodos() {

		return this.usuario.findAll();
	}
	
	public Optional<Usuario> login(String email, String senha) {
        return usuario.findByEmailAndSenha(email, senha);
    }

    public Usuario logon(String email, String senha , String nome) {
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setSenha(senha);
        user.setNome(nome);
        return usuario.save(user);
    }
    
   
}
