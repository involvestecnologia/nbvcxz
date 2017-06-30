package br.com.involves.password.estimator.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListStringComparator {
  private static Comparator<String> comparator = new Comparator<String>() {

    @Override
    public int compare(String o1, String o2) {
      return (o1.length() < o2.length()) ? -1 : ((o1.length() > o2.length()) ? 1 : o1.compareTo(o2));
    }
  };

  public static ArrayList<String> order(ArrayList<String> data) {
    Collections.sort(data, comparator);
    return data;
  }
}
