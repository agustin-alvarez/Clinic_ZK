package es.clinica.veterinaria.mascotas;

import es.clinica.veterinaria.especies.EspecieDAO;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

public class MascotaViewModel {
        
    @Wire("#win-asig-masc")
    private Window win;
    private MascotaDAO eventDao = new MascotaDAO();
    private Mascota selectedEvent, newEvent = new Mascota();
    private String filterChip = "", filterNombre = "", filterPropietario="", filterSexo="", filterFnacimiento="", filterFdefuncion="",
                   filterPeso="", filterAltura="", filterRaza="", filterEspecie="", filterPelo="", filterFechaalta="", filterFechabaja=""; 
    private ListModelList<Mascota> mascotas;

//	private List<Mascota> currentMascota = new MascotaDAO().findAll();
//      private MascotaFilter mascotaFilter = new MascotaFilter();
//        public MascotaFilter getMascotaFilter() {
//            return mascotaFilter;
//        }
//
//        public void setMascotaFilter(MascotaFilter mascotaFilter) {
//            this.mascotaFilter = mascotaFilter;
//        }

//        @Command
//        @NotifyChange({"mascotaModel", "events", "currentMascota"})
//	public void changeFilter() {
//		currentMascota = eventDao.getFilterMascota(mascotaFilter);
//	}

//        public ListModel<Mascota> getMascotaModel() {
//            return new ListModelList<Mascota>(currentMascota);
//        }

