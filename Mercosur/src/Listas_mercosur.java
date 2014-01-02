import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Listas_mercosur implements ActionListener {

	int numerolinea;
	String abrichoabrir;
	String archivoelegido;
	String archivonumerado;
	String codigoporlasdudas;
	String archivoerroes;
	Boolean actalizado;
	String exepto;// departamento cierto

	FileReader fr = null;
	BufferedReader br = null;
	FileWriter fw = null;
	BufferedWriter bw = null;
	Vector<Object> lineasAcopiar = new Vector<Object>();
	ArrayList<String> acumularborradas = new ArrayList<String>();

	private JFrame frame;
	private JTextField txtpregunta;
	private JTextField txtcierto;
	private JTextField txterror1;
	private JTextField txterror2;
	private JTextField txterror3;
	private JButton btnNewButton;
	JLabel lblPregunta;

	JComboBox<String> comboBox;
	JMenuItem mntmLeerarchivo;
	JMenuItem mntmGrabarNuevoarchivo;
	JMenuItem mntmgrabardatos;
	JMenuItem mntmActualizar;
	JMenuItem mntmLineasLargas;
	JMenuItem mntmsalir;

	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenuItem mntmLeerdatos;
	private JMenuItem mntmDefinirArchivoBorrados;

	JMenuItem mntmNumeroPregunta;
	JMenuItem mntmCrearArchBsico;
	JMenuItem mntmCrearArchMedio;

	JMenu mnIrA;

	private JTextField txtTxttema;
	
	JComboBox comboBox_1;
	JComboBox comboBox_2;
	JComboBox comboBox_3;
	JComboBox comboBox_4;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Listas_mercosur window = new Listas_mercosur();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Listas_mercosur() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		txtpregunta = new JTextField();
		txtpregunta.setFont(new Font("Dialog", Font.BOLD, 15));
		txtpregunta.setBackground(Color.PINK);
		txtpregunta.setBounds(31, 24, 658, 44);
		frame.getContentPane().add(txtpregunta);
		txtpregunta.setColumns(10);

		txtcierto = new JTextField();
		txtcierto.setFont(new Font("Dialog", Font.BOLD, 15));
		txtcierto.setBackground(Color.YELLOW);
		txtcierto.setBounds(31, 91, 321, 24);
		frame.getContentPane().add(txtcierto);
		txtcierto.setColumns(10);

		txterror1 = new JTextField();
		txterror1.setFont(new Font("Dialog", Font.BOLD, 13));
		txterror1.setColumns(10);
		txterror1.setBounds(32, 147, 320, 24);
		frame.getContentPane().add(txterror1);

		txterror2 = new JTextField();
		txterror2.setFont(new Font("Dialog", Font.BOLD, 13));
		txterror2.setColumns(10);
		txterror2.setBounds(31, 193, 321, 24);
		frame.getContentPane().add(txterror2);

		txterror3 = new JTextField();
		txterror3.setFont(new Font("Dialog", Font.BOLD, 13));
		txterror3.setColumns(10);
		txterror3.setBounds(31, 255, 321, 24);
		frame.getContentPane().add(txterror3);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(172, 309, 113, 24);
		comboBox.addItem("Alto");
		comboBox.addItem("Medio");
		comboBox.addItem("Basico");
		frame.getContentPane().add(comboBox);

		JButton btnNuevaLinea = new JButton("Próxima");
		btnNuevaLinea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (abrichoabrir == null) {
					JOptionPane.showMessageDialog(frame,
							"No se cargó el archivo de lectura");
					return;
				}

				numerolinea = numerolinea + 1;
				leer(abrichoabrir);// lee una nueva linea
				proponer();
				// pub();// coloca los txt
				btnNewButton.setEnabled(true);

			}
		});
		btnNuevaLinea.setBounds(555, 331, 134, 25);
		frame.getContentPane().add(btnNuevaLinea);

		btnNewButton = new JButton("Grabar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (archivoelegido == null) {
					JOptionPane.showMessageDialog(frame,
							"Falta el archivo, donde grabar");
					return;
				}

				String frase =  txtpregunta.getText() + ";" + txtcierto.getText()
						+ ";" + txterror1.getText() + ";" + txterror2.getText()
						+ ";" + txterror3.getText() + ";"
						+ txtTxttema.getText() + ";" + ponernivel() + ";";
				// System.out.println(frase);
				escribir(frase, archivoelegido);
				acumularborradas.add(codigoporlasdudas);

				actalizado = false;
				// borrador();
				btnNewButton.setEnabled(false);
			}
		});
		btnNewButton.setBounds(481, 384, 134, 25);
		frame.getContentPane().add(btnNewButton);

		lblPregunta = new JLabel("Pregunta");
		lblPregunta.setBounds(31, 314, 70, 15);
		frame.getContentPane().add(lblPregunta);

		JButton btnBorrarLineaSin = new JButton("Borrar linea sin grabar");
		btnBorrarLineaSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				acumularborradas.add(codigoporlasdudas);
				actalizado = false;

			}
		});
		btnBorrarLineaSin.setBounds(29, 408, 213, 25);
		frame.getContentPane().add(btnBorrarLineaSin);

		JButton btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (abrichoabrir == null) {
					JOptionPane.showMessageDialog(frame,
							"No se cargó el archivo de lectura");
					return;
				}

				numerolinea = numerolinea - 1;
				leer(abrichoabrir);// lee una nueva linea
				// por algo sera
				btnNewButton.setEnabled(true);
			}
		});
		btnAnterior.setBounds(406, 331, 134, 25);
		frame.getContentPane().add(btnAnterior);

		JLabel lblTema = new JLabel("Tema");
		lblTema.setBounds(31, 356, 70, 15);
		frame.getContentPane().add(lblTema);

		txtTxttema = new JTextField();
		txtTxttema.setText("txttema");
		txtTxttema.setBounds(172, 354, 114, 19);
		frame.getContentPane().add(txtTxttema);
		txtTxttema.setColumns(10);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addItem("Argentina");
		comboBox_1.addItem("Bolivia");
		comboBox_1.addItem("Brasil");
		comboBox_1.addItem("Chile");
		comboBox_1.addItem("Colombia");
		comboBox_1.addItem("Ecuador");
		comboBox_1.addItem("Paraguay");
		comboBox_1.addItem("Perú");
		comboBox_1.addItem("Uruguay");
		comboBox_1.addItem("Venezuela");
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ponertexto(1);
			}
		});
		comboBox_1.setBounds(395, 146, 181, 24);
		frame.getContentPane().add(comboBox_1);
		
		comboBox_2 = new JComboBox();
		comboBox_2.addItem("Argentina");
		comboBox_2.addItem("Bolivia");
		comboBox_2.addItem("Brasil");
		comboBox_2.addItem("Chile");
		comboBox_2.addItem("Colombia");
		comboBox_2.addItem("Ecuador");
		comboBox_2.addItem("Paraguay");
		comboBox_2.addItem("Perú");
		comboBox_2.addItem("Uruguay");
		comboBox_2.addItem("Venezuela");
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ponertexto(2);
			}
		});
		comboBox_2.setBounds(395, 192, 181, 24);
		frame.getContentPane().add(comboBox_2);
		
		comboBox_3 = new JComboBox();
		comboBox_3.addItem("Argentina");
		comboBox_3.addItem("Bolivia");
		comboBox_3.addItem("Brasil");
		comboBox_3.addItem("Chile");
		comboBox_3.addItem("Colombia");
		comboBox_3.addItem("Ecuador");
		comboBox_3.addItem("Paraguay");
		comboBox_3.addItem("Perú");
		comboBox_3.addItem("Uruguay");
		comboBox_3.addItem("Venezuela");
		comboBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ponertexto(3);
			}
		});
		comboBox_3.setBounds(395, 254, 181, 24);
		frame.getContentPane().add(comboBox_3);
		
		comboBox_4 = new JComboBox();
		comboBox_4.addItem("Argentina");
		comboBox_4.addItem("Bolivia");
		comboBox_4.addItem("Brasil");
		comboBox_4.addItem("Chile");
		comboBox_4.addItem("Colombia");
		comboBox_4.addItem("Ecuador");
		comboBox_4.addItem("Paraguay");
		comboBox_4.addItem("Perú");
		comboBox_4.addItem("Uruguay");
		comboBox_4.addItem("Venezuela");
		comboBox_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ponertexto(4);
			}
		});
		
		
		comboBox_4.setBounds(395, 91, 181, 24);
		frame.getContentPane().add(comboBox_4);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		mntmLeerdatos = new JMenuItem("Leer los Datos");
		mnArchivo.add(mntmLeerdatos);
		mntmLeerdatos.addActionListener(this);

		mntmgrabardatos = new JMenuItem("Grabar los Datos");
		mnArchivo.add(mntmgrabardatos);

		mntmDefinirArchivoBorrados = new JMenuItem("Definir archivo borrados");
		mnArchivo.add(mntmDefinirArchivoBorrados);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnArchivo.add(mntmSalir);

		JMenu mnHerramientas = new JMenu("Herramientas");
		menuBar.add(mnHerramientas);

		mntmActualizar = new JMenuItem("Actualizar");
		mnHerramientas.add(mntmActualizar);

		mntmLineasLargas = new JMenuItem("Lineas largas");
		mnHerramientas.add(mntmLineasLargas);

		mntmCrearArchBsico = new JMenuItem("Crear arch. básico");
		mnHerramientas.add(mntmCrearArchBsico);

		mntmCrearArchMedio = new JMenuItem("Crear arch medio");
		mnHerramientas.add(mntmCrearArchMedio);

		mnIrA = new JMenu("Ir a...");
		menuBar.add(mnIrA);

		mntmNumeroPregunta = new JMenuItem("Numero pregunta");
		mnIrA.add(mntmNumeroPregunta);

		mntmActualizar.addActionListener(this);
		mntmgrabardatos.addActionListener(this);
		mntmDefinirArchivoBorrados.addActionListener(this);
		mntmLineasLargas.addActionListener(this);
		mntmNumeroPregunta.addActionListener(this);
		mntmCrearArchBsico.addActionListener(this);
		mntmCrearArchMedio.addActionListener(this);

	}

	private void dialogo_abrir() {
		/*
		 * seleccionamos un archivo para leeren este caso para cargar
		 */
		// Creamos selector de apertura
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		// Titulo que llevara la ventana
		chooser.setDialogTitle("Leer archivo previamente arreglado");
		// Elegiremos archivos del directorio
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		// Si seleccionamos algún archivo retornaremos su directorio
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Archivo: " + chooser.getSelectedFile());
			abrichoabrir = chooser.getSelectedFile().toString();
			numerolinea = 0;
			// Si no seleccionamos nada retornaremos No seleccion

		} else {

			System.out.println("No seleccion ");

		}

	}

	/*
	 * responden al clik del boton
	 */
	public void actionPerformed(ActionEvent e) {

		// menu carga el nombre del archivo donde leer la linea
		if (e.getSource() == mntmLeerdatos) {
			dialogo_abrir();
		}

		// define el nombre del archivo donde grabar la nueva lista
		if (e.getSource() == mntmgrabardatos) {
			grabar_lineas();
		}

		if (e.getSource() == mntmActualizar) {
			borrador();
			actalizado = true;
		}

		if (e.getSource() == mntmDefinirArchivoBorrados) {
			grabar_errores();
			System.out.println("Pase por definir archivo errores");
		}

		if (e.getSource() == mntmLineasLargas) {
			lineaslargas(abrichoabrir);
		}

		if (e.getSource() == mntmsalir) {
			close();
		}

		if (e.getSource() == mntmNumeroPregunta) {
			iralnumero();
		}

		if (e.getSource() == mntmCrearArchBsico) {
			System.out.println(" crear basico");
			crearbasicosymedios(abrichoabrir, "B");
		}
		
		if (e.getSource() == mntmCrearArchMedio) {
			crearbasicosymedios(abrichoabrir, "M");
		}
	}

	public void leer(String archivoleer) {

		File f = new File(archivoleer);
		BufferedReader entrada;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea;
			int a = 0;
			while (entrada.ready()) {
				a += 1;
				linea = entrada.readLine();
				if (a == numerolinea) {
					String[] parts = linea.split(";");// cabie tab por ;
					if (linea.length() > 150);
						System.out.println(parts[0]);

					codigoporlasdudas = parts[0];// nombre
					exepto= txtcierto.getText();
					System.out.println(exepto);
					txtpregunta.setText(parts[0]);//cierto

					//txtcierto.setText(parts[1]);
					/*lblPregunta.setText(parts[0]);
					
					txterror1.setText(parts[3]);
					txterror2.setText(parts[4]);
					txterror3.setText(parts[5]);
					txtTxttema.setText(parts[6]);*/

					/*if (parts[7].equals("A")) {
						comboBox.setSelectedItem("Alto");
					}

					if (parts[7].equals("M")) {
						comboBox.setSelectedItem("Medio");
					}

					if (parts[7].equals("B")) {
						comboBox.setSelectedItem("Basico");
					}
*/
					// System.out.println("tema="+parts[6]);
					// System.out.println("nivel="+parts[7]);
					// acomodarauxiliar();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void crearbasicosymedios(String archivoleer, String nivel) {
		grabar_lineas();
		File f = new File(archivoleer);
		BufferedReader entrada;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea;
			int a = 0;
			while (entrada.ready()) {
				a += 1;
				linea = entrada.readLine();
				
					String[] parts = linea.split(";");// cabie tab por ;
					if (linea.length() > 150)
						;
					System.out.println(parts[0]);

					
					if (parts[7].equals(nivel)) {
						// crea archivo
						

						escribir(linea, archivoelegido);

					}

				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* define el nombre del archivo a grabar*/
		private void grabar_lineas() {
		// define el nombre archivo donde se guardaran los resultados
		// Creamos selector de apertura
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));

		// Titulo que llevara la ventana
		chooser.setDialogTitle("Donde grabar archivo ");

		// Elegiremos archivos del directorio
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		// Si seleccionamos algún archivo retornaremos su directorio
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("Archivo: " + chooser.getSelectedFile());
			archivoelegido = chooser.getSelectedFile().toString();

			// Si no seleccionamos nada retornaremos No seleccion

		} else {

			System.out.println("No seleccion ");

		}
	}

	private void grabar_errores() {
		// define el archivo donde se guardaran los resultados
		// Creamos selector de apertura

		JFileChooser chooser = new JFileChooser();

		chooser.setCurrentDirectory(new java.io.File("."));

		// Titulo que llevara la ventana

		chooser.setDialogTitle("Leer archivo previamente arreglado");

		// Elegiremos archivos del directorio

		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		chooser.setAcceptAllFileFilterUsed(false);

		// Si seleccionamos algún archivo retornaremos su directorio

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			System.out.println("Archivo: " + chooser.getSelectedFile());
			archivoerroes = chooser.getSelectedFile().toString();

			// Si no seleccionamos nada retornaremos No seleccion

		} else {

			System.out.println("No seleccion ");

		}
	}

	private void escribir(String frase, String archivoelegido) {

		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(archivoelegido, true);
			pw = new PrintWriter(fichero);

			pw.println(frase);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public String ponernivel() {
		String nivel = "";
		if (comboBox.getSelectedItem().toString().equals("Alto"))// if
																	// comboBox.getItemAt(0)
			nivel = "A";
		if (comboBox.getSelectedItem().toString().equals("Medio"))
			nivel = "M";
		if (comboBox.getSelectedItem().toString().equals("Basico"))
			nivel = "B";
		return nivel;
	}

	// lo saque de la clase
	public void borrador() {
		for (int i = 0; i < acumularborradas.size(); i++) {
			escribir(acumularborradas.get(i).toString(), archivoerroes);

		}

	}

	private void close() {
		if (actalizado != true) {
			if (JOptionPane
					.showConfirmDialog(
							frame,
							"¿Desea  salir sin guardar los \n marcados para borrar \n y los comentarios?",
							"Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		} else {
			System.exit(0);
		}
	}

	private void lineaslargas(String archivoleer) {
		File f = new File(archivoleer);
		BufferedReader entrada;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea;
			int a = 0;
			while (entrada.ready()) {
				a += 1;
				linea = entrada.readLine();

				// String[] parts=linea.split(";");// cabie tab por ;
				if (linea.length() > 150) {
					System.out.println(a + linea.length());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void iralnumero() {
		String ax = JOptionPane.showInputDialog("Ingrese un numero: ");
		numerolinea = Integer.parseInt(ax);
		leer(abrichoabrir);// lee una la linea
	}
	
	private void ponertexto(int numero) {
		if (numero==1);
			txterror1.setText(comboBox_1.getSelectedItem().toString());
		if (numero==2);
			txterror2.setText(comboBox_2.getSelectedItem().toString());
		if (numero==3);
			txterror3.setText(comboBox_3.getSelectedItem().toString());
		if  (numero==4);
			txtcierto.setText(comboBox_4.getSelectedItem().toString());	
			
	}
	
	private void  proponer(){
		
		// colocar 3 numeros distintos
		int sacar;
		
		if (exepto.equals("Argentina"));
			sacar=0;
		if (exepto.equals("Bolivia"));
			sacar=1;
		if (exepto.equals("Brasil"));
			sacar=2;
		if (exepto.equals("Chile"));
			sacar=3;
		if (exepto.equals("ColombiaB"));
			sacar=4;
		if (exepto.equals("Ecuador"));
			sacar=5;
		if (exepto.equals("Paraguay"));
			sacar=6;
		if (exepto.equals("Perú"));
			sacar=7;
		if (exepto.equals("Uruguay"));
			sacar=8;
		if (exepto.equals("Venezuela"));
			sacar=9;
			
		sacar=0;
		
			
		NumeroAleatorios numeroAleatorios = new NumeroAleatorios(0, 9,sacar);
					
			comboBox_1.setSelectedIndex(numeroAleatorios.generarmenos());
			comboBox_2.setSelectedIndex(numeroAleatorios.generarmenos());
			comboBox_3.setSelectedIndex(numeroAleatorios.generarmenos());
			comboBox_4.setSelectedIndex(sacar);
			
		
	}
}
