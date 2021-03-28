package forum.config.config.security;

import forum.modelo.Usuario;
import forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Usuario> optionalUsuario = repository.findByEmail(userName);

        if (optionalUsuario.isPresent()) {
            return optionalUsuario.get();
        }

        throw new UsernameNotFoundException("Dados inválidos!");
    }
}