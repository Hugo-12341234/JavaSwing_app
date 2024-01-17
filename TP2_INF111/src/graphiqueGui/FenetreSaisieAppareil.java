package graphiqueGui;
/**
 * Fen�tre de dialogue pour obtenir les donn�es d'un appareil.
 * Sert essentiellement � sauver du temps pour la saisie.  Les valeurs
 * ne sont pas exhaustive mais suffiront.
 * 
 * Il suffit d'appelerFenetreSaisieAppareil.getAppareil() pour obtenir
 * les valeurs saisies ou null si l'utilisateur clique sur le x en haut � droite.
 * 
 * Code fourni dans le cadre du cours inf111 tp3 A16.
 * 
 * @author Pierre B�lisle 
 * @version Copyright  H2023
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import disjoncteurs.Disjoncteur;
import hierarchieAppareils.AppareilAbstrait;
import hierarchieAppareils.AppareilInterrupteur;
import hierarchieAppareils.AppareilVariateur;

public class FenetreSaisieAppareil {
	
	/*
	 * Strat�gie : On utilise JDialog parce que cette fen�tre peut �tre mise en
	 * mode modal.  Une proc�dure cr�e la fen�tre de dialogue et la met visible.
	 * Lorsque le bouton ok est cliqu�, l'appareil est instanci� avec les 
	 * valeurs et le dialog est dispos�.
	 * 
	 * Lorsque la fenetre se ferme, l'appareil est retourn�.
	 * 
	 * Certaines bonnes pratiques n'ont pas �t� utilis�es compte tenu 
	 * du manque de temps.
	 */
	
	private static final double BASE = 10.0;
	
	// Appareil � retourner.
	private static AppareilAbstrait appareil;
	
	// La fenetre de dialogue qui sera modal.
	private static JDialog dialog;
	
	// Paneau de contenu de la fen�tre.
	private static JPanel panneau;
	
	 // Panneau du haut.
	private static JPanel panHaut = new JPanel();
	
	// Panneau en bas avec le bouton.
	private static JPanel panBas = new JPanel();
	
	// Les options possibles des comboBox
	// Les choix des diff�rents attributs.
	private static String [] tabCategories ={"Refrigerateur", 
			                                "Cuisiniere", 
			                                "Grille-pain", 
			                                "Seche-cheveux",
			                                "Seche-ligne",
			                                "Presse-jus",
			                                "Chauffage",
			                                "Autres"};
	
	private static String [] tabTensions ={
			String.valueOf(Disjoncteur.TENSION_PHASE),
			String.valueOf(Disjoncteur.TENSION_ENTREE)};

	private static String [] tabEmplacements ={"Cuisine", 
                                                    "Salon",
                                                    "Chambre principale",
                                                    "Chambre 1",
                                                    "Chambre 2",
                                                    "Chambre 3",
                                                    "Salle familiale",
                                                    "Garage",
                                                    "Autres"};
	

	// N�cessaire d'�tre global pour l'utilisation dans un �couteur de 
	// classe interne. �vite le passage de param�tre au constructeur.
	private static JRadioButton btnVariateur;
	
	private static JSlider variateur;
	
	private static JSlider ampereSlider;
	
	/**
	 * Seule fonction public qui d�marre une fen�tre de saisie et retourne un 
	 * appareil selon les donn�es saisies ou null si l'utilisateur annule.
	 * 
	 * @return null ou un appareil cr�� par l'utilisateur.
	 */
	public static AppareilAbstrait getAppareil(){
		
		initDialog();
		
		dialog.setVisible(true);

		return appareil;	
	}

	
	/*
	 * Cr�e les instances des composants, les ajoute et met la fen�tre visible.
	 */
	private static void initDialog(){
		
		// Instanciation � chaque appel.
		initFenetre();
						
		// Tout ce qui concerne le type d'appareil
		ajouterTypeAppareil();
		
		// Les comboBox sont ajout�s au panneau dans la fonction qui le cr�e.
		JComboBox<String> categorie = 
				ajouterCombo("Categorie", tabCategories);
		
		JComboBox<String> tension = 
				ajouterCombo("Tension", tabTensions);
		
		JComboBox<String> emplacement = 
				ajouterCombo("Emplacement", tabEmplacements);
						
		// Tout ce qui touche le nombre d'amp�res.
	    ajouterAmpere();
				
		//Le bouton est laiss� ici pour permettre � l'�couteur
		// d'utiliser directement les valeurs.
		JButton btn = new JButton("Ok");
		
		btn.setPreferredSize(new Dimension(100,30));
		
		// Un �couteur de bouton anonyme qui instancie l'appareil 
		// selon les donn�es
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				// L'instanciation se fait selon la sorte d'appareil
				// s�lectionn�e.
				if(btnVariateur.isSelected()){

					appareil = 
					  new AppareilVariateur((String)categorie.getSelectedItem(), 
						   ampereSlider.getValue() / BASE, 
				   		   Integer.parseInt((String)tension.getSelectedItem()),
				    	   (String)emplacement.getSelectedItem(),
						   variateur.getValue() / BASE);
									
				}
				else{
					
					appareil = new AppareilInterrupteur(
							(String)categorie.getSelectedItem(), 
					  	    ampereSlider.getValue() / BASE, 
					        Integer.parseInt((String)tension.getSelectedItem()),
							(String)emplacement.getSelectedItem());
				}
				
				dialog.dispose();
				dialog  = null;
								
			}
		
		});
		
		panBas.add(btn);
		
	}

	/*
	 * Fonction locale qui monte un "slider" et la valeur actuellement 
	 * s�lectionn�.
	 */
	private static void ajouterAmpere(){
		
		JLabel etiqAmpere= new JLabel("Ampere ");
		
		ampereSlider = new JSlider(1, 1000);
		JPanel panSliderAmpere = getPanSlider(ampereSlider);
		
		JPanel panAmpere = new JPanel();
		panAmpere.add(etiqAmpere);
		panAmpere.add(panSliderAmpere);		
		
		panHaut.add(panAmpere);		
	}
	
	/**
	 * Initialise les attributs de la fen�tre de dialogue. 
	 */
	private static void initFenetre(){
		
		dialog =  new JDialog ();
		appareil = null;
		panHaut.removeAll();
		panBas.removeAll();
		
		dialog.setTitle("Ajout d'appareils");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		dialog.setSize(dim);
		
		dialog.setResizable(false);
		
		dialog.setModal(true);

		// On contr�le le layout de la fen�tre et attribut est utilis� 
		// globalement.
		panneau = (JPanel) dialog.getContentPane();	
		

		panneau.add(panHaut, BorderLayout.PAGE_START);
		panneau.add(panBas,  BorderLayout.PAGE_END);

	}
	
	/*
	 * Proc�dure locale pour la lisibilit� du code.  Cr�e un panneau avec
	 * un slider et lui ajoute un �couteur pour voir sa valeur lors des 
	 * changements.
	 * 
	 * Le slider est en r�el.
	 */
	private static JPanel getPanSlider(JSlider slider){
		
		JPanel panSlider = new JPanel();
		
		panSlider.add(slider);
		slider.setValue(1);
		slider.setOrientation(JSlider.VERTICAL);
		
		// La plus petit valeur au d�part.
		JLabel etiqSlider = 
				new JLabel(String.valueOf(slider.getValue() / BASE));
		
		panSlider.add(etiqSlider);
			
		// Permet de montrer la valeur du slider en nombre r�el.
		slider.addChangeListener(new ChangeListener(){
			 
			@Override
			public void stateChanged(ChangeEvent e) {
				
				etiqSlider.setText(String.valueOf(slider.getValue() / BASE)); 
				
			}
			
		});
		
		return panSlider;
	}
	
	/*
	 * Proc�dure locale pour cr�er les deux radios button et le silder.
	 * 
	 * Ajoute les �couteur pour mettre activer oiu d�sactiver le slider.
	 */
	private static void ajouterTypeAppareil(){
		
		// Tout ce qui touche la position du variateur.
		variateur = new JSlider(1, 1000);
		
		JPanel panSliderVariateur = getPanSlider(variateur);
		variateur.setMaximumSize(new Dimension(100,50));
		
		// Le type d'appareil et leur �couteur.
		JRadioButton btnInterrupteur = new JRadioButton("Interrupteur");
				
		btnInterrupteur.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				variateur.setEnabled(false);							
			}
			
		});
		
		btnVariateur = new JRadioButton("Variateur ");
		btnVariateur.setSelected(true);
		btnVariateur.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				variateur.setEnabled(true);						
			}
			
		});

		// Rend les boutons mutullement exclusifs.	
		ButtonGroup group = new 	ButtonGroup();
		group.add(btnInterrupteur);
		group.add(btnVariateur);
		
		// Un petit panneau 
		JPanel panBtnTypeAppareil = new JPanel();
		panBtnTypeAppareil.add(btnVariateur);
		panBtnTypeAppareil.add(panSliderVariateur);
		panBtnTypeAppareil.add(btnInterrupteur);
						
		panHaut.add(panBtnTypeAppareil);		

		
	}
	
	/**
	 * Proc�dure local pour cr�er un panneau avec un combobox et une �tiquette.
	 * Sert essentiellement a la r�utilisation.
	 * 
	 * @param etiqStr  Le texte de l'�tiquette.
	 * @param tabOptions Les options du comboBox
	 * 
	 * @return Le comboBox cr��.
	 */
	private static JComboBox<String>  ajouterCombo(String etiqStr, 
	                                                                                      String[] tabOptions){
		
		// On utilise des JCombox g�n�riques, �vite les "warnings.
		JLabel etiq = new JLabel(etiqStr);
		JComboBox<String> combo = 
				new JComboBox<String>(tabOptions);
		
		// Petit panneau pour disposer les composants en FlowLayout.
		JPanel pan = new JPanel();
		pan.add(etiq);
		pan.add(combo);
		
		// Le panneau du comboBox dans le panneau de la fen�tre.
		panHaut.add(pan);
		
		return combo;
	
	}
	

}
