/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.estadisticas;

/**
 * @author SaRCo
 */

import org.ngi.zhighcharts.SimpleExtXYModel;
import org.ngi.zhighcharts.ZHighCharts;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Window;

public class DemoComposer extends Window implements EventListener {
	protected Log logger = Log.lookup(this.getClass());
	
	//Basic Line	
	private ZHighCharts chartComp;
	private SimpleExtXYModel dataChartModel = new SimpleExtXYModel();
							
	public void onCreate() throws Exception {

            //================================================================================
	    // Basic Line
	    //================================================================================

		chartComp = (ZHighCharts) getFellow("chartComp");
		chartComp.setTitle("Ingresos/Gastos mensuales");
		chartComp.setSubTitle("Valores en Miles de Euros");
		chartComp.setType("area");
		chartComp.setXAxisTitle("Meses");
		chartComp.setxAxisOptions("{" +
					"categories: [" +
						"'Ene', " +
						"'Feb', " +
						"'Mar', " +
						"'Abr', " +
						"'May', " +
						"'Jun', " +
						"'Jul', " +
						"'Ago', " +
						"'Sep', " +
						"'Oct', " +
						"'Nov', " +
						"'Dic'" +
					"]" +
				"}");
		chartComp.setYAxisTitle("Cantidad (€)");
		chartComp.setyAxisOptions("{" +
					"plotLines: [" +
					"{" +
						"value: 0, " +
						"width: 1," +
						"color: '#808080' " +
					"}]" +
				"}");
		chartComp.setLegend("{" +
					"layout: 'vertical'," +
					"align: 'right'," +
					"verticalAlign: 'top'," +
					"x: -10," +
					"y: 100," +
					"borderWidth: 0" +
				"}");
		chartComp.setPlotOptions("{" +
				"series:{" +
					"dataLabels:{" +
						"formatter: function (){return this.y +'€';}," + 
						"enabled: true" +
					"}" +
				"}" +
			"}");
		chartComp.setTooltipFormatter("function formatTooltip(obj){ " +
					"return '<b>'+ obj.series.name +'</b>" +
					"<br/>'+ obj.x +': '+ obj.y +'€';" +
				"}");
		
		chartComp.setModel(dataChartModel);
		
		//Adding some data to the model
		dataChartModel.addValue("Ingresos", 0, 7);
		dataChartModel.addValue("Ingresos", 1, 6.9);
		dataChartModel.addValue("Ingresos", 2, 9.5);
		dataChartModel.addValue("Ingresos", 3, 14.5);
		dataChartModel.addValue("Ingresos", 4, 18.2);
		dataChartModel.addValue("Ingresos", 5, 21.5);
		dataChartModel.addValue("Ingresos", 6, 25.2);
		dataChartModel.addValue("Ingresos", 7, 26.5);
		dataChartModel.addValue("Ingresos", 8, 23.3);
		dataChartModel.addValue("Ingresos", 9, 18.3);
		dataChartModel.addValue("Ingresos", 10, 13.9);
		dataChartModel.addValue("Ingresos", 11, 9.6);
                /*
		dataChartModel.addValue("New York", 0, -0.2);
		dataChartModel.addValue("New York", 1, 0.8);
		dataChartModel.addValue("New York", 2, 5.7);
		dataChartModel.addValue("New York", 3, 11.3);
		dataChartModel.addValue("New York", 4, 17.0);
		dataChartModel.addValue("New York", 5, 22.0);
		dataChartModel.addValue("New York", 6, 24.8);
		dataChartModel.addValue("New York", 7, 24.1);
		dataChartModel.addValue("New York", 8, 20.1);
		dataChartModel.addValue("New York", 9, 14.1);
		dataChartModel.addValue("New York", 10, 8.6);
		dataChartModel.addValue("New York", 11, 2.5);
		
		dataChartModel.addValue("Berlin", 0, -0.9);
		dataChartModel.addValue("Berlin", 1, 0.6);
		dataChartModel.addValue("Berlin", 2, 3.5);
		dataChartModel.addValue("Berlin", 3, 8.4);
		dataChartModel.addValue("Berlin", 4, 13.5);
		dataChartModel.addValue("Berlin", 5, 17.0);
		dataChartModel.addValue("Berlin", 6, 18.6);
		dataChartModel.addValue("Berlin", 7, 17.9);
		dataChartModel.addValue("Berlin", 8, 14.3);
		dataChartModel.addValue("Berlin", 9, 9.0);
		dataChartModel.addValue("Berlin", 10, 3.9);
		dataChartModel.addValue("Berlin", 11, 1.0);
		*/
		dataChartModel.addValue("Gastos", 0, 3.9);
		dataChartModel.addValue("Gastos", 1, 4.2);
		dataChartModel.addValue("Gastos", 2, 5.7);
		dataChartModel.addValue("Gastos", 3, 8.5);
		dataChartModel.addValue("Gastos", 4, 11.9);
		dataChartModel.addValue("Gastos", 5, 15.2);
		dataChartModel.addValue("Gastos", 6, 17.0);
		dataChartModel.addValue("Gastos", 7, 16.6);
		dataChartModel.addValue("Gastos", 8, 14.2);
		dataChartModel.addValue("Gastos", 9, 10.3);
		dataChartModel.addValue("Gastos", 10, 6.6);
		dataChartModel.addValue("Gastos", 11, 4.8);
			
	}
	
	@Override
	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * internal method to convert date&amp;time from string to epoch milliseconds
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
//	private long getDateTime(String date) throws Exception {
//		return sdf.parse(date).getTime();
//	}

}
 
