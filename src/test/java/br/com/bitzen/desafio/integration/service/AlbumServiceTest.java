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

import br.com.bitzen.desafio.integration.domain.Album;
import br.com.bitzen.desafio.integration.repository.AlbumRepository;
import br.com.bitzen.desafio.service.AlbumService;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;
    
    @Test
    public void testFindById() {
        // Mocking dos dados
        Album album = new Album();
        album.setId(1L);
        album.setTitle("title");
        album.setReleaseYear(2020);
        
        Optional<Album> albumOpt = Optional.of(album);
       
        // Configurando o comportamento do mock
        when(albumRepository.findById(1L)).thenReturn(albumOpt);

        // Executando o método a ser testado
        Optional<Album> result = albumService.findById(1L);

        // Verificando o resultado
        assertEquals("title", result.get().getTitle());
        assertEquals(2020, result.get().getReleaseYear());

        // Verificando se o método do repositório foi chamado corretamente
        verify(albumRepository, times(1)).findById(1L);
    }

}
