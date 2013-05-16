
package es.clinica.veterinaria.estadisticas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.SimpleCategoryModel;

/**
 * @author SaRCo
 */

public class EstadisticosMascVM {
    
    private int selectedYear;
    List<EstadisticosMasc> events = new ArrayList<EstadisticosMasc>();
    List<EstadisticosMonetario> ingresos = new ArrayList<EstadisticosMonetario>();
    private EstadisticosMascDAO eventDao = new EstadisticosMascDAO();
    
    @Init
    public void init () {
        selectedYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }
    

    public int getSelectedYear() {
        return selectedYear;
    }
    
    @Command("year")
    @NotifyChange({"events","grafica","ingresos","grafica2", "selectedYear"})
    public void year(){}
    
    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }
    
    
    public List<EstadisticosMasc> getEvents() {
            
        events.clear();

        List<EstadisticosMasc> list = 
        eventDao.findAll(
            "SELECT 'Defunciones', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-01-31' THEN 1 ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-02-31' THEN 1 ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-03-31' THEN 1 ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-04-31' THEN 1 ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-05-31' THEN 1 ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-06-31' THEN 1 ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-07-31' THEN 1 ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-08-31' THEN 1 ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-09-31' THEN 1 ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-10-31' THEN 1 ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-11-31' THEN 1 ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha_def AND fecha_def <=  '"+selectedYear+"-12-31' THEN 1 ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_mascota");

        events.add(list.get(0));
        list.clear();

        list = eventDao.findAll(
            "SELECT 'Nuevas Altas', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-01-31' THEN 1 ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-02-31' THEN 1 ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-03-31' THEN 1 ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-04-31' THEN 1 ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-05-31' THEN 1 ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-06-31' THEN 1 ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-07-31' THEN 1 ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-08-31' THEN 1 ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-09-31' THEN 1 ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-10-31' THEN 1 ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-11-31' THEN 1 ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha_alta AND fecha_alta <=  '"+selectedYear+"-12-31' THEN 1 ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_mascota");
        events.add(list.get(0));
        list.clear();

//        EstadisticosMasc dv = new EstadisticosMasc("D/V",1,2,2,0,3,2,0,4,2,0,2,5);
//        events.add(dv);
        
        list = eventDao.findAll(
            "SELECT 'Vacunas', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha AND fecha <=  '"+selectedYear+"-01-31' THEN 1 ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha AND fecha <=  '"+selectedYear+"-02-31' THEN 1 ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha AND fecha <=  '"+selectedYear+"-03-31' THEN 1 ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha AND fecha <=  '"+selectedYear+"-04-31' THEN 1 ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha AND fecha <=  '"+selectedYear+"-05-31' THEN 1 ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha AND fecha <=  '"+selectedYear+"-06-31' THEN 1 ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha AND fecha <=  '"+selectedYear+"-07-31' THEN 1 ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha AND fecha <=  '"+selectedYear+"-08-31' THEN 1 ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha AND fecha <=  '"+selectedYear+"-09-31' THEN 1 ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha AND fecha <=  '"+selectedYear+"-10-31' THEN 1 ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha AND fecha <=  '"+selectedYear+"-11-31' THEN 1 ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha AND fecha <=  '"+selectedYear+"-12-31' THEN 1 ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_mascota_vacuna");
        events.add(list.get(0));
        list.clear();

//        EstadisticosMasc recuperados = new EstadisticosMasc("Recuperados",1,3,3,4,1,4,2,0,1,1,3,2);
//        events.add(recuperados);
        
        list = eventDao.findAll(
            "SELECT 'Citas', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha AND fecha <=  '"+selectedYear+"-01-31' THEN 1 ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha AND fecha <=  '"+selectedYear+"-02-31' THEN 1 ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha AND fecha <=  '"+selectedYear+"-03-31' THEN 1 ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha AND fecha <=  '"+selectedYear+"-04-31' THEN 1 ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha AND fecha <=  '"+selectedYear+"-05-31' THEN 1 ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha AND fecha <=  '"+selectedYear+"-06-31' THEN 1 ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha AND fecha <=  '"+selectedYear+"-07-31' THEN 1 ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha AND fecha <=  '"+selectedYear+"-08-31' THEN 1 ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha AND fecha <=  '"+selectedYear+"-09-31' THEN 1 ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha AND fecha <=  '"+selectedYear+"-10-31' THEN 1 ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha AND fecha <=  '"+selectedYear+"-11-31' THEN 1 ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha AND fecha <=  '"+selectedYear+"-12-31' THEN 1 ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_cita");
        events.add(list.get(0));
        //list.clear();

    return events;
    }
    
    @NotifyChange("selectedYear")
    public SimpleCategoryModel getGrafica(){

        List<EstadisticosMasc> list = events;
        
        
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String tipo[] = {"Defunciones", "Nuevas Altas", "Vacunas", "Citas"};
        
        SimpleCategoryModel demoModel = new SimpleCategoryModel();
        
        for(int i=0; i<4; i++){
            for(int j=0; j<12; j++){
                
                if(j==0) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getEnero());
                }
                if(j==1) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getFebrero());
                }
                if(j==2) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getMarzo());
                }
                if(j==3) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getAbril());
                }
                if(j==4) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getMayo());
                }
                if(j==5) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getJunio());
                }
                if(j==6) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getJulio());
                }
                if(j==7) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getAgosto());
                }
                if(j==8) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getSeptiembre());
                }
                if(j==9) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getOctubre());
                }
                if(j==10) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getNoviembre());
                }
                if(j==11) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getDiciembre());
                }
            }
        }
        return demoModel;
    }
    
    
    public List<EstadisticosMonetario> getIngresos() {
            
        ingresos.clear();

        List<EstadisticosMonetario> list = 
        eventDao.findAllMonetario(
            "SELECT 'Ingresos', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha AND fecha <=  '"+selectedYear+"-01-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha AND fecha <=  '"+selectedYear+"-02-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha AND fecha <=  '"+selectedYear+"-03-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha AND fecha <=  '"+selectedYear+"-04-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha AND fecha <=  '"+selectedYear+"-05-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha AND fecha <=  '"+selectedYear+"-06-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha AND fecha <=  '"+selectedYear+"-07-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha AND fecha <=  '"+selectedYear+"-08-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha AND fecha <=  '"+selectedYear+"-09-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha AND fecha <=  '"+selectedYear+"-10-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha AND fecha <=  '"+selectedYear+"-11-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha AND fecha <=  '"+selectedYear+"-12-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_venta_linea");
        
        
        ingresos.add(list.get(0));
        list.clear();

        list = eventDao.findAllMonetario(
            "SELECT 'Gastos', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha AND fecha <=  '"+selectedYear+"-01-31' THEN coste*cantidad ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha AND fecha <=  '"+selectedYear+"-02-31' THEN coste*cantidad ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha AND fecha <=  '"+selectedYear+"-03-31' THEN coste*cantidad ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha AND fecha <=  '"+selectedYear+"-04-31' THEN coste*cantidad ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha AND fecha <=  '"+selectedYear+"-05-31' THEN coste*cantidad ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha AND fecha <=  '"+selectedYear+"-06-31' THEN coste*cantidad ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha AND fecha <=  '"+selectedYear+"-07-31' THEN coste*cantidad ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha AND fecha <=  '"+selectedYear+"-08-31' THEN coste*cantidad ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha AND fecha <=  '"+selectedYear+"-09-31' THEN coste*cantidad ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha AND fecha <=  '"+selectedYear+"-10-31' THEN coste*cantidad ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha AND fecha <=  '"+selectedYear+"-11-31' THEN coste*cantidad ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha AND fecha <=  '"+selectedYear+"-12-31' THEN coste*cantidad ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_pedido_linea");
        
        ingresos.add(list.get(0));
        list.clear();
        
        list = 
        eventDao.findAllMonetario(
            "SELECT 'I.Ventas', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha AND fecha <=  '"+selectedYear+"-01-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha AND fecha <=  '"+selectedYear+"-02-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha AND fecha <=  '"+selectedYear+"-03-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha AND fecha <=  '"+selectedYear+"-04-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha AND fecha <=  '"+selectedYear+"-05-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha AND fecha <=  '"+selectedYear+"-06-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha AND fecha <=  '"+selectedYear+"-07-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha AND fecha <=  '"+selectedYear+"-08-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha AND fecha <=  '"+selectedYear+"-09-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha AND fecha <=  '"+selectedYear+"-10-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha AND fecha <=  '"+selectedYear+"-11-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha AND fecha <=  '"+selectedYear+"-12-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_venta_linea WHERE tipo=1");
                
        ingresos.add(list.get(0));
        list.clear();
        
        list = 
        eventDao.findAllMonetario(
            "SELECT 'I.Honorarios', " +
                " SUM(CASE WHEN '"+selectedYear+"-01-01' <= fecha AND fecha <=  '"+selectedYear+"-01-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS ENERO," +
                " SUM(CASE WHEN '"+selectedYear+"-02-01' <= fecha AND fecha <=  '"+selectedYear+"-02-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS FEBRERO," +
                " SUM(CASE WHEN '"+selectedYear+"-03-01' <= fecha AND fecha <=  '"+selectedYear+"-03-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS MARZO," +
                " SUM(CASE WHEN '"+selectedYear+"-04-01' <= fecha AND fecha <=  '"+selectedYear+"-04-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS ABRIL," +
                " SUM(CASE WHEN '"+selectedYear+"-05-01' <= fecha AND fecha <=  '"+selectedYear+"-05-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS MAYO," +
                " SUM(CASE WHEN '"+selectedYear+"-06-01' <= fecha AND fecha <=  '"+selectedYear+"-06-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS JUNIO," +
                " SUM(CASE WHEN '"+selectedYear+"-07-01' <= fecha AND fecha <=  '"+selectedYear+"-07-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS JULIO," +
                " SUM(CASE WHEN '"+selectedYear+"-08-01' <= fecha AND fecha <=  '"+selectedYear+"-08-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS AGOSTO," +
                " SUM(CASE WHEN '"+selectedYear+"-09-01' <= fecha AND fecha <=  '"+selectedYear+"-09-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS SEPTIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-10-01' <= fecha AND fecha <=  '"+selectedYear+"-10-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS OCTUBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-11-01' <= fecha AND fecha <=  '"+selectedYear+"-11-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS NOVIEMBRE," +
                " SUM(CASE WHEN '"+selectedYear+"-12-01' <= fecha AND fecha <=  '"+selectedYear+"-12-31' THEN pvp*cantidad*((100+iva)/100) ELSE 0 END) AS DICIEMBRE " +
              " FROM zk_venta_linea WHERE tipo=2");
        
        ingresos.add(list.get(0));
//        list.clear();

    return ingresos;
    }
    
    public SimpleCategoryModel getGrafica2(){

        List<EstadisticosMonetario> list = ingresos;
        
        
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String tipo[] = {"Ingresos", "Gastos", "I.Ventas", "I.Honorarios"};
        
        SimpleCategoryModel demoModel = new SimpleCategoryModel();
        
        for(int i=0; i<4; i++){
            for(int j=0; j<12; j++){
                
                if(j==0) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getEnero());
                }
                if(j==1) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getFebrero());
                }
                if(j==2) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getMarzo());
                }
                if(j==3) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getAbril());
                }
                if(j==4) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getMayo());
                }
                if(j==5) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getJunio());
                }
                if(j==6) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getJulio());
                }
                if(j==7) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getAgosto());
                }
                if(j==8) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getSeptiembre());
                }
                if(j==9) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getOctubre());
                }
                if(j==10) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getNoviembre());
                }
                if(j==11) {
                    demoModel.setValue(tipo[i], meses[j], list.get(i).getDiciembre());
                }
            }
        }
        return demoModel;
    }
}