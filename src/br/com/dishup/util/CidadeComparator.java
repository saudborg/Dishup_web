package br.com.dishup.util;

import java.util.Comparator;

import br.com.dishup.object.Cidade;

public class CidadeComparator implements Comparator<Cidade> {

	@Override
	public int compare(Cidade c1, Cidade c2) {
		if(c1.getId() > c2.getId()) 
			return 1;
		else if(c1.getId() < c2.getId()) 
			return -1;
		else
			return 0;
	}

	
}
