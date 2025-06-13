package jogo_da_velha;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

// Aluno: Jonas Gabriel Sarmento de Figueiredo 20241370009

public class TelaJogo {

	private JFrame frame;
	private JLabel[][] grid;
	private JogoDaVelha jogo;
	private LinkedHashMap <Integer, String> historico;
	
	private int nivelIa = 0;
	private final String[] ResultadoVencedor = {"012", "345", "678", "036", "147", "258", "048", "246"};
	private String[] niveis;
	
	// botoes menu
	private JButton btnDoisJogadores;
	private JButton btnContraMaquina;
	private JButton btnNovaPartida;
	private JButton btnHistorico;
	private JButton btnStart;
	
	// inputs menu
	private JTextField inputSimboloP1;
	private JTextField inputSimboloP2;
	private JComboBox<String> inputIaNivel;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaJogo window = new TelaJogo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaJogo() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setTitle("Jogo da velha");
		frame.setBounds(0, 0, 600, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

	    btnDoisJogadores = new JButton("2 Jogadores");
	    btnDoisJogadores.setBounds(150, 200, 300, 50); 
	    btnDoisJogadores.setBackground(Color.WHITE);
	    btnDoisJogadores.setForeground(Color.BLACK);
	    btnDoisJogadores.setFont(new Font("Arial", Font.BOLD, 18));
	    frame.getContentPane().add(btnDoisJogadores); 

	    btnContraMaquina = new JButton("Contra a Máquina");
	    btnContraMaquina.setBounds(150, 280, 300, 50); 
	    btnContraMaquina.setBackground(Color.WHITE);
	    btnContraMaquina.setForeground(Color.BLACK);
	    btnContraMaquina.setFont(new Font("Arial", Font.BOLD, 18));
	    frame.getContentPane().add(btnContraMaquina); 
	   
	    
	    // listeners
	    btnDoisJogadores.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	load2Players();
	        }
	    });
	    btnContraMaquina.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            loadWithMachine();
	        }
	    });

	}
	
	
	private void load2Players() {
		frame.getContentPane().remove(btnContraMaquina);
		frame.getContentPane().remove(btnDoisJogadores);
	    
	    btnNovaPartida = new JButton("Nova Partida");
	    btnNovaPartida.setBounds(110, 20, 180, 50);
	    btnNovaPartida.setBackground(Color.WHITE);
	    btnNovaPartida.setForeground(Color.BLACK);
	    btnNovaPartida.setFont(new Font("Arial", Font.BOLD, 16));
	    frame.getContentPane().add(btnNovaPartida);

	    btnHistorico = new JButton("Histórico");
	    btnHistorico.setBounds(310, 20, 180, 50);
	    btnHistorico.setBackground(Color.WHITE);
	    btnHistorico.setForeground(Color.BLACK);
	    btnHistorico.setFont(new Font("Arial", Font.BOLD, 16));
	    btnHistorico.setEnabled(false);
	    btnHistorico.setVisible(false);
	    frame.getContentPane().add(btnHistorico);
	    
	    btnStart = new JButton("Start");
	    btnStart.setBounds(210, 140, 180, 50);
	    btnStart.setBackground(Color.WHITE);
	    btnStart.setForeground(Color.BLACK);
	    btnStart.setFont(new Font("Arial", Font.BOLD, 16));
	    btnStart.setEnabled(false);
	    btnStart.setVisible(false);
	    frame.getContentPane().add(btnStart);
	    
	    inputSimboloP1 = new JTextField("X");
	    inputSimboloP1.setBounds(200, 90, 80, 40);
	    inputSimboloP1.setFont(new Font("Arial", Font.BOLD, 24));
	    inputSimboloP1.setHorizontalAlignment(SwingConstants.CENTER);
	    inputSimboloP1.setEnabled(false);
	    inputSimboloP1.setVisible(false);
	    frame.getContentPane().add(inputSimboloP1);
	    
	    inputSimboloP2 = new JTextField("O");
	    inputSimboloP2.setBounds(320, 90, 80, 40);
	    inputSimboloP2.setFont(new Font("Arial", Font.BOLD, 24));
	    inputSimboloP2.setHorizontalAlignment(SwingConstants.CENTER);
	    inputSimboloP2.setEnabled(false);
	    inputSimboloP2.setVisible(false);
	    frame.getContentPane().add(inputSimboloP2);
	    
	    frame.revalidate();
	    frame.repaint();
	    
	    // listeners
	    btnNovaPartida.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		inputSimboloP1.setEnabled(true);
	            inputSimboloP2.setEnabled(true);
	            inputSimboloP1.setVisible(true);
	            inputSimboloP2.setVisible(true);
	            btnStart.setEnabled(true);
	            btnStart.setVisible(true);
	    		;
	    	}
	    });
	    btnStart.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		limparGrid();
	    		jogo = new JogoDaVelha(inputSimboloP1.getText(), inputSimboloP2.getText());
	    		
	    		btnHistorico.setEnabled(true);
	    	    btnHistorico.setVisible(true);
	    	    inputSimboloP1.setEnabled(false);
	            inputSimboloP2.setEnabled(false);
	            btnStart.setEnabled(false);
	            
	    	    loadGrid();
	    	    frame.revalidate();
	    	    frame.repaint();
	    	    
	    	    btnHistorico.addActionListener(new ActionListener() {
	    	    	public void actionPerformed (ActionEvent e) {
	    	    		historico = jogo.getHistorico();
	    	    		JOptionPane.showMessageDialog(frame, historico);
	    	    		
	    	    	}
	    	    });
	    	}
	    	
	    });
	    
	   
	}
	
	private void loadWithMachine() {
		frame.getContentPane().removeAll();
	    
	    btnNovaPartida = new JButton("Nova Partida");
	    btnNovaPartida.setBounds(110, 20, 180, 50);
	    btnNovaPartida.setBackground(Color.WHITE);
	    btnNovaPartida.setForeground(Color.BLACK);
	    btnNovaPartida.setFont(new Font("Arial", Font.BOLD, 16));
	    frame.getContentPane().add(btnNovaPartida);

	    btnHistorico = new JButton("Histórico");
	    btnHistorico.setBounds(310, 20, 180, 50);
	    btnHistorico.setBackground(Color.WHITE);
	    btnHistorico.setForeground(Color.BLACK);
	    btnHistorico.setFont(new Font("Arial", Font.BOLD, 16));
	    btnHistorico.setEnabled(false);
	    btnHistorico.setVisible(false);
	    frame.getContentPane().add(btnHistorico);
	    
	    btnStart = new JButton("Start");
	    btnStart.setBounds(210, 140, 180, 50);
	    btnStart.setBackground(Color.WHITE);
	    btnStart.setForeground(Color.BLACK);
	    btnStart.setFont(new Font("Arial", Font.BOLD, 16));
	    btnStart.setEnabled(false);
	    btnStart.setVisible(false);
	    frame.getContentPane().add(btnStart);
	    
	    inputSimboloP1 = new JTextField("X");
	    inputSimboloP1.setBounds(200, 90, 100, 50);
	    inputSimboloP1.setFont(new Font("Arial", Font.BOLD, 24));
	    inputSimboloP1.setHorizontalAlignment(SwingConstants.CENTER);
	    inputSimboloP1.setEnabled(false);
	    inputSimboloP1.setVisible(false);
	    frame.getContentPane().add(inputSimboloP1);
	    
	    niveis = new String[]{"Nível 1", "Nível 2"};
	    inputIaNivel = new JComboBox<>(niveis);
	    inputIaNivel.setBounds(320, 90, 100, 40);
	    inputIaNivel.setFont(new Font("Arial", Font.PLAIN, 14));
	    inputIaNivel.setEnabled(false);
	    inputIaNivel.setVisible(false);
	    frame.getContentPane().add(inputIaNivel);
	    
	    frame.revalidate();
	    frame.repaint();
	    
	    // listeners
	    btnNovaPartida.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		inputSimboloP1.setEnabled(true);
	    		inputIaNivel.setEnabled(true);
	            inputSimboloP1.setVisible(true);
	            inputIaNivel.setVisible(true);
	            btnStart.setEnabled(true);
	            btnStart.setVisible(true);
	    	}
	    });
	    btnStart.addActionListener(new ActionListener() {
	    	public void actionPerformed (ActionEvent e) {
	    		limparGrid();
	    		nivelIa = inputIaNivel.getSelectedIndex() + 1;
	    		jogo = new JogoDaVelha(inputSimboloP1.getText(), nivelIa);
	    		
	    		btnHistorico.setEnabled(true);
	    	    btnHistorico.setVisible(true);
	    	    inputIaNivel.setEnabled(false);
	    	    inputIaNivel.setEnabled(false);
	            btnStart.setEnabled(false);
	            
	    	    loadGrid();
	    	    frame.revalidate();
	    	    frame.repaint();
	    	    
	    	    btnHistorico.addActionListener(new ActionListener() {
	    	    	public void actionPerformed (ActionEvent e) {
	    	    		historico = jogo.getHistorico();
	    	    		JOptionPane.showMessageDialog(frame, historico);
	    	    		
	    	    	}
	    	    });
	    	}
	    	
	    });
	    
	}
	
	// grid
	private void loadGrid() {
		int n = 3; 
		grid = new JLabel[n][n];
		int contador = 0;
		int d1 = 105; 
		int d2 = 210; 
		for (int y = 0; y < n; y++) { 
			for (int x = 0; x < n; x++) {
				grid[x][y] = new JLabel(contador + ""); 
				contador++;
				final int posicao = contador - 1;
				frame.getContentPane().add(grid[x][y]);
				grid[x][y].setBounds(d1+x*130, d2+y*130, 130, 130); 
				grid[x][y].setBackground(Color.white);
				grid[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				grid[x][y].setBorder(new LineBorder(Color.BLACK, 1, true)); 
				grid[x][y].setOpaque(true);
				grid[x][y].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						JLabel label = (JLabel) e.getSource();
						if (jogo.terminou()) {
							return;
						}
						int vez = (jogo.getHistorico().size() % 2) + 1;
						
						try {
							jogo.jogaJogador(vez, posicao);
							label.setText(jogo.getSimbolo(vez));
							label.setFont(new Font("Arial", Font.BOLD, 80));						
							if (jogo.terminou()) {
								fimDeJogo();
							}
							
							if (nivelIa > 0 && !jogo.terminou()) {
				                jogo.jogaMaquina();
				                ArrayList<Integer> posicoesJogadas = new ArrayList<>(jogo.getHistorico().keySet());
				                int ultimaPosicao = posicoesJogadas.get(posicoesJogadas.size() - 1);
				                int maquinaY = ultimaPosicao / 3; 
				                int maquinaX = ultimaPosicao % 3; 
				                
				                grid[maquinaX][maquinaY].setText(jogo.getSimbolo(2));
				                grid[maquinaX][maquinaY].setFont(new Font("Arial", Font.BOLD, 80));
								if (jogo.terminou()) {
									fimDeJogo();
								}
				             
				            }

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, ex.getMessage());
						}

					}
				});
			}
		}
		
	}
	
	private void limparGrid() {
	    if (grid != null) {
	        for (int y = 0; y < 3; y++) {
	            for (int x = 0; x < 3; x++) {
	                if (grid[x][y] != null) {
	                    frame.getContentPane().remove(grid[x][y]);
	                }
	            }
	        }
	        grid = null;
	        frame.revalidate();
	        frame.repaint();
	    }
	}
	
	
	private void fimDeJogo() {
		if (jogo.terminou()) {
			int resultado = jogo.getResultado();
			String mensagemFinal;
			if (resultado == 0) {
				mensagemFinal = "O jogo terminou em empate!";
				JOptionPane.showMessageDialog(frame, mensagemFinal);
			} else {
				String simboloVencedor = jogo.getSimbolo(resultado);
				String[] celulas = new String[9];
				
			    for (java.util.Map.Entry<Integer, String> entry : jogo.getHistorico().entrySet()) {
			        celulas[entry.getKey()] = entry.getValue();
			    }
			    
				for (String checkResultado : ResultadoVencedor) {
					
					int indice1 = Character.getNumericValue(checkResultado.charAt(0));
					int indice2 = Character.getNumericValue(checkResultado.charAt(1));
					int indice3 = Character.getNumericValue(checkResultado.charAt(2));
					
					if (simboloVencedor.equals(celulas[indice1]) &&
				        simboloVencedor.equals(celulas[indice2]) &&
				        simboloVencedor.equals(celulas[indice3])) {

				            int[] colorir = {indice1, indice2, indice3};

				            for (int pos : colorir) {
				                int y = pos / 3;
				                int x = pos % 3;
				                grid[x][y].setBackground(Color.YELLOW);
				            }
				            break;
				        }
	   
	                
	            }
			}
			
		}
	}

}
