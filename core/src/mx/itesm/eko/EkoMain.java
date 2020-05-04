package mx.itesm.eko;

import mx.itesm.eko.musica.ControladorAudio;
import mx.itesm.eko.transiciones.TransicionPantalla;
import mx.itesm.eko.transiciones.efectoTransicion;


public class EkoMain extends ControlJuego
{

	public ControladorAudio mixer = new ControladorAudio();

	@Override
	public void create () {
		TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);
		setMixer(mixer);
		setScreen(new PantallaMenu(this), transicion);
	}
}
