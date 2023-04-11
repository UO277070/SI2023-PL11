package InscribirAdmin;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

import ActividadesOfertadas.ActividadesOfertadas_Model;
import ActividadesOfertadas.ActividadesOfertadas_View;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import loginSocio.SocioEntity;
import loginSocio.loginController;
import loginSocio.loginModel;
import loginSocio.loginView;

public class InscribirAdminC {
	
	private InscribirAdminM model;
	private InscribirAdminV view;
	//private loginController controller;
	//private SocioEntity socioLog;
	private Date fechaActual;
	private int plazaslibres;
	private int idActividad;
	private int idSocio;
	private int idNoSocio;
	private boolean esSocio;
	 
	
	public InscribirAdminC(InscribirAdminM model, InscribirAdminV view) {
		this.model = model;
		this.view = view;
		this.fechaActual = new Date();
		this.plazaslibres = 0;
		this.idActividad = 0;
		this.idSocio = 0;
		this.idNoSocio = 0;
		this.esSocio = false;
		
		this.initView();
	}
	
	public void initController() {
		//view.getComboBoxActividades().addItemListener(e -> getListaActividadesEnPeriodo());
		view.getButtonInscribir().addActionListener(e -> inscribir());
		view.getCheckBoxSocio().addItemListener(e -> getListaActividadesEnPeriodo());

		
	}
	
	
	public void initView() {
		this.getListaActividadesEnPeriodo();
		this.getListaSocios();
		view.getFrame().setVisible(true);
		
		
		/*//Abre la ventana (sustituye al main generado por WindowBuilder)
		controller=new loginController(new loginModel(), new loginView());
		controller.initController();
		boolean POK = controller.showDialog();
		if (POK) {
			socioLog = controller.getSocio();
		    view.getFrame().setVisible(true);
		}*/
	}
	
	
	public void getListaSocios() {
		List <Object[]> socios = model.getListaSocios();
		
		ComboBoxModel comboId = SwingUtil.getComboModelFromList(socios);
		view.getComboBoxID().setModel(comboId);
		
	}
	
	public void getListaActividadesEnPeriodo() {
		this.esSocio = view.getCheckBoxSocio().isSelected();
		List <Object[]> actividades = model.getListaActividadesEnPeriodoNoSocio(Util.dateToIsoString(fechaActual));
		ComboBoxModel comboActividades = SwingUtil.getComboModelFromList(actividades);
		if(esSocio) {
			actividades = model.getListaActividadesEnPeriodoSocio(Util.dateToIsoString(fechaActual));
			comboActividades = SwingUtil.getComboModelFromList(actividades);
		}
		view.getComboBoxActividades().setModel(comboActividades);
	}
	
	public void inscribir() {
		this.idActividad = (int) model.getIdActividad(view.getComboBoxActividades().getSelectedItem().toString()).get(0)[0];
		this.idSocio = (Integer) view.getComboBoxID().getSelectedItem();
		this.plazaslibres = Math.max(0,  (Integer) model.getPlazasTotalesActividad(this.idActividad).get(0)[0]
				- (Integer) model.getNumInscripcionesEnActividad(this.idActividad).get(0)[0]);
		
		
		if((this.plazaslibres != 0) && (this.esSocio)) {
			model.insertInscripcionActividadSocio(this.idActividad, this.idSocio);
		}
		
		if((this.plazaslibres != 0) && (!this.esSocio)) {
			model.insertNoSocio(view.getTextFieldDNI().getText(), view.getTextFieldNombre().getText(), view.getTextFieldPrimerApellido().getText(), view.getTextFieldSegundoApellido().getText(), view.getTextFieldCorreo().getText());
			List<NoSocio> noSocio = model.getNoSocio(view.getTextFieldDNI().getText());
			this.idNoSocio = noSocio.get(0).getIdNoSocio();
			model.insertInscripcionActividadNoSocio(idActividad, this.idNoSocio);
		}
		
		JOptionPane.showMessageDialog(null,"Inscripcion realizada","Inscripcion", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
