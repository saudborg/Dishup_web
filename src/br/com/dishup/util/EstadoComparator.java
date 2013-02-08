package br.com.dishup.util;

import java.util.Comparator;

import br.com.dishup.object.Estado;

public class EstadoComparator implements Comparator<Estado>{

	@Override
	public int compare(Estado e1, Estado e2) {
		if(e1.getId() > e2.getId()) 
			return 1;
		else if(e1.getId() < e2.getId()) 
			return -1;
		else
			return 0;
	}
	
	

}
