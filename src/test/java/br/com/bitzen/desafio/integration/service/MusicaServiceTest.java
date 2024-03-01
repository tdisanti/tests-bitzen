package br.com.bitzen.desafio.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bitzen.desafio.enumeration.OrderByEnum;
import br.com.bitzen.desafio.integration.domain.Album;
import br.com.bitzen.desafio.integration.domain.Musica;
import br.com.bitzen.desafio.integration.repository.MusicaRepository;
import br.com.bitzen.desafio.service.MusicaService;

@ExtendWith(MockitoExtension.class)
public class MusicaServiceTest {

    @Mock
    private MusicaRepository musicaRepository;

    @InjectMocks
    private MusicaService musicaService;
    
    @Test
    public void testFindById() {
        // Mocking dos dados
        Album album = new Album();
        album.setId(1L);
        
        Musica musica = new Musica();
        musica.setId(1L);
        musica.setTitle("Musica 1");
        musica.setDurationSeconds(180);
        musica.setAlbum(album);
        musica.setTrackNumber(1);
        
        Optional<Musica> musicaOpt = Optional.of(musica);
       
        // Configurando o comportamento do mock
        when(musicaRepository.findById(1L)).thenReturn(musicaOpt);

        // Executando o método a ser testado
        Optional<Musica> result = musicaService.findById(1L);

        // Verificando o resultado
        assertEquals("Musica 1", result.get().getTitle());
        assertEquals(180, result.get().getDurationSeconds());
        assertEquals(1, result.get().getTrackNumber());

        // Verificando se o método do repositório foi chamado corretamente
        verify(musicaRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testFindAllByIdArtista() {
        // Mocking dos dados
        Album album = new Album();
        album.setId(1L);
        
        Musica musica1 = new Musica();
        musica1.setId(1L);
        musica1.setTitle("Musica 1");
        musica1.setDurationSeconds(180);
        musica1.setAlbum(album);
        musica1.setTrackNumber(1);
        
        Musica musica2 = new Musica();
        musica2.setId(2L);
        musica2.setTitle("Musica 2");
        musica2.setDurationSeconds(240);
        musica2.setAlbum(album);
        musica2.setTrackNumber(2);
        
        // Configurando o comportamento do mock
        when(musicaRepository.findAllByIdArtista(1L)).thenReturn(Arrays.asList(musica1, musica2));

        // Executando o método a ser testado
        List<Map<String, Object>> result = musicaService.listAllByIdArtista(1L);

        // Verificando o resultado
        assertEquals(2, result.size());
        assertEquals("Musica 1", result.get(0).get("title"));
        assertEquals(1, result.get(0).get("trackNumber"));
        assertEquals("Musica 2", result.get(1).get("title"));
        assertEquals(2, result.get(1).get("trackNumber"));

        // Verificando se o método do repositório foi chamado corretamente
        verify(musicaRepository, times(1)).findAllByIdArtista(1L);
    }

    @Test
    public void testFindAllByAlbumIdOrderByTrackNumber() {
        // Mocking dos dados
        Album album = new Album();
        album.setId(1L);
        
        Musica musica1 = new Musica();
        musica1.setId(1L);
        musica1.setTitle("Musica 1");
        musica1.setDurationSeconds(180);
        musica1.setAlbum(album);
        musica1.setTrackNumber(1);
        
        Musica musica2 = new Musica();
        musica2.setId(2L);
        musica2.setTitle("Musica 2");
        musica2.setDurationSeconds(240);
        musica2.setAlbum(album);
        musica2.setTrackNumber(2);
        
        // Configurando o comportamento do mock
        when(musicaRepository.findAllByAlbumIdOrderByTrackNumber(1L)).thenReturn(Arrays.asList(musica1, musica2));

        // Executando o método a ser testado
        List<Map<String, Object>> result = musicaService.listAllByIdAlbum(1L, OrderByEnum.TRACK_NUMBER.name());

        // Verificando o resultado
        assertEquals(2, result.size());
        assertEquals("Musica 1", result.get(0).get("title"));
        assertEquals(1, result.get(0).get("trackNumber"));
        assertEquals("Musica 2", result.get(1).get("title"));
        assertEquals(2, result.get(1).get("trackNumber"));

        // Verificando se o método do repositório foi chamado corretamente
        verify(musicaRepository, times(1)).findAllByAlbumIdOrderByTrackNumber(1L);
    }

}
