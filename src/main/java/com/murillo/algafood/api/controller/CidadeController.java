package com.murillo.algafood.api.controller;

import com.murillo.algafood.api.assembler.CidadeInputModelAssembler;
import com.murillo.algafood.api.assembler.CidadeInputModelDisassembler;
import com.murillo.algafood.api.model.input.CidadeInputModel;
import com.murillo.algafood.api.model.output.CidadeOutputModel;
import com.murillo.algafood.domain.exception.EstadoNaoEncontradoException;
import com.murillo.algafood.domain.exception.NegocioException;
import com.murillo.algafood.domain.model.Cidade;
import com.murillo.algafood.domain.repository.CidadeRepository;
import com.murillo.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
@Api(tags = "Cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidade;
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeInputModelAssembler cidadeInputModelAssembler;

    @Autowired
    private CidadeInputModelDisassembler cidadeInputModelDisassembler;


    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeOutputModel> listar() {

        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeInputModelAssembler.toOutputModelCollection(cidades);
    }

    @ApiOperation("Busca a cidade por Id")
    @GetMapping("/{cidadeId}")
    public CidadeOutputModel buscar(@ApiParam(value = "Id de uma cidade", example = "1")
                                    @PathVariable("cidadeId") Long cidadeId) {

        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        return cidadeInputModelAssembler.toOutputModel(cidade);
    }

    @ApiOperation("Atualiza uma cidade por Id")
    @PutMapping("/{cidadeId}")
    public CidadeOutputModel atualizar(@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                                       @RequestBody @Valid CidadeInputModel cidadeInputModel,
                                       @ApiParam(value = "Id de uma cidade", example = "1")
                                       @PathVariable("cidadeId") Long cidadeId) {

        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
            cidadeInputModelDisassembler.copyToDomainObject(cidadeInputModel, cidadeAtual);
            cidadeAtual = cadastroCidade.salvar(cidadeAtual);
            return cidadeInputModelAssembler.toOutputModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Adiciona uma nova cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutputModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                                       @RequestBody @Valid CidadeInputModel cidadeInputModel) {

        try {
            Cidade cidade = cidadeInputModelDisassembler.toDomainObject(cidadeInputModel);
            cidade = cadastroCidade.salvar(cidade);
            return cidadeInputModelAssembler.toOutputModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Remove uma cidade pelo Id")
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam(value = "Id de uma cidade", example = "1")
                        @PathVariable("cidadeId") Long cidadeId) {

        cadastroCidade.excluir(cidadeId);
    }

}
