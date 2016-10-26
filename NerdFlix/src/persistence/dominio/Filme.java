package persistence.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="filme")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Filme implements Serializable {
	@Id
	@SequenceGenerator(name="SEQ_FILME", initialValue=1,
	allocationSize=1, sequenceName="seq_filme")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILME")
	@Column(name="id_filme")
	private int id;
	
	private String titulo;
	private String sinopse;
	private String genero;
	private String resolucao;
	private int duracao;
	
	@ManyToMany(mappedBy="filmesFavoritos")
    private List<Filme> usuarios;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSinopse() {
		return sinopse;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getResolucao() {
		return resolucao;
	}
	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public List<Filme> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Filme> usuarios) {
		this.usuarios = usuarios;
	}
	
}
