package Bean;

import java.util.Objects;

public class ClienteBean extends UtenteBean {

	private String cartaDiCredito;

	public ClienteBean() {
		super();
	}

	public ClienteBean(String cartaDiCredito) {
		super();
		this.cartaDiCredito = cartaDiCredito;
	}

	public boolean checkCartaDiCredito(String cartaDiCredito) {
		// Reference: https://stackoverflow.com/a/9315696
		return cartaDiCredito.matches(
				"^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$");
	}

	@Override
	public String toString() {
		return super.toString() + " ClienteBean [cartaDiCredito=" + cartaDiCredito + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteBean other = (ClienteBean) obj;
		return Objects.equals(cartaDiCredito, other.cartaDiCredito);
	}

	public String getCartaDiCredito() {
		return cartaDiCredito;
	}

	public void setCartaDiCredito(String cartaDiCredito) {
		this.cartaDiCredito = cartaDiCredito;
	}

}
