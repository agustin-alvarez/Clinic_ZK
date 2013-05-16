

package es.clinica.veterinaria.estadisticas;

import java.util.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class MyComposer extends GenericForwardComposer {
        
        @Override
        public void doBeforeComposeChildren(Component comp) throws Exception {
                super.doBeforeComposeChildren(comp);

                comp.setAttribute("model", createModel());
                comp.setAttribute("renderer", new MyRowRenderer());
        }


        private ListModel createModel() {
                List data = new ArrayList();

                List articleList = MyDAO.getArticle();
                
                for (int i = 0, j = articleList.size(); i < j; i++) 
                {
                        String article = (String) articleList.get(i);
                        List subArticleList =  MyDAO.getSubArticle();

                        for (int k = 0, l = subArticleList.size(); k < l; k++) 
                        {
                                String subArticle = (String) subArticleList.get(k);
                                List subSubArticleList =  MyDAO.getSubSubArticle();

                                for (int m = 0, n = subSubArticleList.size(); m < n; m++) 
                                {
                                        String subSubArticle = (String) subSubArticleList.get(m);
                                        data.add(new String[]{article, subArticle, subSubArticle});
                                }
                        }
                }
                
//                for (int i = 0; i < 4; i++) 
//                {
//                        String article = (String) articleList.get(i);
//                        data.add(new String[]{article});
//                }
//                
//                List subArticleList =  MyDAO.getSubArticle();
//                for (int k = 0;k < 4; k++) 
//                {
//                        String subArticle = (String) subArticleList.get(k);
//                        data.add(new String[]{subArticle});
//                }
//                
//                List subSubArticleList =  MyDAO.getSubSubArticle();
//                for (int m = 0; m < 4; m++) 
//                {
//                        String subSubArticle = (String) subSubArticleList.get(m);
//                        data.add(new String[]{subSubArticle});
//                }
                        
                
                return new ListModelList(data);
        }


        public static class MyDAO {
                
                public static List getArticle() {
                        List list = new ArrayList();
                        list.add("Defuncion");
                        list.add("Nuevas Altas");
                        list.add("D/V");
                        list.add("Recuperados");

                        return list;
                }
                
                //Enero
                public static List getSubArticle() {
                        List list = new ArrayList();
                        list.add("16");
                        list.add("24");
                        list.add("3");
                        list.add("5");
                        
                        return list;
                }
                
                //Febrero
                public static List getSubSubArticle() {
                        List list = new ArrayList();
                        list.add("16");
                        list.add("24");
                        list.add("3");
                        list.add("5");
                        return list;
                }

                public static int getTotalArticleCount(String article) 
                {
                        List subArticleList =  getSubArticle();
                        int rowSpan = 0;
                        for (int i = 0, j = subArticleList.size(); i < j; i++) 
                        {
                                String subArticle = (String) subArticleList.get(i);
                                rowSpan += getSubSubArticle().size();
                        }
                        return rowSpan;
                }
        }

        public static class MyRowRenderer implements RowRendererExt, RowRenderer 
        {
                private int index = 0;
                private String prevArticle = "", prevSubArticle="";
                private String[] curData;

                public Row newRow(Grid grid) {
                        Row row = new Row();
                        curData = getData(grid.getModel());
                        boolean isNextArticle = !prevArticle.equals(curData[0]);
                        boolean isNextSubArticle = !prevSubArticle.equals(curData[1]);

                        //Map arg = new HashMap();

                        // first row of each article
                        if (isNextArticle)
                        {
                                Cell cell = new Cell();
                                prevArticle = curData[0];
                                cell.setRowspan(MyDAO.getTotalArticleCount(prevArticle));
                                cell.appendChild(new Label(prevArticle));
                                row.appendChild(cell);
                        }
                        // first row of each SubArticle
                        if (isNextSubArticle)
                        {
                                prevSubArticle = curData[1];
                                Cell cell = new Cell();
                                cell.setRowspan(MyDAO.getSubSubArticle().size());
                                cell.appendChild(new Label(prevSubArticle));
                                row.appendChild(cell);
                        }
                        return row;
                }
                
                private String[] getData(ListModel model) {
                        if (model != null) {
                            return (String[]) model.getElementAt(index++);
                        }
                        return null;
                }
                public Component newCell(Row row) {
                        return new Label(curData[2].toString());
                }
                public int getControls() {
                        return 0;
                }
                public void render(Row row, Object data) throws Exception {
                }

                public void render(Row row, Object t, int i) throws Exception {
//                    throw new UnsupportedOperationException("Not supported yet.");
                }
        }
}