package br.com.jorgevmachado.javaspringapi.services;

import br.com.jorgevmachado.javaspringapi.domain.Cliente;
import br.com.jorgevmachado.javaspringapi.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);

	void sendHtmlEmail(MimeMessage msg);

	void sendOrderConfirmationHtmlEmail(Pedido obj);
}
