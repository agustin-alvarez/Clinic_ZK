package es.clinica.veterinaria.user;

import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class UserViewModel {
	
        @Wire
        private Textbox txtNueva;
        
	private UserDAO eventDao = new UserDAO();
	User user = new User();
	private User selectedEvent, newEvent = new User();
	Session session = Sessions.getCurrent();
        
        @Init
        public void init () {
            if(UserCredentialManager.getIntance(session).isAuthenticated()) {
                user = UserCredentialManager.getIntance(session).getUser();
            }
            
        }
        
	public User getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(User selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public User getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(User newEvent) {
		this.newEvent = newEvent;
	}

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

	public List<User> getEvents() {
		return eventDao.findAll();
	}
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
		eventDao.insert(this.newEvent);
		this.newEvent = new User();
	}
	
	@Command("update")
	@NotifyChange("events")
	public void update() {
		eventDao.update(this.selectedEvent);
	}
	
        //Funcion para cambiar el usuario su propia contraseña
        @Command("changepass")
	public void changepass(String pass) {
                user.setPassword(pass);
                if(!eventDao.change(this.user)){
                Messagebox.show("No se ha modificado la contraseña", "Error", Messagebox.OK, Messagebox.ERROR);
            }
	}
        
	@Command("delete")
	@NotifyChange({"events", "selectedEvent"})
	public void delete() {
		//shouldn't be able to delete with selectedEvent being null anyway
		//unless trying to hack the system, so just ignore the request
		if(this.selectedEvent != null && this.selectedEvent.getTipo() != 1) { //Tipo 1)Admin, 2)Veterinario 3)Empleado
			eventDao.delete(this.selectedEvent);
			this.selectedEvent = null;
		}
	}
        
        @Command("nuevoUsuario")
        @NotifyChange("events")
        public void nuevoUsuario() {
            final HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("selectedUsuario", selectedEvent );
            Executions.createComponents("../users/user-nuevo.zul", null, map);
        }
        
        @Command("modificarUsuario")
        @NotifyChange("events")
        public void modificarUsuario() {
            final HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("selectedUsuario", selectedEvent );
            Executions.createComponents("../users/user-modificar.zul", null, map);
        }
        
        @GlobalCommand
        @NotifyChange("events")
        public void refreshNewUsuario(@BindingParam("returnNewUsuario") User newuser) {
            if(!eventDao.insert(newuser)){
                Messagebox.show("No se ha añadido el usuario", "Error", Messagebox.OK, Messagebox.ERROR);
            }
        }
        
        @GlobalCommand
        @NotifyChange({"selectedEvent", "events"})
        public void refreshUpdateUsuario(@BindingParam("returnUpdateUsuario") User user) {
            if(!eventDao.update(user)){
                Messagebox.show("No se ha modificado los datos del usuario", "Error", Messagebox.OK, Messagebox.ERROR);
            }
        }

}
