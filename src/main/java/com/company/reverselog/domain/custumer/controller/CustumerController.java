package com.company.reverselog.domain.custumer.controller;

import com.company.reverselog.domain.custumer.dto.CustomerDetailData;
import com.company.reverselog.domain.custumer.dto.CustumerDTO;
import com.company.reverselog.domain.custumer.dto.DadosListagemClientes;
import com.company.reverselog.domain.custumer.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
public class CustumerController {

    @Autowired
    private ClienteService clienteService;

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/ativo")
    public ResponseEntity<Page<DadosListagemClientes>> listCostumerActive(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable){
        Page<DadosListagemClientes> dadosListagemClientes = clienteService.fildAllActive(pageable);

        return  ResponseEntity.ok(dadosListagemClientes);
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DadosListagemClientes>> listAllCostumers(@PageableDefault(size = 5,sort = {"id"}) Pageable pageable){
        Page<DadosListagemClientes> dadosListagemClientes = clienteService.findAllCustumer(pageable);

        return ResponseEntity.ok(dadosListagemClientes);
    }

    @PostMapping("/post")
    @Transactional
    public ResponseEntity<CustumerDTO> registerCustomer(@RequestBody @Valid CustumerDTO data, UriComponentsBuilder builder){
        CustumerDTO custumer = clienteService.saveCustumer(data);

        var uri = builder.path("/clientes/{id}").buildAndExpand(custumer.id()).toUri();

        return ResponseEntity.created(uri).body(custumer);
    }

    @PutMapping("/update/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<CustomerDetailData> updateCustumer(@PathVariable Long id, @RequestBody @Valid CustomerDetailData data){
       CustomerDetailData custumer = clienteService.updateCustumer(id, data);

        return ResponseEntity.ok(custumer);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteCustumer(@PathVariable Long id){
        clienteService.deleteCustumer(id);

        return ResponseEntity.noContent().build();
    }


}
