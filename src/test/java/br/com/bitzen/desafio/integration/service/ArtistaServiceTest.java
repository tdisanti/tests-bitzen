package br.com.bitzen.desafio.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bitzen.desafio.integration.domain.Artista;
import br.com.bitzen.desafio.integration.repository.ArtistaRepository;
import br.com.bitzen.desafio.service.ArtistaService;

@ExtendWith(MockitoExtension.class)
public class ArtistaServiceTest {

    @Mock
    private ArtistaRepository artistaRepository;

    @InjectMocks
    private ArtistaService artistaService;
    
    @Test
    public void testFindById() {
        // Mocking dos dados
        Artista artista = new Artista();
        artista.setId(1L);
        artista.setName("name");
        artista.setNationality("Nationality");
        artista.setWebsite("Website");
        
        Optional<Artista> artistaOpt = Optional.of(artista);
       
        // Configurando o comportamento do mock
        when(artistaRepository.findById(1L)).thenReturn(artistaOpt);

        // Executando o método a ser testado
        Optional<Artista> result = artistaService.findById(1L);

        // Verificando o resultado
        assertEquals("name", result.get().getName());
        assertEquals("Nationality", result.get().getNationality());
        assertEquals("Website", result.get().getWebsite());

        // Verificando se o método do repositório foi chamado corretamente
        verify(artistaRepository, times(1)).findById(1L);
    }

}
