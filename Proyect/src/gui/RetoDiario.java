package gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RetoDiario extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 LinkedList<Color> listaColores = new LinkedList<>();
	 ArrayList<JLabel> listaLabels = new ArrayList<>();
	 ArrayList<String> listaRetos = new ArrayList<>();


    public RetoDiario(String usuario) {
        setTitle("Reto diario");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        // Lista de retos
        listaRetos.add("Correr 5KM");
        listaRetos.add("Hacer 50 flexiones");
        listaRetos.add("Realizar 30 minutos de yoga");
        listaRetos.add("Hacer 100 abdominales");
        listaRetos.add("Montar en bicicleta 20KM");
         
        
       
        this.setLayout(new BorderLayout());
        
//--------------------------------------LADO SUPERIOR----------------------------------
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.setBackground(Color.blue);
        
        JTextArea txtExplicacion = new JTextArea("¡Apúntate al reto diario para esos días en los que te sientes con motivación extra!");
        txtExplicacion.setFont(new Font("Arial", Font.BOLD, 18));
        txtExplicacion.setBackground(this.getBackground());
        txtExplicacion.setPreferredSize(new Dimension(800, 70));
        
        txtExplicacion.setLineWrap(true);          
        txtExplicacion.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtExplicacion.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtExplicacion.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtExplicacion.setDisabledTextColor(Color.black);
        panelSuperior.add(txtExplicacion);

        this.add(panelSuperior, BorderLayout.NORTH);

//-----------------------PARTE CENTRAL------------------------------------
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1, 2));
        panelCentral.setBackground(Color.blue);
        panelCentral.setBorder(new EmptyBorder(20,20,20,20));
        
        //----------LADO IZQUIERDO CENTRAL
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new GridLayout(3, 1));
        panelIzquierda.setBorder(new EmptyBorder(0,0,0,10));
        panelIzquierda.setBackground(Color.blue);
        
        //1 DE 3 partes LADO IZQUIERDO CENTRAL
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 70));
        JButton boton = new JButton("OBTEN RETO");
        boton.setPreferredSize(new Dimension(120,60));
        
        
        panel1.add(boton);
        panelIzquierda.add(panel1);
        
        // 2 DE 3 partes para LADO IZQUIERDO CENTRAL
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        JTextArea txtResultado = new JTextArea("Presiona el botón para obtener un reto");
        txtResultado.setFont(new Font("Arial", Font.BOLD, 18));
        txtResultado.setBackground(this.getBackground());
        txtResultado.setPreferredSize(new Dimension(345, 100));
        
        txtResultado.setLineWrap(true);          
        txtResultado.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtResultado.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtResultado.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtResultado.setDisabledTextColor(Color.black);
        
        panel2.add(txtResultado);
        panelIzquierda.add(panel2);
        
        // 3 DE 3 partes para LADO IZQUIERDO CENTRAL
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        JPanel panelContenedor3 = new JPanel();
        
        panelContenedor3.setLayout(new GridLayout(1, 10));
        panelContenedor3.setPreferredSize(new Dimension(0, 50));
        
        listaColores.push(new Color(255,0,0));
        listaColores.push(new Color(255,165,0));
        listaColores.push(new Color(255,200,0));
        listaColores.push(new Color(255,255,0));
        listaColores.push(new Color(200,255,0));
        listaColores.push(new Color(0,255,0));
        listaColores.push(new Color(0,255,200));
        listaColores.push(new Color(0,0,255));
        listaColores.push(new Color(75,0,130));
        listaColores.push(new Color(238,130,238));
        
        for (int i = 0; i < 10; i++) {
			JLabel color = new JLabel();
			color.setBackground(Color.white);
			color.setOpaque(true);
			panelContenedor3.add(color);
			listaLabels.add(color);
		}
        panel3.add(panelContenedor3, BorderLayout.SOUTH);
        panelIzquierda.add(panel3);
        
        panelCentral.add(panelIzquierda);
        
        //----------LADO DERECHO CENTRAL
        JPanel panelDerecha = new JPanel();
        panelDerecha.setBorder(new EmptyBorder(0,10,0,0));
        panelDerecha.setBackground(Color.blue);
        panelDerecha.setLayout(new BorderLayout());
        
        //Label retos pasados
        JLabel retosLabel = new JLabel("Registro de retos");
        retosLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        
        panelDerecha.add(retosLabel, BorderLayout.NORTH);
        
        RetosModel modelo = new RetosModel(); 
        JTable table = new JTable(modelo);
        table.getColumnModel().getColumn(5).setCellRenderer(new RendererBotonReto());
        table.getColumnModel().getColumn(5).setCellEditor(new EditorBotonReto(usuario));
        
        //Ajustar el tamaño de la fecha
      	table.getColumnModel().getColumn(2).setWidth(70);
      	table.getColumnModel().getColumn(2).setMinWidth(70);
      	table.getColumnModel().getColumn(2).setMaxWidth(70);
      	
      	//Ajustar el tamaño de la dificultad
      	table.getColumnModel().getColumn(3).setWidth(70);
      	table.getColumnModel().getColumn(3).setMinWidth(70);
      	table.getColumnModel().getColumn(3).setMaxWidth(70); 
      	
      	//Ajustar el tamaño de la dificultad
      	table.getColumnModel().getColumn(4).setWidth(70);
      	table.getColumnModel().getColumn(4).setMinWidth(70);
      	table.getColumnModel().getColumn(4).setMaxWidth(70); 
      	
      	//Ajustar el tamaño de las opciones
      	table.getColumnModel().getColumn(5).setWidth(140);
      	table.getColumnModel().getColumn(5).setMinWidth(140);
      	table.getColumnModel().getColumn(5).setMaxWidth(140); 
     
     
        // Oculta la columna "id"
     	table.getColumnModel().getColumn(0).setMinWidth(0);
     	table.getColumnModel().getColumn(0).setMaxWidth(0);
     	table.getColumnModel().getColumn(0).setPreferredWidth(0);
     	
     	//Cambiar el tamaño de las filas
     	table.setRowHeight(50);
        
        JScrollPane scrollPane = new JScrollPane(table);   
        panelDerecha.add(scrollPane, BorderLayout.CENTER);
        
        panelCentral.add(panelDerecha);

        
        
        this.add(panelCentral, BorderLayout.CENTER);
          
        
        boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Iniciar el hilo
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						long inicio = System.currentTimeMillis();
						int variableTiempo = 10;
						while(System.currentTimeMillis()- inicio < 3000) {
							Color primerElemento = listaColores.removeFirst(); // Elimina el primer elemento
				            listaColores.addLast(primerElemento);
							for (int i = 0; i < 10; i++) {
								listaLabels.get(i).setBackground(listaColores.get(i));
							}try {
								Thread.sleep(variableTiempo);
								variableTiempo += 2;
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
						 // Después de 5 segundos, seleccionar un reto aleatorio
                        String retoSeleccionado = listaRetos.get((int) (Math.random() * listaRetos.size()));
                        txtResultado.setText("Tu reto diario es: " + retoSeleccionado);
					}
				}).start();
				
			}
		});
        
        setVisible(true);
    }
    
    class RetosModel extends AbstractTableModel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String[] nombreDatos = {"id", "Nombre", "Fecha", "Dificultad","Completado", "Opciones"}; 
		private Object[][] data;
		
		public void cargarDatosDesdeBD(String user) {
	    	try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("No se ha podido cargar el driver de la BD");
			}
			try {
				Connection conn = DriverManager.getConnection
					("jdbc:sqlite:Sources/bd/baseDeDatos.db");
		
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM RetoDiario WHERE Usuario LIKE ?";
				PreparedStatement queryStmt = conn.prepareStatement(sql);
				queryStmt.setString(1, user);
				ResultSet rs = queryStmt.executeQuery();
				List<Object[]> listaDatos = new ArrayList<>();
				while (rs.next()) {
					int ID_RetoDiario = rs.getInt("ID_RetoDiario");
					String nombre = rs.getString("Nombre");
					String fecha = rs.getString("Fecha");
					Integer dificultad = rs.getInt("Dificultad");
					Integer completado = rs.getInt("Completado");
	                listaDatos.add(new Object[] {ID_RetoDiario, nombre, fecha, dificultad, completado, "Botones"});
	                data = listaDatos.toArray(new Object[0][]);
	                //Notifica a la tabla que los datos han cambiado
	                fireTableDataChanged();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
		
		@Override
		public String getColumnName(int column) {
			return nombreDatos[column];
		}


		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 5;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return nombreDatos.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
    	
    }
    
    class RendererBotonReto extends JPanel implements TableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public RendererBotonReto() {
			setLayout(new FlowLayout(FlowLayout.LEFT));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			this.removeAll();
			
			//Icono si
	        ImageIcon iconoSi = new ImageIcon("Sources/imagenes/tickVerde.png");
	        Image img1 = iconoSi.getImage();
	        Image imgEscalada1 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoSi = new ImageIcon(imgEscalada1);
			
			JButton botonSi1 = new JButton(iconoSi);
			botonSi1.setBackground(Color.white);
			this.add(botonSi1);
			
			//Icono no
			ImageIcon iconoNo = new ImageIcon("Sources/imagenes/cruzroja.png");
	        Image img2 = iconoNo.getImage();
	        Image imgEscalada2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoNo = new ImageIcon(imgEscalada2);
			
			JButton botonNo1 = new JButton(iconoNo);
			botonNo1.setBackground(Color.white);
			this.add(botonNo1);
			
			return this;
		}
    	
    }
    
    class EditorBotonReto extends AbstractCellEditor implements TableCellEditor{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JPanel panel;
		private JTable table;
		private int editingRow = -1;
		
		public EditorBotonReto(String usuario) {
			panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			//Icono si
	        ImageIcon iconoSi = new ImageIcon("Sources/imagenes/tickVerde.png");
	        Image img1 = iconoSi.getImage();
	        Image imgEscalada1 = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoSi = new ImageIcon(imgEscalada1);
			
			JButton botonSi1 = new JButton(iconoSi);
			botonSi1.setBackground(Color.white);
		
			
			//Icono no
			ImageIcon iconoNo = new ImageIcon("Sources/imagenes/cruzroja.png");
	        Image img2 = iconoNo.getImage();
	        Image imgEscalada2 = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoNo = new ImageIcon(imgEscalada2);
	        
	        JButton botonNo1 = new JButton(iconoNo);
	        botonNo1.setBackground(Color.white);
			
	        
	        //Action listener para modificar el estado del reto
			botonSi1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (editingRow != -1) {
						int completado = 1;
						Object id = table.getModel().getValueAt(editingRow, 0);
						completadoReto(id, completado);
						new RetoDiario(usuario);					}
					
				}
			});
			
			botonNo1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (editingRow != -1) {
						int completado = 0;
						Object id = table.getModel().getValueAt(editingRow, 0);
						completadoReto(id, completado);
						new RetoDiario(usuario);
					}	
				}
			});
			
			panel.add(botonSi1);
			panel.add(botonNo1);
			
		}

		protected void completadoReto(Object id, int completado) {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("No se ha podido cargar el driver de la BD");
			}
			
			//Conectar a la BD
			try {
				Connection conn = DriverManager.getConnection
						("jdbc:sqlite:Sources/bd/baseDeDatos.db");
				
				
				String sql = "UPDATE RetoDiario SET COMPLETADO = "+completado+" WHERE id ="+id ;
				PreparedStatement queryStmt = conn.prepareStatement(sql);
				
				queryStmt.close();
				conn.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			
		}

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.table = table;
			this.editingRow = row;
			return panel;
		}
    	
    }
    
    public static void main(String[] args) {
		new RetoDiario("admin");
	}
}


