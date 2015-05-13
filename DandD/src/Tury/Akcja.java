/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tury;

import Dane.Bohaterowie;
import Dane.Mapa;
import Dane.Postac;
import Gra.GUIStart;
import static Tury.Atak.Atak;
import static Tury.Czar.Czar;
import static Tury.Komenda.getKomenda;
import static Tury.Komenda.podajKomunikat;
import static Tury.Komenda.podajStaty;
import static Tury.Komenda.podajStatyW;
import static Tury.Ruch.Ruch;
import static Tury.Ruch.RuchSprawdz;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Grzechu
 */
public class Akcja {

    static int[] tablica = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static Postac bohater;
    static boolean x = true;

    public static void akcjaGracza(Bohaterowie postaci, Mapa mapa, int czyjRuch) {
        podajKomunikat("\nTura Gracza: ");

        uzupelnijTablice(tablica, postaci, mapa, czyjRuch);

        bohater = postaci.tablica.get(czyjRuch + 1);
        podajStaty(bohater);
        bohater = postaci.tablica.get(czyjRuch);
        podajStatyW(bohater);
        tablicaKierunek();
        boolean koniec = true;
        GUIStart gui2;
        if (x) {
            gui2 = new GUIStart();
        }
        x = false;

        while (koniec) {
            int kierunek = -2;
            String klawiatura = getKomenda();
            Scanner odczyt = new Scanner(System.in);
            char akcja = klawiatura.charAt(0);
            //PRZYJĄŁEM KIERUNKI TAK JAK SĄ NA NUMERYCZNEJ
            if (klawiatura.length() != 1) {
                if (Character.isDigit(klawiatura.charAt(1))) {
                    kierunek = klawiatura.charAt(1) - 48;
                    kierunek = ustalKierunek(mapa, czyjRuch, kierunek);
                }
            }
            uzupelnijTablice(tablica, postaci, mapa, czyjRuch);

            bohater = postaci.tablica.get(czyjRuch + 1);
            podajStaty(bohater);

            bohater = postaci.tablica.get(czyjRuch);
            podajStatyW(bohater);

            switch (akcja) {
                case 'R':
                    Ruch(postaci, mapa, czyjRuch, kierunek);
                    uzupelnijTablice(tablica, postaci, mapa, czyjRuch);
                    break;

                case 'A':
                    if (postaci.tablica.get(czyjRuch).lAtakPom > 0) {
                        kierunek = mapa.znajdzBohatera(2);
                        Atak(postaci, mapa, czyjRuch, kierunek);
                    }
                    if (postaci.tablica.get(czyjRuch).lAtakPom == 0) {
                        tablica[0] = 0;
                    }
                    break;

                case 'C':
                    Czar(postaci, mapa, czyjRuch, kierunek);
                    break;

                case 'S':
                    koniec = false;
                    break;

                case 'M':
                    System.out.println(mapa.druk());
                    break;

                case 'N':
                    break;
            }
            if (!postaci.tablica.get(0).zywy || !postaci.tablica.get(1).zywy) {
                for (int i = 0; i < 10; i++) {
                    tablica[i] = 0;
                }
                break;
            }
        }
        //TEST
        //System.out.print("Gracz    |");
    }

    public static void akcjaKomputera(Bohaterowie postaci, Mapa mapa, int czyjRuch) {
        podajKomunikat("\nTura Komputera");
        int cel = mapa.znajdzBohatera(1);
        int w;
        Random r = new Random();
        boolean koniec = true;

        while (koniec) {
            tablica[0] = 0;
            uzupelnijTablice(tablica, postaci, mapa, czyjRuch);
            int kierunek = 0;

            if (tablica[0] == 1) {
                if (postaci.tablica.get(czyjRuch).lAtakPom > 0) {
                    Atak(postaci, mapa, czyjRuch, cel);
                }
                if (postaci.tablica.get(czyjRuch).lAtakPom == 0) {
                    tablica[0] = 0;
                    koniec = false;
                }
            } else if (postaci.tablica.get(czyjRuch).ruchPom > 0) {
                int wrog = mapa.znajdzBohatera(czyjRuch + 1);

                int odlegloscX = mapa.plansza.get(cel).kolumna - mapa.plansza.get(wrog).kolumna;
                int odlegloscY = mapa.plansza.get(cel).rzad - mapa.plansza.get(wrog).rzad;

                w = Math.abs((r.nextInt() % 3 + 1));

                if (odlegloscX < 0) {
                    if (w == 1 && tablica[4] == 1) {
                        kierunek = 4;
                    }
                    if (w == 2 && tablica[7] == 1) {
                        kierunek = 7;
                    }
                    if (w == 3 && tablica[1] == 1) {
                        kierunek = 1;
                    }

                    if (odlegloscY < 0) {
                        if (w == 1 && tablica[4] == 1) {
                            kierunek = 4;
                        }
                        if (w == 2 && tablica[7] == 1) {
                            kierunek = 7;
                        }
                        if (w == 3 && tablica[8] == 1) {
                            kierunek = 8;
                        }
                    }

                    if (odlegloscY > 0) {
                        if (w == 1 && tablica[4] == 1) {
                            kierunek = 4;
                        }
                        if (w == 2 && tablica[1] == 1) {
                            kierunek = 1;
                        }
                        if (w == 3 && tablica[2] == 1) {
                            kierunek = 2;
                        }
                    }
                }

                if (odlegloscX > 0) {
                    if (w == 1 && tablica[6] == 1) {
                        kierunek = 6;
                    }
                    if (w == 2 && tablica[9] == 1) {
                        kierunek = 9;
                    }
                    if (w == 3 && tablica[3] == 1) {
                        kierunek = 3;
                    }

                    if (odlegloscY < 0) {
                        if (w == 1 && tablica[8] == 1) {
                            kierunek = 8;
                        }
                        if (w == 2 && tablica[6] == 1) {
                            kierunek = 6;
                        }
                        if (w == 3 && tablica[9] == 1) {
                            kierunek = 9;
                        }
                    }

                    if (odlegloscY > 0) {
                        if (w == 1 && tablica[3] == 1) {
                            kierunek = 3;
                        }
                        if (w == 2 && tablica[6] == 1) {
                            kierunek = 6;
                        }
                        if (w == 3 && tablica[2] == 1) {
                            kierunek = 2;
                        }
                    }
                } else {
                    if (odlegloscY < 0) {
                        if (w == 1 && tablica[8] == 1) {
                            kierunek = 8;
                        }
                        if (w == 2 && tablica[7] == 1) {
                            kierunek = 7;
                        }
                        if (w == 3 && tablica[9] == 1) {
                            kierunek = 9;
                        }
                    }

                    if (odlegloscY > 0) {
                        if (w == 1 && tablica[2] == 1) {
                            kierunek = 2;
                        }
                        if (w == 2 && tablica[3] == 1) {
                            kierunek = 3;
                        }
                        if (w == 3 && tablica[1] == 1) {
                            kierunek = 1;
                        }
                    }
                }

                int i = 1;

                while (kierunek == 0 && i <= 9) {
                    if (tablica[i] == 1) {
                        kierunek = i;
                    }
                    i++;
                }

                kierunek = ustalKierunek(mapa, czyjRuch, kierunek);
                Ruch(postaci, mapa, czyjRuch, kierunek);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                uzupelnijTablice(tablica, postaci, mapa, czyjRuch);
                System.out.println(mapa.plansza.get(wrog).kolumna + " " + mapa.plansza.get(wrog).rzad);
                
            } else {
                koniec = false;
            }
        }
    }

