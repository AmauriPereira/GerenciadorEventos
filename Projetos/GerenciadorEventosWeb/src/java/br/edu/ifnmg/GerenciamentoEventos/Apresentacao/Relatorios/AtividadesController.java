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
package br.edu.ifnmg.GerenciamentoEventos.Apresentacao.Relatorios;

import br.edu.ifnmg.GerenciamentoEventos.Aplicacao.ControllerBaseRelatorio;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Atividade;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.AtividadeTipo;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.AtividadeRepositorio;
import javax.inject.Named;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author petronio
 */
@Named(value = "atividadesDoEventoController")
@RequestScoped
public class AtividadesController
        extends ControllerBaseRelatorio<Atividade>
        implements Serializable {

    /**
     * Creates a new instance of FuncionarioBean
     */
    public AtividadesController() {
        setArquivoSaida("AtividadesDoEvento");
        setRelatorio("Atividades.jasper");        
        filtro = new Atividade();
    }

    @EJB
    AtividadeRepositorio daoAtividade;

    Atividade filtro;

    @Override
    protected Map<String, Object> carregaParametros() {
        try {

            Map<String, Object> tmp = getParametrosComuns();
            tmp.put("data", new Date());
            return tmp;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ListaPresencaEventoController.class.getName()).log(Level.SEVERE, null, ex);
            return new HashMap<>();
        }
    }

    @Override
    public List<Atividade> getDados() {
        return daoAtividade
                .IgualA("tipo", filtro.getTipo())
                .IgualA("evento", filtro.getEvento())
                
                // .Join("atividade", "a")
                 .Ordenar("inicio", "ASC")
                .Buscar();
    }

    public Atividade getFiltro() {
        return filtro;
    }

    public void setFiltro(Atividade filtro) {
        this.filtro = filtro;
    }

}
