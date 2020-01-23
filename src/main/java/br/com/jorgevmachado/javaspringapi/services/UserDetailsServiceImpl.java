//package br.com.jorgevmachado.javaspringapi.services;
import br.com.jorgevmachado.javaspringapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private ClienteRepository repository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Cliente cli = repo.findByEmail(email);
//		if (cli == null) {
//			throw new UsernameNotFoundException(email);
//		}
//		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
//	}
//}
