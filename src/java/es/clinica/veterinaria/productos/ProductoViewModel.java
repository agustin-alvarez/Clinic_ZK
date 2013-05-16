package es.clinica.veterinaria.productos;

import es.clinica.veterinaria.albaranes.FirstPdf;
import es.clinica.veterinaria.albaranes.MyFirstTable;
import es.clinica.veterinaria.iva.Iva;
import es.clinica.veterinaria.iva.IvaDAO;
import es.clinica.veterinaria.producto_familia.ProductoFamilia;
import es.clinica.veterinaria.producto_familia.ProductoFamiliaDAO;
import es.clinica.veterinaria.proveedores.Proveedor;
import es.clinica.veterinaria.proveedores.ProveedorDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
            
public class ProductoViewModel {
        @Wire
        Image imgText, mainImage;
        
//        @Wire("#winbuscarproducto")
//        private Window winbuscarproducto;
        private String filterCodigo = "",
                       filterNombre = "",
                       filterFamilia = "",
                       filterProveedor = "",
                       filterIva = "",
                       filterStock = "",
                       filterFechaalta = "";
        
        private ProductoFamiliaDAO prodfamiliaDao = new ProductoFamiliaDAO();
        private ProveedorDAO proveedorDao = new ProveedorDAO();
        private IvaDAO ivaDao = new IvaDAO();
        private ListModelList<Producto> productos, prodprov;
        private List<Producto> events;
	private ProductoDAO eventDao = new ProductoDAO();
	
        private Set <Producto> selectedItems;
	private Producto selectedEvent, newEvent = new Producto();
        private Proveedor selectedProveedor =  new Proveedor();
	
        private ProductoFamilia auxProdFamilia = new ProductoFamilia();
        private Iva auxIva = new Iva();
        private Media media;
        private final DataSourceProducto ds = DataSourceProducto.INSTANCE;
        

