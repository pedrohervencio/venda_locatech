package br.com.locacar.api.venda_locatech.service;

import br.com.locacar.api.venda_locatech.controller.exception.ControllerNotFoundException;
import br.com.locacar.api.venda_locatech.dto.ReservaDTO;
import br.com.locacar.api.venda_locatech.dto.VeiculoDTO;
import br.com.locacar.api.venda_locatech.dto.VendaDTO;
import br.com.locacar.api.venda_locatech.entities.Veiculo;
import br.com.locacar.api.venda_locatech.entities.Venda;
import br.com.locacar.api.venda_locatech.repository.VendaRepository;
import br.com.locacar.api.venda_locatech.valueobject.Status;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class VendaService {
    private final VendaRepository vendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public Page<VendaDTO> findAll(Pageable pageable) {
        Page<Venda> vendas = vendaRepository.findAll(pageable);
        return vendas.map(this::toDTO);
    }

    public VendaDTO findById(Long numero) {
        Venda venda = vendaRepository.findById(numero).
                orElseThrow(() -> new ControllerNotFoundException("Venda nâo encontrada"));
        return toDTO(venda);
    }

    public VendaDTO save(VendaDTO vendaDTO) {
        Venda venda = toEntity(vendaDTO);
        venda.setStatus(Status.VENDA_SOLICITADA);
        venda = vendaRepository.save(venda);
        return toDTO(venda);
    }

    public VendaDTO agendaVenda(Long numero, VendaDTO vendaDTO) {
        vendaDTO = update(numero, vendaDTO, Status.VENDA_AGENDADA);
        bloqueiaReserva(vendaDTO);
        return vendaDTO;
    }

    public VendaDTO efetivaVenda(Long numero, VendaDTO vendaDTO) {
        vendaDTO = update(numero, vendaDTO, Status.VENDA_CONCLUIDA);
        encerraPlano(vendaDTO);
        retiraLocacao(vendaDTO);
        return vendaDTO;
    }

    public VendaDTO retiraSeguro(Long numero, VendaDTO vendaDTO) {
        vendaDTO = update(numero, vendaDTO, Status.SEGURO_RETIRADO);
        return vendaDTO;
    }

    public VendaDTO update(Long numero, VendaDTO vendaDTO, Status status) {
        try {
            Venda venda = vendaRepository.getReferenceById(numero);
            venda.setDataAgendada(vendaDTO.dataAgendada());
            venda.setDiretor(vendaDTO.diretor());
            venda.setAdministrativo(vendaDTO.administrativo());
            venda.setVeiculo(vendaDTO.veiculo());
            venda.setStatus(status);
            venda = vendaRepository.save(venda);
            return toDTO(venda);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Venda não encontrada");
        }
    }

    public void delete(Long numero) {
        vendaRepository.deleteById(numero);
        return;
    }

    private VendaDTO toDTO(Venda venda) {
        return new VendaDTO(
                venda.getNumero(),
                venda.getDataAgendada(),
                venda.getDiretor(),
                venda.getAdministrativo(),
                venda.getVeiculo(),
                venda.getStatus()
                );
    }

    private Venda toEntity(VendaDTO vendaDTO) {
        return new Venda(
                vendaDTO.numero(),
                vendaDTO.dataAgendada(),
                vendaDTO.diretor(),
                vendaDTO.administrativo(),
                vendaDTO.veiculo(),
                vendaDTO.status()
        );
    }

    private void bloqueiaReserva(VendaDTO vendaDTO) {
        ReservaDTO reservaDTO = new ReservaDTO(
                vendaDTO.dataAgendada(),
                365,
                0,
                vendaDTO.veiculo()
        );

        RestTemplate restTemplate = new RestTemplate();
        try {
            URI uri = new URI("http://localhost:" + "3002" + "/reserva");
            ResponseEntity<ReservaDTO> result = restTemplate.postForEntity(uri, reservaDTO, ReservaDTO.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void encerraPlano(VendaDTO vendaDTO) {
        Veiculo veiculo = vendaDTO.veiculo();
        VeiculoDTO veiculoDTO = new VeiculoDTO(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAnoFabricacao(),
                veiculo.getAnoModelo(),
                veiculo.getCor()
        );

        RestTemplate restTemplate = new RestTemplate();
        try {
            URI uri = new URI("http://localhost:" + "3001" + "/veiculo/" + veiculoDTO.id());
            // ResponseEntity<VeiculoDTO> result = restTemplate.postForEntity(uri, veiculoDTO, VeiculoDTO.class);
            restTemplate.delete(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void retiraLocacao(VendaDTO vendaDTO) {
        Veiculo veiculo = vendaDTO.veiculo();
        VeiculoDTO veiculoDTO = new VeiculoDTO(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAnoFabricacao(),
                veiculo.getAnoModelo(),
                veiculo.getCor()
        );

        RestTemplate restTemplate = new RestTemplate();
        try {
            URI uri = new URI("http://localhost:" + "3003" + "/veiculo/" + veiculoDTO.id());
            // ResponseEntity<VeiculoDTO> result = restTemplate.postForEntity(uri, veiculoDTO, VeiculoDTO.class);
            restTemplate.delete(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
