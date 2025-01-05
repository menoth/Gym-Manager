package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import domain.Ejercicio;
import domain.EjercicioEnEntrenamiento;
import domain.Entrenamiento;
import domain.Musculo;
import domain.Rutina;
import domain.Serie;


public class EstadisticasRutina extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EstadisticasRutina(Rutina rutina){
		
		//Detalles de la ventana
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Separamos la ventana con gridlayout en dos 
        this.setLayout(new GridLayout(1,2));
        
        //Datos para las estadísticas, de momento comentados
        String nombre = rutina.getNombre();
        Double volumenEn = 0.0;
        ArrayList<Entrenamiento> entrenamientos = rutina.getEntrenamientos();
        ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
        ConectarBaseDeDatos.ConectarBaseDeDatosEjercicios(ejercicios);
        
        //Bucle para calcular el volumen de entrenamiento
        double VolumenTotal = 0;
        int NumeroEjercicios = 0;
        for (Entrenamiento entrenamiento : entrenamientos) {
        	for(EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
        		for(Serie serie : ejercicio.getSeries()) {
        			VolumenTotal+=(serie.getPeso()*serie.getRepeticiones());
        		}
        	}
		}
        for (Entrenamiento entrenamiento : entrenamientos) {
        	for(EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
        		NumeroEjercicios+=1;
        	}
		}
        
        
//------------------------------LADO IZQUIERDO-------------------------------------------------------
        
        JPanel pIzquierda = new JPanel();
        pIzquierda.setBackground(new Color(255, 255, 255));

        pIzquierda.setLayout(new GridLayout(3, 1));
        pIzquierda.setBorder(new EmptyBorder(0,0,0,30));
        pIzquierda.setBorder(new LineBorder(Color.BLACK, 3));
        
        
        //Panel con el nombre que aparece en la parte superior
        JPanel panelNombre = new JPanel();
        panelNombre.setForeground(new Color(255, 255, 255));
        panelNombre.setLayout(new BorderLayout());
        panelNombre.setBorder(new EmptyBorder(80,20,20,20));
        
        JTextArea txtNombreRutina = new JTextArea("Estas son las estadísticas de tu rutina "+ nombre +", en la grafica inferior podrás ver la intensidad de trabajo de cada músuclo, siendo el valor más optimo el 12.");
        txtNombreRutina.setFont(new Font("Arial", Font.BOLD, 18));
        txtNombreRutina.setBackground(this.getBackground());
        
        txtNombreRutina.setLineWrap(true);          
        txtNombreRutina.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtNombreRutina.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtNombreRutina.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtNombreRutina.setDisabledTextColor(Color.black);
        
        panelNombre.add(txtNombreRutina, BorderLayout.NORTH);
        pIzquierda.add(panelNombre);
        
        //Panel para el Volumen de entrenamiento
        JPanel pVolumen = new JPanel();
        pVolumen.setForeground(new Color(255, 255, 255));
        pVolumen.setLayout(new BorderLayout(0,15));
        pVolumen.setBorder(new EmptyBorder(20,20,20,20));
        
        JLabel volumenEntrenamiento = new JLabel("VOLUMEN DE ENTRENAMIENTO");
        volumenEntrenamiento.setFont(new Font("Arial", Font.BOLD, 22));
        
        JTextArea txtVolumen = new JTextArea("Has movido un total de " + VolumenTotal + " kg este semana dividido en " + rutina.getEntrenamientos().size() + " entrenamientos y "+ NumeroEjercicios + " ejercicios diferentes");
        txtVolumen.setFont(new Font("Arial", Font.PLAIN, 20));
        //txtVolumen.setBackground(this.getBackground());
        
        txtVolumen.setLineWrap(true);          
        txtVolumen.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtVolumen.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtVolumen.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtVolumen.setDisabledTextColor(Color.black);
        
        
        pVolumen.add(volumenEntrenamiento, BorderLayout.NORTH);
        pVolumen.add(txtVolumen, BorderLayout.CENTER);
        
//----------------------gráfico-----------------------------
        JPanel panelGrafico = new CustomChart(CrearMapaGrafico(rutina, ejercicios));
        
