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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RetoDiario extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	HashMap<String, Integer> retos = new HashMap<>();
	 LinkedList<Color> listaColores = new LinkedList<>();
	 ArrayList<JLabel> listaLabels = new ArrayList<>();
	 ArrayList<String> listaRetos = new ArrayList<>();
	
    public RetoDiario(String usuario) {
    	setUndecorated(true);
        setTitle("Reto diario");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        cargarRetos();
        
        // Lista de retos
       for (String reto : retos.keySet()) {
		listaRetos.add(reto);
	}
       
        this.setLayout(new BorderLayout());
        
//--------------------------------------LADO SUPERIOR----------------------------------
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50,20));
        panelSuperior.setBackground(new Color(70, 130, 180));
        
        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setPreferredSize(new Dimension(120, 60));
		
        botonVolver.setBackground(new Color(255, 255, 255));
        botonVolver.setForeground(new Color(70, 130, 180));
        botonVolver.setFont(new Font("Arial", Font.BOLD, 16));
        
        botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PrincipalWindow(usuario);
				
			}
		});
        
        panelSuperior.add(botonVolver);
        
 

        
        JTextArea txtExplicacion = new JTextArea("¡Apúntate al reto diario para esos días en los que te sientes con motivación extra! Las dificultades van del 1 (mínimo) al 5 (máximo)");
        txtExplicacion.setFont(new Font("Arial", Font.BOLD, 24));
        txtExplicacion.setBackground(new Color(70, 130, 180));
        
        txtExplicacion.setPreferredSize(new Dimension(800, 70));
        
        txtExplicacion.setLineWrap(true);          
        txtExplicacion.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtExplicacion.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtExplicacion.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtExplicacion.setDisabledTextColor(new Color(255, 255, 255));
        panelSuperior.add(txtExplicacion);

        this.add(panelSuperior, BorderLayout.NORTH);

