/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.user;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author SaRCo
 */
public class LoginViewModel extends GenericForwardComposer {//SelectorComposer<Window> {
    @Wire
    private Textbox nameTxb, passwordTxb;
     
    @Wire
    private Label mesgLbl;
    
    @Listen("onClick=#confirmBtn")
    public void confirm() {
        doLogin();
    }
    
    public void onClick$confirmBtn() {
		doLogin();
	}
    
    public void onOK() {
		doLogin();
	}
    
    public void onCancel() {
        nameTxb.setValue("");
        passwordTxb.setValue("");
        nameTxb.setFocus(true);
    }
   
    private void doLogin() {
        UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions.getCurrent());
        mgmt.login(nameTxb.getValue(), passwordTxb.getValue());
        if (mgmt.isAuthenticated()) {
                execution.sendRedirect("principal.zul");
        } else {
//                mesgLbl.setValue("Nombre de usuario o contraseña incorrecto");
        }
    }
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
            super.doAfterCompose(comp);
            if (UserCredentialManager.getIntance(session).isAuthenticated()) {
                    execution.sendRedirect("principal.zul");
            }
            nameTxb.setFocus(true);
    }
}
