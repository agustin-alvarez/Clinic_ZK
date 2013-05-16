package es.clinica.veterinaria.historial;

import org.zkoss.bind.annotation.Command;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.impl.Attribute;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

public class HistorialComposer extends Window implements EventListener {
    private Media media;
    private Div divImagenes;
    
    public void onCreate() throws Exception {
        divImagenes = (Div) getFellow("divImagenes");
        
        for(int i=0; i<3; i++)
        {
//            org.zkoss.util.media.Media media = event.getMedia();
//            if (media instanceof org.zkoss.image.Image) {
//                org.zkoss.zul.Image image = new org.zkoss.zul.Image();
//                image.setContent(media);
//            }
            
            String s = "../images/paperclip.png";
            Image img = new Image(s);
            
            Attribute attbte = new Attribute("onClick");
            img.setAttribute("onClick", null);
            img.setParent(divImagenes);
            System.out.println("Height: " + img.getHeight());
            System.out.println("Width: " + img.getWidth());
            
//            if (img.getWidth() > img.getHeight()){
//                if (img.getHeight() > 300) {
//                    pics.setHeight("300px");
//                    pics.setWidth(img.getWidth() * 300 / img.getHeight() + "px");
//                }
//            }
//            if (img.getHeight() > img.getWidth()){
//                if (img.getWidth() > 400) {
//                    pics.setWidth("400px");
//                    pics.setHeight(img.getHeight() * 400 / img.getWidth() + "px");
//                }
//            }
    //        imagen.setHeight("200px");
    //        imagen.setWidth("200px");
            divImagenes.appendChild(img);

            System.out.println(s);
        }
    }
    
    public void onEvent(Event t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
            
        
    @Command
    public void downloadFile() {
        if (media != null) {
            Filedownload.save(media);
        }
    }
}