        @Init
        public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                              @ExecutionArgParam("selectedProducto") Producto selectedEvent) 
        {
            Selectors.wireComponents(view, this, false);
            this.selectedEvent = selectedEvent;
//            productos = getProductos();
            if(this.selectedEvent != null){
                auxProdFamilia = this.selectedEvent.getFamilia();
                auxIva = this.selectedEvent.getIva();
            }
        }

        public Media getMedia() {
            return media;
        }
 
	public Producto getSelectedEvent() {
            return selectedEvent;
	}

	public void setSelectedEvent(Producto selectedEvent) {
            this.selectedEvent = selectedEvent;
	}

        public Set getSelectedItems() {
            return selectedItems;
        }

        public void setSelectedItems(Set<Producto> selectedItems) {
            this.selectedItems = selectedItems;
        }
        
	public Producto getNewEvent() {
            return newEvent;
	}

	public void setNewEvent(Producto newEvent) {
            this.newEvent = newEvent;
	}

	public List<Producto> getEvents() {
            if(events == null) {
                events = new ListModelList<Producto>();//new ListModelList<Producto>(eventDao.findAll());
            }
            return events;
	}
        
         public ListModelList<Producto> getProductos() {
            if(productos == null) {
                productos = new ListModelList<Producto>(getEvents());
            }
            productos.setMultiple(true);
            return productos;
        }

        public void setProductos(ListModelList<Producto> productos) {
            this.productos = productos;
        }
        
        //Devuelve el listado de productos de un proveedor
        public List<Producto> getProdprov() {
            prodprov = new ListModelList<Producto>(eventDao.findAll("select * from zk_producto where id_proveedor = " + this.selectedProveedor.getId() ));
            prodprov.setMultiple(true);
            return prodprov;
	}
        
        public List<ProductoFamilia> getFamilias() {
            return prodfamiliaDao.findAll();
        }
        
        public List<Proveedor> getProveedores() {
            return proveedorDao.findAll();
        }
        
        public List<Iva> getIvas() {
            return ivaDao.findAll();
        }
        
        public String getFilterCodigo() {
            return filterCodigo;
        }
        
        /* FILTRADO */
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

        public String getFilterProveedor() {
            return filterProveedor;
        }
        
        @NotifyChange
        public void setFilterProveedor(String filterProveedor) {
            this.filterProveedor = filterProveedor;
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

        public String getFilterFechaalta() {
            return filterFechaalta;
        }

        @NotifyChange
        public void setFilterFechaalta(String filterFechaalta) {
            this.filterFechaalta = filterFechaalta;
        }

        public String getFilterStock() {
            return filterStock;
        }

        public void setFilterStock(String filterStock) {
            this.filterStock = filterStock;
        }

        
        @Command
        @NotifyChange({"doSearchProducto", "events"})
        public void doSearchProducto() 
        {
            events.clear();
            List<Producto> allEvents;// = eventDao.findAll(); //Para mostrar todo

            if((filterCodigo == null || "".equals(filterCodigo)) && 
               (filterNombre == null || "".equals(filterNombre)) &&
               (filterProveedor == null || "".equals(filterProveedor)) &&
               (filterFamilia == null || "".equals(filterFamilia)) && 
               (filterStock == null || "".equals(filterStock)) &&
               (filterIva == null || "".equals(filterIva)) && 
               (filterFechaalta == null || "".equals(filterFechaalta))) {
                    //events.addAll(allEvents); //Para mostrar todo
            }
            else 
            {
                allEvents = eventDao.findAll();//Quitar para mostrar todo
                for (Iterator<Producto> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
                {
                    Producto prod = it.next();
                    String iva = prod.getIva().getValor() + "";
                    String stock = prod.getStock() + "";
                    if ((prod.getCodigo().toLowerCase().startsWith(filterCodigo.toLowerCase())   ) &&
                        (prod.getNombre().toLowerCase().startsWith(filterNombre.toLowerCase())  )  &&
                        (prod.getProveedor().getNombre().toLowerCase().startsWith(filterProveedor.toLowerCase())  ) &&
                        (prod.getFamilia().getNombre().toLowerCase().startsWith(filterFamilia.toLowerCase())   ) &&
                        (prod.getFecha_alta().toString().toLowerCase().startsWith(filterFechaalta.toLowerCase())  )  &&
                        (iva.toLowerCase().startsWith(filterIva.toLowerCase())   ) &&
                        (stock.toLowerCase().startsWith(filterStock.toLowerCase())  )) {
                            events.add(prod);
                    }
                }
            }
        }
        // Buscador para hacer el filtrado
        @Command 
        @NotifyChange("productos")
        public void doSearch() 
        {
            productos.clear();
            List<Producto> allEvents = eventDao.findAll();

            if((filterCodigo == null || "".equals(filterCodigo)) && 
               (filterNombre == null || "".equals(filterNombre)) &&
               (filterProveedor == null || "".equals(filterProveedor)) &&
               (filterFamilia == null || "".equals(filterFamilia)) && 
               (filterStock == null || "".equals(filterStock)) &&
               (filterIva == null || "".equals(filterIva)) && 
               (filterFechaalta == null || "".equals(filterFechaalta))) {
                    productos.addAll(allEvents);
            }
            else 
            {
                for (Iterator<Producto> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
                {
                    Producto prod = it.next();
                    String iva = prod.getIva().getValor() + "";
                    String stock = prod.getStock() + "";
                    if ((prod.getCodigo().toLowerCase().startsWith(filterCodigo.toLowerCase())   ) &&
                        (prod.getNombre().toLowerCase().startsWith(filterNombre.toLowerCase())  )  &&
                        (prod.getProveedor().getNombre().toLowerCase().startsWith(filterProveedor.toLowerCase())  ) &&
                        (prod.getFamilia().getNombre().toLowerCase().startsWith(filterFamilia.toLowerCase())   ) &&
                        (prod.getFecha_alta().toString().toLowerCase().startsWith(filterFechaalta.toLowerCase())  )  &&
                        (iva.toLowerCase().startsWith(filterIva.toLowerCase())   ) &&
                        (stock.toLowerCase().startsWith(filterStock.toLowerCase())  )) {
                            productos.add(prod);
                    }
                }
            }
        }
        
        @Command 
        @NotifyChange("prodprov")
        public void doSearchProv() 
        {
            System.out.println();
            prodprov.clear();
            List<Producto> allEvents = getProdprov();

            if((filterCodigo == null || "".equals(filterCodigo)) && 
               (filterNombre == null || "".equals(filterNombre)) &&
               (filterProveedor == null || "".equals(filterProveedor)) &&
               (filterFamilia == null || "".equals(filterFamilia)) && 
               (filterStock == null || "".equals(filterStock)) &&
               (filterIva == null || "".equals(filterIva)) && 
               (filterFechaalta == null || "".equals(filterFechaalta))) {
                    prodprov.addAll(allEvents);
            }
            else 
            {
                for (Iterator<Producto> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
                {
                    Producto prod = it.next();
                    String iva = prod.getIva().getValor() + "";
                    String stock = prod.getStock() + "";
                    if ((prod.getCodigo().toLowerCase().startsWith(filterCodigo.toLowerCase())   ) &&
                        (prod.getNombre().toLowerCase().startsWith(filterNombre.toLowerCase())  )  &&
                        (prod.getProveedor().getNombre().toLowerCase().startsWith(filterProveedor.toLowerCase())  ) &&
                        (prod.getFamilia().getNombre().toLowerCase().startsWith(filterFamilia.toLowerCase())   ) &&
                        (prod.getFecha_alta().toString().toLowerCase().startsWith(filterFechaalta.toLowerCase())  )  &&
                        (iva.toLowerCase().startsWith(filterIva.toLowerCase())   ) &&
                        (stock.toLowerCase().startsWith(filterStock.toLowerCase())  )) {
                            prodprov.add(prod);
                    }
                }
            }
        }
        
        /* FIN FILTRADO */
        
	@Command("add")
        @NotifyChange({"events", "productos"})
	public void add() {
            String sbfamilia = this.newEvent.getFamilia().getNombre().substring(0, 3),
                   sbnombre = this.newEvent.getNombre().substring(0, 3),
                   sbproveedor = this.newEvent.getProveedor().getNombre().substring(0, 2);
            String codigo = (sbfamilia + sbnombre + sbproveedor).toLowerCase();
            
            List<Producto> prodlist = eventDao.findAll("SELECT * FROM zk_producto WHERE codigo LIKE '"+codigo+"%' ORDER BY id DESC");
            if(prodlist.isEmpty()){
                    this.newEvent.setCodigo(codigo+"001");
            }
            else{
                Producto prodaux = prodlist.get(0);
                Formatter fmt = new Formatter();
                int num = Integer.parseInt(prodaux.getCodigo().substring(8, 11));
                ++num ;
                fmt.format("%03d",num);
                this.newEvent.setCodigo(codigo+fmt);
            }
            
            if(events == null){
                System.out.println("--->>>>>>EVENTS es NULO");
            }
            else{
                if(events.isEmpty()){
                    System.out.println("--->>>>>>EVENTS isEmpty()");
                }
                else{
                    System.out.println("--->>>>>>EVENTS no esta vacio");
                }
            }
            if(eventDao.insert(this.newEvent)){
                System.out.println("--->>>>>>Insertado en EVENTS");
                events = new ListModelList<Producto>(eventDao.findAll());
            }
            
            this.newEvent = new Producto();
	}
        
        //Recoge el producto nuevo
//        @GlobalCommand
//        @NotifyChange("events")
//        public void refreshProducto(@BindingParam("returnProducto") Producto prod)
//        {
//            System.out.println("Producto: " + prod.getNombre());
//
//            eventDao.insert(prod);
//            this.newEvent = new Producto();
//        }
        
	@Command("update")
	@NotifyChange({"events", "productos"})
	public void update() {
            if((this.selectedEvent.getFamilia() == null) ){
                this.selectedEvent.setFamilia(this.auxProdFamilia);
            }
            if((this.selectedEvent.getIva() == null)){
                this.selectedEvent.setIva(auxIva);
            }
            eventDao.update(this.selectedEvent);
	}
	
	@Command("delete")
	@NotifyChange({"events", "productos"})
	public void delete() {
		//shouldn't be able to delete with selectedEvent being null anyway
		//unless trying to hack the system, so just ignore the request
		if(this.selectedEvent != null) {
			eventDao.delete(this.selectedEvent);
			this.selectedEvent = null;
		}
	}
        
    
//    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Command
    public void send() {
        Map args = new HashMap();
        args.put("returnProducto", this.selectedItems);
        BindUtils.postGlobalCommand(null, null, "refreshvalues", args);
    }
    
    @Command
    public void crearPDF(){
        FirstPdf.main();
        MyFirstTable.main();
            
    }
    
    public List<Producto> getAvisoProducto(){
        return eventDao.findAll("SELECT * FROM zk_producto WHERE stock < 3");
    }
    
    
    @Command
    @NotifyChange({"media", "events"})
    public void uploadFile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) 
            throws FileNotFoundException, IOException {
        Media media = event.getMedia();
        String name = media.getName();
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        
        if (media instanceof org.zkoss.image.Image) {
            byte[] fi = media.getByteData();
            Desktop desktop = Executions.getCurrent().getDesktop();
            String realpath = desktop.getWebApp().getRealPath("/uploads/productos");
            
//            System.out.println("Path de uploads: " + realpath);
                    
            File aud = new File(name);
            String PATH = realpath + "/" + name;
            try {
                InputStream fin = media.getStreamData();
                in = new BufferedInputStream(fin);

                File baseDir = new File(realpath + "/");

                if (!baseDir.exists()) {
                    baseDir.mkdirs();
                }
                File file = new File(PATH);
                
//                System.out.println(PATH);
                
                FileOutputStream fout = new FileOutputStream(file);
                out = new BufferedOutputStream(fout);
                byte buffer[] = fi;// 
                int ch = in.read(buffer);
                while (ch != -1) {
                    out.write(buffer, 0, ch);
                    ch = in.read(buffer);
                }
                
                if(this.selectedEvent != null){
                    this.selectedEvent.setFotografia("../uploads/productos/" + name);
                }
                else{
                    this.newEvent.setFotografia("../uploads/productos/" + name);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                    try {
                            if (out != null) {
                                out.close();
                            }
                            if (in != null) {
                                in.close();
                            }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }
        
        
    @Command
    public void downloadFile() {
        if (media != null) {
            Filedownload.save(media);
        }
    }
    
    @GlobalCommand
    @NotifyChange({"selectedProveedor", "events", "prodprov"})
    public void refreshProveedor(@BindingParam("returnProveedor") Proveedor prov){
        this.selectedProveedor = prov;
    }
    
    @Command("modificarProducto")
    @NotifyChange({"events", "productos"})
    public void modificarProducto() {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedProducto", selectedEvent );
        Executions.createComponents("../productos/producto-modificar.zul", null, map);
    }
    
    @Command("addproducto")
    @NotifyChange({"events", "productos"})
    public void addproducto() {
        Executions.createComponents("producto-nuevo.zul", null, null);
    }
}
