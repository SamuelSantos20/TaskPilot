package dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Projeto;



@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

	List<Projeto> findByUsuarioId(Long usuarioId);

}
