/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import model.Docente;
import util.DocenteDataModel;

/**
 *
 * @author charles
 */
@Named(value = "docenteController")
@SessionScoped
public class DocenteController implements Serializable {

    Docente docente;
    @EJB
    private facade.DocenteFacade docenteFacade;
    private DocenteDataModel docenteDataModel = null;
   public DocenteController(){
       docente = new Docente();
   }

    public DocenteDataModel getDocenteDatamodel() {
        if(docenteDataModel == null){
            List<Docente> docentes = docenteFacade.findAll();
            docenteDataModel = new DocenteDataModel(docentes);
        }
        return docenteDataModel;
    }

    public void setDocenteDataModel(DocenteDataModel itens) {
        this.docenteDataModel = itens;
    }

    
    
    
    public Docente getDocente() {
        //docente = new Docente();
        //docente.setNome("TesteDocente");
//        docenteFacade.save(docente);
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public void teste(ActionEvent evt) {

        int a = 2;
        System.out.println("dsfhdghdghgdhdhgddfhdhdfhghd");
       
    }

    public void salvarDocente(ActionEvent evt) {
        docenteFacade.save(docente);
        docente = new Docente();
        docenteDataModel = null;
    }

    private Docente getDocente(java.lang.Long id) {
        return docenteFacade.find(id);
    }

    @FacesConverter(forClass = Docente.class)
    public static class DocenteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DocenteController controller = (DocenteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "docenteController");
            return controller.getDocente(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Docente) {
                Docente o = (Docente) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Docente.class.getName());
            }
        }
    }
}
