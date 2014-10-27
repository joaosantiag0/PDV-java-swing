import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class main {
	protected static DefaultListModel<String> list;
	protected static String code = "";
	protected static float total = 0;
	
	public static void main(String[] args) {
		JFrame win =  new JFrame("Sistema PDV");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(800, 600);
		win.setLayout(new GridLayout(11, 2));
		JLabel blank = new JLabel();
		JLabel blank1 = new JLabel();
		JLabel blank2 = new JLabel();
		JLabel nameL = new JLabel("Nome do Cliente");
		JTextField name = new JTextField();
		JLabel cpfl = new JLabel("CPF do Cliente (somente numeros)");
		JTextField cpf = new JTextField();
		JButton start = new JButton("Iniciar Compra");
		JLabel codl = new JLabel("Código do Produto");
		JTextField cod = new JTextField();
		cod.setEnabled(false);
		JButton search = new JButton("Pesquisar Produto");
		search.setEnabled(false);
		JLabel product = new JLabel();
		JLabel value = new JLabel();
		JLabel qtdl = new JLabel("Quantidade");
		JTextField qtd = new JTextField();
		qtd.setEnabled(false);
		JButton buy = new JButton("Comprar");
		buy.setEnabled(false);
		list = new DefaultListModel<String>();
		JList<String> listProducts = new JList<String>(list);
		//listProducts.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		//listProducts.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		//listProducts.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(listProducts);
		//listScroller.setPreferredSize(new Dimension(250, 80));
		JLabel totalAmount = new JLabel("Valor total");
		
		
		/* Add'res in Layout */
		win.add(nameL);
		win.add(name);
		win.add(cpfl);
		win.add(cpf);
		win.add(blank);
		win.add(start);
		win.add(codl);
		win.add(cod);
		win.add(blank1);
		win.add(search);
		win.add(product);
		win.add(value);
		win.add(qtdl);
		win.add(qtd);
		win.add(blank2);
		win.add(buy);
		win.add(listScroller);
		win.add(totalAmount);
		
		
		
		
		/* Listeners */
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name.getText().length() > 0){
				if(cpf.getText().length() == 11){
					cod.setEnabled(true);
					qtd.setEnabled(true);
					search.setEnabled(true);
					buy.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "CPF Inválido!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, Digite seu nome!");
			}
			} 
		});
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cod.getText().length() > 0){
					BufferedReader reader = null;
					String line = "";
					Boolean found = false;
					try {
						reader = new BufferedReader(new FileReader("produtos.txt"));
						while((line = reader.readLine()) != null){
							
								List<String> result = Arrays.asList( line.split("\\|", -1));
								if(result.get(0).equals(cod.getText())){		
									
								product.setText(result.get(1));
								value.setText(result.get(2));
								code = result.get(0);
								found = true;
							}
						}
						if (!found) JOptionPane.showMessageDialog(null, "O código digitado não corresponde a nenhum produto cadastrado!");
					} catch (FileNotFoundException err){
						JOptionPane.showMessageDialog(null, err.getMessage());
					} catch (IOException err1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, err1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, digite um código.");
				}
			}
		});
		buy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(qtd.getText().length() > 0){
				String result = qtd.getText()+"X  (R$ "+value.getText()+") [Cód: "+code+"] "+product.getText()+"\t\t TOTAL: "+(Integer.valueOf(qtd.getText()) * Float.valueOf(value.getText()));
				list.addElement(result);
				total += Float.valueOf(value.getText()) * Integer.valueOf(qtd.getText());
				totalAmount.setText("Valor total: R$ " + String.valueOf(total));
				} else {
					JOptionPane.showMessageDialog(null, "Por favor defina a quantidade!");
				}
			}
		});
		
		win.setVisible(true);

	}

}
