package utils;

public class FormaPagamento {
	public static String formaPagamento(int forma) {
		switch(forma) {
		case 1:
			return "Cartão de Crédito";
		case 2:
			return "PayPal";
		case 3:
			return "Boleto";
		case 4:
			return "PagSeguro";
		default:
			return null;
		}
	}
}
