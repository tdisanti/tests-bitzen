package br.com.bitzen.desafio.integration.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AlbumTest {

    @Test
    void testAlbum() {
        // Criar uma instância mock
    	Album albumMock = mock(Album.class);
    	
        // Definir comportamento do mock
    	when(albumMock.getId()).thenReturn(1L);
        when(albumMock.getTitle()).thenReturn("title");
        when(albumMock.getReleaseYear()).thenReturn(2020);

        // Testar os métodos
        assertEquals(1L, albumMock.getId());
        assertEquals("title", albumMock.getTitle());
        assertEquals(2020, albumMock.getReleaseYear());
    }

}
