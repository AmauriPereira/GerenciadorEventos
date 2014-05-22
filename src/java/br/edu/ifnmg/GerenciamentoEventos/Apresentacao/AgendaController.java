/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.GerenciamentoEventos.Apresentacao;

import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Atividade;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Evento;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.AtividadeRepositorio;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.EventoRepositorio;
import br.edu.ifnmg.GerenciamentoEventos.Aplicacao.ControllerBaseEntidade;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author petronio
 */
@Named(value = "agendaController")
@RequestScoped
public class AgendaController
        extends ControllerBaseEntidade<Evento>
        implements Serializable {

    private ScheduleModel eventModel;

    /**
     * Creates a new instance of FuncionarioBean
     */
    public AgendaController() {

    }

    @EJB
    EventoRepositorio dao;

    @EJB
    AtividadeRepositorio daoA;

    Evento padrao;

    public void checaEventoPadrao() {
        String evt = getConfiguracao("EVENTO_PADRAO");
        if (evt != null) {
            padrao = dao.Abrir(Long.parseLong(evt));
        }
    }

    @PostConstruct
    public void init() {
        setRepositorio(dao);
    }

    private ScheduleEvent add(Evento e) {
        return new DefaultScheduleEvent(e.getNome(), e.getInicio(), e.getTermino());
    }

    private ScheduleEvent addInscricao(Evento e) {
        return new DefaultScheduleEvent("Inscrição " + e.getNome(), e.getInicioInscricao(), e.getTerminoInscricao());
    }

    private ScheduleEvent add(Atividade a) {
        return new DefaultScheduleEvent(a.getNome(), a.getInicio(), a.getTermino());
    }

    private ScheduleEvent addInscricao(Atividade a) {
        return new DefaultScheduleEvent("Inscrição " + a.getNome(), a.getInicioInscricao(), a.getTerminoInscricao());
    }

    public ScheduleModel getEventModel() {
        if (eventModel == null) {
            checaEventoPadrao();
            eventModel = new DefaultScheduleModel();
            for (Evento evt : dao.BuscarEventosDoUsuario(getUsuarioCorrente())) {
                eventModel.addEvent(add(evt));
                if (evt.isNecessitaInscricao()) {
                    eventModel.addEvent(addInscricao(evt));
                }
            }
            for (Atividade atividade : daoA.BuscarAtividadesDoUsuario(getUsuarioCorrente())) {
                eventModel.addEvent(add(atividade));
                if (atividade.isNecessitaInscricao()) {
                    eventModel.addEvent(addInscricao(atividade));
                }
            }
        }
        return eventModel;
    }


    @Override
    public void salvar() {
    }

    @Override
    public String apagar() {
        return "";
    }

    @Override
    public String abrir() {
        return "";
    }

    @Override
    public String cancelar() {
        return "";
    }

    @Override
    public void limpar() {
        setEntidade(new Evento());
    }

    @Override
    public String novo() {
        limpar();
        return "";
    }


}
