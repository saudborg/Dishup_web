package br.com.dishup.util;

import java.util.Comparator;
import br.com.dishup.object.Pais;

public class PaisComparator implements Comparator<Pais> {

	@Override
	public int compare(Pais p1, Pais p2) {
		if(p1.getId() > p2.getId()) 
			return 1;
		else if(p1.getId() < p2.getId()) 
			return -1;
		else
			return 0;
	}

	
	
}
