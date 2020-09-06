package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.FormaPagamentoInputModelAssembler;
import com.murillo.algafood.api.assembler.FormaPagamentoInputModelDisassembler;
import com.murillo.algafood.api.model.input.FormaPagamentoInputModel;
import com.murillo.algafood.api.model.output.FormaPagamentoOutputModel;
import com.murillo.algafood.api.openapi.controller.FormaPagamentoOpenApi;
import com.murillo.algafood.domain.model.FormaPagamento;
import com.murillo.algafood.domain.repository.FormaPagamentoRepository;
import com.murillo.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoOpenApi {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoInputModelAssembler formaPagamentoInputModelAssembler;

    @Autowired
    private FormaPagamentoInputModelDisassembler formaPagamentoInputModelDisassembler;

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoOutputModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacaoById(formaPagamentoId);
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        FormaPagamentoOutputModel formaPagamentoOutputModel = formaPagamentoInputModelAssembler.toOutputModel(formaPagamento);


//        return ResponseEntity.ok()
//                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)
//                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//                //.cacheControl(CacheControl.noCache()) //força validação condicional (eTags como se cache fosse sempre stale)
//                //.cacheControl(CacheControl.noStore()) //desabilita o cache
//                .body(formaPagamentoOutputModel);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag) // diferentemente do ShallowEtag o cabeçalho de ser inserido manualmente
                .body(formaPagamentoOutputModel);
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutputModel>> listar(ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        String eTag = "0";
        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
        List<FormaPagamentoOutputModel> formasPagamentoModel = formaPagamentoInputModelAssembler.toOutputModelCollection(formasPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag) // diferentemente do ShallowEtag o cabeçalho de ser inserido manualmente
                .body(formasPagamentoModel);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutputModel adicionar(@RequestBody @Valid FormaPagamentoInputModel formaPagamentoInput) {

        FormaPagamento novaFormaPagamento = formaPagamentoInputModelDisassembler.toDomainObject(formaPagamentoInput);
        novaFormaPagamento = cadastroFormaPagamento.salvar(novaFormaPagamento);
        FormaPagamentoOutputModel formaPagamentoOutputModel = formaPagamentoInputModelAssembler.toOutputModel(novaFormaPagamento);

        return formaPagamentoOutputModel;
    }

    @PutMapping(value = "/{formaPagamentoId}")
    public FormaPagamentoOutputModel atualizar(@PathVariable Long formaPagamentoId,
                                               @Valid @RequestBody FormaPagamentoInputModel formaPagamentoInput) {

        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        formaPagamentoInputModelDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);

        return formaPagamentoInputModelAssembler.toOutputModel(formaPagamentoAtual);
    }


    @DeleteMapping(value = "/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {

        cadastroFormaPagamento.excluir(formaPagamentoId);
    }


}
