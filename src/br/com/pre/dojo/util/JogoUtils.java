package br.com.pre.dojo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class JogoUtils {
	public static <T extends Object> ArrayList<T> ordenarCollectionToArrayList(Collection<T> collection, Comparator<T> novoComparator) {
		ArrayList listaOrdenada = new ArrayList<T>(collection);
		Collections.sort(listaOrdenada, novoComparator);
		return listaOrdenada;
	}
}
