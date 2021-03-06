/*
 *   This file is part of SGEA - Sistema de Gestão de Eventos Acadêmicos - TADS IFNMG Campus Januária.
 *
 *   SGEA is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   SGEA is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SGEA.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.edu.ifnmg.GerenciamentoEventos.Apresentacao;

import br.edu.ifnmg.DomainModel.Configuracao;
import br.edu.ifnmg.DomainModel.Services.ConfiguracaoRepositorio;
import br.edu.ifnmg.GerenciamentoEventos.Aplicacao.ControllerBaseEntidade;
import br.edu.ifnmg.DomainModel.Pessoa;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.PessoaRepositorioLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author petronio
 */
@Named(value = "configuracaoController")
@RequestScoped
public class ConfiguracaoController
        extends ControllerBaseEntidade<Configuracao>
        implements Serializable {

    /**
     * Creates a new instance of FuncionarioBean
     */
    public ConfiguracaoController() {
    }

    @EJB
    ConfiguracaoRepositorio dao;

    @EJB
    PessoaRepositorioLocal pessoaDAO;

    @PostConstruct
    public void init() {
        setRepositorio(dao);
        setPaginaEdicao("editarConfiguracao.xhtml");
        setPaginaListagem("listagemConfiguracoes.xhtml");
    }

    public List<Configuracao> autoCompleteConfiguracao(String query) {
        Configuracao i = new Configuracao();
        i.setChave(query);
        return dao.Buscar(i);
    }

    @Override
    public Configuracao getFiltro() {
        if (filtro == null) {
            filtro = new Configuracao();
            filtro.setChave(getSessao("cnfctrl_chave"));
            filtro.setUsuario((Pessoa) getSessao("cnfctrl_usuario", pessoaDAO));
        }
        return filtro;
    }

    @Override
    public void setFiltro(Configuracao filtro) {
        this.filtro = filtro;
        if (filtro != null) {
            setSessao("cnfctrl_chave", filtro.getChave());
            setSessao("cnfctrl_usuario", filtro.getUsuario());
        }
    }

    @Override
    public void salvar() {

        Rastrear(getEntidade());

        // salva o objeto no BD
        if (dao.Set(getEntidade().getUsuario(), getEntidade().getChave(), getEntidade().getValor())) {

            setId(getEntidade().getId());

            Mensagem("Sucesso", "Registro salvo com sucesso!");
            AppendLog("Editou a entidade " + getEntidade().getClass().getSimpleName() + " " + getEntidade().getId() + "(" + getEntidade().toString() + ")");
        } else {
            MensagemErro("Falha", "Registro não foi salvo! Consulte o Log ou o administrador do sistema!");
            AppendLog("Falha ao editar a entidade " + getEntidade().getClass().getSimpleName() + " " + getEntidade().getId() + "(" + getEntidade().toString() + ")" + ": " + repositorio.getErro().getMessage());
        }

        // atualiza a listagem
        filtrar();
    }

    @Override
    public void limpar() {

        setEntidade(new Configuracao());
    }

}
