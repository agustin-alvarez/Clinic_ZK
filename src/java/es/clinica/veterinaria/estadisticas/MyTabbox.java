
package es.clinica.veterinaria.estadisticas;

/**
 *
 * @author SaRCo
 */
import es.clinica.veterinaria.provincias.Provincia;
import es.clinica.veterinaria.provincias.ProvinciaDAO;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs; 

public class MyTabbox extends Tabbox {
    
    Map<String, ArrayList> auxmap;
    ProvinciaDAO provDao = new ProvinciaDAO();
    private List<Provincia> provincias =  new ArrayList<Provincia>();
    Map<String, ArrayList> _data;
    
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view) 
    {
        Selectors.wireComponents(view, this, false);
        provincias = provDao.findAll();
        this._data = iniAuxMap();
        this.setData(this._data);
        
    }
    
    public Map<String, ArrayList> iniAuxMap()
    {
            Iterator <Provincia> it = provincias.iterator();
            while(it.hasNext()){
                ArrayList items2 = new ArrayList(); 
                String[] data = new String[1000];
                for (int j = 0; j < data.length; ++j) 
                {
                    data[j] = "option " + j;
                    items2.add(data);
                }
                Provincia alitem = it.next();
                auxmap.put(alitem.getProvincia(), items2);
            }
          return auxmap;  
    }
    
    public void setData(Map<String, ArrayList> data) {
        
        Tabs tabs = new Tabs();
        Tabpanels tabpanels = new Tabpanels();
        
        //iterate over Map keys 
        for (String tabname : data.keySet()) 
        {
            Tab tab = new Tab(tabname); //Crea un hijo de Tabs
            tabs.appendChild(tab);      //Le añade el hijo a tabs

            ArrayList al = data.get(tabname);

            Grid grid = new Grid();
            Columns columns = new Columns();
            
            Column column1 = new Column();
            column1.setLabel(tabname);
            
            Column column2 = new Column();
            column2.setLabel(tabname);
            
            Column column3 = new Column();
            column3.setLabel(tabname);
            
            Column column4 = new Column();
            column4.setLabel(tabname);
            
            Column column5 = new Column();
            column5.setLabel(tabname);
            
            Column column6 = new Column();
            column6.setLabel(tabname);
            
            Column column7 = new Column();
            column7.setLabel(tabname);
            
            Column column8 = new Column();
            column8.setLabel(tabname);
            
            Column column9 = new Column();
            column9.setLabel(tabname);
            
            Column column10 = new Column();
            column10.setLabel(tabname);
            
            Column column11 = new Column();
            column11.setLabel(tabname);
            
            Column column12 = new Column();
            column12.setLabel(tabname);
            
            columns.appendChild(column1);
            columns.appendChild(column2);
            columns.appendChild(column3);
            columns.appendChild(column4);
            columns.appendChild(column5);
            columns.appendChild(column6);
            columns.appendChild(column7);
            columns.appendChild(column8);
            columns.appendChild(column9);
            columns.appendChild(column10);
            columns.appendChild(column11);
            columns.appendChild(column12);
            
            grid.appendChild(columns);

            Rows rows = new Rows();
            Iterator <ArrayList> it = al.iterator();
            while(it.hasNext()){
                ArrayList alitem = it.next();
                Row row = new Row();
                Label lab = new Label();
                rows.appendChild(row);
            }
            
/*            
            for (Iterator it = al.iterator(); it.hasNext();) {
                ArrayList  = it.next();
                Row row = new Row();
                rows.appendChild(row);
            }
*/
            grid.appendChild(rows);

            Tabpanel tabpanel = new Tabpanel();
            tabpanel.appendChild(grid);
            tabpanels.appendChild(tabpanel);
        }

        this.appendChild(tabs);
        this.appendChild(tabpanels);
    }
    
    public Map<String, ArrayList> getData()
    {
        return _data;
    }
}
