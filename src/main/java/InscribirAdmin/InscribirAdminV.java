package InscribirAdmin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class InscribirAdminV {

	private JFrame frmInscribir;
	private JTextField textFieldNombre;
	private JTextField textFieldPrimerApellido;
	private JTextField textFieldDNI;
	private JTextField textFieldCorreo;
	private JComboBox comboBoxID;
	private JComboBox comboBoxActividades;
	private JTextField textFieldSegundoApellido;
	private JCheckBox checkBoxSocio;
	private JButton buttonInscribir;


	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InscribirAdminV window = new InscribirAdminV();
					window.frmInscribir.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InscribirAdminV() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInscribir = new JFrame();
		frmInscribir.setTitle("Inscribir a Actividad");
		frmInscribir.setBounds(100, 100, 518, 300);
		frmInscribir.getContentPane().setLayout(null);
		
		
		
		JLabel labelID = new JLabel("ID Socio:");
		labelID.setBounds(27, 82, 49, 16);
		frmInscribir.getContentPane().add(labelID);
		
		JLabel lableNombre = new JLabel("Nombre:");
		lableNombre.setBounds(27, 107, 48, 16);
		frmInscribir.getContentPane().add(lableNombre);
		
		JLabel labelPrimerApellido = new JLabel("Primer Apellido:");
		labelPrimerApellido.setBounds(27, 132, 74, 14);
		frmInscribir.getContentPane().add(labelPrimerApellido);
		
		JLabel labelDNI = new JLabel("DNI:");
		labelDNI.setBounds(27, 187, 22, 16);
		frmInscribir.getContentPane().add(labelDNI);
		
		JLabel labelCorreo = new JLabel("Correo:");
		labelCorreo.setBounds(27, 212, 42, 16);
		frmInscribir.getContentPane().add(labelCorreo);
		
		comboBoxID = new JComboBox();
		comboBoxID.setEnabled(false);
		comboBoxID.setBounds(120, 78, 156, 22);
		frmInscribir.getContentPane().add(comboBoxID);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(120, 104, 156, 20);
		frmInscribir.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldPrimerApellido = new JTextField();
		textFieldPrimerApellido.setBounds(120, 129, 156, 20);
		frmInscribir.getContentPane().add(textFieldPrimerApellido);
		textFieldPrimerApellido.setColumns(10);
		
		textFieldSegundoApellido = new JTextField();
		textFieldSegundoApellido.setBounds(120, 156, 156, 20);
		frmInscribir.getContentPane().add(textFieldSegundoApellido);
		textFieldSegundoApellido.setColumns(10);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setBounds(120, 184, 156, 20);
		frmInscribir.getContentPane().add(textFieldDNI);
		textFieldDNI.setColumns(10);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setBounds(120, 209, 156, 20);
		frmInscribir.getContentPane().add(textFieldCorreo);
		textFieldCorreo.setColumns(10);
		
		JLabel labelActividad = new JLabel("Actividad:");
		labelActividad.setBounds(283, 32, 55, 16);
		frmInscribir.getContentPane().add(labelActividad);
		
		comboBoxActividades = new JComboBox();
		comboBoxActividades.setBounds(344, 28, 148, 22);
		frmInscribir.getContentPane().add(comboBoxActividades);
		
		buttonInscribir = new JButton("Inscribir");
		buttonInscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonInscribir.setBounds(403, 227, 89, 23);
		frmInscribir.getContentPane().add(buttonInscribir);
		
		checkBoxSocio = new JCheckBox("Socio");
		checkBoxSocio.setSelected(false);
		checkBoxSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxSocio.isSelected()) {
					comboBoxID.setEnabled(true);
					textFieldNombre.setEnabled(false);
					textFieldNombre.setText("");
					textFieldPrimerApellido.setEnabled(false);
					textFieldPrimerApellido.setText("");
					textFieldSegundoApellido.setEnabled(false);
					textFieldSegundoApellido.setText("");
					textFieldDNI.setEnabled(false);
					textFieldDNI.setText("");
					textFieldCorreo.setEnabled(false);
					textFieldCorreo.setText("");
				}
				else {
					comboBoxID.setEnabled(false);
					textFieldNombre.setEnabled(true);
					textFieldPrimerApellido.setEnabled(true);
					textFieldSegundoApellido.setEnabled(true);
					textFieldDNI.setEnabled(true);
					textFieldCorreo.setEnabled(true);
				}
			}
		});
		checkBoxSocio.setBounds(27, 28, 57, 24);
		frmInscribir.getContentPane().add(checkBoxSocio);
		
		JLabel labelSegundoApellido = new JLabel("Segundo Apellido:");
		labelSegundoApellido.setBounds(27, 159, 86, 14);
		frmInscribir.getContentPane().add(labelSegundoApellido);
		
		
	}

	public JComboBox getComboBoxID() {
		return comboBoxID;
	}

	public void setComboBoxID(JComboBox comboBoxID) {
		this.comboBoxID = comboBoxID;
	}
	
	public JComboBox getComboBoxActividades() {
		return comboBoxActividades;
	}

	public void setComboBoxActividades(JComboBox comboBoxActividades) {
		this.comboBoxActividades = comboBoxActividades;
	}

	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public void setTextFieldNombre(JTextField textFieldNombre) {
		this.textFieldNombre = textFieldNombre;
	}

	public JTextField getTextFieldPrimerApellido() {
		return textFieldPrimerApellido;
	}

	public void setTextFieldPrimerApellido(JTextField textFielPrimerApellido) {
		this.textFieldPrimerApellido = textFieldPrimerApellido;
	}
	
	public JTextField getTextFieldSegundoApellido() {
		return textFieldSegundoApellido;
	}

	public void setTextFieldSegundoApellido(JTextField textFielSegundoApellido) {
		this.textFieldSegundoApellido = textFieldSegundoApellido;
	}

	public JTextField getTextFieldDNI() {
		return textFieldDNI;
	}

	public void setTextFieldDNI(JTextField textFieldDNI) {
		this.textFieldDNI = textFieldDNI;
	}

	public JTextField getTextFieldCorreo() {
		return textFieldCorreo;
	}

	public void setTextFieldCorreo(JTextField textFieldContacto) {
		this.textFieldCorreo = textFieldContacto;
	}

	public JFrame getFrame() {
		return this.frmInscribir;
	}
	
	
	public JButton getButtonInscribir() {
		return buttonInscribir;
	}

	public void setButtonInscribir(JButton buttonInscribir) {
		this.buttonInscribir = buttonInscribir;
	}

	public JCheckBox getCheckBoxSocio() {
		return checkBoxSocio;
	}

	public void setCheckBoxSocio(JCheckBox checkBoxSocio) {
		this.checkBoxSocio = checkBoxSocio;
	}
	
}
