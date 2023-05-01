package InscribirSocio;

import java.io.IOException;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

import InscribirAdmin.ListaEsperaSocio;
import InscribirAdmin.NoSocio;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import loginSocio.*;

public class InscribirSocioC {
	
	private InscribirSocioM model;
	private InscribirSocioV view;
	private Date fechaActual;
	private loginController controller;
	private SocioEntity socioLog;
	private Actividades actividad;
	private List<Inscripcion> inscripciones;
	private int idSocio;
	private int idActividad;
	private int plazaslibres;
	
	public InscribirSocioC(InscribirSocioM model, InscribirSocioV view) {
		this.model = model;
		this.view = view;
		this.fechaActual = new Date();
		
		
		
		
		this.initView();
	}
	
	public void initController() {
		view.getComboBoxActividad().addItemListener(e -> getActividad());
		view.getComboBoxActividad().addItemListener(e -> getInscripciones());
		view.getButtonInscribir().addActionListener(e -> inscribir());
	}
	
	
	public void initView() {
		this.getListaActividadesEnPeriodo();
		
		//Abre la ventana (sustituye al main generado por WindowBuilder)
		controller=new loginController(new loginModel(), new loginView());
		controller.initController();
		boolean POK = controller.showDialog();
		if (POK) {
			socioLog = controller.getSocio();
			view.getFrame().setVisible(true);
		}
	}
	
	public void setSocio() {
		view.getFrame().setTitle(view.getFrame().getTitle()+" - " + socioLog.getNombre());
	}
	
	public void getListaActividadesEnPeriodo() {
		List <Object[]> actividades = model.getListaActividadesEnPeriodoSocio(Util.dateToIsoString(fechaActual));
		ComboBoxModel combo = SwingUtil.getComboModelFromList(actividades);
		view.getComboBoxActividad().setModel(combo);
	}
	
	public void getActividad() {		
		this.actividad = model.getActividad(view.getComboBoxActividad().getSelectedItem().toString()).get(0);
		
		view.getLabelTipo().setText(this.actividad.getTipo());
		view.getLabelPrecio().setText(""+this.actividad.getPrecio());
		view.getLabelFechaIni().setText(actividad.getFechaini());
		view.getLabelFechaFin().setText(actividad.getFechafin());
		view.getLabelHoraIni().setText(actividad.getHoraini());
		view.getLabelHoraFin().setText(actividad.getHorafin());
		view.getLabelDias().setText(actividad.getDiasem());
		view.getLabelAforo().setText(""+model.getNumInscripcionesEnActividad(this.idActividad).get(0)[0]+"/"+""+actividad.getPlazas());
	}
	
	public void getInscripciones() {
		this.actividad = model.getActividad(view.getComboBoxActividad().getSelectedItem().toString()).get(0);
		this.idActividad = actividad.getIdActividad();
		this.plazaslibres =  Math.max(0, this.actividad.getPlazas() - (Integer) model.getNumInscripcionesEnActividad(this.idActividad).get(0)[0]);
	}
	
	public void getInscripcionesSocio() {
		this.idSocio = this.socioLog.getIdSocio();
		this.inscripciones = model.getInscripcionesSocio(this.idSocio);
		
	}
	
