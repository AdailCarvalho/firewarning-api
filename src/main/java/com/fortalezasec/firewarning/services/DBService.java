package com.fortalezasec.firewarning.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Incidente;
import com.fortalezasec.firewarning.domain.NivelPerigo;
import com.fortalezasec.firewarning.domain.Status;
import com.fortalezasec.firewarning.domain.Tipo;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.repository.EmpresaRepository;
import com.fortalezasec.firewarning.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired 
  private IncidenteService incidenteService;

  @Autowired
  private BCryptPasswordEncoder bcrypt;

  public void instatiateTestDatabase() throws Exception {
    // Gerar Empresas
    Empresa empresa1 = new Empresa(null, "05.014.725/0001-52", "Shell", "contato@shell.com", "1111-2222",
        NivelPerigo.OK);
    Empresa empresa2 = new Empresa(null, "03.021.302/0001-34", "Texaco", "contato@texaco.com", "2222-3333",
        NivelPerigo.WARNING);
    Empresa empresa3 = new Empresa(null, "69.855.137/0001-24", "Petrobras", "contato@petrobras.com", "3333-4444",
        NivelPerigo.DANGER);
    empresaRepository.saveAll(Arrays.asList(empresa1, empresa2, empresa3));

    // Gerar Usuarios do Sistema
    Usuario usuario1 = new Usuario(null, "José Carlos", "josecarlos@etc.br", bcrypt.encode("senha"),
        new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO)), new EmpresaFavorita(null, empresa1.getCnpj(), "Tudo ok!"));
    Usuario usuario2 = new Usuario(null, "Patrícia Ramos", "pattyr@tal.br", bcrypt.encode("senha"),
        new HashSet<Tipo>(Arrays.asList(Tipo.POPULACAO, Tipo.ADMIN)),
        new EmpresaFavorita(null, empresa2.getCnpj(), "Vazamento de óleo..."));
    Usuario usuario3 = new Usuario(null, "Zé Cantor", "zecantor@texaco.com", bcrypt.encode("senha"),
        new HashSet<Tipo>(Arrays.asList(Tipo.ADMIN)),
        new EmpresaFavorita(null, empresa3.getCnpj(), "Foco de incendio!!!"));
    Usuario usuario4 = new Usuario(null, "SYS_SHELL", "sysshell@shell.com", bcrypt.encode("senha"),
        new HashSet<Tipo>(Arrays.asList(Tipo.SISTEMA)),
        new EmpresaFavorita(null, empresa1.getCnpj(), "Foco de incendio na plataforma"));

    usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2, usuario3, usuario4));

    // Gerar Incidentes
    LocalDateTime dataAb1 = LocalDateTime.of(2020, 12, 25, 19, 02, 37);
    LocalDateTime dataAb2 = LocalDateTime.of(2020, 12, 24, 13, 04, 29);
    LocalDateTime dataFe1 = LocalDateTime.of(2020, 12, 25, 14, 32, 58);
    Incidente incidente1 = new Incidente(null, empresa1.getCnpj(), NivelPerigo.DANGER, "Vazamento de óleo no setor 3",
        dataAb1, Status.ABERTO, null);
    Incidente incidente2 = new Incidente(null, empresa2.getCnpj(), NivelPerigo.WARNING, "Vazamento de combustível no setor 4",
        dataAb2, Status.RESOLVIDO, dataFe1);

    incidenteService.insert(incidente1);
    incidenteService.insert(incidente2);
  }

}
