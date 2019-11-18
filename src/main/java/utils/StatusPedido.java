package utils;

public class StatusPedido {
	public static String statusPedido(int status) {
		switch(status) {
		case 1:
			return "Criado";
		case 2:
			return "Pagamento Autorizado";
		case 3:
			return "Nota Fiscal Emitida";
		case 4:
			return "Em transporte";
		case 5:
			return "Entregue";
		default:
			return null;
		}
	}
}
