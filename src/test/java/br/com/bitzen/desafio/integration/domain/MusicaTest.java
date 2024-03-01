package br.com.bitzen.desafio.integration.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MusicaTest {

    @Test
    void testMusica() {
        // Criar uma instância mock
    	Musica musicaMock = mock(Musica.class);

        // Definir comportamento do mock
    	when(musicaMock.getId()).thenReturn(1L);
        when(musicaMock.getTitle()).thenReturn("Musica 1");
        when(musicaMock.getDurationSeconds()).thenReturn(180);
        when(musicaMock.getTrackNumber()).thenReturn(1);

        // Testar os métodos
        assertEquals(1L, musicaMock.getId());
        assertEquals("Musica 1", musicaMock.getTitle());
        assertEquals(180, musicaMock.getDurationSeconds());
        assertEquals(1, musicaMock.getTrackNumber());
    }

}
