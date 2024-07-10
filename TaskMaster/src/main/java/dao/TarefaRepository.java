package dao;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Tarefa;



@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
List<Tarefa> findByProjetoId(Long id);

}