    public Mascota getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Mascota selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Mascota getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Mascota newEvent) {
            this.newEvent = newEvent;
    }

    public List<Mascota> getEvents() {
            return eventDao.findAll();
    }

    @Command("add")
    @NotifyChange("mascotas")
    public void add() {
            eventDao.insert(this.newEvent);
            this.newEvent = new Mascota();
    }

    @Command("update")
    @NotifyChange("mascotas")
    public void update() {
            eventDao.update(this.selectedEvent);
    }

    @Command("delete")
    @NotifyChange("mascotas")
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                    eventDao.delete(this.selectedEvent);
                    mascotas.remove(this.selectedEvent);
                    this.selectedEvent = null;
            }
    }

    @Command
    public void closeThis() {
        win.detach();
    }

    public ListModelList<Mascota> getMascotas() 
    {
        if(mascotas == null) {
            mascotas = new ListModelList<Mascota>();//new ListModelList<Mascota>(getEvents()); //Para mostrar todo
        }
        
        return mascotas;
    }

    /* FILTRADO */

    public String getFilterChip() {
        return filterChip;
    }

    public String getFilterNombre() {
        return filterNombre;
    }

    @NotifyChange
    public void setFilterNombre(String filterNombre) {
        this.filterNombre = filterNombre;
    }

    @NotifyChange
    public void setFilterChip(String filterChip) {
        this.filterChip = filterChip;
    }

    public String getFilterPropietario() {
        return filterPropietario;
    }

    @NotifyChange
    public void setFilterPropietario(String filterPropietario) {
        this.filterPropietario = filterPropietario;
    }

    public String getFilterSexo() {
        return filterSexo;
    }

    @NotifyChange
    public void setFilterSexo(String filterSexo) {
        this.filterSexo = filterSexo;
    }

    public String getFilterFnacimiento() {
        return filterFnacimiento;
    }

    @NotifyChange
    public void setFilterFnacimiento(String filterFnacimiento) {
        this.filterFnacimiento = filterFnacimiento;
    }

    public String getFilterPeso() {
        return filterPeso;
    }

    @NotifyChange
    public void setFilterPeso(String filterPeso) {
        this.filterPeso = filterPeso;
    }

    public String getFilterAltura() {
        return filterAltura;
    }

    @NotifyChange
    public void setFilterAltura(String filterAltura) {
        this.filterAltura = filterAltura;
    }

    public String getFilterRaza() {
        return filterRaza;
    }

    @NotifyChange
    public void setFilterRaza(String filterRaza) {
        this.filterRaza = filterRaza;
    }

    public String getFilterEspecie() {
        return filterEspecie;
    }

    @NotifyChange
    public void setFilterEspecie(String filterEspecie) {
        this.filterEspecie = filterEspecie;
    }

    public String getFilterPelo() {
        return filterPelo;
    }

    @NotifyChange
    public void setFilterPelo(String filterPelo) {
        this.filterPelo = filterPelo;
    }

    public String getFilterFechaalta() {
        return filterFechaalta;
    }

    @NotifyChange
    public void setFilterFechaalta(String filterFechaalta) {
        this.filterFechaalta = filterFechaalta;
    }

    public String getFilterFdefuncion() {
        return filterFdefuncion;
    }
    
    @NotifyChange
    public void setFilterFdefuncion(String filterFdefuncion) {
        this.filterFdefuncion = filterFdefuncion;
    }

    public String getFilterFechabaja() {
        return filterFechabaja;
    }

    @NotifyChange
    public void setFilterFechabaja(String filterFechabaja) {
        this.filterFechabaja = filterFechabaja;
    }
    

    // Buscador para hacer el filtrado
    @Command 
    @NotifyChange( "mascotas")
    public void doSearch() 
    {
        System.out.println();
        mascotas.clear();
        List<Mascota> allEvents;// = eventDao.findAll(); //Para mostrar todo

        if((filterChip == null          ||  "".equals(filterChip))          && 
           (filterNombre == null        ||  "".equals(filterNombre))        &&
           (filterPropietario == null   ||  "".equals(filterPropietario))   && 
           (filterSexo == null          ||  "".equals(filterSexo))          &&
           (filterFnacimiento == null   ||  "".equals(filterFnacimiento))   && 
           (filterFdefuncion == null    ||  "".equals(filterFdefuncion))    && 
           (filterPeso == null          ||  "".equals(filterPeso))          &&
           (filterAltura == null        ||  "".equals(filterAltura))        && 
           (filterRaza == null          ||  "".equals(filterRaza))          &&
           (filterEspecie == null       ||  "".equals(filterEspecie))       && 
           (filterPelo == null          ||  "".equals(filterPelo))          &&
           (filterFechaalta == null     ||  "".equals(filterFechaalta))     &&
           (filterFechabaja == null     ||  "".equals(filterFechabaja))  ) {
                //mascotas.addAll(allEvents); //Para mostrar todo
        }
        else 
        {
            allEvents = eventDao.findAll();
            for (Iterator<Mascota> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
            {
                Mascota masc = it.next();
                String peso =  masc.getPeso() + "";
                String altura = masc.getAltura() + "";
                String propietario = masc.getCliente().getFullname();
                String raza = masc.getRaza().getRaza();
                String especie = masc.getEspecie().getEspecie();
                String fechabaja, fechadef;
                if(masc.getFechabaja() != null) {
                    fechabaja = masc.getFechabaja().toString();
                }
                else {
                    fechabaja = "";
                }
                if(masc.getDefuncion() != null) {
                    fechadef = masc.getDefuncion().toString();
                }
                else {
                    fechadef = "";
                }

                if ((masc.getChip().toLowerCase().startsWith(filterChip.toLowerCase())  )      &&
                    (masc.getNombre().toLowerCase().startsWith(filterNombre.toLowerCase()) )   &&
                    (masc.getSexo().toLowerCase().startsWith(filterSexo.toLowerCase())  )      &&
                    (masc.getNacimiento().toString().toLowerCase().startsWith(filterFnacimiento.toLowerCase())  ) &&
                    (fechadef.toLowerCase().startsWith(filterFdefuncion.toLowerCase())  ) &&
                    (masc.getPelo().toLowerCase().startsWith(filterPelo.toLowerCase())  )      &&
                    (masc.getFechaalta().toString().toLowerCase().startsWith(filterFechaalta.toLowerCase())  ) &&
                    (fechabaja.toLowerCase().startsWith(filterFechabaja.toLowerCase())  ) &&
                    (peso.toLowerCase().startsWith(filterPeso.toLowerCase())  )                &&
                    (altura.toLowerCase().startsWith(filterAltura.toLowerCase())  )            &&
                    (propietario.toLowerCase().startsWith(filterPropietario.toLowerCase())  )  &&
                    (raza.toLowerCase().startsWith(filterRaza.toLowerCase())  )                &&
                    (especie.toLowerCase().startsWith(filterEspecie.toLowerCase())  )
                   ) {
                    mascotas.add(masc);
                }
            }
        }
    }

    /* FIN FILTRADO */

    @Command
    public void showHistorial()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedMascota", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("mascota-historial.zul", center, map);
    }

    @Command
    public void showMascota()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedMascota", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("mascota.zul", center, map);
    }
        
    @Command("mostrarPeso")
    public void mostrarPeso()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedMascota", selectedEvent );
        Executions.createComponents("mascota-peso.zul", null, map);
    }
       
}
