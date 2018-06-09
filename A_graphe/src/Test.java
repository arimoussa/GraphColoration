import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		String s = "1 2 3 46 78 98 98 5";
		
		String[] data = s.split(" ");
		
		System.out.println( "Test split :"+Arrays.toString( data ));
		*/
		
		FileReader in = null ;
		BufferedReader buffReader = null;

		try
		{

			JFileChooser open = new JFileChooser();
			FileNameExtensionFilter filtre = new FileNameExtensionFilter("fichier.txt","txt" );
			open.setFileFilter(filtre);
			int c = open.showOpenDialog( null );
			if( c != JFileChooser.APPROVE_OPTION )
			{
				System.out.println("\nAucun fichier choisi");
				System.exit(0);
			}

			in = new FileReader( open.getSelectedFile().getAbsoluteFile() );
			 buffReader = new BufferedReader( in );
			
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
			return ;
		}
		
		if( buffReader != null  ) 
		{
			Graphe g = new Graphe( buffReader ) ;
			//System.out.println( "\n\nAffichage du graphe \n" + g.toString() );
			/*WelshPowell w = new WelshPowell( g , 0) ;
			WelshPowell w1 = new WelshPowell( g , 1) ;
			WelshPowell w2 = new WelshPowell( g , 2) ;*/
			
			DSatur dsat = new DSatur(g);
			
		}
	//	else System.out.println("BuffReader invalide ");
		
		
	

	}

}



/*
chercher ordre de tri des sommets et test et courbe en fonction des parametres des donnees utiliser tas de fibronaci sur Dsatur pour trouver max 

Resultats de l'experience avec courbes ...

Voir les autres jeu de donnees lien dans cours slide 13 cours coloration
Code à executer facilement Faire un executable 
*/