package Bean;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

public class FotoBean {

	private String ID;
	private String IDArticolo;
	private Blob foto;

	public FotoBean(String iD, String iDArticolo, Blob foto) {
		super();
		ID = iD;
		IDArticolo = iDArticolo;
		this.foto = foto;
	}

	public FotoBean() {
	}

	@Override
	public String toString() {
		return "FotoBeans [ID=" + ID + ", IDArticolo=" + IDArticolo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FotoBean other = (FotoBean) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(IDArticolo, other.IDArticolo)
				&& Objects.equals(foto, other.foto);
	}

	public String getID() {
		return ID;
	}

	public String getIDArticolo() {
		return IDArticolo;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) throws SQLException {
		if (foto != null && foto.length() != 0)
			this.foto = foto;
	}

}
