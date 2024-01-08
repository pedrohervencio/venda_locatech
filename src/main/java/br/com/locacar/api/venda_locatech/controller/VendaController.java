package br.com.locacar.api.venda_locatech.controller;

import br.com.locacar.api.venda_locatech.dto.VendaDTO;
import br.com.locacar.api.venda_locatech.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/aquisicao/venda")
@RestController
public class VendaController {
    private final VendaService vendaService;

    @Autowired
    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    /*
    Lista vendas
     */
    @GetMapping
    public ResponseEntity<Page<VendaDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "numero")Pageable pageable) {
        Page<VendaDTO> vendasDTO = vendaService.findAll(pageable);
        return ResponseEntity.ok(vendasDTO);
    }

    /*
    Lista determinada venda
     */
    @GetMapping("/{numero}")
    public ResponseEntity<VendaDTO> findById(@PathVariable Long numero) {
        VendaDTO vendaDTO = vendaService.findById(numero);
        return ResponseEntity.ok(vendaDTO);
    }

    /*
    Diretor Solicita venda
     */
    @PostMapping
    public ResponseEntity<VendaDTO> save(@RequestBody VendaDTO vendaDTO) {
        VendaDTO savedVendaDTO = vendaService.save(vendaDTO);
        return new ResponseEntity<>(savedVendaDTO, HttpStatus.CREATED);
    }

    /*
    Administrativo agenda venda
     */
    @PutMapping("/agendavenda/{numero}")
    public ResponseEntity<VendaDTO> agendaVenda(
            @PathVariable Long numero,
            @RequestBody VendaDTO vendaDTO) {
        VendaDTO updatedVendaDTO = vendaService.agendaVenda(numero, vendaDTO);
        return ResponseEntity.ok(updatedVendaDTO);
    }

    /*
    Administrativo efetiva venda
     */
    @PutMapping("/efetivavenda/{numero}")
    public ResponseEntity<VendaDTO> efetivaVenda(
            @PathVariable Long numero,
            @RequestBody VendaDTO vendaDTO) {
        VendaDTO updatedVendaDTO = vendaService.efetivaVenda(numero, vendaDTO);
        return ResponseEntity.ok(updatedVendaDTO);
    }
    @PutMapping("/retiraseguro/{numero}")
    public ResponseEntity<VendaDTO> retiraSeguro(
            @PathVariable Long numero,
            @RequestBody VendaDTO vendaDTO) {
        VendaDTO updatedVendaDTO = vendaService.retiraSeguro(numero, vendaDTO);
        return ResponseEntity.ok(updatedVendaDTO);
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> delete(@PathVariable Long numero) {
        vendaService.delete(numero);
        return ResponseEntity.noContent().build();
    }
    
}
