package mx.itesm.eko;

import mx.itesm.eko.transiciones.TransicionPantalla;
import mx.itesm.eko.transiciones.efectoTransicion;


public class EkoMain extends ControlJuego
{
	@Override
	public void create () {
		TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);
		setScreen(new PantallaMenu(this), transicion);
	}
}
