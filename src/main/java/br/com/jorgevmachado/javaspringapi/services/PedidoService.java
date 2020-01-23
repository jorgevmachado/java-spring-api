package br.com.jorgevmachado.javaspringapi.services;
import java.util.Date;
import java.util.Optional;

import br.com.jorgevmachado.javaspringapi.domain.Cliente;
import br.com.jorgevmachado.javaspringapi.domain.ItemPedido;
import br.com.jorgevmachado.javaspringapi.domain.PagamentoComBoleto;
import br.com.jorgevmachado.javaspringapi.domain.Pedido;
import br.com.jorgevmachado.javaspringapi.domain.enumerations.EstadoPagamento;
import br.com.jorgevmachado.javaspringapi.repositories.ItemPedidoRepository;
import br.com.jorgevmachado.javaspringapi.repositories.PagamentoRepository;
import br.com.jorgevmachado.javaspringapi.repositories.PedidoRepository;
import br.com.jorgevmachado.javaspringapi.services.esceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException(
				        "Objeto não encontrado! Id: " +
						 id +
						 ", Tipo: " +
						 Pedido.class.getName()
				)
		);
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
//		emailService.sendOrderConfirmationEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
//		System.out.println(obj);
		return obj;
	}
	
//	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
////		UserSS user = UserService.authenticated();
////		if (user == null) {
////			throw new AuthorizationException("Acesso negado");
////		}
//		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
//		Cliente cliente =  clienteService.find(user.getId());
//		return repo.findByCliente(cliente, pageRequest);
//	}
}