//-----------------------PARTE CENTRAL------------------------------------
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1, 2));
        panelCentral.setBackground(new Color(70, 130, 180));
        panelCentral.setBorder(new EmptyBorder(20,20,20,20));
        
        //----------LADO IZQUIERDO CENTRAL
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new GridLayout(3, 1));
        panelIzquierda.setBorder(new EmptyBorder(0,0,0,10));
        panelIzquierda.setBackground(new Color(70, 130, 180));
        
        //1 DE 3 partes LADO IZQUIERDO CENTRAL
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 70));
        
        JButton boton = new JButton("OBTEN RETO");
        boton.setBackground(new Color(255, 255, 255));
        boton.setForeground(new Color(70, 130, 180));
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(160,60));
        
        
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
        txtResultado.setDisabledTextColor(new Color(70, 130, 180));
        
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
        panelDerecha.setBackground(new Color(70, 130, 180));
        panelDerecha.setLayout(new BorderLayout());
        
        //Label retos pasados
        JLabel retosLabel = new JLabel("Registro de retos");
        retosLabel.setForeground(new Color(255, 255, 255));
        retosLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        
        panelDerecha.add(retosLabel, BorderLayout.NORTH);
        
        RetosModel modelo = new RetosModel(); 
        modelo.cargarDatosDesdeBD(usuario);
        JTable table = new JTable(modelo);
        

      	TableCellRenderer cellRenderer = (table2, value, isSelected, hasFocus, row, column) -> {
      			
      
      		if(column == 1) {
      			JTextArea desc = new JTextArea(value.toString());
        		desc.setBackground(new Color(255,255,255));
        		
        		// Detalles del JTextArea
        		desc.setWrapStyleWord(true);
        		desc.setLineWrap(true);
        		desc.setEditable(false);
        		desc.setFont(new Font("Arial", Font.BOLD, 15));
          			
          		return desc;
      		}else {
      			JLabel result = new JLabel(value.toString());			
      			result.setHorizontalAlignment(JLabel.CENTER);
      			
      			result.setFont(new Font("Arial", Font.BOLD, 13));
      			
      			result.setOpaque(true);
      			result.setBackground(new Color(255, 255, 255));
      			result.setBackground(new Color(255, 255, 255));
      			
      			return result;
      		}
    		
      	};
      		
      		//Se define un CellRenderer para las cabeceras de las dos tabla usando una expresión lambda
      		TableCellRenderer headerRenderer = (table3, value, isSelected, hasFocus, row, column) -> {
      			JLabel result = new JLabel(value.toString());			
      			result.setHorizontalAlignment(JLabel.CENTER);
      			
      			result.setFont(new Font("Arial", Font.BOLD, 13));
      			
      			result.setBackground(new Color(25,25,112));
      			result.setForeground(new Color(255, 255, 255));
      			
      			result.setOpaque(true);
      			
      			return result;
      		};        
        
      	table.getTableHeader().setDefaultRenderer(headerRenderer);		
    	table.setDefaultRenderer(Object.class, cellRenderer);
    		
        table.getColumnModel().getColumn(5).setCellRenderer(new RendererBotonReto());
        table.getColumnModel().getColumn(5).setCellEditor(new EditorBotonReto(usuario, modelo));
        
        
        //Ajustar el tamaño de la fecha
      	table.getColumnModel().getColumn(2).setWidth(75);
      	table.getColumnModel().getColumn(2).setMinWidth(75);
      	table.getColumnModel().getColumn(2).setMaxWidth(75);
      	
      	//Ajustar el tamaño de la dificultad
      	table.getColumnModel().getColumn(3).setWidth(80);
      	table.getColumnModel().getColumn(3).setMinWidth(80);
      	table.getColumnModel().getColumn(3).setMaxWidth(80); 
      	
      	//Ajustar el tamaño de la dificultad
      	table.getColumnModel().getColumn(4).setWidth(80);
      	table.getColumnModel().getColumn(4).setMinWidth(80);
      	table.getColumnModel().getColumn(4).setMaxWidth(80); 
      	
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
				
				if(hayReto(usuario)) {
					JOptionPane.showMessageDialog(RetoDiario.this, "Ya tienes un reto asignado para hoy, vuelve mañana");
				}else {
					
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
                        
                        escribirReto(retoSeleccionado, usuario);
                        modelo.cargarDatosDesdeBD(usuario);
					}
					
				}).start();
				
			}
			}
		});
             
        setVisible(true);
    }
    
    protected void cargarRetos() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha podido cargar el driver de la BD");
            e.printStackTrace();
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");  
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT Nombre, Dificultad FROM ListadoRetos";
            PreparedStatement queryStmt = conn.prepareStatement(sql);
            
            ResultSet rs = queryStmt.executeQuery();
            
            while (rs.next()) {
                String nombreReto = rs.getString("Nombre"); 
                int dificultad = rs.getInt("Dificultad");   
                retos.put(nombreReto, dificultad);  
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void escribirReto(String retoSeleccionado, String usuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
			e.printStackTrace();
		}try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");	
			Statement stmt = conn.createStatement();
			
			String nombre = retoSeleccionado;
			
			Date fechaActual = new Date();
	        // Formatear la fecha en formato día-mes-año
	        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	        String fecha = formatoFecha.format(fechaActual);
			
			Integer dificultad = 0;
			
			//Bucle para tener su dificultad
			for (Integer reto : retos.values()) {
				dificultad = reto;
			}
			
			String sql = "INSERT INTO RetoDiario (Nombre, Fecha, Dificultad, Completado, Usuario) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			
			queryStmt.setString(1, nombre);
			queryStmt.setString(2, fecha);
			queryStmt.setInt(3, dificultad);
			queryStmt.setInt(4, 0);
			queryStmt.setString(5, usuario);

			int filasInsertadas = queryStmt.executeUpdate();
            System.out.println("Filas insertadas: " + filasInsertadas);
			
            stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    
    private boolean hayReto(String usuario) {
		Boolean resultado = false;
		try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }
	    try {
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
			Statement stmt = conn.createStatement();

	        // Obtener la fecha actual en el formato de la base de datos
	        Date fechaActual = new Date();
	        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
	        String fechaHoy = formatoFecha.format(fechaActual);

	        String sql = "SELECT COUNT(*) AS total FROM RetoDiario WHERE Usuario = ? AND Fecha = ?";
	        PreparedStatement queryStmt = conn.prepareStatement(sql);
	        queryStmt.setString(1, usuario);
	        queryStmt.setString(2, fechaHoy);

	        ResultSet rs = queryStmt.executeQuery();
	        if (rs.next() && rs.getInt("total") > 0) {
	            resultado = true;
	        }
			stmt.close();
			conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    	
    	return resultado;
	}
    
    class RetosModel extends AbstractTableModel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String[] nombreDatos = {"id", "Nombre", "Fecha", "Dificultad","Completado", "Opciones"}; 
		private Object[][] data = new Object[0][0];
		
		
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
	                
	            }
				fireTableDataChanged();	
				stmt.close();
				conn.close();
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
		    if (columnIndex == 4) { 
		        Integer completado = (Integer) data[rowIndex][columnIndex];
		        if (completado == 0) {
		            return "No";
		        } else if (completado == 1) {
		            return "Sí";
		        }
		    } 
		    return data[rowIndex][columnIndex];
		}

    	
    }
    
    class RendererBotonReto extends JPanel implements TableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public RendererBotonReto() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
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
		
		public EditorBotonReto(String usuario, RetosModel modelo) {
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
						modelo.cargarDatosDesdeBD(usuario);
					}
					
				}
			});
			
			botonNo1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (editingRow != -1) {
						int completado = 0;
						Object id = table.getModel().getValueAt(editingRow, 0);
						completadoReto(id, completado);
						modelo.cargarDatosDesdeBD(usuario);
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
				
				
				
				String sql = "UPDATE RetoDiario SET Completado = "+completado+" WHERE ID_RetoDiario ="+id ;
				PreparedStatement queryStmt = conn.prepareStatement(sql);
				
				int filasInsertadas = queryStmt.executeUpdate();
	            System.out.println("Filas insertadas: " + filasInsertadas);
				
				queryStmt.close();
				conn.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
			
		}

		@Override
		public Object getCellEditorValue() {
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
}

