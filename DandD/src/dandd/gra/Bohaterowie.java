/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dandd.gra;

import java.util.ArrayList;

/**
 *
 * @author Grzechu
 */
public class Bohaterowie {

    public ArrayList<Postac> tablica;

    public Bohaterowie() {
        tablica = new ArrayList<>();
    }

    public void dodajBohatera(String dane) {
        Postac p = new Postac(dane);
        tablica.add(p);
    }
}