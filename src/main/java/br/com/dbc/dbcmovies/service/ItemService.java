package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.dto.*;
import br.com.dbc.dbcmovies.entity.Filtro;
import br.com.dbc.dbcmovies.entity.ItemEntretenimentoEntity;
import br.com.dbc.dbcmovies.entity.TipoTemplate;
import br.com.dbc.dbcmovies.entity.TipoUsuario;
import br.com.dbc.dbcmovies.exceptions.BancoDeDadosException;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public ItemEntretenimentoDto createFilme(ItemEntretenimentoCreateDto itemEntretenimentoDto, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class) ;
      if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)){
          throw new RegraDeNegocioException("Usuario precisa ser administrador para cadastrar um filme.");
      }
        try{
            ItemEntretenimentoEntity itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimentoEntity.class);
            itemEntity = itemRepository.adicionar(itemEntity);
            ItemEntretenimentoDto dto = objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);
            emailService.sendEmailItemEntretenimento(dto, TipoTemplate.CREATE, usuarioDto);
            return objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Erro ao criar filme");
        }
    }

    public ItemEntretenimentoDto createSerie(ItemEntretenimentoCreateDto itemEntretenimentoDto, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class) ;
        try{

            ItemEntretenimentoEntity itemEntity = objectMapper.convertValue(itemEntretenimentoDto, ItemEntretenimentoEntity.class);
            itemEntity = itemRepository.adicionar(itemEntity);
            ItemEntretenimentoDto dto = objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);
            emailService.sendEmailItemEntretenimento(dto, TipoTemplate.CREATE, usuarioDto);
            return objectMapper.convertValue(itemEntity, ItemEntretenimentoDto.class);

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Erro ao cria série");
        }
    }

    public List<ItemEntretenimentoDto> list() throws RegraDeNegocioException {

        try {

            List<ItemEntretenimentoEntity> resultList = itemRepository.listar();
            resultList.forEach(item -> {
                double media = calcularAvaliacao(item.getId());
                if(media == 0){
                    item.setMediaAvaliacoes(null);
                }else {
                    item.setMediaAvaliacoes(media);
                }
            });

            return resultList.stream()
                    .map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class))
                    .toList();

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException(ex.getMessage());
        }
    }

    public List<ItemEntretenimentoDto> filter(Filtro filtro) throws RegraDeNegocioException {

        try {

            List<ItemEntretenimentoEntity> resultList = itemRepository.filtrarItens(filtro);
            resultList.forEach(item -> {
                double media = calcularAvaliacao(item.getId());
                if(media == 0){
                    item.setMediaAvaliacoes(null);
                }else {
                    item.setMediaAvaliacoes(media);
                }
            });

            return resultList.stream().map(item -> objectMapper.convertValue(item, ItemEntretenimentoDto.class)).toList();

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException(ex.getMessage());
        }
    }

    public ItemEntretenimentoDto updateFilme(Integer id, FilmeCreateDto filmeCreateDto, Integer idAdmin) throws RegraDeNegocioException{
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class) ;
        try {

            findById(id);
            ItemEntretenimentoEntity itemEntretenimentoEntity = objectMapper.convertValue(filmeCreateDto, ItemEntretenimentoEntity.class);
            ItemEntretenimentoDto dto = objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class);
            emailService.sendEmailItemEntretenimento(dto, TipoTemplate.UPDATE, usuarioDto);

            if(itemRepository.editar(id, itemEntretenimentoEntity)){
                return objectMapper.convertValue(itemRepository.pegar(id), ItemEntretenimentoDto.class);
            }else {
                throw new RegraDeNegocioException("Não foi possivel atualizar o filme.");
            }

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Não foi possivel atualizar o filme");
        }
    }

    public ItemEntretenimentoDto updateSerie(Integer id, SerieCreateDto serieCreateDto, Integer idAdmin) throws RegraDeNegocioException {
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class) ;
        try {

            findById(id);
            ItemEntretenimentoEntity itemEntretenimentoEntity = objectMapper.convertValue(serieCreateDto, ItemEntretenimentoEntity.class);
            ItemEntretenimentoDto dto = objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class);
            emailService.sendEmailItemEntretenimento(dto, TipoTemplate.UPDATE, usuarioDto);
            if(itemRepository.editar(id, itemEntretenimentoEntity)){
                return objectMapper.convertValue(itemRepository.pegar(id), ItemEntretenimentoDto.class);
            }else {
                throw new RegraDeNegocioException("Não foi possivel atualizar a série.");
            }

        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Não foi possivel atualizar a série");
        }
    }

    public void delete(Integer id, Integer idAdmin) throws RegraDeNegocioException{
        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioService.findById(idAdmin), UsuarioDto.class) ;
        if (usuarioDto.getTipoUsuario().equals(TipoUsuario.CLIENTE)){
            throw new RegraDeNegocioException("Usuario precisa ser administrador para cadastrar um filme.");
        }
        ItemEntretenimentoEntity itemEntretenimentoEntity = findById(id);
        ItemEntretenimentoDto itemDto = objectMapper.convertValue(itemEntretenimentoEntity, ItemEntretenimentoDto.class);
        emailService.sendEmailItemEntretenimento(itemDto, TipoTemplate.DELETE, usuarioDto);
        try {
            itemRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException(e.getMessage());
        }

    }

    public ItemEntretenimentoDto getItem(Integer id) throws RegraDeNegocioException {
        try{
            return objectMapper.convertValue(itemRepository.pegar(id), ItemEntretenimentoDto.class);
        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Id não encontrado.");
        }
    }

    public ItemEntretenimentoEntity findById(Integer id) throws RegraDeNegocioException {
        try{
            return itemRepository.pegar(id);
        }catch (BancoDeDadosException ex){
            throw new RegraDeNegocioException("Id não encontrado.");
        }
    }

    public Double calcularAvaliacao(Integer id){
        try{
            return itemRepository.calcularAvaliacoes(id);
        } catch (BancoDeDadosException ex) {
            System.out.println("ERRO: "+ex.getMessage());
        }
        return null;
    }
}
