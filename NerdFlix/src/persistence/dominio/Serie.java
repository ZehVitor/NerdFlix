package persistence.dominio;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="serie")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Serie extends Filme{
	 
	private int episodio;
	private int temporada;
	 
	public int getEpisodio() {
		return episodio;
	}
	public void setEpisodio(int episodio) {
		this.episodio = episodio;
	}
	public int getTemporada() {
		return temporada;
	}
	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}
}
