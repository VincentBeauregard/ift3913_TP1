import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.AbstractListModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ListSelectionModel;


public class Gui{
	
	private  JFrame frame;
	
	private JPanel panel_load;
	private JPanel panel_info;
	private JTextField pathIn;
	private JButton btnNewButton;
	private JButton pathbtn;
	
	private JLabel lblClasses;
	private JLabel lblMethodes;
	private JLabel lblAttributs;
	private JLabel lblAssociations;
	private JLabel lblSousClasses;
	private JLabel lblDetails;
	
	private JList list_classes;
	private JList list_sousClasses;
	private JList list_attributs;
	private JList list_methodes;
	private JList list_association;
	
	private JTextPane textPane_Details;
	
	private SpringLayout springLayout;
	private SpringLayout sl_panel_load;
	private SpringLayout sl_panel_info;
	
	private static Fichier file;

	/**
	 * Launch the application.
	 */
	public static void runGui(Fichier f) {
		file=f;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
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
	public Gui(){
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		panel_load = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_load, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_load, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_load, 50, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_load, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel_load);
		
		panel_info = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_info, 8, SpringLayout.SOUTH, panel_load);
		springLayout.putConstraint(SpringLayout.WEST, panel_info, 0, SpringLayout.WEST, panel_load);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_info, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_info, 0, SpringLayout.EAST, frame.getContentPane());
		sl_panel_load = new SpringLayout();
		panel_load.setLayout(sl_panel_load);
		
		btnNewButton = new JButton("Charger fichier");
		sl_panel_load.putConstraint(SpringLayout.NORTH, btnNewButton, 10, SpringLayout.NORTH, panel_load);
		sl_panel_load.putConstraint(SpringLayout.WEST, btnNewButton, 10, SpringLayout.WEST, panel_load);
		sl_panel_load.putConstraint(SpringLayout.SOUTH, btnNewButton, -10, SpringLayout.SOUTH, panel_load);
		panel_load.add(btnNewButton);
		
		pathIn = new JTextField();
		sl_panel_load.putConstraint(SpringLayout.WEST, pathIn, 10, SpringLayout.EAST, btnNewButton);
		sl_panel_load.putConstraint(SpringLayout.SOUTH, pathIn, -10, SpringLayout.SOUTH, panel_load);
		sl_panel_load.putConstraint(SpringLayout.EAST, pathIn, 540, SpringLayout.EAST, btnNewButton);
		sl_panel_load.putConstraint(SpringLayout.NORTH, pathIn, 10, SpringLayout.NORTH, panel_load);
		panel_load.add(pathIn);
		pathIn.setColumns(10);

		pathbtn = new JButton("  ...  ");
		sl_panel_load.putConstraint(SpringLayout.NORTH, pathbtn, 0, SpringLayout.NORTH, btnNewButton);
		sl_panel_load.putConstraint(SpringLayout.WEST, pathbtn, 6, SpringLayout.EAST, pathIn);
		sl_panel_load.putConstraint(SpringLayout.SOUTH, pathbtn, 0, SpringLayout.SOUTH, btnNewButton);
		sl_panel_load.putConstraint(SpringLayout.EAST, pathbtn, -10, SpringLayout.EAST, panel_load);
		panel_load.add(pathbtn);
		frame.getContentPane().add(panel_info);
		sl_panel_info = new SpringLayout();
		panel_info.setLayout(sl_panel_info);
		
		lblClasses = new JLabel("Classes");
		sl_panel_info.putConstraint(SpringLayout.NORTH, lblClasses, 10, SpringLayout.NORTH, panel_info);
		sl_panel_info.putConstraint(SpringLayout.WEST, lblClasses, 10, SpringLayout.WEST, panel_info);
		panel_info.add(lblClasses);
		
		lblAttributs = new JLabel("Attributs");
		sl_panel_info.putConstraint(SpringLayout.SOUTH, lblAttributs, 0, SpringLayout.SOUTH, lblClasses);
		panel_info.add(lblAttributs);
		
		lblMethodes = new JLabel("Methodes");
		sl_panel_info.putConstraint(SpringLayout.NORTH, lblMethodes, 10, SpringLayout.NORTH, panel_info);
		panel_info.add(lblMethodes);
		
		lblSousClasses = new JLabel("Sous-classes");
		sl_panel_info.putConstraint(SpringLayout.WEST, lblSousClasses, 0, SpringLayout.WEST, lblAttributs);
		panel_info.add(lblSousClasses);
		
		lblAssociations = new JLabel("Association/Aggregations");
		sl_panel_info.putConstraint(SpringLayout.WEST, lblAssociations, 0, SpringLayout.WEST, lblMethodes);
		panel_info.add(lblAssociations);
		
		lblDetails = new JLabel("Details");
		sl_panel_info.putConstraint(SpringLayout.WEST, lblDetails, 0, SpringLayout.WEST, lblAttributs);
		panel_info.add(lblDetails);
		
		list_attributs = new JList();
		list_attributs.setEnabled(false);
		list_attributs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sl_panel_info.putConstraint(SpringLayout.EAST, list_attributs, 246, SpringLayout.WEST, lblAttributs);
		list_attributs.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_panel_info.putConstraint(SpringLayout.NORTH, list_attributs, 0, SpringLayout.SOUTH, lblAttributs);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, list_attributs, 140, SpringLayout.SOUTH, lblAttributs);
		sl_panel_info.putConstraint(SpringLayout.WEST, list_attributs, 0, SpringLayout.WEST, lblAttributs);
		panel_info.add(list_attributs);
		
		list_sousClasses = new JList();
		list_sousClasses.setEnabled(false);
		list_sousClasses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_sousClasses.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_panel_info.putConstraint(SpringLayout.NORTH, list_sousClasses, 0, SpringLayout.SOUTH, lblSousClasses);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, list_sousClasses, 140, SpringLayout.SOUTH, lblSousClasses);
		sl_panel_info.putConstraint(SpringLayout.WEST, list_sousClasses, 0, SpringLayout.WEST, lblSousClasses);
		panel_info.add(list_sousClasses);
		
		list_association = new JList();
		list_association.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_association.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_panel_info.putConstraint(SpringLayout.NORTH, list_association, 0, SpringLayout.SOUTH, lblAssociations);
		sl_panel_info.putConstraint(SpringLayout.WEST, list_association, 0, SpringLayout.WEST, lblAssociations);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, list_association, 0, SpringLayout.SOUTH, list_sousClasses);
		panel_info.add(list_association);
		
		list_methodes = new JList();
		list_methodes.setEnabled(false);
		list_methodes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_methodes.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_panel_info.putConstraint(SpringLayout.NORTH, list_methodes, 0, SpringLayout.SOUTH, lblMethodes);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, list_methodes, 0, SpringLayout.SOUTH, list_attributs);
		sl_panel_info.putConstraint(SpringLayout.EAST, list_association, 0, SpringLayout.EAST, list_methodes);
		sl_panel_info.putConstraint(SpringLayout.WEST, list_methodes, 0, SpringLayout.WEST, lblMethodes);
		sl_panel_info.putConstraint(SpringLayout.EAST, list_methodes, -10, SpringLayout.EAST, panel_info);
		panel_info.add(list_methodes);
		
		textPane_Details = new JTextPane();
		textPane_Details.setEditable(false);
		
				textPane_Details.setBorder(new LineBorder(new Color(0, 0, 0)));
				textPane_Details.setForeground(new Color(51, 51, 51));
				sl_panel_info.putConstraint(SpringLayout.NORTH, textPane_Details, 0, SpringLayout.SOUTH, lblDetails);
				sl_panel_info.putConstraint(SpringLayout.WEST, textPane_Details, 0, SpringLayout.WEST, lblDetails);
				sl_panel_info.putConstraint(SpringLayout.SOUTH, textPane_Details, -10, SpringLayout.SOUTH, panel_info);
				sl_panel_info.putConstraint(SpringLayout.EAST, textPane_Details, 0, SpringLayout.EAST, list_association);
				panel_info.add(textPane_Details);
				
				
				JScrollPane scrollPane_detail = new JScrollPane(textPane_Details);
				sl_panel_info.putConstraint(SpringLayout.NORTH, scrollPane_detail, 0, SpringLayout.SOUTH, lblDetails);
				sl_panel_info.putConstraint(SpringLayout.WEST, scrollPane_detail, 0, SpringLayout.WEST, lblDetails);
				sl_panel_info.putConstraint(SpringLayout.SOUTH, scrollPane_detail, -10, SpringLayout.SOUTH, panel_info);
				sl_panel_info.putConstraint(SpringLayout.EAST, scrollPane_detail, -10, SpringLayout.EAST, panel_info);
				panel_info.add(scrollPane_detail);
		
		list_classes = new JList();
		list_classes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sl_panel_info.putConstraint(SpringLayout.EAST, list_classes, 246, SpringLayout.WEST, lblClasses);
		sl_panel_info.putConstraint(SpringLayout.NORTH, list_classes, 0, SpringLayout.SOUTH, lblClasses);
		sl_panel_info.putConstraint(SpringLayout.WEST, list_classes, 10, SpringLayout.WEST, panel_info);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, list_classes, -10, SpringLayout.SOUTH, panel_info);
		list_classes.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_info.add(list_classes);
		JScrollPane scrollPane_classes = new JScrollPane(list_classes);
		sl_panel_info.putConstraint(SpringLayout.EAST, scrollPane_classes, 246, SpringLayout.WEST, lblClasses);
		sl_panel_info.putConstraint(SpringLayout.WEST, lblAttributs, 20, SpringLayout.EAST, scrollPane_classes);
		sl_panel_info.putConstraint(SpringLayout.NORTH, scrollPane_classes, 0, SpringLayout.SOUTH, lblClasses);
		sl_panel_info.putConstraint(SpringLayout.WEST, scrollPane_classes, 0, SpringLayout.WEST, panel_info);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, scrollPane_classes, -10, SpringLayout.SOUTH, panel_info);
		panel_info.add(scrollPane_classes);
		
		String current = System.getProperty("user.dir");

		pathIn.setText(current+"/");
		
		
		
		list_classes.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {

                	int classeIndex = list_classes.getSelectedIndex();
                	if (classeIndex==-1)classeIndex=0;
                	changeClassDisplay(file,classeIndex,null);
                }
            }
        });
		JScrollPane scrollPane_attributs = new JScrollPane(list_attributs);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, scrollPane_attributs, 140, SpringLayout.SOUTH, lblAttributs);
		sl_panel_info.putConstraint(SpringLayout.EAST, list_sousClasses, 0, SpringLayout.EAST, scrollPane_attributs);
		sl_panel_info.putConstraint(SpringLayout.NORTH, lblSousClasses, 10, SpringLayout.SOUTH, scrollPane_attributs);
		sl_panel_info.putConstraint(SpringLayout.NORTH, scrollPane_attributs, 0, SpringLayout.SOUTH, lblAttributs);
		sl_panel_info.putConstraint(SpringLayout.WEST, scrollPane_attributs, 0, SpringLayout.WEST, lblAttributs);
		sl_panel_info.putConstraint(SpringLayout.WEST, lblMethodes, 20, SpringLayout.EAST, scrollPane_attributs);
		sl_panel_info.putConstraint(SpringLayout.EAST, scrollPane_attributs, 246, SpringLayout.WEST, lblAttributs);
		panel_info.add(scrollPane_attributs);
		JScrollPane scrollPane_methodes = new JScrollPane(list_methodes);
		sl_panel_info.putConstraint(SpringLayout.NORTH, scrollPane_methodes, 0, SpringLayout.SOUTH, lblMethodes);
		sl_panel_info.putConstraint(SpringLayout.NORTH, lblAssociations, 10, SpringLayout.SOUTH, scrollPane_methodes);
		sl_panel_info.putConstraint(SpringLayout.WEST, scrollPane_methodes, 0, SpringLayout.WEST, lblMethodes);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, scrollPane_methodes, 140, SpringLayout.SOUTH, lblMethodes);
		sl_panel_info.putConstraint(SpringLayout.EAST, scrollPane_methodes, -10, SpringLayout.EAST, panel_info);
		panel_info.add(scrollPane_methodes);
		JScrollPane scrollPane_ssClasses = new JScrollPane(list_sousClasses);
		sl_panel_info.putConstraint(SpringLayout.EAST, scrollPane_ssClasses, 246, SpringLayout.WEST, lblSousClasses);
		sl_panel_info.putConstraint(SpringLayout.NORTH, lblDetails, 10, SpringLayout.SOUTH, scrollPane_ssClasses);
		sl_panel_info.putConstraint(SpringLayout.NORTH, scrollPane_ssClasses, 0, SpringLayout.SOUTH, lblSousClasses);
		sl_panel_info.putConstraint(SpringLayout.WEST, scrollPane_ssClasses, 0, SpringLayout.WEST, lblSousClasses);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, scrollPane_ssClasses, 140, SpringLayout.SOUTH, lblSousClasses);
		panel_info.add(scrollPane_ssClasses);
		JScrollPane scrollPane_association = new JScrollPane(list_association);
		sl_panel_info.putConstraint(SpringLayout.NORTH, scrollPane_association, 0, SpringLayout.SOUTH, lblAssociations);
		sl_panel_info.putConstraint(SpringLayout.WEST, scrollPane_association, 0, SpringLayout.WEST, lblAssociations);
		sl_panel_info.putConstraint(SpringLayout.SOUTH, scrollPane_association, 140, SpringLayout.SOUTH, lblAssociations);
		sl_panel_info.putConstraint(SpringLayout.EAST, scrollPane_association, -10, SpringLayout.EAST, panel_info);
		panel_info.add(scrollPane_association);
		

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(pathIn.getText().split(System.getProperty("user.dir")).length);
				System.out.println("in : "+pathIn.getText());
				
				changeFullDisplay(TP1.loadFile(pathIn.getText()));
			}
		});
		
		pathbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Handle open button action.

				final JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(panel_load);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					pathIn.setText(selectedFile.getAbsolutePath());
					System.out.println(selectedFile.getAbsolutePath());
					changeFullDisplay(TP1.loadFile(selectedFile.getAbsolutePath()));

				}
			}
		});
		list_association.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                	int classeIndex = list_classes.getSelectedIndex();
                	int associationIndex = list_association.getSelectedIndex();
                	if (classeIndex==-1)classeIndex=0;
                	if (associationIndex==-1)associationIndex=0;
                	changeDetailDisplay(file,classeIndex,associationIndex,null);
                }
            }
        });
	}
	
	private void changeDetailString(String details){
		textPane_Details.setText(details);
	}
	private void changeDetailDisplay(Fichier file,int classeIndex, int lienIndex,String error){
		if(error == null&&file.liens.length>0&&file.classes[classeIndex].indexLiens.length>0){
		Lien l = file.liens[file.classes[classeIndex].indexLiens[lienIndex]];
		String detailstring="";
		if (l.type==0){
			detailstring+="Relation "+l.nom+"\n\tROLES";
			int nbRoles = l.classes.length;
			for(int i=0;i<nbRoles;i++){
				detailstring += "\n\t\t" + l.classes[i] +" "+ l.cardinality[i];
			}
			
		}
		else if (l.type==1){
			detailstring+="AGGREGATION "+l.nom+"\n\tCONTAINER";
			int nbContainer = l.container.length;
			for(int i=0;i<nbContainer;i++){
				detailstring += "\n\t\t" + l.container[i] +" "+ l.cardinality_containers[i];
			}
			detailstring+="\n\tPARTS";
			int nbParts = l.parts.length;
			for(int i=0;i<nbParts;i++){
				detailstring += "\n\t\t" + l.parts[i] +" "+ l.cardinality_parts[i];
			}
		}
		changeDetailString(detailstring);
		}
		else changeDetailString(error);
	}
	
	
	private void changeClassDisplay(Fichier file, int classeindex,String error){
		if(error == null){
		Classe c = file.classes[classeindex];
		int attlength = c.attributs.length;
		int metlength = c.methodes.length;
		int ssclength = c.sousClasses.length;
		int linlength = c.indexLiens.length;

		final String att[] = new String[attlength];
		final String met[] = new String[metlength];
		final String ssc[] = new String[ssclength];
		final String lin[] = new String[linlength];
		for (int i = 0 ; i<attlength;i++){
			att[i]=c.attributs[i];
		}
		for (int i = 0 ; i<metlength;i++){
			met[i]=c.methodes[i];
		}
		for (int i = 0 ; i<ssclength;i++){
			ssc[i]=c.sousClasses[i];
		}
		System.out.println(linlength);
		for (int i = 0 ; i<linlength;i++){
			lin[i]=file.liens[c.indexLiens[i]].nom;
		}
		

		changeList(list_attributs,att);
		changeList(list_methodes,met);
		changeList(list_sousClasses,ssc);
		changeList(list_association,lin);
		list_association.setSelectedIndex(0);
		changeDetailDisplay(file,classeindex,0,null);}
		else changeDetailDisplay(file,classeindex,0,error);
	}
	
	
	
	
	
	
	private void changeFullDisplay(Fichier file){
		this.file = file;
		if(file.valide==true){
			int length = file.classes.length;
			final String classeOut[] = new String[length];
			for (int i = 0 ; i<length;i++){
				classeOut[i]=file.classes[i].nom;
			}
			changeList(list_classes,classeOut);
			list_classes.setSelectedIndex(0);
			changeClassDisplay(file,0,null);
		}
		else {
			changeClassDisplay(file,0,"Erreur : \""+pathIn.getText()+"\". "+file.modele);
			}
	}
	
	private void changeList(JList list, final String[] values){
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}
}
