package Bean;

import java.util.Objects;

public class FotoBean {
	
	private String ID;
	private String IDArticolo;
	private String path;
	
	public FotoBean(String iD, String iDArticolo, String path) {
		super();
		ID = iD;
		IDArticolo = iDArticolo;
		this.path = path;
	}
	
	public FotoBean() {}

	@Override
	public String toString() {
		return "FotoBeans [ID=" + ID + ", IDArticolo=" + IDArticolo + ", path=" + path + "]";
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
				&& Objects.equals(path, other.path);
	}

	public String getID() {
		return ID;
	}

	public String getIDArticolo() {
		return IDArticolo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