	public void inscribir() {
		this.actividad = model.getActividad(view.getComboBoxActividad().getSelectedItem().toString()).get(0);
		this.idActividad = actividad.getIdActividad();
		this.idSocio = this.socioLog.getIdSocio();
		this.getInscripciones();
		this.getInscripcionesSocio();
		Iterator itr = this.inscripciones.iterator();
		int idAux = 0;
		boolean yaInscrito = false;
		List<ListaEsperaSocio> listaEspera = model.getListaEsperaSocioActividad(this.idActividad);
		Iterator<ListaEsperaSocio> itrlista = listaEspera.iterator();
		
		//Comprobar si el socio ya está en lista de espera
		while (itrlista.hasNext()) {
	        ListaEsperaSocio espera = itrlista.next();
	        if (espera.getIdSocio() == this.idSocio) {
	            JOptionPane.showMessageDialog(null,"Ya estás en la lista de espera para esta actividad.","Inscripcion", JOptionPane.INFORMATION_MESSAGE);
	            return;
	        }
	    }
		
		//Comprobar si el socio ya está inscrito en la actividad
		while(itr.hasNext()) {
			idAux = ((Inscripcion) itr.next()).getIdActividad();
			if(idAux == this.idActividad) {
				yaInscrito = true;
				break;
			}
		}
		//Si ya está inscrito se informa y no se inscribe
		if(yaInscrito) {
			JOptionPane.showMessageDialog(null,"Ya tienes una inscripción para esa actividad.","Inscripcion", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		//No hay plazas libres y se inserta a la lista de espera
		if(this.plazaslibres == 0) {
			int max, min, pos;
			max = 1;
			min = Integer.MAX_VALUE;
			if(!itrlista.hasNext()) min = 1;
			while(itrlista.hasNext()) {
				pos = itrlista.next().getPosicion();
				if(pos >= max) max = pos + 1;
				if(pos <= min) min = pos;
			}
			model.insertListaEsperaSocio(this.idActividad, this.idSocio, max);
			JOptionPane.showMessageDialog(null,"El aforo para esta actividad esá completo.\nSe ha inscrito en lista de espera de socios para la actividad " + idActividad + " con éxito.\n" +
					"Posición en lista de espera de socios: " + (max - min + 1),"Inscripcion", JOptionPane.INFORMATION_MESSAGE);
		}
		
		//Hay plazas libres y no está inscrito
		else if(!yaInscrito && this.plazaslibres > 0) {
			int max = 1, min, pos;
			min = Integer.MAX_VALUE;
			
			//Hay socios en lista de espera 
			if(!listaEspera.isEmpty()) {
				//Obtener al primer socio en la lista de espera
				ListaEsperaSocio siguiente = listaEspera.get(0);
				int idSiguiente = siguiente.getIdSocio();
				int posicion = siguiente.getPosicion();
				//Eliminar al socio de la lista de espera
				model.deleteListaEsperaSocio(this.idActividad, idSiguiente);
				//Inscribir al socio en espera en la actividad
				model.insertInscripcionActividadSocio(this.idActividad, idSiguiente);
				JOptionPane.showMessageDialog(null,"Se ha inscrito automáticamente al siguiente socio en lista de espera para la actividad " + idActividad + ".\n" +
		                "Posición en lista de espera de socios: " + posicion,"Inscripcion", JOptionPane.INFORMATION_MESSAGE);
				//Añadir al socio actual a la lista de espera
				if(!itrlista.hasNext()) min = 1;
				while(itrlista.hasNext()) {
					pos = itrlista.next().getPosicion();
					if(pos >= max) max = pos + 1;
					if(pos <= min) min = pos;
				}
				model.insertListaEsperaSocio(this.idActividad, this.idSocio, max);
				JOptionPane.showMessageDialog(null,"El aforo para esta actividad esá completo.\nSe ha inscrito en lista de espera de socios para la actividad " + idActividad + " con éxito.\n" +
						"Posición en lista de espera de socios: " + (max - min + 1),"Inscripcion", JOptionPane.INFORMATION_MESSAGE);
				
			}
			//No hay nadie en lista de espera
			else {
				model.insertInscripcionActividadSocio(this.idActividad, this.idSocio);
				this.resguardo(actividad);
				JOptionPane.showMessageDialog(null,"Inscripcion realizada.","Inscripcion", JOptionPane.INFORMATION_MESSAGE);
			}	
		}
	}
	
	public void resguardo(Actividades detalles) {
		Formatter out = null;
		try {
			//Ruta para llegar a la carpeta de descargas
			String home = System.getProperty("user.home");
			out = new Formatter(home+"\\Downloads\\resguardo.txt");
			
			//Texto del txt
			out.format("Resguardo de su inscripción\n"+
						"Actividad: "+detalles.nombre+"\n"+
						"Instalacion: "+detalles.idInstalacion+"\n"+
						"Tipo: "+detalles.tipo+"\n"+
						"Fecha de Inicio: "+detalles.fechaini+"\n"+
						"Fecha de Fin: "+detalles.fechafin+"\n"+
						"Hora de Inicio: "+detalles.horaini+"\n"+
						"Hora de Fin: "+detalles.horafin+"\n"+
						"Días de realización: "+detalles.diasem+"\n"+
						"Plazas Totales: "+detalles.plazas+"\n");
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (out !=null) out.close();
		}
	}
	
}
