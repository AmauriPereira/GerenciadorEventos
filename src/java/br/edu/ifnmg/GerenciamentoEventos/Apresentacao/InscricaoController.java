/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.GerenciamentoEventos.Apresentacao;



import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Inscricao;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Evento;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.InscricaoItem;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Lancamento;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.InscricaoRepositorio;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.EventoRepositorio;
import br.edu.ifnmg.GerenciamentoEventos.Aplicacao.ControllerBaseEntidade;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.InscricaoCategoria;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.InscricaoStatus;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Pessoa;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author petronio
 */
@Named(value = "inscricaoController")
@SessionScoped
public class InscricaoController
        extends ControllerBaseEntidade<Inscricao>
        implements Serializable {

    /**
     * Creates a new instance of FuncionarioBean
     */
    public InscricaoController() {
        id = 0L;
        setEntidade(new Inscricao());
        setFiltro(new Inscricao());
        item = new InscricaoItem();
    }
    
    Evento padrao;
    
    @EJB
    InscricaoRepositorio dao;
    
    @EJB
    EventoRepositorio evtDAO;
    
    @Inject
    LancamentoController lancamentoCtl;
    
    InscricaoItem item;
    
    @PostConstruct
    public void init() {
        setRepositorio(dao);
        checaEventoPadrao();
    }
    
    public void checaEventoPadrao() {
        String evt = getConfiguracao("EVENTO_PADRAO");
        if (evt != null && padrao == null) {
            padrao = evtDAO.Abrir(Long.parseLong(evt));
            if(getEntidade().getEvento() == null)
                getEntidade().setEvento(padrao);
            if(getFiltro().getEvento() == null)
                getFiltro().setEvento(padrao);
        }
    }

   

    @Override
    public void filtrar() {
        checaEventoPadrao();
        
    }

    @Override
    public void salvar() {
        
        SalvarEntidade();
        
        // atualiza a listagem
        filtrar();
    }

    @Override
    public String apagar() {
        ApagarEntidade();
        filtrar();
        return "listagemInscricoes.xtml";
    }

    @Override
    public String abrir() {
        setEntidade(dao.Abrir(id));
        return "editarInscricao.xhtml";
    }

    @Override
    public String cancelar() {
        return "listagemInscricoes.xhtml";
    }

    @Override
    public void limpar() {
        checaEventoPadrao();
        setEntidade(new Inscricao());
    }

    @Override
    public String novo() {
        limpar();
        return "editarInscricao.xhtml";
    }


    public InscricaoItem getItem() {
        return item;
    }

    public void setItem(InscricaoItem item) {
        this.item = item;
    }
       
    public void addItem() {
        Refresh();
        item.setEvento(entidade.getEvento());
        Rastrear(item);
        entidade.add(item);
        SalvarAgregado(item);
        item = new InscricaoItem();
    }
    
    public void removeItem() {
        Refresh();
        entidade.remove(item);
        RemoverAgregado(item);
        item = new InscricaoItem();
    }
    
    public String gerarLancamento() {
        Lancamento l = entidade.criarLancamento(getUsuarioCorrente());
        Rastrear(l);
        Rastrear(entidade);
        dao.Salvar(entidade);
        lancamentoCtl.setEntidade(l);
        return "editarLancamento.xhtml";
    }

    public LancamentoController getLancamentoCtl() {
        return lancamentoCtl;
    }

    public void setLancamentoCtl(LancamentoController lancamentoCtl) {
        this.lancamentoCtl = lancamentoCtl;
    }

    public InscricaoStatus[] getStatus() {
        return InscricaoStatus.values();
    }
    
    public InscricaoCategoria[] getCategorias() {
        return InscricaoCategoria.values();
    }
    
    public List<Pessoa> getPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();
        for(Inscricao i : getListagem())
            pessoas.add(i.getPessoa());
        
        return pessoas;
    }
    
    
}
