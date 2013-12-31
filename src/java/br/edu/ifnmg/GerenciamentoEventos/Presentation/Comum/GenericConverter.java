/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.GerenciamentoEventos.Presentation.Comum;


import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Entidade;
import br.edu.ifnmg.GerenciamentoEventos.DomainModel.Servicos.Repositorio;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author petronio
 * @param <TObj>
 * @param <TDAO>
 */
public class GenericConverter<TObj extends Entidade, TDAO extends Repositorio<TObj>> implements Converter {

    private TDAO repositorio;

    protected void setRepositorio(TDAO repositorio) {
        this.repositorio = repositorio;
    }
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("") || value == null) {
            return null;
        } else {
            try {
                long id = Long.parseLong(value);
                TObj obj = repositorio.Abrir(id);

                return obj;

            } catch (NumberFormatException exception) {
                //throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));  
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (value == null || value.equals("")) {
                return "";
            } else {
                return String.valueOf(((TObj) value).getId());
            }
        } catch (Exception ex) {
            return "";
        }
    }
}