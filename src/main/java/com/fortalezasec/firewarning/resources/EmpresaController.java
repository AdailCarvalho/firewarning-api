package com.fortalezasec.firewarning.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.fortalezasec.firewarning.CustomExceptions.TypeDoNotExistsException;
import com.fortalezasec.firewarning.Utils.FirewarningApplicationContext;
import com.fortalezasec.firewarning.domain.Empresa;
import com.fortalezasec.firewarning.domain.EmpresaFavorita;
import com.fortalezasec.firewarning.domain.Incidente;
import com.fortalezasec.firewarning.domain.Usuario;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaDTO;
import com.fortalezasec.firewarning.domain.DTOs.EmpresaFavoritaDTO;
import com.fortalezasec.firewarning.services.EmpresaService;
import com.fortalezasec.firewarning.services.IncidenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

  @Autowired
  private EmpresaService empresaService;

  @Autowired
  private IncidenteService incidenteService;

  @Autowired
  private FirewarningApplicationContext firewarningApplicationContext;

  @GetMapping
  @PreAuthorize("permitAll()")
  public ResponseEntity<List<EmpresaDTO>> getEmpresas() {
    List<EmpresaDTO> empresas = empresaService.getAll();

    return ResponseEntity.ok().body(empresas);
  }

  @GetMapping("/incidentes")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'SISTEMA')")
  public ResponseEntity<List<Incidente>> getReportsBy(@RequestParam(required = false) String tipo,
      @RequestParam(required = false) String valor) throws TypeDoNotExistsException {

    List<Incidente> incidentesFiltrados = new ArrayList<>();

    if (tipo == null && valor == null) {
      incidentesFiltrados = incidenteService.getAll();
      return ResponseEntity.ok().body(incidentesFiltrados);
    }
    
    incidentesFiltrados = incidenteService.filterBy(tipo, valor);

    return ResponseEntity.ok().body(incidentesFiltrados);
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/favorita")
  public ResponseEntity<EmpresaFavoritaDTO> getEmpresaFavorita() {
    try {
      Usuario usuarioLogado = firewarningApplicationContext.getUsuarioLogado();
      Empresa empresa = empresaService.getByCnpj(usuarioLogado.getEmpresaFavorita().getCnpj());
      EmpresaFavorita empresaFavorita = usuarioLogado.getEmpresaFavorita();
      EmpresaFavoritaDTO dto = new EmpresaFavoritaDTO(empresaFavorita, empresa);

      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      throw new EntityNotFoundException("Empresa favorita não existe com esse CNPJ");
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/{cnpj}")
  public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable String cnpj) {
    try {
      Empresa empresa = empresaService.getByCnpj(cnpj);
      EmpresaDTO dto = new EmpresaDTO(empresa);

      return ResponseEntity.ok().body(dto);
    } catch (Exception e) {
      throw new EntityNotFoundException("Empresa favorita não existe com esse CNPJ");
    }
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'SISTEMA')")
  @PostMapping("/{cnpj}")
  public ResponseEntity<Incidente> incidenteRegister(@PathVariable String cnpj, @RequestBody Incidente incidente) {

    incidente.setCnpjEmpresa(cnpj);
    try {
      if (empresaService.getByCnpj(cnpj) == null)
        throw new Exception("Empresa com CNPJ não cadastrada");

      Incidente entity = incidenteService.insert(incidente);

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand()
          .toUri();

      return ResponseEntity.created(uri).body(entity);
    } catch (Exception e) {
      throw new EntityNotFoundException("Empresa não encontrada com este CNPJ");
    }

  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'SISTEMA')")
  @PutMapping("/{id}")
  public ResponseEntity<Incidente> update(@PathVariable Long id, @RequestBody Incidente incidente) {
    Incidente entity = incidenteService.update(id, incidente);

    return ResponseEntity.ok().body(entity);
  }

  @PreAuthorize("hasAnyAuthority('ADMIN', 'SISTEMA')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    empresaService.delete(id);

    return ResponseEntity.ok().body("Empresa excluída com sucesso");
  }

}
