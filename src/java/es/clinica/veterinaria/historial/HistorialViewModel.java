package es.clinica.veterinaria.historial;

import es.clinica.veterinaria.ficheros.Fichero;
import es.clinica.veterinaria.ficheros.FicheroDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.pesos.Peso;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserCredentialManager;
import es.clinica.veterinaria.vacunas.Vacuna;
import es.clinica.veterinaria.vacunas.VacunaDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

//import org.zkoss.zul.ListModelList;

public class HistorialViewModel {
	
    private HistorialDAO eventDao = new HistorialDAO();
    private FicheroDAO ficheroDao = new FicheroDAO();
    private VacunaDAO vacunaDao = new VacunaDAO();
    private Historial selectedEvent, newEvent = new Historial();
    private Fichero selectedFichero, newFichero = new Fichero();
    private Mascota selectedMascota;
    private User selectedVet;
    Session s = Sessions.getCurrent();
    private Media media;
    private final DataSourceHistorial ds = DataSourceHistorial.INSTANCE;
    float _peso = 0;

    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                          @ExecutionArgParam("selectedMascota") Mascota selectedMascota, 
                          @ExecutionArgParam("selectedHistorial") Historial selectedHistorial) 
    {
        Selectors.wireComponents(view, this, false);
        
        if(selectedMascota != null) //Si la venta es nula, entonces es Venta Rápida
        {   
            this.selectedMascota = selectedMascota;
            this.selectedVet =  UserCredentialManager.getIntance(s).getUser();
            if(selectedHistorial != null)
            {
                this.selectedEvent = selectedHistorial;
            }
            else{
                this.newEvent.setId_veterinario(this.selectedVet);
                this.newEvent.setPeso(new Peso());
            }
        }
    }

    public float getPeso() {
        return _peso;
    }

    public void setPeso(float _peso) {
        this._peso = _peso;
    }
    
    public Historial getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Historial selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Mascota getSelectedMascota() {
        return selectedMascota;
    }

    public void setSelectedMascota(Mascota selectedMascota) {
        this.selectedMascota = selectedMascota;
    }

    public Fichero getSelectedFichero() {
        return selectedFichero;
    }

    public void setSelectedFichero(Fichero selectedFichero) {
        this.selectedFichero = selectedFichero;
    }

    public Historial getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Historial newEvent) {
            this.newEvent = newEvent;
    }

    public Fichero getNewFichero() {
        return newFichero;
    }

    public void setNewFichero(Fichero newFichero) {
        this.newFichero = newFichero;
    }

    public List<Historial> getEvents() {
            return eventDao.findAll();
    }
    
    public HashSet<Fichero> getFicheros() {
        
        if(this.newEvent.getId_veterinario() != null) {  
            return this.newEvent.getFicheros();
        }
        //El Historial ya existe
        else{ 
            return this.selectedEvent.getFicheros();
        }
    }
    
    public List<Vacuna> getVacunas() {
                return vacunaDao.findAll();
        }

    
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
    public void send() {
        Map args = new HashMap();
        this.newEvent.setMascota(selectedMascota);
        this.newEvent.setId_veterinario(selectedVet);
        Peso peso = new Peso();
        peso.setMascota(this.selectedMascota);
        peso.setValor(_peso);
        this.newEvent.setPeso(peso);
        args.put("returnHistorial", this.newEvent);
        BindUtils.postGlobalCommand(null, null, "refreshHistorial", args);
    }
    
    //Es utilizado en mascota-historial-modificar.zul
    @Command
    public void sendModificado() {
        Map args = new HashMap();
        args.put("returnHistorial", this.selectedEvent);
        BindUtils.postGlobalCommand(null, null, "refreshHistorialModificado", args);
    }
    
    
    @Command
    @NotifyChange({"ficheros","media"})
    public void uploadFile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) throws FileNotFoundException, IOException {
        
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try{
            Media media = event.getMedia();
            String name = media.getName();
//            byte[] fi = media.getByteData();
//            String fi = media.getStringData();

            Desktop desktop = Executions.getCurrent().getDesktop();
            String realpath = desktop.getWebApp().getRealPath("/uploads/historiales");

            File aud = new File(name);
            String PATH = realpath + "/" + this.selectedMascota.getId() + "-" + this.selectedMascota.getNombre() + "/"; //+ "/" + name;
            String ruta = PATH + name;
        
                InputStream fin = media.getStreamData();
                in = new BufferedInputStream(fin);
                File baseDir = new File(PATH);

                if (!baseDir.exists()) {
                    baseDir.mkdirs();
                }

                File file = new File(ruta);
                FileOutputStream fout = new FileOutputStream(file);
                out = new BufferedOutputStream(fout);
                byte buffer[] = new byte[1024];//fi.getBytes();// 
                int ch = in.read(buffer);

                while (ch != -1) {
                    out.write(buffer, 0, ch);
                    ch = in.read(buffer);
                }

                if(this.newFichero != null){
                    this.newFichero.setTipo(1); // 1)Historial
                    this.newFichero.setRuta("../uploads/historiales/" 
                                            + this.selectedMascota.getId() + "-" 
                                            + this.selectedMascota.getNombre() + "/"
                                            + name);
                    this.newFichero.setFecha(new Date());
                    
                    //Nuevo Historial
                    if(this.newEvent.getId_veterinario() != null) {  
                        this.newEvent.addFichero(this.newFichero);
                    }
                    //El Historial ya existe
                    else{  
                            
                            this.newFichero.setId_externo(this.selectedEvent.getId());
                            int id_fichero = ficheroDao.insert(this.newFichero);
                            if(id_fichero > 0){
                                System.out.println("Modificado>>Fichero insertado!!");
                            }
                            this.newFichero.setId(id_fichero);
                            this.selectedEvent.addFichero(this.newFichero);

                            
                    }
                }
                this.newFichero = new Fichero();
                media.getStreamData().close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         catch (Exception e) {
            throw new RuntimeException(e);
        } 
        finally {
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
    
//    @Command
//    public void downloadFile() throws FileNotFoundException {
//        Desktop desktop = Executions.getCurrent().getDesktop();
//        StringBuilder sb = new StringBuilder(this.selectedFichero.getRuta());
//        
//        sb.delete(0, 22);
//        String realpath = desktop.getWebApp().getRealPath("/uploads/historiales");
//
//        String ruta = realpath + sb;
//        System.out.println(" >>>>>>>>> Ruta: " + ruta);
//        File file = new File(ruta);
//        if(file.exists()){
//            System.out.println("------------>El fichero existe");
//        }
//        else{
//            System.out.println("------------>¡¡¡¡El fichero NO existe!!!");
//        }
//        System.out.println("AbsolutePath: " + file.getAbsolutePath());
//        if(file.canRead()){
//            System.out.println("------------>can read");
//        }
//        if(file.canWrite()){
//            System.out.println("------------>can write");
//        }
//        java.io.InputStream is = desktop.getWebApp().getResourceAsStream(file.getAbsolutePath());
//        if(is == null){
//            System.out.println("---------> IS es NULO");
//        }
//        else{
//            System.out.println("---------> IS es correcto!!!");
//        }
//        Filedownload.save(ruta, "application/pdf");
//
//    }
    
    @Command
    public void descargarFichero() throws FileNotFoundException, IOException {
        Desktop desktop = Executions.getCurrent().getDesktop();
        StringBuilder sb = new StringBuilder(this.selectedFichero.getRuta());
        
        sb.delete(0, 22);
        String realpath = desktop.getWebApp().getRealPath("/uploads/historiales");

        String ruta = realpath + sb;
        File f = new File(ruta);
        byte[] buffer = new byte [(int) f.length()]; 
        FileInputStream fs = new FileInputStream(f);
        fs.read( buffer ); 
        fs.close();

        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia(f, null, null);
        
        if (is != null){
            Filedownload.save(amedia);
        }
        else{
            Messagebox.show("Fichero no encontrado", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
    
    @Command
    @NotifyChange({"ficheros","media", "selectedFichero"})
    public void deleteFichero() throws FileNotFoundException, IOException {
        Desktop desktop = Executions.getCurrent().getDesktop();
        StringBuilder sb = new StringBuilder(this.selectedFichero.getRuta());
        
        sb.delete(0, 22);
        String realpath = desktop.getWebApp().getRealPath("/uploads/historiales");

        String ruta = realpath + sb;
        File f = new File(ruta);
        
        // Make sure the file or directory exists and isn't write protected
        if (!f.exists()) {
                Messagebox.show("Fichero no existe", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }

        if (!f.canWrite()) {
                Messagebox.show("Fichero no tengo persmisos de escritura", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }

        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
          String[] files = f.list();
          if (files.length > 0) {
                Messagebox.show("Fichero no es un archivo", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }

        if (!f.delete()) {
            Messagebox.show("Fichero no borrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            if(ficheroDao.delete(this.selectedFichero)){
                System.out.println("Fichero borrado de la BD");
            }
            this.selectedEvent.deleteFichero(this.selectedFichero);
            this.selectedFichero = null;
        }
        else{
            if(ficheroDao.delete(this.selectedFichero)){
                System.out.println("Fichero borrado de la BD");
            }
            this.selectedEvent.deleteFichero(this.selectedFichero);
            this.selectedFichero = null;
            
        }
    }
}
