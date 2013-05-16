/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.servicios;

import es.clinica.veterinaria.iva.Iva;
import es.clinica.veterinaria.iva.IvaDAO;
import es.clinica.veterinaria.servicio_familia.ServicioFamilia;
import es.clinica.veterinaria.servicio_familia.ServicioFamiliaDAO;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.ListModelList;

/**
 * @author SaRCo
 */
public class ServicioViewModel {
	
    private ServicioDAO eventDao = new ServicioDAO();
    private Servicio selectedEvent, newEvent = new Servicio();
    private Set <Servicio> selectedItems;
    private ServicioFamiliaDAO servfamiliaDao = new ServicioFamiliaDAO();
    private IvaDAO ivaDao = new IvaDAO();
    private ServicioFamilia auxServFamilia = new ServicioFamilia();
    private Iva auxIva = new Iva();
    private ListModelList<Servicio> events;

    private String filterCodigo = "",
                   filterNombre = "",
                   filterFamilia = "",
                   filterIva = "";
    
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                          @ExecutionArgParam("selectedServicio") Servicio selectedEvent) 
    {
        Selectors.wireComponents(view, this, false);
        this.selectedEvent = selectedEvent;
        if(this.selectedEvent != null){
            auxServFamilia = this.selectedEvent.getFamilia();
            auxIva = this.selectedEvent.getIva();
        }
    }
    
    public ListModelList<Servicio> getEvents() {
        if(events == null) {
            events = new ListModelList<Servicio>();//new ListModelList<Servicio>(eventDao.findAll());
        }
        events.setMultiple(true);
        return events;
    }
    
//    public List<Servicio> getEvents() {
//        return eventDao.findAll();
//    }
    
    public Servicio getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Servicio selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Servicio getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Servicio newEvent) {
            this.newEvent = newEvent;
    }

    public List<ServicioFamilia> getFamilias() {
            return servfamiliaDao.findAll();
    }
    
    public List<Iva> getIvas() {
        return ivaDao.findAll();
    }
    
    public Set getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(Set<Servicio> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getFilterCodigo() {
        return filterCodigo;
    }

    @NotifyChange
    public void setFilterCodigo(String filterCodigo) {
        this.filterCodigo = filterCodigo;
    }

    public String getFilterNombre() {
        return filterNombre;
    }

    @NotifyChange
    public void setFilterNombre(String filterNombre) {
        this.filterNombre = filterNombre;
    }

    public String getFilterFamilia() {
        return filterFamilia;
    }

    @NotifyChange
    public void setFilterFamilia(String filterFamilia) {
        this.filterFamilia = filterFamilia;
    }

    public String getFilterIva() {
        return filterIva;
    }

    @NotifyChange
    public void setFilterIva(String filterIva) {
        this.filterIva = filterIva;
    }

    
    @Command("add")
    @NotifyChange({"events", "productos"})
    public void add() {

        String sbfamilia = this.newEvent.getFamilia().getNombre().substring(0, 3),
               sbnombre = this.newEvent.getNombre().substring(0, 3);
        String codigo = (sbfamilia + sbnombre ).toLowerCase();

        List<Servicio> prodlist = eventDao.findAll("SELECT * FROM zk_servicio WHERE codigo LIKE '"+codigo+"%' ORDER BY id DESC");
        if(prodlist.isEmpty()){
                this.newEvent.setCodigo(codigo+"001");
                eventDao.insert(this.newEvent);
        }
        else{
            Servicio prodaux = prodlist.get(0);
            Formatter fmt = new Formatter();
            int num = Integer.parseInt(prodaux.getCodigo().substring(6, 9));
            ++num ;
            fmt.format("%03d",num);
            this.newEvent.setCodigo(codigo+fmt);
            eventDao.insert(this.newEvent);
        }
        this.newEvent = new Servicio();
    }

    @Command("update")
    @NotifyChange("events")
    public void update() {
        if(this.selectedEvent.getIva() == null){
            this.selectedEvent.setIva(auxIva);
        }
            eventDao.update(this.selectedEvent);
    }

    @Command("delete")
    @NotifyChange({"events", "selectedEvent"})
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                    eventDao.delete(this.selectedEvent);
                    this.selectedEvent = null;
            }
    }
        
    @Command("modificarServicio")
    @NotifyChange({"events", "servicios"})
    public void modificarServicio()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedServicio", selectedEvent );
        Executions.createComponents("../servicios/servicio-modificar.zul", null, map);
    }
    
    @Command
    public void send() {
        Map args = new HashMap();
        args.put("returnServicio", this.selectedItems);
        BindUtils.postGlobalCommand(null, null, "refreshServicios", args);
    }
    
    @Command("addservicio")
    @NotifyChange({"events", "servicios"})
    public void addservicio()
    {
        Executions.createComponents("../servicios/servicio-nuevo.zul", null, null);
    }
    
    @Command 
    @NotifyChange("productos")
    public void doSearch() 
    {
        events.clear();
        List<Servicio> allEvents; // = eventDao.findAll();

        if((filterCodigo == null  || "".equals(filterCodigo))  && 
           (filterNombre == null  || "".equals(filterNombre))  &&
           (filterFamilia == null || "".equals(filterFamilia)) && 
           (filterIva == null     || "".equals(filterIva))) {
//                events.addAll(allEvents);
        }
        else 
        {
            allEvents = eventDao.findAll();//Quitar para mostrar todo
            for (Iterator<Servicio> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
            {
                Servicio prod = it.next();
                String iva = prod.getIva().getValor() + "";
                if ((prod.getCodigo().toLowerCase().startsWith(filterCodigo.toLowerCase())   ) &&
                    (prod.getNombre().toLowerCase().startsWith(filterNombre.toLowerCase())  )  &&
                    (prod.getFamilia().getNombre().toLowerCase().startsWith(filterFamilia.toLowerCase())   ) &&
                    (iva.toLowerCase().startsWith(filterIva.toLowerCase())   )) {
                        events.add(prod);
                }
            }
        }
    }
}

