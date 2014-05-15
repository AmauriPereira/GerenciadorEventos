/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos;

import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Atividade;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Evento;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Pessoa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author petronio
 */
@Local
public interface PessoaRepositorio extends Repositorio<Pessoa> {
    public Pessoa Abrir(String login);
    public Pessoa AbrirPorCPF(String cpf);
    public List<Pessoa> Buscar(Evento e);
    public List<Pessoa> Buscar(Atividade a);
}
