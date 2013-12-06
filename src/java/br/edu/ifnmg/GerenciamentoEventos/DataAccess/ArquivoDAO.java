/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifnmg.GerenciamentoEventos.DataAccess;

import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.ArquivoRepositorio;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.*;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author petronio
 */
@Stateless
public class ArquivoDAO 
    extends DAOGenerico<Arquivo> 
    implements ArquivoRepositorio {
    
    public ArquivoDAO(){
        super(Arquivo.class);
    }

    @Override
    public List<Arquivo> Buscar(Arquivo filtro) {
        return IgualA("id", filtro.getId())
                .Like("nome", filtro.getNome())
                .IgualA("responsavel", filtro.getResponsavel())
                .Like("uri", filtro.getUri())
                .Buscar();
    }

    @Override
    public Arquivo Abrir(String uri) {
        return IgualA("uri", uri).Abrir();
    }
    
}