    private static int ustalKierunek(Mapa mapa, int czyjRuch, int kierunek) {
        int skad = mapa.znajdzBohatera(czyjRuch + 1);

        //Sprawdzanie czy dany kierunek nie wychodzi poza planszę
        if (skad == 0) {
            if (kierunek == 1 || kierunek == 4 || kierunek == 7 || kierunek == 8 || kierunek == 9) {
                kierunek = -1;
            }
        }

        if (skad == mapa.wymiarX() - 1) {
            if (kierunek == 6 || kierunek == 3 || kierunek == 7 || kierunek == 8 || kierunek == 9) {
                kierunek = -1;
            }
        }

        if (skad == (mapa.wymiarX() * mapa.wymiarY()) - 1) {
            if (kierunek == 6 || kierunek == 3 || kierunek == 1 || kierunek == 2 || kierunek == 9) {
                kierunek = -1;
            }
        }

        if (skad == mapa.wymiarX() * (mapa.wymiarY() - 1)) {
            if (kierunek == 7 || kierunek == 4 || kierunek == 1 || kierunek == 2 || kierunek == 3) {
                kierunek = -1;
            }
        }

        if (skad > 0 && skad < mapa.wymiarX() - 1) {
            if (kierunek == 7 || kierunek == 8 || kierunek == 9) {
                kierunek = -1;
            }
        }

        if (skad > mapa.wymiarX() * (mapa.wymiarY() - 1) && skad < mapa.wymiarX() * mapa.wymiarY() - 1) {
            if (kierunek == 1 || kierunek == 2 || kierunek == 3) {
                kierunek = -1;
            }
        }

        if (skad % mapa.wymiarX() == 0) {
            if (kierunek == 1 || kierunek == 4 || kierunek == 7) {
                kierunek = -1;
            }
        }

        if ((skad + 1) % mapa.wymiarX() == 0) {
            if (kierunek == 9 || kierunek == 6 || kierunek == 3) {
                kierunek = -1;
            }
        }

        switch (kierunek) {
            case 1:
                kierunek = skad + mapa.wymiarX() - 1;
                break;
            case 2:
                kierunek = skad + mapa.wymiarX();
                break;
            case 3:
                kierunek = skad + mapa.wymiarX() + 1;
                break;
            case 4:
                kierunek = skad - 1;
                break;
            case 5:
                kierunek = -2;
                break;
            case 6:
                kierunek = skad + 1;
                break;
            case 7:
                kierunek = skad - mapa.wymiarX() - 1;
                break;
            case 8:
                kierunek = skad - mapa.wymiarX();
                break;
            case 9:
                kierunek = skad - mapa.wymiarX() + 1;
                break;
            case 0:
                kierunek = -3;
                break;
        }

        return kierunek;
    }

    public synchronized static int[] tablicaKierunek() {
        return tablica;
    }

    private synchronized static void uzupelnijTablice(int[] tablica, Bohaterowie postaci, Mapa mapa, int czyjRuch) {
        int kierunek;
        for (int i = 1; i < 10; i++) {
            tablica[i] = 0;
            kierunek = ustalKierunek(mapa, czyjRuch, i);
            if (RuchSprawdz(postaci, mapa, czyjRuch, kierunek)) {
                tablica[i] = 1;
            } else {
                tablica[i] = 0;
            }

            if (kierunek > 0) {
                if (mapa.plansza.get(kierunek).ktoZajmuje > 0 && postaci.tablica.get(czyjRuch).lAtakPom > 0 && czyjRuch != mapa.plansza.get(kierunek).ktoZajmuje - 1) {
                    tablica[0] = 1;
                }
            }
        }
        tablica[5] = 0;
    }

}
