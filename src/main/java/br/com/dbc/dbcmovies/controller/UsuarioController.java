package br.com.dbc.dbcmovies.controller;

import br.com.dbc.dbcmovies.dto.UsuarioAvaliacaoPersonalizadoDto;
import br.com.dbc.dbcmovies.dto.UsuarioCreateDto;
import br.com.dbc.dbcmovies.dto.UsuarioDto;
import br.com.dbc.dbcmovies.dto.UsuarioItemPersonalizadoDto;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @Operation(summary = "Listar Usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de usuários"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar(){
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @Operation(summary = "Pega o usuário pelo id dele", description = "Resgata o usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> pegar(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegar(idUsuario), HttpStatus.OK);
    }


    @Operation(summary = "Pega o login do usuário", description = "Resgata o usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi logado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/pegar-login")
    public ResponseEntity<UsuarioDto> pegarLogin(@RequestParam(name = "email") String email,@RequestParam(name = "senha") String senha) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegarLogin(email,senha), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar Usuário", description = "Atualiza o usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> editar(@PathVariable(name = "idUsuario") Integer idUsuario, @Valid @RequestBody UsuarioCreateDto usuario) throws RegraDeNegocioException {
        log.info("Editando o Usuário...");
        UsuarioDto user = usuarioService.editar(idUsuario, usuario);
        log.info("Usuário editado com sucesso!");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Deletar Usuário", description = "Deletar o usuário no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> remover(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.remover(idUsuario);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Pega a lista personaliza com os items do usuário", description = "Resgata a lista personalizada do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-item-personalizado")
    public ResponseEntity<List<UsuarioItemPersonalizadoDto>> listaPersonalizadaUsuarioItem(@RequestParam(required = false, name = "idUsuario") Integer idUsuario){
        return new ResponseEntity<>(usuarioService.listaPersonalizadaUsuarioItem(idUsuario),HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista personaliza com as avaliações do usuário", description = "Resgata a lista personalizada do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario-avaliacao-personalizado")
    public ResponseEntity<List<UsuarioAvaliacaoPersonalizadoDto>> listaPersonalizadaUsuarioAvaliacao(@RequestParam(required = false, name = "idUsuario") Integer idUsuario){
        return new ResponseEntity<>(usuarioService.listaPersonalizadaUsuarioAvaliacao(idUsuario),HttpStatus.OK);
    }
}