        pIzquierda.add(pVolumen);
        pIzquierda.add(panelGrafico);
        
        //Añadimos el panel izquierda
        add(pIzquierda);
        
        
        
//------------------------------LADO DERECHO------------------------------
        JPanel pDerecha = new JPanel();
        pDerecha.setForeground(new Color(255, 255, 255));
        pDerecha.setBorder(new EmptyBorder(0,30,0,0));
        pDerecha.setLayout(new GridLayout(3,1));
        pDerecha.setBorder(new LineBorder(Color.BLACK, 3));
        
        //Primer panel
        JPanel panelDerecha1 = new JPanel();
        panelDerecha1.setForeground(new Color(255, 255, 255));
        panelDerecha1.setLayout(new BorderLayout(0,0));
        panelDerecha1.setBorder(new EmptyBorder(100, 10, 30,0));
        
        JLabel optimizacion = new JLabel("OPTIMIZACIÓN");
        optimizacion.setFont(new Font("Arial", Font.BOLD, 22));
        
        JTextArea txtOptimizacion = new JTextArea("Según nuestro algoritmo este es el desglose de la rutina:");
        txtOptimizacion.setFont(new Font("Arial", Font.PLAIN, 20));
        txtOptimizacion.setBackground(this.getBackground());
        
        txtOptimizacion.setLineWrap(true);          
        txtOptimizacion.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtOptimizacion.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtOptimizacion.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtOptimizacion.setDisabledTextColor(Color.black);
        
        panelDerecha1.add(optimizacion, BorderLayout.CENTER);
        panelDerecha1.add(txtOptimizacion, BorderLayout.SOUTH);
        
        pDerecha.add(panelDerecha1);
        
        //Segundo panel derecho con las recomendaciones de optimización
        JPanel musculosOpt = new JPanel();
        musculosOpt.setForeground(new Color(255, 255, 255));
        musculosOpt.setLayout(new GridLayout(3,2));
        musculosOpt.setBorder(new EmptyBorder(0,30,20,20));
        
        //
        JTextArea opt1 = new JTextArea("Entrenas de manera SUPERFICIAL los siguientes músculos: ");       
        opt1.setFont(new Font("Arial", Font.PLAIN, 18));
        opt1.setBackground(new Color(153,204,255));
        opt1.setLineWrap(true);          
        opt1.setWrapStyleWord(true); 
        //Para que no se pueda editar
        opt1.setEditable(false);  
        //Para que no esté el cursor sobre el TxtArea
        opt1.setEnabled(false);
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        opt1.setDisabledTextColor(Color.black);
        
        //
        JTextArea opt2 = new JTextArea("Entrenas de manera OPTIMA los siguientes músculos: ");
        opt2.setFont(new Font("Arial", Font.PLAIN, 18));
        opt2.setBackground(new Color(153,255,153));
        opt2.setLineWrap(true);          
        opt2.setWrapStyleWord(true); 
        //Para que no se pueda editar
        opt2.setEditable(false);  
        //Para que no esté el cursor sobre el TxtArea
        opt2.setEnabled(false);
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        opt2.setDisabledTextColor(Color.black);
        
        //
        JTextArea opt3 = new JTextArea("Estás SOBREENTRENANDO los siguientes músculos: ");
        opt3.setFont(new Font("Arial", Font.PLAIN, 18));
        opt3.setBackground(new Color(255,102,102));
        opt3.setLineWrap(true);          
        opt3.setWrapStyleWord(true); 
        //Para que no se pueda editar
        opt3.setEditable(false);  
        //Para que no esté el cursor sobre el TxtArea
        opt3.setEnabled(false);
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        opt3.setDisabledTextColor(Color.black);
        
        HashMap<String, Double> mapa = CrearMapaGrafico(rutina, ejercicios);
        for(String musculo : mapa.keySet()) {
        	if (mapa.get(musculo) >=15) {
				opt3.append(musculo + ", ");
			} else if (mapa.get(musculo)<=10) {
				opt1.append(musculo + ", ");
			} else {
				opt2.append(musculo + ", ");
			}
        }
        musculosOpt.add(opt1);
        musculosOpt.add(opt2);
        musculosOpt.add(opt3);
        
