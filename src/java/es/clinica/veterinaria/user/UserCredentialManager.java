/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.user;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class UserCredentialManager {
        
	private static final String KEY_USER_MODEL = UserCredentialManager.class.getName() + "_MODEL";
	private UserDAO userDAO;
	private User user;

	private UserCredentialManager() {
		userDAO = new UserDAO();
	}

	public static UserCredentialManager getIntance() {
		return getIntance(Sessions.getCurrent());
	}

	public static UserCredentialManager getIntance(Session zkSession) {
		synchronized (zkSession) {
			UserCredentialManager userModel = (UserCredentialManager) zkSession.getAttribute(KEY_USER_MODEL);
			if (userModel == null) {
				zkSession.setAttribute(KEY_USER_MODEL, userModel = new UserCredentialManager());
			}
			return userModel;
		}
	}

	public synchronized void login(String name, String password) {
		User tempUser = userDAO.findUserByName(name);
                System.out.println("Nombre: "+ tempUser.getUser());
		if (tempUser.getUser() != null && tempUser.getPassword().equals(StringMD.getStringMessageDigest(password,StringMD.MD5))) {
			user = tempUser;
		} else {
                    Messagebox.show("Nombre de usuario o contraseña incorrecto", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
                    user = null;
		}
	}

	public synchronized void logOff() {
		this.user = null;
	}

	public synchronized User getUser() {
		return user;
	}

	public synchronized boolean isAuthenticated() {
		return user != null;
	}
        
        public synchronized int getRol() {
                return user.getTipo();
        }
        
        public synchronized String getNombre() {
            return user.getNombre();
        }

}