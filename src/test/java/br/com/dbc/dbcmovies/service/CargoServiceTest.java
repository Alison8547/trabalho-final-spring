package br.com.dbc.dbcmovies.service;

import br.com.dbc.dbcmovies.entity.CargoEntity;
import br.com.dbc.dbcmovies.exceptions.RegraDeNegocioException;
import br.com.dbc.dbcmovies.repository.CargoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer busca = 1;
        CargoEntity cargoEntity = getCargoEntity();
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(cargoEntity));

        // Ação (ACT)
        CargoEntity cargoServiceById = cargoService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(cargoServiceById);
        assertEquals(1, cargoServiceById.getIdCargo());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        cargoService.findById(busca);
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("ROLE_ADMIN");
        cargoEntity.setIdCargo(1);
        return cargoEntity;
    }
}