        pDerecha.add(musculosOpt);
        
        // Panel de información
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE); 
        panelInfo.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        // Configuración del GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        // Foto con el logo de información
        ImageIcon logoInfo = new ImageIcon("Sources/imagenes/info.png");
        Image imagenVitrina1 = logoInfo.getImage();
        Image nuevaImagen1 = imagenVitrina1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logoInfo = new ImageIcon(nuevaImagen1);
        JLabel labelLogoInfo = new JLabel(logoInfo);

        //Agregarlo a la izquierda de lo que queda del pDerecha
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        panelInfo.add(labelLogoInfo, gbc);

        // Texto con la información
        JTextArea informacion = new JTextArea(
            "Recuerda que para ajustar el nivel de fatiga que le provocamos a un músculo, puedes ajustar la frecuencia con la que lo entrenas o el nivel de intensidad de cada serie."
        );
        informacion.setFont(new Font("Arial", Font.PLAIN, 17));
        informacion.setBackground(Color.WHITE);
        informacion.setLineWrap(true); 
        informacion.setWrapStyleWord(true); 
        informacion.setEditable(false);
        informacion.setEnabled(false); 
        informacion.setDisabledTextColor(Color.BLACK); 
        informacion.setBorder(new LineBorder(Color.BLACK, 2)); 
        informacion.setPreferredSize(new Dimension(400, 100)); 

        //Agregarlo en el centro de lo que deja la imagen libre en el pDerecha y expandirse horizontalmente
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0; 
        panelInfo.add(informacion, gbc);

        
        pDerecha.add(panelInfo, BorderLayout.CENTER);

        // Agrega el panel derecho a la ventana principal
        add(pDerecha);

        // Hace visible el marco
        setVisible(true);
	}
	
	class CustomChart extends JPanel {
        private static final long serialVersionUID = 1L;

        //Datos de prueba
        ArrayList<Double> datos = new ArrayList<Double>();
        ArrayList<String> musculos = new ArrayList<String>();
        
        public CustomChart(HashMap<String, Double> mapa) {
        	
        	for(String musculo : mapa.keySet()) {
            	musculos.add(musculo);
            	datos.add(mapa.get(musculo));
        	}
        	
        }

		@Override
        protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            //Dimensiones del panel
            int width = getWidth();
            int height = getHeight();

            //márgenes para el gráfico
            int margenIzquierdo = 50;
            int margenInferior = 50;

            // Dimensiones del área del gráfico
            int anchoGrafico = width - margenIzquierdo - 20;
            int altoGrafico = height - margenInferior - 20;

            // Dibujar ejes
            g2.setColor(Color.BLACK);
            
            //Traza una línea desde la parte inferior izquierda (x=margenIzquierdo, y=height-margenInferio)
            //a la parte superior izquierda (x=margenIzquierdo, y	=20)
            g2.drawLine(margenIzquierdo, height - margenInferior, margenIzquierdo, 20);
            
            //Traza una línea desde la parte inferior izquierda (x=margenIzquierdo, y=height-margenInferio)
            //a la parte inferior derecha (x=ancho-20, height-margenInferior)
            g2.drawLine(margenIzquierdo, height - margenInferior, width - 20, height - margenInferior);

            //---------------Detalles del eje Y-------------
            //Esto hace que el valor del eye Y sea entre 0-10, además hace que haya 10 puntos de referencia 
            double maxY = 30;
            for (int i = 0; i <= 10; i++) { // 10 divisiones
                int y = height - margenInferior - (int) (i * altoGrafico / 10);
                double valorY = i * maxY / 10;
                g2.drawLine(margenIzquierdo - 5, y, margenIzquierdo, y);
                
                //Hecho con chatGPT4, pone los 10 valores de referencia 
                g2.drawString(String.format("%.1f", valorY), margenIzquierdo - 35, y + 5);
            }

            //------------------Detalles del eje X------------------
            int numEtiquetas = musculos.size();
            int anchoBarra = anchoGrafico / (numEtiquetas * 2);
            
            //Escribe los musculos 
            for (int i = 0; i < numEtiquetas; i++) {
            	g2.setFont(new Font("SansSerif", Font.PLAIN, anchoBarra/4));
                int x = margenIzquierdo + i * (anchoGrafico / numEtiquetas) + anchoBarra / 2;
                g2.drawString(musculos.get(i), x, height - margenInferior + 20);
            }

            //-------------LÍNEA VERDE DEL VALOR ÓPTIMO---------------------
            //Con esto se calcula el y de la línea verde, es decir el punto 1
            int yLineaVerde = height - margenInferior - (int) (altoGrafico * 12.0 / maxY);
            
            g2.setColor(Color.GREEN);
            
            //Para cambiar el grosor de la línea verde a 3 px
            g2.setStroke(new BasicStroke(3.0f)); 
            g2.drawLine(margenIzquierdo, yLineaVerde, width - 20, yLineaVerde);

            //Colocar las barras del gráfico hecho con chatGpt4
            g2.setColor(Color.BLACK);
            for (int i = 0; i < datos.size(); i++) {
                int x = margenIzquierdo + i * (anchoGrafico / numEtiquetas) + anchoBarra / 2;
                int y = height - margenInferior - (int) (datos.get(i) * altoGrafico / maxY);
                int alturaBarra = height - margenInferior - y;

                g2.fillRect(x, y, anchoBarra, alturaBarra); // Barra
            }
        }
      }
    
	public HashMap<String, Double> CrearMapaGrafico(Rutina rutina, ArrayList<Ejercicio> ejercicios) {
    	HashMap<String, Double> mapa = new HashMap<String, Double>();
    	for(Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
    		for(EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
    			for(Ejercicio ejercicio2 : ejercicios) {
    				if (ejercicio.getID_Ejercicio()== ejercicio2.getId()) {
    					for(Serie serie : ejercicio.getSeries()) {
							Double valorTamaño = 0.0;
							Double valorTamaño2 = 0.0;
							Double valorRPE = 0.0;
							if (ejercicio2.getMusculoPrincipal().getTamanoMusculo().equals(Musculo.TamanoMusculo.PEQUENO.toString())) {
								valorTamaño = 1.5;
							} else if (ejercicio2.getMusculoPrincipal().getTamanoMusculo().equals(Musculo.TamanoMusculo.MEDIANO.toString())) {
								valorTamaño = 1.0;
							} else { 
								valorTamaño = 0.5;
							}
							if (ejercicio2.getMusculoSecundario().getTamanoMusculo().equals(Musculo.TamanoMusculo.PEQUENO.toString())) {
								valorTamaño2 = 0.75;
							} else if (ejercicio2.getMusculoSecundario().getTamanoMusculo().equals(Musculo.TamanoMusculo.MEDIANO.toString())) {
								valorTamaño2 = 0.5;
							} else { 
								valorTamaño2 = 0.25;
							}
							if (serie.getEsfuerzo().equals(Serie.Esfuerzo.TOPSET)) {
								valorRPE = 1.5;
							} else if (serie.getEsfuerzo().equals(Serie.Esfuerzo.ESTANDAR)) {
								valorRPE = 1.0;
							} else { 
								valorRPE = 0.5;
							}
							if (!mapa.containsKey(ejercicio2.getMusculoPrincipal().getNombre())) {
								mapa.put(ejercicio2.getMusculoPrincipal().getNombre(), valorTamaño*valorRPE);
							} else {
								double valorPuesto = mapa.get(ejercicio2.getMusculoPrincipal().getNombre());
								mapa.put(ejercicio2.getMusculoPrincipal().getNombre(), valorTamaño*valorRPE+valorPuesto);
							}
							if (!mapa.containsKey(ejercicio2.getMusculoSecundario().getNombre())) {
								mapa.put(ejercicio2.getMusculoSecundario().getNombre(), valorTamaño2*valorRPE);
							} else {
								double valorPuesto2 = mapa.get(ejercicio2.getMusculoSecundario().getNombre());
								mapa.put(ejercicio2.getMusculoSecundario().getNombre(), valorTamaño2*valorRPE+valorPuesto2);
							}
						}
    				}
    			}
    		}
    	}
    return mapa;
	}
}