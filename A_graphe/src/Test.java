import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FileReader in = null ;
		BufferedReader buffReader = null;

		//Lecture du fichier
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
		
		//Si le buffreader est valide on lance les algorithmes
		if( buffReader != null  ) 
		{
			//Costruction du graphe
			Graphe g = new Graphe( buffReader ) ;
			System.out.println( "\n\nAffichage du graphe \n" + g.toString() );
			WelshPowell w = new WelshPowell( g , 0) ;
			WelshPowell w1 = new WelshPowell( g , 1) ;
			WelshPowell w2 = new WelshPowell( g , 2) ;
			Greedy greed3 = new Greedy(g,0);
			Greedy greed2 = new Greedy(g,1);
			Greedy greed = new Greedy(g,2);
			
			DSatur dsat = new DSatur(g);
			
		
			
		}

		
	}

}
